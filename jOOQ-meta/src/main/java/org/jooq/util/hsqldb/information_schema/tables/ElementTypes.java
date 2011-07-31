/**
 * This class is generated by jOOQ
 */
package org.jooq.util.hsqldb.information_schema.tables;

/**
 * This class is generated by jOOQ.
 */
@javax.annotation.Generated(value    = "http://jooq.sourceforge.net",
                            comments = "This class is generated by jOOQ")
public class ElementTypes extends org.jooq.impl.TableImpl<org.jooq.util.hsqldb.information_schema.tables.records.ElementTypesRecord> {

	private static final long serialVersionUID = 427398722;

	/**
	 * The singleton instance of ELEMENT_TYPES
	 */
	public static final org.jooq.util.hsqldb.information_schema.tables.ElementTypes ELEMENT_TYPES = new org.jooq.util.hsqldb.information_schema.tables.ElementTypes();

	/**
	 * The class holding records for this type
	 */
	private static final java.lang.Class<org.jooq.util.hsqldb.information_schema.tables.records.ElementTypesRecord> __RECORD_TYPE = org.jooq.util.hsqldb.information_schema.tables.records.ElementTypesRecord.class;

	/**
	 * The class holding records for this type
	 */
	@Override
	public java.lang.Class<org.jooq.util.hsqldb.information_schema.tables.records.ElementTypesRecord> getRecordType() {
		return __RECORD_TYPE;
	}

	/**
	 * An uncommented item
	 */
	public static final org.jooq.TableField<org.jooq.util.hsqldb.information_schema.tables.records.ElementTypesRecord, java.lang.String> OBJECT_CATALOG = new org.jooq.impl.TableFieldImpl<org.jooq.util.hsqldb.information_schema.tables.records.ElementTypesRecord, java.lang.String>("OBJECT_CATALOG", org.jooq.impl.SQLDataType.VARCHAR, ELEMENT_TYPES);

	/**
	 * An uncommented item
	 */
	public static final org.jooq.TableField<org.jooq.util.hsqldb.information_schema.tables.records.ElementTypesRecord, java.lang.String> OBJECT_SCHEMA = new org.jooq.impl.TableFieldImpl<org.jooq.util.hsqldb.information_schema.tables.records.ElementTypesRecord, java.lang.String>("OBJECT_SCHEMA", org.jooq.impl.SQLDataType.VARCHAR, ELEMENT_TYPES);

	/**
	 * An uncommented item
	 */
	public static final org.jooq.TableField<org.jooq.util.hsqldb.information_schema.tables.records.ElementTypesRecord, java.lang.String> OBJECT_NAME = new org.jooq.impl.TableFieldImpl<org.jooq.util.hsqldb.information_schema.tables.records.ElementTypesRecord, java.lang.String>("OBJECT_NAME", org.jooq.impl.SQLDataType.VARCHAR, ELEMENT_TYPES);

	/**
	 * An uncommented item
	 */
	public static final org.jooq.TableField<org.jooq.util.hsqldb.information_schema.tables.records.ElementTypesRecord, java.lang.String> OBJECT_TYPE = new org.jooq.impl.TableFieldImpl<org.jooq.util.hsqldb.information_schema.tables.records.ElementTypesRecord, java.lang.String>("OBJECT_TYPE", org.jooq.impl.SQLDataType.VARCHAR, ELEMENT_TYPES);

	/**
	 * An uncommented item
	 */
	public static final org.jooq.TableField<org.jooq.util.hsqldb.information_schema.tables.records.ElementTypesRecord, java.lang.String> COLLECTION_TYPE_IDENTIFIER = new org.jooq.impl.TableFieldImpl<org.jooq.util.hsqldb.information_schema.tables.records.ElementTypesRecord, java.lang.String>("COLLECTION_TYPE_IDENTIFIER", org.jooq.impl.SQLDataType.VARCHAR, ELEMENT_TYPES);

	/**
	 * An uncommented item
	 */
	public static final org.jooq.TableField<org.jooq.util.hsqldb.information_schema.tables.records.ElementTypesRecord, java.lang.String> DATA_TYPE = new org.jooq.impl.TableFieldImpl<org.jooq.util.hsqldb.information_schema.tables.records.ElementTypesRecord, java.lang.String>("DATA_TYPE", org.jooq.impl.SQLDataType.VARCHAR, ELEMENT_TYPES);

	/**
	 * An uncommented item
	 */
	public static final org.jooq.TableField<org.jooq.util.hsqldb.information_schema.tables.records.ElementTypesRecord, java.lang.Long> CHARACTER_MAXIMUM_LENGTH = new org.jooq.impl.TableFieldImpl<org.jooq.util.hsqldb.information_schema.tables.records.ElementTypesRecord, java.lang.Long>("CHARACTER_MAXIMUM_LENGTH", org.jooq.impl.SQLDataType.BIGINT, ELEMENT_TYPES);

	/**
	 * An uncommented item
	 */
	public static final org.jooq.TableField<org.jooq.util.hsqldb.information_schema.tables.records.ElementTypesRecord, java.lang.Long> CHARACTER_OCTET_LENGTH = new org.jooq.impl.TableFieldImpl<org.jooq.util.hsqldb.information_schema.tables.records.ElementTypesRecord, java.lang.Long>("CHARACTER_OCTET_LENGTH", org.jooq.impl.SQLDataType.BIGINT, ELEMENT_TYPES);

	/**
	 * An uncommented item
	 */
	public static final org.jooq.TableField<org.jooq.util.hsqldb.information_schema.tables.records.ElementTypesRecord, java.lang.String> CHARACTER_SET_CATALOG = new org.jooq.impl.TableFieldImpl<org.jooq.util.hsqldb.information_schema.tables.records.ElementTypesRecord, java.lang.String>("CHARACTER_SET_CATALOG", org.jooq.impl.SQLDataType.VARCHAR, ELEMENT_TYPES);

	/**
	 * An uncommented item
	 */
	public static final org.jooq.TableField<org.jooq.util.hsqldb.information_schema.tables.records.ElementTypesRecord, java.lang.String> CHARACTER_SET_SCHEMA = new org.jooq.impl.TableFieldImpl<org.jooq.util.hsqldb.information_schema.tables.records.ElementTypesRecord, java.lang.String>("CHARACTER_SET_SCHEMA", org.jooq.impl.SQLDataType.VARCHAR, ELEMENT_TYPES);

	/**
	 * An uncommented item
	 */
	public static final org.jooq.TableField<org.jooq.util.hsqldb.information_schema.tables.records.ElementTypesRecord, java.lang.String> CHARACTER_SET_NAME = new org.jooq.impl.TableFieldImpl<org.jooq.util.hsqldb.information_schema.tables.records.ElementTypesRecord, java.lang.String>("CHARACTER_SET_NAME", org.jooq.impl.SQLDataType.VARCHAR, ELEMENT_TYPES);

	/**
	 * An uncommented item
	 */
	public static final org.jooq.TableField<org.jooq.util.hsqldb.information_schema.tables.records.ElementTypesRecord, java.lang.String> COLLATION_CATALOG = new org.jooq.impl.TableFieldImpl<org.jooq.util.hsqldb.information_schema.tables.records.ElementTypesRecord, java.lang.String>("COLLATION_CATALOG", org.jooq.impl.SQLDataType.VARCHAR, ELEMENT_TYPES);

	/**
	 * An uncommented item
	 */
	public static final org.jooq.TableField<org.jooq.util.hsqldb.information_schema.tables.records.ElementTypesRecord, java.lang.String> COLLATION_SCHEMA = new org.jooq.impl.TableFieldImpl<org.jooq.util.hsqldb.information_schema.tables.records.ElementTypesRecord, java.lang.String>("COLLATION_SCHEMA", org.jooq.impl.SQLDataType.VARCHAR, ELEMENT_TYPES);

	/**
	 * An uncommented item
	 */
	public static final org.jooq.TableField<org.jooq.util.hsqldb.information_schema.tables.records.ElementTypesRecord, java.lang.String> COLLATION_NAME = new org.jooq.impl.TableFieldImpl<org.jooq.util.hsqldb.information_schema.tables.records.ElementTypesRecord, java.lang.String>("COLLATION_NAME", org.jooq.impl.SQLDataType.VARCHAR, ELEMENT_TYPES);

	/**
	 * An uncommented item
	 */
	public static final org.jooq.TableField<org.jooq.util.hsqldb.information_schema.tables.records.ElementTypesRecord, java.lang.Long> NUMERIC_PRECISION = new org.jooq.impl.TableFieldImpl<org.jooq.util.hsqldb.information_schema.tables.records.ElementTypesRecord, java.lang.Long>("NUMERIC_PRECISION", org.jooq.impl.SQLDataType.BIGINT, ELEMENT_TYPES);

	/**
	 * An uncommented item
	 */
	public static final org.jooq.TableField<org.jooq.util.hsqldb.information_schema.tables.records.ElementTypesRecord, java.lang.Long> NUMERIC_PRECISION_RADIX = new org.jooq.impl.TableFieldImpl<org.jooq.util.hsqldb.information_schema.tables.records.ElementTypesRecord, java.lang.Long>("NUMERIC_PRECISION_RADIX", org.jooq.impl.SQLDataType.BIGINT, ELEMENT_TYPES);

	/**
	 * An uncommented item
	 */
	public static final org.jooq.TableField<org.jooq.util.hsqldb.information_schema.tables.records.ElementTypesRecord, java.lang.Long> NUMERIC_SCALE = new org.jooq.impl.TableFieldImpl<org.jooq.util.hsqldb.information_schema.tables.records.ElementTypesRecord, java.lang.Long>("NUMERIC_SCALE", org.jooq.impl.SQLDataType.BIGINT, ELEMENT_TYPES);

	/**
	 * An uncommented item
	 */
	public static final org.jooq.TableField<org.jooq.util.hsqldb.information_schema.tables.records.ElementTypesRecord, java.lang.Long> DATETIME_PRECISION = new org.jooq.impl.TableFieldImpl<org.jooq.util.hsqldb.information_schema.tables.records.ElementTypesRecord, java.lang.Long>("DATETIME_PRECISION", org.jooq.impl.SQLDataType.BIGINT, ELEMENT_TYPES);

	/**
	 * An uncommented item
	 */
	public static final org.jooq.TableField<org.jooq.util.hsqldb.information_schema.tables.records.ElementTypesRecord, java.lang.String> INTERVAL_TYPE = new org.jooq.impl.TableFieldImpl<org.jooq.util.hsqldb.information_schema.tables.records.ElementTypesRecord, java.lang.String>("INTERVAL_TYPE", org.jooq.impl.SQLDataType.VARCHAR, ELEMENT_TYPES);

	/**
	 * An uncommented item
	 */
	public static final org.jooq.TableField<org.jooq.util.hsqldb.information_schema.tables.records.ElementTypesRecord, java.lang.Long> INTERVAL_PRECISION = new org.jooq.impl.TableFieldImpl<org.jooq.util.hsqldb.information_schema.tables.records.ElementTypesRecord, java.lang.Long>("INTERVAL_PRECISION", org.jooq.impl.SQLDataType.BIGINT, ELEMENT_TYPES);

	/**
	 * An uncommented item
	 */
	public static final org.jooq.TableField<org.jooq.util.hsqldb.information_schema.tables.records.ElementTypesRecord, java.lang.String> UDT_CATALOG = new org.jooq.impl.TableFieldImpl<org.jooq.util.hsqldb.information_schema.tables.records.ElementTypesRecord, java.lang.String>("UDT_CATALOG", org.jooq.impl.SQLDataType.VARCHAR, ELEMENT_TYPES);

	/**
	 * An uncommented item
	 */
	public static final org.jooq.TableField<org.jooq.util.hsqldb.information_schema.tables.records.ElementTypesRecord, java.lang.String> UDT_SCHEMA = new org.jooq.impl.TableFieldImpl<org.jooq.util.hsqldb.information_schema.tables.records.ElementTypesRecord, java.lang.String>("UDT_SCHEMA", org.jooq.impl.SQLDataType.VARCHAR, ELEMENT_TYPES);

	/**
	 * An uncommented item
	 */
	public static final org.jooq.TableField<org.jooq.util.hsqldb.information_schema.tables.records.ElementTypesRecord, java.lang.String> UDT_NAME = new org.jooq.impl.TableFieldImpl<org.jooq.util.hsqldb.information_schema.tables.records.ElementTypesRecord, java.lang.String>("UDT_NAME", org.jooq.impl.SQLDataType.VARCHAR, ELEMENT_TYPES);

	/**
	 * An uncommented item
	 */
	public static final org.jooq.TableField<org.jooq.util.hsqldb.information_schema.tables.records.ElementTypesRecord, java.lang.String> SCOPE_CATALOG = new org.jooq.impl.TableFieldImpl<org.jooq.util.hsqldb.information_schema.tables.records.ElementTypesRecord, java.lang.String>("SCOPE_CATALOG", org.jooq.impl.SQLDataType.VARCHAR, ELEMENT_TYPES);

	/**
	 * An uncommented item
	 */
	public static final org.jooq.TableField<org.jooq.util.hsqldb.information_schema.tables.records.ElementTypesRecord, java.lang.String> SCOPE_SCHEMA = new org.jooq.impl.TableFieldImpl<org.jooq.util.hsqldb.information_schema.tables.records.ElementTypesRecord, java.lang.String>("SCOPE_SCHEMA", org.jooq.impl.SQLDataType.VARCHAR, ELEMENT_TYPES);

	/**
	 * An uncommented item
	 */
	public static final org.jooq.TableField<org.jooq.util.hsqldb.information_schema.tables.records.ElementTypesRecord, java.lang.String> SCOPE_NAME = new org.jooq.impl.TableFieldImpl<org.jooq.util.hsqldb.information_schema.tables.records.ElementTypesRecord, java.lang.String>("SCOPE_NAME", org.jooq.impl.SQLDataType.VARCHAR, ELEMENT_TYPES);

	/**
	 * An uncommented item
	 */
	public static final org.jooq.TableField<org.jooq.util.hsqldb.information_schema.tables.records.ElementTypesRecord, java.lang.Long> MAXIMUM_CARDINALITY = new org.jooq.impl.TableFieldImpl<org.jooq.util.hsqldb.information_schema.tables.records.ElementTypesRecord, java.lang.Long>("MAXIMUM_CARDINALITY", org.jooq.impl.SQLDataType.BIGINT, ELEMENT_TYPES);

	/**
	 * An uncommented item
	 */
	public static final org.jooq.TableField<org.jooq.util.hsqldb.information_schema.tables.records.ElementTypesRecord, java.lang.String> DTD_IDENTIFIER = new org.jooq.impl.TableFieldImpl<org.jooq.util.hsqldb.information_schema.tables.records.ElementTypesRecord, java.lang.String>("DTD_IDENTIFIER", org.jooq.impl.SQLDataType.VARCHAR, ELEMENT_TYPES);

	/**
	 * An uncommented item
	 */
	public static final org.jooq.TableField<org.jooq.util.hsqldb.information_schema.tables.records.ElementTypesRecord, java.lang.String> DECLARED_DATA_TYPE = new org.jooq.impl.TableFieldImpl<org.jooq.util.hsqldb.information_schema.tables.records.ElementTypesRecord, java.lang.String>("DECLARED_DATA_TYPE", org.jooq.impl.SQLDataType.VARCHAR, ELEMENT_TYPES);

	/**
	 * An uncommented item
	 */
	public static final org.jooq.TableField<org.jooq.util.hsqldb.information_schema.tables.records.ElementTypesRecord, java.lang.Long> DECLARED_NUMERIC_PRECISION = new org.jooq.impl.TableFieldImpl<org.jooq.util.hsqldb.information_schema.tables.records.ElementTypesRecord, java.lang.Long>("DECLARED_NUMERIC_PRECISION", org.jooq.impl.SQLDataType.BIGINT, ELEMENT_TYPES);

	/**
	 * An uncommented item
	 */
	public static final org.jooq.TableField<org.jooq.util.hsqldb.information_schema.tables.records.ElementTypesRecord, java.lang.Long> DECLARED_NUMERIC_SCALE = new org.jooq.impl.TableFieldImpl<org.jooq.util.hsqldb.information_schema.tables.records.ElementTypesRecord, java.lang.Long>("DECLARED_NUMERIC_SCALE", org.jooq.impl.SQLDataType.BIGINT, ELEMENT_TYPES);

	/**
	 * No further instances allowed
	 */
	private ElementTypes() {
		super("ELEMENT_TYPES", org.jooq.util.hsqldb.information_schema.InformationSchema.INFORMATION_SCHEMA);
	}
}