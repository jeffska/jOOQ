/**
 * This class is generated by jOOQ
 */
package org.jooq.test.sybase.generatedclasses.tables;


import javax.annotation.Generated;

import org.jooq.SQLDialect;
import org.jooq.TableField;
import org.jooq.impl.TableFieldImpl;
import org.jooq.impl.TableImpl;
import org.jooq.test.sybase.generatedclasses.Dba;
import org.jooq.test.sybase.generatedclasses.tables.records.VLibraryRecord;
import org.jooq.util.sybase.SybaseDataType;


/**
 * This class is generated by jOOQ.
 */
@Generated(value    = "http://jooq.sourceforge.net",
           comments = "This class is generated by jOOQ")
public class VLibrary extends TableImpl<VLibraryRecord> {

	private static final long serialVersionUID = 1462068941;

	/**
	 * The singleton instance of V_LIBRARY
	 */
	public static final VLibrary V_LIBRARY = new VLibrary();

	/**
	 * The class holding records for this type
	 */
	private static final Class<VLibraryRecord> __RECORD_TYPE = VLibraryRecord.class;

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<VLibraryRecord> getRecordType() {
		return __RECORD_TYPE;
	}

	/**
	 * An uncommented item
	 */
	public static final TableField<VLibraryRecord, String> AUTHOR = new TableFieldImpl<VLibraryRecord, String>(SQLDialect.SYBASE, "AUTHOR", SybaseDataType.VARCHAR, V_LIBRARY);

	/**
	 * An uncommented item
	 */
	public static final TableField<VLibraryRecord, String> TITLE = new TableFieldImpl<VLibraryRecord, String>(SQLDialect.SYBASE, "TITLE", SybaseDataType.VARCHAR, V_LIBRARY);

	/**
	 * No further instances allowed
	 */
	private VLibrary() {
		super(SQLDialect.SYBASE, "V_LIBRARY", Dba.DBA);
	}
}