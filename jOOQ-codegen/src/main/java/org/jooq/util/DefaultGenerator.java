/**
 * Copyright (c) 2009-2011, Lukas Eder, lukas.eder@gmail.com
 * All rights reserved.
 *
 * This software is licensed to you under the Apache License, Version 2.0
 * (the "License"); You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * . Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 *
 * . Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * . Neither the name "jOOQ" nor the names of its contributors may be
 *   used to endorse or promote products derived from this software without
 *   specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */

package org.jooq.util;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.lang.reflect.TypeVariable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jooq.Configuration;
import org.jooq.DataType;
import org.jooq.EnumType;
import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.MasterDataType;
import org.jooq.Parameter;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.SQLDialectNotSupportedException;
import org.jooq.SchemaMapping;
import org.jooq.Select;
import org.jooq.Sequence;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UDT;
import org.jooq.UDTField;
import org.jooq.UniqueKey;
import org.jooq.impl.AbstractKeys;
import org.jooq.impl.ArrayRecordImpl;
import org.jooq.impl.FieldTypeHelper;
import org.jooq.impl.JooqLogger;
import org.jooq.impl.PackageImpl;
import org.jooq.impl.ParameterImpl;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.SchemaImpl;
import org.jooq.impl.SequenceImpl;
import org.jooq.impl.StopWatch;
import org.jooq.impl.StoredFunctionImpl;
import org.jooq.impl.StoredProcedureImpl;
import org.jooq.impl.StringUtils;
import org.jooq.impl.TableFieldImpl;
import org.jooq.impl.TableImpl;
import org.jooq.impl.TableRecordImpl;
import org.jooq.impl.UDTFieldImpl;
import org.jooq.impl.UDTImpl;
import org.jooq.impl.UDTRecordImpl;
import org.jooq.impl.UpdatableRecordImpl;
import org.jooq.impl.UpdatableTableImpl;
import org.jooq.util.postgres.PostgresSingleUDTOutParameterProcedure;


/**
 * A default implementation for code generation. Replace this code with your own
 * logic, if you need your database schema represented in a different way.
 * <p>
 * Note that you can also extend this class to generate POJO's or other stuff
 * entirely independent of jOOQ.
 *
 * @author Lukas Eder
 */
public class DefaultGenerator implements Generator {

    private static final JooqLogger                 log                = JooqLogger.getLogger(DefaultGenerator.class);
    private static final Map<Class<?>, Set<String>> reservedColumns    = new HashMap<Class<?>, Set<String>>();

    private boolean                                 generateDeprecated = true;
    private boolean                                 generateRelations  = false;
    private GeneratorStrategy                       strategy;

    @Override
    public void setStrategy(GeneratorStrategy strategy) {
        this.strategy = strategy;
    }

    @Override
    public GeneratorStrategy getStrategy() {
        return strategy;
    }

    @Override
    public boolean generateDeprecated() {
        return generateDeprecated;
    }

    @Override
    public void setGenerateDeprecated(boolean generateDeprecated) {
        this.generateDeprecated = generateDeprecated;
    }

    @Override
    public boolean generateRelations() {
        return generateRelations;
    }

    @Override
    public void setGenerateRelations(boolean generateRelations) {
        this.generateRelations = generateRelations;
    }

    // ----

    @Override
    public void setTargetDirectory(String directory) {
        strategy.setTargetDirectory(directory);
    }

    @Override
    public String getTargetDirectory() {
        return strategy.getTargetDirectory();
    }

    @Override
    public void setTargetPackage(String packageName) {
        strategy.setTargetPackage(packageName);
    }

    @Override
    public String getTargetPackage() {
        return strategy.getTargetPackage();
    }

    // ----

    @Override
	public void generate(Database database) throws SQLException, IOException {
        StopWatch watch = new StopWatch();

        log.info("Database parameters");
	    log.info("----------------------------------------------------------");
	    log.info("  dialect", database.getDialect());
	    log.info("  schema", database.getSchemaName());
	    log.info("  target dir", getTargetDirectory());
	    log.info("  target package", getTargetPackage());
	    log.info("----------------------------------------------------------");

		String targetPackage = getTargetPackage();
        File targetPackageDir = new File(getTargetDirectory() + File.separator + targetPackage.replace('.', File.separatorChar));

		// ----------------------------------------------------------------------
		// XXX Initialising
		// ----------------------------------------------------------------------
		log.info("Emptying", targetPackageDir.getCanonicalPath());
		empty(targetPackageDir);

        // ----------------------------------------------------------------------
        // XXX Generating schemas
        // ----------------------------------------------------------------------
		log.info("Generating classes in", targetPackageDir.getCanonicalPath());
		SchemaDefinition schema = database.getSchema();
		GenerationWriter outS = null;
		GenerationWriter outF = null;

		if (!schema.isDefaultSchema()) {
			targetPackageDir.mkdirs();

			// Generating the schema
			// -----------------------------------------------------------------
			log.info("Generating schema", strategy.getFileName(schema));

			outS = new GenerationWriter(new PrintWriter(new File(targetPackageDir, strategy.getFileName(schema))));
			printHeader(outS, targetPackage);
			printClassJavadoc(outS, schema);

			outS.print("public class ");
			outS.print(strategy.getJavaClassName(schema));
			outS.print(" extends ");
			outS.print(SchemaImpl.class);
			outS.println(" {");
			outS.printSerial();
			outS.println();
			outS.println("\t/**");
			outS.println("\t * The singleton instance of " + schema.getName());
			outS.println("\t */");
			outS.println("\tpublic static final " + strategy.getJavaClassName(schema) + " " + strategy.getJavaIdentifierUC(schema) + " = new " + strategy.getJavaClassName(schema) + "();");

			outS.println();
			printNoFurtherInstancesAllowedJavadoc(outS);
			outS.println("\tprivate " + strategy.getJavaClassName(schema) + "() {");
			outS.println("\t\tsuper(\"" + schema.getName() + "\");");
			outS.println("\t}");

			outS.printInitialisationStatementsPlaceholder();

			// Generating the factory
            // -----------------------------------------------------------------
            log.info("Generating factory", strategy.getFileName(schema, "Factory"));

            outF = new GenerationWriter(new PrintWriter(new File(targetPackageDir, strategy.getFileName(schema, "Factory"))));
            printHeader(outF, targetPackage);
            printClassJavadoc(outF, schema);

            outF.print("public class ");
            outF.print(strategy.getJavaClassName(schema, "Factory"));
            outF.print(" extends ");
            outF.print(database.getDialect().getFactory());
            outF.println(" {");
            outF.printSerial();

            outF.println();
            outF.println("\t/**");
            outF.println("\t * Create a factory with a connection");
            outF.println("\t *");
            outF.println("\t * @param connection The connection to use with objects created from this factory");
            outF.println("\t */");
            outF.print("\tpublic ");
            outF.print(strategy.getJavaClassName(schema, "Factory"));
            outF.print("(");
            outF.print(Connection.class);
            outF.println(" connection) {");
            outF.println("\t\tsuper(connection);");
            outF.println("\t}");

            outF.println();
            outF.println("\t/**");
            outF.println("\t * Create a factory with a connection and a schema mapping");
            outF.println("\t *");
            outF.println("\t * @param connection The connection to use with objects created from this factory");
            outF.println("\t * @param mapping The schema mapping to use with objects created from this factory");
            outF.println("\t */");
            outF.print("\tpublic ");
            outF.print(strategy.getJavaClassName(schema, "Factory"));
            outF.print("(");
            outF.print(Connection.class);
            outF.print(" connection, ");
            outF.print(SchemaMapping.class);
            outF.println(" mapping) {");
            outF.println("\t\tsuper(connection, mapping);");
            outF.println("\t}");

            watch.splitInfo("Schema generated");
		}

        // ----------------------------------------------------------------------
        // XXX Generating sequences
        // ----------------------------------------------------------------------
		if (database.getSequences().size() > 0) {
		    log.info("Generating sequences", targetPackageDir.getCanonicalPath());
		    targetPackageDir.mkdirs();

            GenerationWriter out = new GenerationWriter(new PrintWriter(new File(targetPackageDir, "Sequences.java")));
            printHeader(out, targetPackage);
            printClassJavadoc(out, "Convenience access to all sequences in " + schema.getName());
            out.println("public final class Sequences {");

            for (SequenceDefinition sequence : database.getSequences()) {
                out.println();
                out.println("\t/**");
                out.println("\t * The sequence " + sequence.getQualifiedName());
                out.println("\t */");

                out.print("\tpublic static final ");
                out.print(Sequence.class);
                out.print(" ");
                out.print(strategy.getJavaIdentifierUC(sequence));
                out.print(" = new ");
                out.print(SequenceImpl.class);
                out.print("(\"");
                out.print(sequence.getName());
                out.print("\"");

                if (!schema.isDefaultSchema()) {
                    out.print(", ");
                    out.print(strategy.getFullJavaIdentifierUC(schema));
                } else {
                    out.print(", null");
                }

                out.println(");");
            }

            printPrivateConstructor(out, "Sequences");
            out.println("}");
            out.close();

            registerInSchema(outS, database.getSequences(), Sequence.class, false);
            watch.splitInfo("Sequences generated");
		}

        // ---------------------------------------------------------------------
        // XXX Generating master data tables
        // ---------------------------------------------------------------------
        File targetMasterDataTablePackageDir = new File(targetPackageDir, "enums");

        if (database.getMasterDataTables().size() > 0) {
            log.info("Generating master data", targetMasterDataTablePackageDir.getCanonicalPath());

            for (MasterDataTableDefinition table : database.getMasterDataTables()) {
                try {
                    targetMasterDataTablePackageDir.mkdirs();

                    log.info("Generating table", strategy.getFileName(table));

                    GenerationWriter out = new GenerationWriter(new PrintWriter(new File(targetMasterDataTablePackageDir, strategy.getFileName(table))));
                    printHeader(out, targetPackage + ".enums");
                    printClassJavadoc(out, table);

                    ColumnDefinition pk = table.getPrimaryKeyColumn();
                    ColumnDefinition l = table.getLiteralColumn();
                    ColumnDefinition d = table.getDescriptionColumn();

                    Result<Record> data = table.getData();

                    out.print("public enum ");
                    out.print(strategy.getJavaClassName(table));
                    out.print(" implements ");
                    out.print(MasterDataType.class);
                    out.print("<");
                    out.print(data.getField(pk.getName()).getType());
                    out.println("> {");

                    Set<ColumnDefinition> columns =
                        new LinkedHashSet<ColumnDefinition>(Arrays.asList(pk, l, d));


                    for (Record record : data) {
                        String literal = record.getValueAsString(l.getName());
                        String description = record.getValueAsString(d.getName());

                        if (!StringUtils.isEmpty(description)) {
                            out.println();
                            out.println("\t/**");
                            out.println("\t * " + description);
                            out.println("\t */");
                        }

                        out.print("\t");
                        out.print(GenerationUtil.convertToJavaIdentifier(literal));
                        out.print("(");

                        String separator = "";
                        for (ColumnDefinition column : columns) {
                            out.print(separator);
                            out.printNewJavaObject(record.getValue(column.getName()));

                            separator = ", ";
                        }

                        out.println("),");
                    }

                    out.println("\t;");
                    out.println();

                    // Fields
                    for (ColumnDefinition column : columns) {
                        out.print("\tprivate final ");
                        out.print(data.getField(column.getName()).getType());
                        out.print(" ");
                        out.println(strategy.getJavaClassNameLC(column) + ";");
                    }

                    // Constructor
                    out.println();
                    out.print("\tprivate " + strategy.getJavaClassName(table) + "(");

                    String separator = "";
                    for (ColumnDefinition column : columns) {
                        out.print(separator);
                        out.print(data.getField(column.getName()).getType());
                        out.print(" ");
                        out.print(strategy.getJavaClassNameLC(column));

                        separator = ", ";
                    }

                    out.println(") {");
                    for (ColumnDefinition column : columns) {
                        out.print("\t\tthis.");
                        out.print(strategy.getJavaClassNameLC(column));
                        out.print(" = ");
                        out.print(strategy.getJavaClassNameLC(column));
                        out.println(";");
                    }
                    out.println("\t}");

                    // Implementation methods
                    out.println();
                    printOverride(out);
                    out.print("\tpublic ");
                    out.print(data.getField(pk.getName()).getType());
                    out.println(" getPrimaryKey() {");
                    out.println("\t\treturn " + strategy.getJavaClassNameLC(pk) + ";");
                    out.println("\t}");

                    // Getters
                    for (ColumnDefinition column : columns) {
                        printFieldJavaDoc(out, "", column);
                        out.print("\tpublic final ");
                        out.print(data.getField(column.getName()).getType());
                        out.print(" get");
                        out.print(strategy.getJavaClassName(column));
                        out.println("() {");
                        out.print("\t\treturn ");
                        out.print(strategy.getJavaClassNameLC(column));
                        out.println(";");
                        out.println("\t}");
                    }

                    out.println("}");
                    out.close();
                } catch (Exception e) {
                    log.error("Exception while generating master data table " + table, e);
                }
            }

            watch.splitInfo("Master data generated");
        }

		// ----------------------------------------------------------------------
		// XXX Generating tables
		// ----------------------------------------------------------------------
		File targetTablePackageDir = new File(targetPackageDir, "tables");
		if (database.getTables().size() > 0) {
		    log.info("Generating tables", targetTablePackageDir.getCanonicalPath());

    		for (TableDefinition table : database.getTables()) {
    		    try {
        			targetTablePackageDir.mkdirs();

        			log.info("Generating table", strategy.getFileName(table));

        			GenerationWriter out = new GenerationWriter(new PrintWriter(new File(targetTablePackageDir, strategy.getFileName(table))));
        			printHeader(out, targetPackage + ".tables");
        			printClassJavadoc(out, table);

        			Class<?> baseClass;
        			if (generateRelations() && table.getMainUniqueKey() != null) {
        				baseClass = UpdatableTableImpl.class;
        			} else {
        				baseClass = TableImpl.class;
        			}

        			out.print("public class ");
        			out.print(strategy.getJavaClassName(table));
        			out.print(" extends ");
        			out.print(baseClass);
        			out.print("<");
        			out.print(strategy.getFullJavaClassName(table, "Record"));
        			out.println("> {");
        			out.printSerial();
        			printSingletonInstance(table, out);
        			printRecordTypeMethod(table, out);

        			for (ColumnDefinition column : table.getColumns()) {
        				printTableColumn(out, column, table);
        			}

        			out.println();
        			printNoFurtherInstancesAllowedJavadoc(out);
        			out.println("\tprivate " + strategy.getJavaClassName(table) + "() {");

        			if (!schema.isDefaultSchema()) {
        			    out.println("\t\tsuper(\"" + table.getName() + "\", " + strategy.getFullJavaIdentifierUC(schema) + ");");
        			} else {
        			    out.println("\t\tsuper(\"" + table.getName() + "\");");
        			}

        			out.println("\t}");

        			// Add primary / unique / foreign key information
                    if (generateRelations()) {
                        ColumnDefinition identity = table.getIdentity();

                        // The identity column
                        if (identity != null) {
                            out.println();

                            out.println("\t@Override");
                            out.print("\tpublic ");
                            out.print(Identity.class);
                            out.print("<");
                            out.print(strategy.getFullJavaClassName(table, "Record"));
                            out.print(", ");
                            out.print(getJavaType(table.getIdentity().getType()));
                            out.println("> getIdentity() {");

                            out.print("\t\treturn ");
                            out.print(strategy.getTargetPackage());
                            out.print(".Keys.IDENTITY_");
                            out.print(strategy.getJavaIdentifier(identity.getContainer()));
                            out.println(";");

                            out.println("\t}");
                        }

                        UniqueKeyDefinition mainKey = table.getMainUniqueKey();

                        // The primary / main unique key
                        if (mainKey != null) {
                            out.println();

                            out.println("\t@Override");
                            out.print("\tpublic ");
                            out.print(UniqueKey.class);
                            out.print("<");
                            out.print(strategy.getFullJavaClassName(table, "Record"));
                            out.println("> getMainKey() {");

                            out.print("\t\treturn ");
                            out.print(strategy.getTargetPackage());
                            out.print(".Keys.");
                            out.print(strategy.getJavaIdentifier(mainKey));
                            out.println(";");

                            out.println("\t}");
                        }

                        // The remaining unique keys
                        List<UniqueKeyDefinition> uniqueKeys = table.getUniqueKeys();
                        if (uniqueKeys.size() > 0) {
                            out.println();
                            out.println("\t@Override");
                            out.println("\t@SuppressWarnings(\"unchecked\")");

                            out.print("\tpublic ");
                            out.print(List.class);
                            out.print("<");
                            out.print(UniqueKey.class);
                            out.print("<");
                            out.print(strategy.getFullJavaClassName(table, "Record"));
                            out.println(">> getKeys() {");

                            out.print("\t\treturn ");
                            out.print(Arrays.class);
                            out.print(".<");
                            out.print(UniqueKey.class);
                            out.print("<");
                            out.print(strategy.getFullJavaClassName(table, "Record"));
                            out.print(">>asList(");

                            String separator = "";
                            for (UniqueKeyDefinition uniqueKey : uniqueKeys) {
                                out.print(separator);
                                out.print(strategy.getTargetPackage());
                                out.print(".Keys.");
                                out.print(strategy.getJavaIdentifier(uniqueKey));

                                separator = ", ";
                            }

                            out.println(");");
                            out.println("\t}");
                        }

                        // Foreign keys
                        List<ForeignKeyDefinition> foreignKeys = table.getForeignKeys();
                        if (foreignKeys.size() > 0) {
                            out.println();
                            out.println("\t@Override");
                            out.println("\t@SuppressWarnings(\"unchecked\")");

                            out.print("\tpublic ");
                            out.print(List.class);
                            out.print("<");
                            out.print(ForeignKey.class);
                            out.print("<");
                            out.print(strategy.getFullJavaClassName(table, "Record"));
                            out.println(", ?>> getReferences() {");

                            out.print("\t\treturn ");
                            out.print(Arrays.class);
                            out.print(".<");
                            out.print(ForeignKey.class);
                            out.print("<");
                            out.print(strategy.getFullJavaClassName(table, "Record"));
                            out.print(", ?>>asList(");

                            String separator = "";
                            for (ForeignKeyDefinition foreignKey : foreignKeys) {
                                TableDefinition referencedTable = foreignKey.getReferencedTable();

                                // Skip master data foreign keys
                                if (referencedTable instanceof MasterDataTableDefinition) {
                                    continue;
                                }

                                out.print(separator);
                                out.print(strategy.getTargetPackage());
                                out.print(".Keys.");
                                out.print(strategy.getJavaIdentifier(foreignKey));

                                separator = ", ";
                            }

                            out.println(");");
                            out.println("\t}");
                        }
                    }

        			out.printStaticInitialisationStatementsPlaceholder();
        			out.println("}");
        			out.close();
    		    } catch (Exception e) {
    		        log.error("Error while generating table " + table, e);
    		    }
    		}

    		registerInSchema(outS, database.getTables(), Table.class, true);
    		watch.splitInfo("Tables generated");
		}

        // ----------------------------------------------------------------------
        // XXX Generating relations
        // ----------------------------------------------------------------------
		if (generateRelations() && database.getTables().size() > 0) {
		    log.info("Generating Keys", targetTablePackageDir.getCanonicalPath());
            targetPackageDir.mkdirs();

            GenerationWriter out = new GenerationWriter(new PrintWriter(new File(targetPackageDir, "Keys.java")));
            printHeader(out, targetPackage);
            printClassJavadoc(out, "A class modelling foreign key relationships between tables of the " + schema.getName() + " schema");

            out.suppressWarnings("unchecked");
            out.print("public class Keys extends ");
            out.print(AbstractKeys.class);
            out.println(" {");
            out.println();
            out.println("\t// IDENTITY definitions");

            for (TableDefinition table : database.getTables()) {
                try {
                    ColumnDefinition identity = table.getIdentity();

                    if (identity != null) {
                        out.print("\tpublic static final ");
                        out.print(Identity.class);
                        out.print("<");
                        out.print(strategy.getFullJavaClassName(identity.getContainer(), "Record"));
                        out.print(", ");
                        out.print(getJavaType(identity.getType()));
                        out.print("> IDENTITY_");
                        out.print(strategy.getJavaIdentifier(identity.getContainer()));
                        out.print(" = createIdentity(");
                        out.print(strategy.getFullJavaIdentifierUC(identity.getContainer()));
                        out.print(", ");
                        out.print(strategy.getFullJavaIdentifierUC(identity));
                        out.println(");");
                    }
                }
                catch (Exception e) {
                    log.error("Error while generating table " + table, e);
                }
            }

            // Unique keys
            out.println();
            out.println("\t// UNIQUE and PRIMARY KEY definitions");

            for (TableDefinition table : database.getTables()) {
		        try {
                    List<UniqueKeyDefinition> uniqueKeys = table.getUniqueKeys();

                    if (uniqueKeys.size() > 0) {
                        for (UniqueKeyDefinition uniqueKey : uniqueKeys) {
                            out.print("\tpublic static final ");
                            out.print(UniqueKey.class);
                            out.print("<");
                            out.print(strategy.getFullJavaClassName(uniqueKey.getTable(), "Record"));
                            out.print("> ");
                            out.print(strategy.getJavaIdentifier(uniqueKey));
                            out.print(" = createUniqueKey(");
                            out.print(strategy.getFullJavaIdentifierUC(uniqueKey.getTable()));
                            out.print(", ");

                            String separator = "";
                            for (ColumnDefinition column : uniqueKey.getKeyColumns()) {
                                out.print(separator);
                                out.print(strategy.getFullJavaIdentifierUC(column));
                                separator = ", ";
                            }

                            out.println(");");
                        }
                    }
		        }
		        catch (Exception e) {
		            log.error("Error while generating table " + table, e);
		        }
		    }

            // Foreign keys
            out.println();
            out.println("\t// FOREIGN KEY definitions");

            for (TableDefinition table : database.getTables()) {
                try {
                    List<ForeignKeyDefinition> foreignKeys = table.getForeignKeys();

                    if (foreignKeys.size() > 0) {
                        for (ForeignKeyDefinition foreignKey : foreignKeys) {

                            // Skip master data foreign keys
                            if (foreignKey.getReferencedTable() instanceof MasterDataTableDefinition) {
                                continue;
                            }

                            out.print("\tpublic static final ");
                            out.print(ForeignKey.class);
                            out.print("<");
                            out.print(strategy.getFullJavaClassName(foreignKey.getKeyTable(), "Record"));
                            out.print(", ");
                            out.print(strategy.getFullJavaClassName(foreignKey.getReferencedTable(), "Record"));
                            out.print("> ");
                            out.print(strategy.getJavaIdentifier(foreignKey));
                            out.print(" = createForeignKey(");
                            out.print(strategy.getJavaIdentifier(foreignKey.getReferencedKey()));
                            out.print(", ");
                            out.print(strategy.getFullJavaIdentifierUC(foreignKey.getKeyTable()));
                            out.print(", ");

                            String separator = "";
                            for (ColumnDefinition column : foreignKey.getKeyColumns()) {
                                out.print(separator);
                                out.print(strategy.getFullJavaIdentifierUC(column));
                                separator = ", ";
                            }

                            out.println(");");
                        }
                    }
                }
                catch (Exception e) {
                    log.error("Error while generating reference " + table, e);
                }
            }

            printPrivateConstructor(out, "Keys");
            out.println("}");
            out.close();

		    watch.splitInfo("Keys generated");
		}

        // ----------------------------------------------------------------------
        // XXX Generating table records
        // ----------------------------------------------------------------------
		File targetTableRecordPackageDir = new File(new File(targetPackageDir, "tables"), "records");
		if  (database.getTables().size() > 0) {
		    log.info("Generating records", targetTableRecordPackageDir.getCanonicalPath());

    		for (TableDefinition table : database.getTables()) {
    		    try {
        			targetTableRecordPackageDir.mkdirs();

        			log.info("Generating record", strategy.getFileName(table, "Record"));

        			GenerationWriter out = new GenerationWriter(new PrintWriter(new File(targetTableRecordPackageDir, strategy.getFileName(table, "Record"))));
        			printHeader(out, targetPackage + ".tables.records");
        			printClassJavadoc(out, table);

        			Class<?> baseClass;

        			Set<String> reserved;
        			if (generateRelations() && table.getMainUniqueKey() != null) {
        				baseClass = UpdatableRecordImpl.class;
                        reserved = reservedColumns(UpdatableRecordImpl.class);
        			} else {
        				baseClass = TableRecordImpl.class;
        				reserved = reservedColumns(TableRecordImpl.class);
        			}

        			out.print("public class ");
        			out.print(strategy.getJavaClassName(table, "Record"));
        			out.print(" extends ");
        			out.print(baseClass);
        			out.print("<");
        			out.print(strategy.getFullJavaClassName(table, "Record"));
        			out.println("> {");
        			out.printSerial();

        			for (ColumnDefinition column : table.getColumns()) {
        				printGetterAndSetter(out, column, table, reserved);
        			}

        			out.println();
        			out.println("\t/**");
        			out.println("\t * Create a detached " + strategy.getJavaClassName(table, "Record"));
        			out.println("\t */");
                    out.println("\tpublic " + strategy.getJavaClassName(table, "Record") + "() {");
                    out.print("\t\tsuper(");
                    out.print(strategy.getFullJavaIdentifierUC(table));
                    out.println(");");
                    out.println("\t}");

        			if (TableRecordImpl.class != baseClass || generateDeprecated()) {
        	            out.println();

        	            out.println("\t/**");
        	            out.println("\t * Create an attached " + strategy.getJavaClassName(table, "Record"));
        	            if (TableRecordImpl.class == baseClass) {
        	                out.println("\t * @deprecated - (#363) use the other constructor instead");
        	            }

        	            out.println("\t */");
                        if (TableRecordImpl.class == baseClass) {
                            out.println("\t@Deprecated");
                        }

    	                out.print("\tpublic ");
    	                out.print(strategy.getJavaClassName(table, "Record"));
    	                out.print("(");
    	                out.print(Configuration.class);
    	                out.println(" configuration) {");

    	                out.print("\t\tsuper(");
    	                out.print(strategy.getFullJavaIdentifierUC(table));
    	                out.println(", configuration);");

    	                out.println("\t}");
        			}

        			out.println("}");
        			out.close();
    		    } catch (Exception e) {
    		        log.error("Error while generating table record " + table, e);
    		    }
    		}

    		watch.splitInfo("Table records generated");
		}

        // ----------------------------------------------------------------------
        // XXX Generating UDTs
        // ----------------------------------------------------------------------

		File targetUDTPackageDir = new File(targetPackageDir, "udt");
        if (database.getUDTs().size() > 0) {
            log.info("Generating UDTs", targetUDTPackageDir.getCanonicalPath());

            for (UDTDefinition udt : database.getUDTs()) {
                try {
                    targetUDTPackageDir.mkdirs();

                    log.info("Generating UDT ", strategy.getFileName(udt));

                    GenerationWriter out = new GenerationWriter(new PrintWriter(new File(targetUDTPackageDir, strategy.getFileName(udt))));
                    printHeader(out, targetPackage + ".udt");
                    printClassJavadoc(out, udt);

                    out.print("public class ");
                    out.print(strategy.getJavaClassName(udt));
                    out.print(" extends ");
                    out.print(UDTImpl.class);
                    out.print("<");
                    out.print(strategy.getFullJavaClassName(udt, "Record"));
                    out.println("> {");
                    out.printSerial();

                    printSingletonInstance(udt, out);
                    printRecordTypeMethod(udt, out);

                    for (AttributeDefinition attribute : udt.getAttributes()) {
                        printUDTColumn(out, attribute, udt);
                    }

                    out.println();
                    printNoFurtherInstancesAllowedJavadoc(out);
                    out.println("\tprivate " + strategy.getJavaClassName(udt) + "() {");

                    if (!schema.isDefaultSchema()) {
                        out.println("\t\tsuper(\"" + udt.getName() + "\", " + strategy.getFullJavaIdentifierUC(schema) + ");");
                    } else {
                        out.println("\t\tsuper(\"" + udt.getName() + "\");");
                    }

                    out.println("\t}");

                    out.println("}");
                    out.close();

                    if (outS != null) {
                        outS.printInitialisationStatement(
                            "addMapping(\"" + schema.getName() + "." + udt.getName() + "\", " +
                            strategy.getFullJavaClassName(udt, "Record") + ".class);");
                    }
                } catch (Exception e) {
                    log.error("Error while generating udt " + udt, e);
                }
            }

            registerInSchema(outS, database.getUDTs(), UDT.class, true);
            watch.splitInfo("UDTs generated");
        }

        // ----------------------------------------------------------------------
        // XXX Generating UDT record classes
        // ----------------------------------------------------------------------
        File targetRecordUDTPackageDir = new File(new File(targetPackageDir, "udt"), "records");
        if (database.getUDTs().size() > 0) {
            log.info("Generating UDT records", targetRecordUDTPackageDir.getCanonicalPath());

            for (UDTDefinition udt : database.getUDTs()) {
                try {
                    targetRecordUDTPackageDir.mkdirs();

                    log.info("Generating UDT record", strategy.getFileName(udt, "Record"));

                    GenerationWriter out = new GenerationWriter(new PrintWriter(new File(targetRecordUDTPackageDir, strategy.getFileName(udt, "Record"))));
                    printHeader(out, targetPackage + ".udt.records");
                    printClassJavadoc(out, udt);

                    out.print("public class ");
                    out.print(strategy.getJavaClassName(udt, "Record"));
                    out.print(" extends ");
                    out.print(UDTRecordImpl.class);
                    out.print("<");
                    out.print(strategy.getFullJavaClassName(udt, "Record"));
                    out.println("> {");

                    Set<String> reserved = reservedColumns(UDTRecordImpl.class);
                    out.printSerial();
                    out.println();

                    for (AttributeDefinition attribute : udt.getAttributes()) {
                        printGetterAndSetter(out, attribute, udt, reserved);
                    }

                    out.println();
                    out.println("\tpublic " + strategy.getJavaClassName(udt, "Record") + "() {");

                    out.print("\t\tsuper(");
                    out.print(strategy.getFullJavaIdentifierUC(udt));
                    out.println(");");

                    out.println("\t}");
                    out.println("}");
                    out.close();
                } catch (Exception e) {
                    log.error("Error while generating UDT record " + udt, e);
                }
            }

            watch.splitInfo("UDT records generated");
        }

        // ----------------------------------------------------------------------
        // XXX Generating ARRAY record classes
        // ----------------------------------------------------------------------
        File targetRecordARRAYPackageDir = new File(new File(targetPackageDir, "udt"), "records");
        if (database.getArrays().size() > 0) {
            log.info("Generating ARRAYs", targetRecordARRAYPackageDir.getCanonicalPath());

            for (ArrayDefinition array : database.getArrays()) {
                try {
                    targetRecordARRAYPackageDir.mkdirs();

                    log.info("Generating ARRAY", strategy.getFileName(array, "Record"));

                    GenerationWriter out = new GenerationWriter(new PrintWriter(new File(targetRecordARRAYPackageDir, strategy.getFileName(array, "Record"))));
                    printHeader(out, targetPackage + ".udt.records");
                    printClassJavadoc(out, array);

                    out.print("public class ");
                    out.print(strategy.getJavaClassName(array, "Record"));
                    out.print(" extends ");
                    out.print(ArrayRecordImpl.class);
                    out.print("<");
                    out.print(getJavaType(array.getElementType()));
                    out.println("> {");
                    out.printSerial();

                    out.println();
                    out.print("\tpublic ");
                    out.print(strategy.getJavaClassName(array, "Record"));
                    out.print("(");
                    out.print(Configuration.class);
                    out.println(" configuration) {");
                    out.print("\t\tsuper(\"");
                    out.print(array.getSchemaName());
                    out.print(".");
                    out.print(array.getName());
                    out.print("\", ");
                    out.print(getJavaTypeReference(database, array.getElementType()));
                    out.println(", configuration);");
                    out.println("\t}");

                    out.println();
                    out.print("\tpublic ");
                    out.print(strategy.getJavaClassName(array, "Record"));
                    out.print("(");
                    out.print(Configuration.class);
                    out.print(" configuration, ");
                    out.print(getJavaType(array.getElementType()));
                    out.print("... array");
                    out.println(") {");
                    out.println("\t\tthis(configuration);");
                    out.println("\t\tset(array);");
                    out.println("\t}");

                    out.println();
                    out.print("\tpublic ");
                    out.print(strategy.getJavaClassName(array, "Record"));
                    out.print("(");
                    out.print(Configuration.class);
                    out.print(" configuration, ");
                    out.print(List.class);
                    out.print("<? extends ");
                    out.print(getJavaType(array.getElementType()));
                    out.print("> list");
                    out.println(") {");
                    out.println("\t\tthis(configuration);");
                    out.println("\t\tsetList(list);");
                    out.println("\t}");

                    out.println("}");
                    out.close();
                } catch (Exception e) {
                    log.error("Error while generating ARRAY record " + array, e);
                }
            }

            watch.splitInfo("ARRAYs generated");
        }

        // ----------------------------------------------------------------------
        // XXX Generating enums
        // ----------------------------------------------------------------------
        File targetEnumPackageDir = new File(targetPackageDir, "enums");
        if (database.getEnums().size() > 0) {
            log.info("Generating ENUMs", targetEnumPackageDir.getCanonicalPath());

            for (EnumDefinition e : database.getEnums()) {
                try {
                    targetEnumPackageDir.mkdirs();

                    log.info("Generating ENUM", strategy.getFileName(e));

                    GenerationWriter out = new GenerationWriter(new PrintWriter(new File(targetEnumPackageDir, strategy.getFileName(e))));
                    printHeader(out, targetPackage + ".enums");
                    printClassJavadoc(out, e);

                    out.print("public enum ");
                    out.print(strategy.getJavaClassName(e));
                    out.print(" implements ");
                    out.print(EnumType.class);
                    out.print(" {");
                    out.println();

                    for (String literal : e.getLiterals()) {
                        out.println("\t" + GenerationUtil.convertToJavaIdentifier(literal) + "(\"" + literal + "\"),");
                        out.println();
                    }

                    out.println("\t;");
                    out.println();
                    out.println("\tprivate final java.lang.String literal;");
                    out.println();
                    out.println("\tprivate " + strategy.getJavaClassName(e) + "(java.lang.String literal) {");
                    out.println("\t\tthis.literal = literal;");
                    out.println("\t}");
                    out.println();
                    out.println("\t@Override");
                    out.println("\tpublic java.lang.String getName() {");
                    out.println("\t\treturn \"" + e.getName() + "\";");
                    out.println("\t}");
                    out.println();
                    out.println("\t@Override");
                    out.println("\tpublic java.lang.String getLiteral() {");
                    out.println("\t\treturn literal;");
                    out.println("\t}");

                    out.println("}");

                    out.close();
                } catch (Exception ex) {
                    log.error("Error while generating enum " + e, ex);
                }
            }

            watch.splitInfo("Enums generated");
        }

		// ----------------------------------------------------------------------
		// XXX Generating stored procedures
		// ----------------------------------------------------------------------
		if (database.getProcedures().size() > 0) {
		    File targetProcedurePackageDir = new File(targetPackageDir, "procedures");
		    log.info("Generating procedures", targetProcedurePackageDir.getCanonicalPath());

    		GenerationWriter outP = new GenerationWriter(new PrintWriter(new File(targetPackageDir, "Procedures.java")));
    		printHeader(outP, targetPackage);
    		printClassJavadoc(outP, "Convenience access to all stored procedures in " + schema.getName());
    		outP.println("public final class Procedures {");
    		for (ProcedureDefinition procedure : database.getProcedures()) {
    		    try {
        			printProcedure(database, schema, procedure);

        			// Static execute() convenience method
        			printConvenienceMethodProcedure(outP, procedure);
    		    } catch (Exception e) {
    		        log.error("Error while generating procedure " + procedure, e);
    		    }
    		}

    		printPrivateConstructor(outP, "Procedures");
    		outP.println("}");
    		outP.close();

    		watch.splitInfo("Procedures generated");
		}

		// ----------------------------------------------------------------------
		// XXX Generating stored functions
		// ----------------------------------------------------------------------
		if (database.getFunctions().size() > 0) {
		    File targetFunctionPackageDir = new File(targetPackageDir, "functions");
		    log.info("Generating functions", targetFunctionPackageDir.getCanonicalPath());

            GenerationWriter outFn = new GenerationWriter(new PrintWriter(new File(targetPackageDir, "Functions.java")));
            printHeader(outFn, targetPackage);
            printClassJavadoc(outFn, "Convenience access to all stored functions in " + schema.getName());
            outFn.println("public final class Functions {");

    		for (FunctionDefinition function : database.getFunctions()) {
    		    try {
        			printFunction(database, schema, function);

                    // Static execute() convenience method
    			    printConvenienceMethodFunction(outFn, function);

                    // Static asField() convenience method
                    printConvenienceMethodFunctionAsField(outFn, function, false);
                    printConvenienceMethodFunctionAsField(outFn, function, true);
    		    } catch (Exception e) {
    		        log.error("Error while generating function " + function, e);
    		    }
    		}

    		printPrivateConstructor(outFn, "Functions");
            outFn.println("}");
            outFn.close();

            watch.splitInfo("Functions generated");
		}

        // ----------------------------------------------------------------------
        // XXX Generating packages
        // ----------------------------------------------------------------------
		File targetPackagesPackageDir = new File(targetPackageDir, "packages");
		if (database.getPackages().size() > 0) {
		    log.info("Generating packages", targetPackagesPackageDir.getCanonicalPath());

            for (PackageDefinition pkg : database.getPackages()) {
                try {
                    // [#767] This is not the correct package name!
                    File targetPackagePackageDir = new File(targetPackagesPackageDir, strategy.getJavaClassName(pkg).toLowerCase());
                    log.info("Generating package", targetPackagePackageDir.getCanonicalPath());

                    for (ProcedureDefinition procedure : pkg.getProcedures()) {
                        try {
                            printProcedure(database, schema, procedure);
                        } catch (Exception e) {
                            log.error("Error while generating procedure " + procedure, e);
                        }
                    }

                    for (FunctionDefinition function : pkg.getFunctions()) {
                        try {
                            printFunction(database, schema, function);
                        } catch (Exception e) {
                            log.error("Error while generating function " + function, e);
                        }
                    }

                    // Static convenience methods
                    GenerationWriter outPkg = new GenerationWriter(new PrintWriter(new File(targetPackagesPackageDir, strategy.getFileName(pkg))));
                    printHeader(outPkg, targetPackage + ".packages");
                    printClassJavadoc(outPkg, "Convenience access to all stored procedures and functions in " + pkg.getName());
                    outPkg.print("public final class ");
                    outPkg.print(strategy.getJavaClassName(pkg));
                    outPkg.print(" extends ");
                    outPkg.print(PackageImpl.class);
                    outPkg.println(" {");
                    outPkg.printSerial();

                    outPkg.println();
                    outPkg.println("\t/**");
                    outPkg.println("\t * The singleton instance of " + strategy.getJavaIdentifierUC(pkg));
                    outPkg.println("\t */");
                    outPkg.print("\tpublic static ");
                    outPkg.print(strategy.getFullJavaClassName(pkg));
                    outPkg.print(" ");
                    outPkg.print(strategy.getJavaIdentifierUC(pkg));
                    outPkg.print(" = new ");
                    outPkg.print(strategy.getFullJavaClassName(pkg));
                    outPkg.println("();");

                    for (ProcedureDefinition procedure : pkg.getProcedures()) {
                        try {
                            printConvenienceMethodProcedure(outPkg, procedure);
                        } catch (Exception e) {
                            log.error("Error while generating procedure " + procedure, e);
                        }
                    }

                    for (FunctionDefinition function : pkg.getFunctions()) {
                        try {
                            printConvenienceMethodFunction(outPkg, function);

                            printConvenienceMethodFunctionAsField(outPkg, function, false);
                            printConvenienceMethodFunctionAsField(outPkg, function, true);
                        } catch (Exception e) {
                            log.error("Error while generating function " + function, e);
                        }
                    }

                    printNoFurtherInstancesAllowedJavadoc(outPkg);
                    outPkg.println("\tprivate " + strategy.getJavaClassName(pkg) + "() {");
                    outPkg.print("\t\tsuper(\"");
                    outPkg.print(strategy.getJavaIdentifierUC(pkg));
                    outPkg.print("\", ");
                    outPkg.print(strategy.getFullJavaIdentifierUC(schema));
                    outPkg.println(");");
                    outPkg.println("\t}");
                    outPkg.println("}");

                    outPkg.close();
                } catch (Exception e) {
                    log.error("Error while generating package " + pkg, e);
                }
            }

            watch.splitInfo("Packages generated");
		}

        // Finalise schema
        if (outS != null) {
            outS.println("}");
            outS.close();
        }

        // Finalise factory
        if (outF != null) {
            outF.println("}");
            outF.close();
        }

        // XXX [#651] Refactoring-cursor
        watch.splitInfo("GENERATION FINISHED!");
	}

    private void registerInSchema(GenerationWriter outS, List<? extends Definition> definitions, Class<?> type, boolean isGeneric) {
        if (outS != null) {
            outS.println();
            printOverride(outS);
            outS.print("\tpublic final ");
            outS.print(List.class);
            outS.print("<");
            outS.print(type);

            if (isGeneric) {
                outS.print("<?>");
            }

            outS.print("> get");
            outS.print(type.getSimpleName());
            outS.println("s() {");

            outS.print("\t\treturn ");
            outS.print(Arrays.class);
            outS.print(".<");
            outS.print(type);

            if (isGeneric) {
                outS.print("<?>");
            }

            outS.print(">asList(");

            if (definitions.size() > 1) {
                outS.print("\n\t\t\t");
            }

            for (int i = 0; i < definitions.size(); i++) {
                Definition def = definitions.get(i);

                if (i > 0) {
                    outS.print(",\n\t\t\t");
                }

                printSingletonReference(outS, def);
            }

            outS.println(");");
            outS.println("\t}");
        }
    }

	/**
	 * Find all column names that are reserved because of the extended
	 * class hierarchy of a generated class
	 *
	 * @see <a href="https://sourceforge.net/apps/trac/jooq/ticket/182">https://sourceforge.net/apps/trac/jooq/ticket/182</a>
	 */
    private Set<String> reservedColumns(Class<?> clazz) {
        if (clazz == null) {
            return Collections.emptySet();
        }

        Set<String> result = reservedColumns.get(clazz);

        if (result == null) {
            result = new HashSet<String>();
            reservedColumns.put(clazz, result);

            result.addAll(reservedColumns(clazz.getSuperclass()));
            for (Class<?> c : clazz.getInterfaces()) {
                result.addAll(reservedColumns(c));
            }

            for (Method m : clazz.getDeclaredMethods()) {
                String name = m.getName();

                if (name.startsWith("get") && m.getParameterTypes().length == 0) {
                    result.add(name.substring(3));
                }
            }
        }

        return result;
    }

    private void printProcedure(Database database, SchemaDefinition schema, ProcedureDefinition procedure)
        throws FileNotFoundException, SQLException {
        strategy.getFile(procedure).getParentFile().mkdirs();
        log.info("Generating procedure", strategy.getFileName(procedure));

        GenerationWriter out = new GenerationWriter(new PrintWriter(strategy.getFile(procedure)));
        printHeader(out, strategy.getJavaPackageName(procedure));
        printClassJavadoc(out, procedure);

        Class<?> procedureClass = StoredProcedureImpl.class;
        if (database.getDialect() == SQLDialect.POSTGRES &&
            procedure.getOutParameters().size() == 1 &&
            procedure.getOutParameters().get(0).getType().isUDT()) {

            procedureClass = PostgresSingleUDTOutParameterProcedure.class;
        }

        out.print("public class ");
        out.print(strategy.getJavaClassName(procedure));
        out.print(" extends ");
        out.print(procedureClass);
        out.println(" {");
        out.printSerial();
        out.println();

        for (ParameterDefinition parameter : procedure.getAllParameters()) {
        	printParameter(out, parameter, procedure);
        }

        out.println();
        printJavadoc(out, "Create a new procedure call instance");
        out.println("\tpublic " + strategy.getJavaClassName(procedure) + "() {");
        out.print("\t\tsuper(");
        out.print(SQLDialect.class);
        out.print(".");
        out.print(database.getDialect().name());
        out.print(", \"");
        out.print(procedure.getName());
        out.print("\", ");
        out.print(strategy.getFullJavaIdentifierUC(schema));

        if (procedure.getPackage() != null) {
            out.print(", ");
            out.print(strategy.getFullJavaClassName(procedure.getPackage()));
            out.print(".");
            out.print(strategy.getJavaIdentifierUC(procedure.getPackage()));
        }

        out.println(");");

        if (procedure.getAllParameters().size() > 0) {
            out.println();
        }

        for (ParameterDefinition parameter : procedure.getAllParameters()) {
        	String parameterNameUC = parameter.getName().toUpperCase();

        	out.print("\t\t");

        	if (parameter.equals(procedure.getReturnValue())) {
        	    out.println("setReturnParameter(" + parameterNameUC + ");");
        	}
        	else if (procedure.getInParameters().contains(parameter)) {
        		if (procedure.getOutParameters().contains(parameter)) {
        			out.println("addInOutParameter(" + parameterNameUC + ");");
        		}
        		else {
        			out.println("addInParameter(" + parameterNameUC + ");");
        		}
        	}
        	else {
        		out.println("addOutParameter(" + parameterNameUC + ");");
        	}
        }

        if (procedure.getOverload() != null) {
            out.println("\t\tsetOverloaded(true);");
        }

        out.println("\t}");

        for (ParameterDefinition parameter : procedure.getInParameters()) {
        	out.println();
        	out.print("\tpublic void set");
            out.print(strategy.getJavaClassName(parameter));
            out.print("(");
            printNumberType(out, parameter.getType());
            out.println(" value) {");

            out.print("\t\tset");
            if (parameter.getType().isGenericNumberType()) {
                out.print("Number");
            }
            else {
                out.print("Value");
            }
            out.print("(");
            out.print(strategy.getJavaIdentifierUC(parameter));
            out.println(", value);");
        	out.println("\t}");
        }

        for (ParameterDefinition parameter : procedure.getAllParameters()) {
            if (parameter.equals(procedure.getReturnValue()) ||
                procedure.getOutParameters().contains(parameter)) {

                out.println();
                out.println("\tpublic " + getJavaType(parameter.getType()) + " get" + strategy.getJavaClassName(parameter) + "() {");
                out.println("\t\treturn getValue(" + strategy.getJavaIdentifierUC(parameter) + ");");
                out.println("\t}");
            }
        }

        out.println("}");
        out.close();
    }

    private void printFunction(Database database, SchemaDefinition schema, FunctionDefinition function)
        throws SQLException, FileNotFoundException {
        strategy.getFile(function).getParentFile().mkdirs();
        log.info("Generating function", strategy.getFileName(function));

        GenerationWriter out = new GenerationWriter(new PrintWriter(strategy.getFile(function)));
        printHeader(out, strategy.getJavaPackageName(function));
        printClassJavadoc(out, function);

        out.print("public class ");
        out.print(strategy.getJavaClassName(function));
        out.print(" extends ");
        out.print(StoredFunctionImpl.class);
        out.print("<");
        out.print(getJavaType(function.getReturnType()));
        out.println("> {");
        out.printSerial();
        out.println();

        for (ParameterDefinition parameter : function.getInParameters()) {
        	printParameter(out, parameter, function);
        }

        out.println();
        printJavadoc(out, "Create a new function call instance");
        out.println("\tpublic " + strategy.getJavaClassName(function) + "() {");
        out.print("\t\tsuper(");
        out.print(SQLDialect.class);
        out.print(".");
        out.print(database.getDialect().name());
        out.print(", \"");

        out.print(function.getName());
        out.print("\", ");

        out.print(strategy.getFullJavaIdentifierUC(schema));
        out.print(", ");

        if (function.getPackage() != null) {
            out.print(strategy.getFullJavaClassName(function.getPackage()));
            out.print(".");
            out.print(strategy.getJavaIdentifierUC(function.getPackage()));
            out.print(", ");
        }

        out.print(getJavaTypeReference(database, function.getReturnType()));
        out.println(");");

        if (function.getInParameters().size() > 0) {
            out.println();
        }

        for (ParameterDefinition parameter : function.getInParameters()) {
        	String parameterNameUC = parameter.getName().toUpperCase();

        	out.print("\t\t");
        	out.println("addInParameter(" + parameterNameUC + ");");
        }

        if (function.getOverload() != null) {
            out.println("\t\tsetOverloaded(true);");
        }

        out.println("\t}");

        for (ParameterDefinition parameter : function.getInParameters()) {
            out.println();
            out.println("\t/**");
            out.println("\t * Set the <code>" + parameter.getName() + "</code> parameter to the function");
            out.println("\t */");
            out.print("\tpublic void set");
            out.print(strategy.getJavaClassName(parameter));
            out.print("(");
            printNumberType(out, parameter.getType());
            out.println(" value) {");

            out.print("\t\tset");
            if (parameter.getType().isGenericNumberType()) {
                out.print("Number");
            }
            else {
                out.print("Value");
            }
            out.print("(");
            out.print(strategy.getJavaIdentifierUC(parameter));
            out.println(", value);");
            out.println("\t}");
            out.println();
            out.println("\t/**");
            out.println("\t * Set the <code>" + parameter.getName() + "</code> parameter to the function");
            out.println("\t * <p>");
            out.print("\t * Use this method only, if the function is called as a {@link ");
            out.print(Field.class);
            out.print("} in a {@link ");
            out.print(Select.class);
            out.println("} statement!");
            out.println("\t */");
            out.print("\tpublic void set");
            out.print(strategy.getJavaClassName(parameter));
            out.print("(");
            out.print(Field.class);
            out.print("<");
            printExtendsNumberType(out, parameter.getType());
            out.println("> field) {");

            out.print("\t\tset");
            if (parameter.getType().isGenericNumberType()) {
                out.print("Number");
            }
            else {
                out.print("Field");
            }
            out.print("(");
            out.print(strategy.getJavaIdentifierUC(parameter));
            out.println(", field);");
            out.println("\t}");
        }

        out.println("}");
        out.close();
    }

    private void printConvenienceMethodFunctionAsField(GenerationWriter out, FunctionDefinition function, boolean parametersAsField) throws SQLException {
        // [#281] - Java can't handle more than 255 method parameters
        if (function.getInParameters().size() > 254) {
            log.warn("Too many parameters", "Function " + function + " has more than 254 in parameters. Skipping generation of convenience method.");
            return;
        }

        // Do not generate separate convenience methods, if there are no IN
        // parameters. They would have the same signature and no additional
        // meaning
        if (parametersAsField && function.getInParameters().isEmpty()) {
            return;
        }

        out.println();
        out.println("\t/**");
        out.println("\t * Get " + strategy.getJavaIdentifierUC(function) + " as a field");
        out.println("\t *");

        for (ParameterDefinition parameter : function.getInParameters()) {
            out.println("\t * @param " + strategy.getJavaClassNameLC(parameter));
        }

        out.println("\t */");
        out.print("\tpublic static ");
        out.print(Field.class);
        out.print("<");
        out.print(getJavaType(function.getReturnType()));
        out.print("> ");
        out.print(strategy.getJavaClassNameLC(function));
        out.print("(");

        String separator = "";
        for (ParameterDefinition parameter : function.getInParameters()) {
            out.print(separator);

            if (parametersAsField) {
                out.print(Field.class);
                out.print("<");
                printExtendsNumberType(out, parameter.getType());
                out.print(">");
            } else {
                printNumberType(out, parameter.getType());
            }

            out.print(" ");
            out.print(strategy.getJavaClassNameLC(parameter));

            separator = ", ";
        }

        out.println(") {");
        out.print("\t\t");
        out.print(strategy.getFullJavaClassName(function));
        out.print(" f = new ");
        out.print(strategy.getFullJavaClassName(function));
        out.println("();");

        for (ParameterDefinition parameter : function.getInParameters()) {
            out.println("\t\tf.set" + strategy.getJavaClassName(parameter) + "(" + strategy.getJavaClassNameLC(parameter) + ");");
        }

        out.println();
        out.println("\t\treturn f.asField();");
        out.println("\t}");
    }

    private void printConvenienceMethodFunction(GenerationWriter out, FunctionDefinition function) throws SQLException {
        // [#281] - Java can't handle more than 255 method parameters
        if (function.getInParameters().size() > 254) {
            log.warn("Too many parameters", "Function " + function + " has more than 254 in parameters. Skipping generation of convenience method.");
            return;
        }

        // i == 0: Version 1.6.1 standard API
        // i == 1: Deprecated API according to [#453]
        for (int i = 0; i < 2; i++) {
            String deprecation = null;

            if (i == 1 && !generateDeprecated()) {
                break;
            }
            else if (i == 1) {
                deprecation =
                    "1.6.1 [#453] This method of calling stored functions is not supported anymore<br/>\n"+
                    "See {@link org.jooq.StoredObject#execute(java.sql.Connection)} for details.<br/><br/>\n" +
                    "If you wish to remove this method, adapt your configuration:<br/>\n" +
                    "<code>generator.generate.deprecated=false</code>";
            }

            out.println();
            out.println("\t/**");
            out.println("\t * Invoke " + strategy.getJavaIdentifierUC(function));
            out.println("\t *");

            for (ParameterDefinition parameter : function.getInParameters()) {
                out.println("\t * @param " + strategy.getJavaClassNameLC(parameter));
            }

            printDeprecation(out, deprecation);
            out.println("\t */");

            if (i == 1) {
                out.println("\t@Deprecated");
            }
            out.print("\tpublic static ");
            out.print(getJavaType(function.getReturnType()));
            out.print(" ");
            out.print(strategy.getJavaClassNameLC(function));

            if (i == 0) {
                out.print("(");
                out.print(Configuration.class);
                out.print(" configuration");
            }
            else {
                out.print("(");
                out.print(Connection.class);
                out.print(" connection");
            }

            for (ParameterDefinition parameter : function.getInParameters()) {
                out.print(", ");
                printNumberType(out, parameter.getType());
                out.print(" ");
                out.print(strategy.getJavaClassNameLC(parameter));
            }

            out.print(") throws ");
            out.print(SQLException.class);
            out.println(" {");
            out.print("\t\t");
            out.print(strategy.getFullJavaClassName(function));
            out.print(" f = new ");
            out.print(strategy.getFullJavaClassName(function));
            out.println("();");

            for (ParameterDefinition parameter : function.getInParameters()) {
                out.println("\t\tf.set" + strategy.getJavaClassName(parameter) + "(" + strategy.getJavaClassNameLC(parameter) + ");");
            }

            out.println();

            if (i == 0) {
                out.println("\t\tf.execute(configuration);");
            }
            else {
                out.println("\t\tf.execute(connection);");
            }

            out.println("\t\treturn f.getReturnValue();");
            out.println("\t}");
        }
    }

    private void printPrivateConstructor(GenerationWriter out, String javaClassName) {
        out.println();
        out.println("\t/**");
        out.println("\t * No instances");
        out.println("\t */");
        out.println("\tprivate " + javaClassName + "() {}");
    }

    private void printConvenienceMethodProcedure(GenerationWriter out, ProcedureDefinition procedure) throws SQLException {
        // [#281] - Java can't handle more than 255 method parameters
        if (procedure.getInParameters().size() > 254) {
            log.warn("Too many parameters", "Procedure " + procedure + " has more than 254 in parameters. Skipping generation of convenience method.");
            return;
        }

        // i == 0: Version 1.6.1 standard API
        // i == 1: Deprecated API according to [#453]
        for (int i = 0; i < 2; i++) {
            String deprecation = null;

            if (i == 1 && !generateDeprecated()) {
                break;
            }
            else if (i == 1) {
                deprecation =
                    "1.6.1 [#453] This method of calling stored functions is not supported anymore<br/>\n"+
                    "See {@link org.jooq.StoredObject#execute(java.sql.Connection)} for details.<br/><br/>\n" +
                    "If you wish to remove this method, adapt your configuration:<br/>\n" +
                    "<code>generator.generate.deprecated=false</code>";
            }

            out.println();
            out.println("\t/**");
            out.println("\t * Invoke " + strategy.getJavaIdentifierUC(procedure));
            out.println("\t *");

            for (ParameterDefinition parameter : procedure.getAllParameters()) {
                out.print("\t * @param " + strategy.getJavaClassNameLC(parameter) + " ");

                if (procedure.getInParameters().contains(parameter)) {
                    if (procedure.getOutParameters().contains(parameter)) {
                        out.println("IN OUT parameter");
                    } else {
                        out.println("IN parameter");
                    }
                } else {
                    out.println("OUT parameter");
                }
            }

            printDeprecation(out, deprecation);
            out.println("\t */");

            if (i == 1) {
                out.println("\t@Deprecated");
            }
            out.print("\tpublic static ");

            if (procedure.getOutParameters().size() == 0) {
                out.print("void ");
            }
            else if (procedure.getOutParameters().size() == 1) {
                out.print(getJavaType(procedure.getOutParameters().get(0).getType()));
                out.print(" ");
            }
            else {
                out.print(strategy.getFullJavaClassName(procedure) + " ");
            }

            out.print(strategy.getJavaClassNameLC(procedure));
            if (i == 0) {
                out.print("(");
                out.print(Configuration.class);
                out.print(" configuration");
            }
            else {
                out.print("(");
                out.print(Connection.class);
                out.print(" connection");
            }

            for (ParameterDefinition parameter : procedure.getInParameters()) {
                out.print(", ");
                printNumberType(out, parameter.getType());
                out.print(" ");
                out.print(strategy.getJavaClassNameLC(parameter));
            }

            out.print(") throws ");
            out.print(SQLException.class);
            out.println(" {");
            out.print("\t\t");
            out.print(strategy.getFullJavaClassName(procedure));
            out.print(" p = new ");
            out.print(strategy.getFullJavaClassName(procedure));
            out.println("();");

            for (ParameterDefinition parameter : procedure.getInParameters()) {
                out.println("\t\tp.set" + strategy.getJavaClassName(parameter) + "(" + strategy.getJavaClassNameLC(parameter) + ");");
            }

            out.println();
            if (i == 0) {
                out.println("\t\tp.execute(configuration);");
            }
            else {
                out.println("\t\tp.execute(connection);");
            }

            if (procedure.getOutParameters().size() == 1) {
                out.println("\t\treturn p.get" + strategy.getJavaClassName(procedure.getOutParameters().get(0)) + "();");
            }
            else if (procedure.getOutParameters().size() > 1) {
                out.println("\t\treturn p;");
            }

            out.println("\t}");
        }
    }

    private void printRecordTypeMethod(Definition definition, GenerationWriter out) {
        out.println();
        out.println("\t/**");
        out.println("\t * The class holding records for this type");
        out.println("\t */");
        out.print("\tprivate static final ");
        out.print(Class.class);
        out.print("<");
        out.print(strategy.getFullJavaClassName(definition, "Record"));
        out.print("> __RECORD_TYPE = ");
        out.print(strategy.getFullJavaClassName(definition, "Record"));
        out.println(".class;");

        out.println();
        out.println("\t/**");
        out.println("\t * The class holding records for this type");
        out.println("\t */");
        printOverride(out);
        out.print("\tpublic ");
        out.print(Class.class);
        out.print("<");
        out.print(strategy.getFullJavaClassName(definition, "Record"));
        out.println("> getRecordType() {");
        out.println("\t\treturn __RECORD_TYPE;");
        out.println("\t}");
    }

    private void printSingletonInstance(Definition definition, GenerationWriter out) {
        out.println();
        out.println("\t/**");
        out.println("\t * The singleton instance of " + definition.getName());
        out.println("\t */");
        out.print("\tpublic static final ");
        out.print(strategy.getFullJavaClassName(definition));
        out.print(" ");
        out.print(strategy.getJavaIdentifierUC(definition));
        out.print(" = new ");
        out.print(strategy.getFullJavaClassName(definition));
        out.println("();");
    }

    private void printSingletonReference(GenerationWriter out, Definition definition) {
        if (definition instanceof SequenceDefinition) {
            out.print(strategy.getTargetPackage());
            out.print(".");
            out.print("Sequences");
            out.print(".");
            out.print(strategy.getJavaIdentifierUC(definition));
        }
        else {
            out.print(strategy.getFullJavaIdentifierUC(definition));
        }
    }

	private void printOverride(GenerationWriter out) {
		out.println("\t@Override");
	}

	/**
	 * If file is a directory, recursively empty its children.
	 * If file is a file, delete it
	 */
	private void empty(File file) {
		if (file != null) {
			if (file.isDirectory()) {
				File[] children = file.listFiles();

				if (children != null) {
					for (File child : children) {
						empty(child);
					}
				}
			} else {
				if (file.getName().endsWith(".java")) {
					file.delete();
				}
			}
		}
	}

	private void printGetterAndSetter(GenerationWriter out, TypedElementDefinition<?> element, Definition type, Set<String> reserved) throws SQLException {
        String columnDisambiguationSuffix = "";
        String getterDisambiguationSuffix = "";

		if (strategy.getJavaIdentifierUC(element).equals(strategy.getJavaIdentifierUC(type))) {
		    columnDisambiguationSuffix = "_";
		}

		if (reserved.contains(strategy.getJavaClassName(element))) {
		    getterDisambiguationSuffix = "_";
		}

		printFieldJavaDoc(out, getterDisambiguationSuffix, element);
		out.println("\tpublic void set" + strategy.getJavaClassName(element) + getterDisambiguationSuffix + "(" + getJavaType(element.getType()) + " value) {");
		out.println("\t\tsetValue(" + strategy.getFullJavaIdentifierUC(element) + columnDisambiguationSuffix + ", value);");
		out.println("\t}");
		printFieldJavaDoc(out, getterDisambiguationSuffix, element);
		out.println("\tpublic " + getJavaType(element.getType()) + " get" + strategy.getJavaClassName(element) + getterDisambiguationSuffix + "() {");
		out.println("\t\treturn getValue(" + strategy.getFullJavaIdentifierUC(element) + columnDisambiguationSuffix + ");");
		out.println("\t}");

		if (generateRelations() && element instanceof ColumnDefinition) {
		    ColumnDefinition column = (ColumnDefinition) element;

			List<UniqueKeyDefinition> uniqueKeys = column.getUniqueKeys();

            // Print references from this column's unique keys to all
            // corresponding foreign keys.

            // e.g. in TAuthorRecord, print getTBooks()
			// -----------------------------------------------------------------
			for (UniqueKeyDefinition uniqueKey : uniqueKeys) {
			    if (out.printOnlyOnce(uniqueKey)) {
	                foreignKeyLoop: for (ForeignKeyDefinition foreignKey : uniqueKey.getForeignKeys()) {

	                    // #64 - If the foreign key does not match the referenced key, it
	                    // is most likely because it references a non-primary unique key
	                    // Skip code generation for this foreign key

	                    // #69 - Should resolve this issue more thoroughly.
	                    if (foreignKey.getReferencedColumns().size() != foreignKey.getKeyColumns().size()) {
	                        log.warn("Foreign key mismatch", foreignKey.getName() + " does not match its primary key! No code is generated for this key. See trac tickets #64 and #69");
	                        continue foreignKeyLoop;
	                    }

	                    TableDefinition referencing = foreignKey.getKeyTable();

	                    prefixLoop: for (String prefix : new String[] { "fetch", "get" }) {

	                        // #187 - Mark getter navigation methods as deprecated
	                        String deprecation = null;
	                        if ("get".equals(prefix)) {

	                            // Only generate this code, if deprecation is
	                            // accepted
	                            if (!generateDeprecated()) {
	                                continue prefixLoop;
	                            }

	                            deprecation =
	                                "Because of risk of ambiguity (#187), code generation for this<br/>\n"+
	                                "method will not be supported anymore, soon.<br/><br/>\n" +
	                                "If you wish to remove this method, adapt your configuration:<br/>\n" +
	                                "<code>generator.generate.deprecated=false</code>";
	                        }


	                        printFieldJavaDoc(out, null, column, deprecation);
	                        out.print("\tpublic ");
	                        out.print(List.class);
	                        out.print("<");
	                        out.print(strategy.getFullJavaClassName(referencing, "Record"));
	                        out.print("> ");
	                        out.print(prefix);
	                        out.print(strategy.getJavaClassName(referencing));

	                        // #352 - Disambiguate foreign key navigation directions
	                        out.print("List");

	                        // #350 - Disambiguate multiple foreign keys referencing
	                        // the same table
	                        if (foreignKey.countSimilarReferences() > 1) {
	                            out.print("By");
	                            out.print(strategy.getJavaClassName(foreignKey.getKeyColumns().get(0)));
	                        }

	                        out.print("() throws ");
	                        out.print(SQLException.class);
	                        out.println(" {");

	                        out.println("\t\treturn create()");
	                        out.print("\t\t\t.selectFrom(");
	                        out.print(strategy.getFullJavaIdentifierUC(referencing));
	                        out.println(")");

	                        String connector = "\t\t\t.where(";

	                        for (int i = 0; i < foreignKey.getReferencedColumns().size(); i++) {
	                            out.print(connector);
	                            out.print(strategy.getFullJavaIdentifierUC(foreignKey.getKeyColumns().get(i)));
	                            out.print(".equal(getValue");

	                            DataTypeDefinition foreignType = foreignKey.getKeyColumns().get(i).getType();
	                            DataTypeDefinition primaryType = uniqueKey.getKeyColumns().get(i).getType();

	                            // Convert foreign key value, if there is a type mismatch
	                            if (!match(foreignType, primaryType)) {
	                                out.print("As");
	                                out.print(getSimpleJavaType(foreignKey.getKeyColumns().get(i).getType()));
	                            }

	                            out.print("(");
	                            out.print(strategy.getFullJavaIdentifierUC(uniqueKey.getKeyColumns().get(i)));
	                            out.println(")))");

	                            connector = "\t\t\t.and(";
	                        }

	                        out.println("\t\t\t.fetch();");
	                        out.println("\t}");
	                    }
	                }
	            }
			}

			// Print references from this foreign key to its related primary key
			// E.g. in TBookRecord print getTAuthor()
			// -----------------------------------------------------------------
			ForeignKeyDefinition foreignKey = column.getForeignKey();
			if (foreignKey != null && out.printOnlyOnce(foreignKey)) {
			    boolean skipGeneration = false;

                // #64 - If the foreign key does not match the referenced key, it
                // is most likely because it references a non-primary unique key
                // Skip code generation for this foreign key

                // #69 - Should resolve this issue more thoroughly.
                if (foreignKey.getReferencedColumns().size() != foreignKey.getKeyColumns().size()) {
                    log.warn("Foreign key mismatch", foreignKey.getName() + " does not match its primary key! No code is generated for this key. See trac tickets #64 and #69");
                    skipGeneration = true;
                }

                // Do not generate referential code for master data tables
                TableDefinition referenced = foreignKey.getReferencedTable();
                if (referenced instanceof MasterDataTableDefinition) {
                    skipGeneration = true;
                }

                if (!skipGeneration) {
                    prefixLoop: for (String prefix : new String[] { "fetch", "get" }) {

                        // #187 - Mark getter navigation methods as deprecated
                        String deprecation = null;
                        if ("get".equals(prefix)) {

                            // Only generate this code, if deprecation is
                            // accepted
                            if (!generateDeprecated()) {
                                continue prefixLoop;
                            }

                            deprecation =
                                "Because of risk of ambiguity (#187), code generation for this<br/>\n"+
                                "method will not be supported anymore, soon.<br/><br/>\n" +
                                "If you wish to remove this method, adapt your configuration:<br/>\n" +
                                "<code>generator.generate.deprecated=false</code>";
                        }

                        printFieldJavaDoc(out, null, column, deprecation);
                        out.print("\tpublic ");
                        out.print(strategy.getFullJavaClassName(referenced, "Record"));
                        out.print(" ");
                        out.print(prefix);
                        out.print(strategy.getJavaClassName(referenced));

                        // #350 - Disambiguate multiple foreign keys referencing
                        // the same table
                        if (foreignKey.countSimilarReferences() > 1) {
                            out.print("By");
                            out.print(strategy.getJavaClassName(column));
                        }

                        out.print("() throws ");
                        out.print(SQLException.class);
                        out.println(" {");

                        out.println("\t\treturn create()");
                        out.print("\t\t\t.selectFrom(");
                        out.print(strategy.getFullJavaIdentifierUC(referenced));
                        out.println(")");

                        String connector = "\t\t\t.where(";

                        for (int i = 0; i < foreignKey.getReferencedColumns().size(); i++) {
                            out.print(connector);
                            out.print(strategy.getFullJavaIdentifierUC(foreignKey.getReferencedColumns().get(i)));
                            out.print(".equal(getValue");

                            DataTypeDefinition foreignType = foreignKey.getKeyColumns().get(i).getType();
                            DataTypeDefinition primaryType = foreignKey.getReferencedColumns().get(i).getType();

                            // Convert foreign key value, if there is a type mismatch
                            if (!match(foreignType, primaryType)) {
                                out.print("As");
                                out.print(getSimpleJavaType(foreignKey.getReferencedColumns().get(i).getType()));
                            }

                            out.print("(");
                            out.print(strategy.getFullJavaIdentifierUC(foreignKey.getKeyColumns().get(i)));
                            out.println(")))");

                            connector = "\t\t\t.and(";
                        }

                        out.println("\t\t\t.fetchOne();");
                        out.println("\t}");
                    }
                }
			}
		}
	}

    private void printUDTColumn(GenerationWriter out, AttributeDefinition attribute, Definition table) throws SQLException {
        Class<?> declaredMemberClass = UDTField.class;
        Class<?> concreteMemberClass = UDTFieldImpl.class;

        printColumnDefinition(out, attribute, table, declaredMemberClass, concreteMemberClass);
    }

    private void printTableColumn(GenerationWriter out, ColumnDefinition column, Definition table) throws SQLException {
        Class<?> declaredMemberClass = TableField.class;
        Class<?> concreteMemberClass = TableFieldImpl.class;

        printColumnDefinition(out, column, table, declaredMemberClass, concreteMemberClass);
    }

	private void printParameter(GenerationWriter out, ParameterDefinition parameter, Definition proc) throws SQLException {
		printColumnDefinition(out, parameter, proc, Parameter.class, ParameterImpl.class);
	}

	private void printColumnDefinition(GenerationWriter out, TypedElementDefinition<?> column, Definition type, Class<?> declaredMemberClass, Class<?> concreteMemberClass) throws SQLException {
		String columnDisambiguationSuffix =
		    strategy.getJavaIdentifierUC(column).equals(strategy.getJavaIdentifierUC(type)) ? "_" : "";
		printFieldJavaDoc(out, columnDisambiguationSuffix, column);

		String genericPrefix = "<";
		boolean hasType =
		    type instanceof TableDefinition ||
		    type instanceof UDTDefinition;

        if (hasType) {
			genericPrefix += strategy.getFullJavaClassName(type, "Record") + ", ";
		}

		out.print("\tpublic static final ");
		out.print(declaredMemberClass);
		out.print(genericPrefix);
		out.print(getJavaType(column.getType()));
		out.print("> ");
		out.print(strategy.getJavaIdentifierUC(column));
		out.print(columnDisambiguationSuffix);
		out.print(" = new ");
		out.print(concreteMemberClass);
		out.print(genericPrefix);
		out.print(getJavaType(column.getType()));
		out.print(">(\"");
		out.print(column.getName());
		out.print("\", ");
		out.print(getJavaTypeReference(column.getDatabase(), column.getType()));

		if (hasType) {
		    out.print(", " + strategy.getJavaIdentifierUC(type));
		}

		out.println(");");
	}

	private void printFieldJavaDoc(GenerationWriter out, String disambiguationSuffix, TypedElementDefinition<?> element) throws SQLException {
	    printFieldJavaDoc(out, disambiguationSuffix, element, null);
	}

    private void printFieldJavaDoc(GenerationWriter out, String disambiguationSuffix, TypedElementDefinition<?> element, String deprecation) throws SQLException {
		out.println();
		out.println("\t/**");

		String comment = element.getComment();

		if (comment != null && comment.length() > 0) {
			out.println("\t * " + comment);
		} else {
			out.println("\t * An uncommented item");
		}

		if (getJavaType(element.getType()).startsWith("java.lang.Object")) {
		    String t = element.getType().getType();
            String u = element.getType().getUserType();
            String combined = t.equalsIgnoreCase(u) ? t : t + ", " + u;

		    out.println("\t * ");
		    out.print("\t * The SQL type of this item (");
		    out.print(combined);
		    out.println(") could not be mapped.<br/>");
		    out.println("\t * Deserialising this field might not work!");

		    log.warn("Unknown type", element.getQualifiedName() + " (" + combined + ")");
		}

		if (element instanceof ColumnDefinition) {
		    ColumnDefinition column = (ColumnDefinition) element;

	        UniqueKeyDefinition primaryKey = column.getPrimaryKey();
	        ForeignKeyDefinition foreignKey = column.getForeignKey();

	        if (primaryKey != null) {
	            out.println("\t * ");
	            out.print("\t * PRIMARY KEY");
	            out.println();
	        }

	        if (foreignKey != null) {
	            out.println("\t * <p>");
	            out.println("\t * <code><pre>");
	            out.print("\t * FOREIGN KEY ");
	            out.println(foreignKey.getKeyColumns().toString());

	            out.print("\t * REFERENCES ");
	            out.print(foreignKey.getReferencedTable().getName());
	            out.print(" ");
	            out.println(foreignKey.getReferencedColumns().toString());
	            out.println("\t * </pre></code>");
	        }
		}

		if (disambiguationSuffix != null && disambiguationSuffix.length() > 0) {
			out.println("\t * ");
			out.println("\t * This item causes a name clash. That is why an underline character was appended to the Java field name");
		}

		printDeprecation(out, deprecation);

		out.println("\t */");

		if (deprecation != null) {
		    out.println("\t@Deprecated");
		}
	}

    private void printDeprecation(GenerationWriter out, String deprecation) {
        if (deprecation != null) {
		    out.println("\t *");

		    String[] strings = deprecation.split("[\n\r]+");
            for (int i = 0; i < strings.length; i++) {
                if (i == 0) {
                    out.println("\t * @deprecated " + strings[i]);
                }
                else {
                    out.println("\t *             " + strings[i]);
                }
		    }
		}
    }

    private void printNoFurtherInstancesAllowedJavadoc(GenerationWriter out) {
        printJavadoc(out, "No further instances allowed");
    }

    private void printJavadoc(GenerationWriter out, String doc) {
        out.println("\t/**");
        out.println("\t * " + doc);
        out.println("\t */");
    }

    private void printClassJavadoc(GenerationWriter out, Definition definition) {
        printClassJavadoc(out, definition.getComment());
    }

    private void printClassJavadoc(GenerationWriter out, String comment) {
        printClassJavadoc(out, comment, null);
    }

    private void printClassJavadoc(GenerationWriter out, String comment, String deprecation) {
        out.println("/**");
        out.println(" * This class is generated by jOOQ.");

        if (comment != null && comment.length() > 0) {
            out.println(" *");
            out.println(" * " + comment);
        }

        if (deprecation != null && deprecation.length() > 0) {
            out.println(" *");
            out.println(" * @deprecated : " + deprecation);
        }

        out.println(" */");
        out.println(
            "@javax.annotation.Generated(value    = \"http://jooq.sourceforge.net\",\n" +
            "                            comments = \"This class is generated by jOOQ\")");

        if (deprecation != null && deprecation.length() > 0) {
            out.println("@Deprecated");
        }

        out.printSuppressWarningsPlaceholder();
    }

	private void printHeader(GenerationWriter out, String packageName) {
		out.println("/**");
		out.println(" * This class is generated by jOOQ");
		out.println(" */");
		out.println("package " + packageName + ";");
		out.println();
	}

    private void printExtendsNumberType(GenerationWriter out, DataTypeDefinition type) throws SQLException {
        printNumberType(out, type, "? extends ");
    }

    private void printNumberType(GenerationWriter out, DataTypeDefinition type) throws SQLException {
        printNumberType(out, type, "");
    }

    private void printNumberType(GenerationWriter out, DataTypeDefinition type, String prefix) throws SQLException {
        if (type.isGenericNumberType()) {
            out.print(prefix);
            out.print(Number.class);
        }
        else {
            out.print(getJavaType(type));
        }
    }

	private String getSimpleJavaType(DataTypeDefinition type) throws SQLException {
        return GenerationUtil.getSimpleJavaType(getJavaType(type));
    }

    private String getJavaTypeReference(Database db, DataTypeDefinition type) throws SQLException {
        if (type instanceof MasterDataTypeDefinition) {
            StringBuilder sb = new StringBuilder();

            sb.append(getJavaTypeReference(db, ((MasterDataTypeDefinition) type).underlying));
            sb.append(".asMasterDataType(");
            sb.append(getJavaType(type));
            sb.append(".class)");

            return sb.toString();
        }

        else {
            if (db.isArrayType(type.getType())) {
                String baseType = GenerationUtil.getArrayBaseType(db.getDialect(), type.getType(), type.getUserType());
                return getTypeReference(db, baseType, 0, 0, baseType) + ".getArrayDataType()";
            }
            else {
                return getTypeReference(db, type.getType(), type.getPrecision(), type.getScale(), type.getUserType());
            }
        }
    }

    private String getJavaType(DataTypeDefinition type) throws SQLException {
        if (type instanceof MasterDataTypeDefinition) {
            return strategy.getFullJavaClassName(((MasterDataTypeDefinition) type).table);
        }
        else {
            return getType(
                type.getDatabase(),
                type.getType(),
                type.getPrecision(),
                type.getScale(),
                type.getUserType(),
                Object.class.getName());
        }
    }

    private String getType(Database db, String t, int p, int s, String u, String defaultType) throws SQLException {
        String type = defaultType;

        // Array types
        if (db.isArrayType(t)) {
            String baseType = GenerationUtil.getArrayBaseType(db.getDialect(), t, u);
            type = getType(db, baseType, p, s, baseType, defaultType) + "[]";
        }

        // Check for Oracle-style VARRAY types
        else if (db.getArray(u) != null) {
            type = strategy.getFullJavaClassName(db.getArray(u), "Record");
        }

        // Check for ENUM types
        else if (db.getEnum(u) != null) {
            type = strategy.getFullJavaClassName(db.getEnum(u));
        }

        // Check for UDTs
        else if (db.getUDT(u) != null) {
            type = strategy.getFullJavaClassName(db.getUDT(u), "Record");
        }

        // Try finding a basic standard SQL type according to the current dialect
        else {
            try {
                Class<?> clazz = FieldTypeHelper.getDialectJavaType(db.getDialect(), t, p, s);
                type = clazz.getCanonicalName();

                if (clazz.getTypeParameters().length > 0) {
                    type += "<";

                    String separator = "";
                    for (TypeVariable<?> var : clazz.getTypeParameters()) {
                        type += separator;
                        type += ((Class<?>) var.getBounds()[0]).getCanonicalName();

                        separator = ", ";
                    }

                    type += ">";
                }
            }
            catch (SQLDialectNotSupportedException e) {
                if (defaultType == null) {
                    throw e;
                }
            }
        }

        return type;
    }

    private String getTypeReference(Database db, String t, int p, int s, String u) throws SQLException {
        StringBuilder sb = new StringBuilder();
        if (db.getArray(u) != null) {
            ArrayDefinition array = db.getArray(u);

            sb.append(getJavaTypeReference(db, array.getElementType()));
            sb.append(".asArrayDataType(");
            sb.append(strategy.getFullJavaClassName(array, "Record"));
            sb.append(".class)");
        }
        else if (db.getUDT(u) != null) {
            UDTDefinition udt = db.getUDT(u);

            sb.append(strategy.getFullJavaIdentifierUC(udt));
            sb.append(".getDataType()");
        }
        else if (db.getEnum(u) != null) {
            sb.append("org.jooq.util.");
            sb.append(db.getDialect().getName().toLowerCase());
            sb.append(".");
            sb.append(db.getDialect().getName());
            sb.append("DataType.");
            sb.append(FieldTypeHelper.normalise(FieldTypeHelper.getDataType(db.getDialect(), String.class).getTypeName()));
            sb.append(".asEnumDataType(");
            sb.append(strategy.getFullJavaClassName(db.getEnum(u)));
            sb.append(".class)");
        }
        else {
            DataType<?> dataType = null;

            try {
                dataType = FieldTypeHelper.getDialectDataType(db.getDialect(), t, p, s);
            }

            // Mostly because of unsupported data types. Will be handled later.
            catch (SQLDialectNotSupportedException ignore) {
            }

            // If there is a standard SQLDataType available for the dialect-
            // specific DataType t, then reference that one.
            if (dataType != null && dataType.getSQLDataType() != null) {
                SQLDataType<?> sqlDataType = dataType.getSQLDataType();

                sb.append(SQLDataType.class.getCanonicalName());
                sb.append(".");
                sb.append(FieldTypeHelper.normalise(sqlDataType.getTypeName()));
            }

            // Otherwise, reference the dialect-specific DataType itself.
            else {
                sb.append("org.jooq.util.");
                sb.append(db.getDialect().getName().toLowerCase());
                sb.append(".");
                sb.append(db.getDialect().getName());
                sb.append("DataType.");

                try {
                    String type1 = getType(db, t, p, s, u, null);
                    String type2 = getType(db, t, 0, 0, u, null);

                    sb.append(FieldTypeHelper.normalise(t));

                    if (!type1.equals(type2)) {
                        Class<?> clazz = FieldTypeHelper.getDialectJavaType(db.getDialect(), t, p, s);

                        sb.append(".asNumberDataType(");
                        sb.append(clazz.getCanonicalName());
                        sb.append(".class)");
                    }
                }

                // Mostly because of unsupported data types
                catch (SQLDialectNotSupportedException e) {
                    sb.append("getDefaultDataType(\"");
                    sb.append(t);
                    sb.append("\")");
                }
            }
        }

        return sb.toString();
    }

    private boolean match(DataTypeDefinition type1, DataTypeDefinition type2) throws SQLException {
        return getJavaType(type1).equals(getJavaType(type2));
    }
}