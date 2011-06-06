/**
 * This class is generated by jOOQ
 */
package org.jooq.test.oracle.generatedclasses.tables;


import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.ForeignKey;
import org.jooq.SQLDialect;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.TableFieldImpl;
import org.jooq.impl.UpdatableTableImpl;
import org.jooq.test.oracle.generatedclasses.Keys;
import org.jooq.test.oracle.generatedclasses.Test;
import org.jooq.test.oracle.generatedclasses.tables.records.XTestCase_85Record;
import org.jooq.util.oracle.OracleDataType;


/**
 * This class is generated by jOOQ.
 */
@Generated(value    = "http://jooq.sourceforge.net",
           comments = "This class is generated by jOOQ")
public class XTestCase_85 extends UpdatableTableImpl<XTestCase_85Record> {

	private static final long serialVersionUID = -273590482;

	/**
	 * The singleton instance of X_TEST_CASE_85
	 */
	public static final XTestCase_85 X_TEST_CASE_85 = new XTestCase_85();

	/**
	 * The class holding records for this type
	 */
	private static final Class<XTestCase_85Record> __RECORD_TYPE = XTestCase_85Record.class;

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<XTestCase_85Record> getRecordType() {
		return __RECORD_TYPE;
	}

	/**
	 * An uncommented item
	 * 
	 * PRIMARY KEY
	 */
	public static final TableField<XTestCase_85Record, Integer> ID = new TableFieldImpl<XTestCase_85Record, Integer>(SQLDialect.ORACLE, "ID", OracleDataType.NUMBER.asNumberDataType(Integer.class), X_TEST_CASE_85);

	/**
	 * An uncommented item
	 * <p>
	 * <code><pre>
	 * FOREIGN KEY [TEST.X_TEST_CASE_85.X_UNUSED_ID, TEST.X_TEST_CASE_85.X_UNUSED_NAME]
	 * REFERENCES X_UNUSED [TEST.X_UNUSED.ID, TEST.X_UNUSED.NAME]
	 * </pre></code>
	 */
	public static final TableField<XTestCase_85Record, Integer> X_UNUSED_ID = new TableFieldImpl<XTestCase_85Record, Integer>(SQLDialect.ORACLE, "X_UNUSED_ID", OracleDataType.NUMBER.asNumberDataType(Integer.class), X_TEST_CASE_85);

	/**
	 * An uncommented item
	 * <p>
	 * <code><pre>
	 * FOREIGN KEY [TEST.X_TEST_CASE_85.X_UNUSED_ID, TEST.X_TEST_CASE_85.X_UNUSED_NAME]
	 * REFERENCES X_UNUSED [TEST.X_UNUSED.ID, TEST.X_UNUSED.NAME]
	 * </pre></code>
	 */
	public static final TableField<XTestCase_85Record, String> X_UNUSED_NAME = new TableFieldImpl<XTestCase_85Record, String>(SQLDialect.ORACLE, "X_UNUSED_NAME", OracleDataType.VARCHAR2, X_TEST_CASE_85);

	/**
	 * No further instances allowed
	 */
	private XTestCase_85() {
		super(SQLDialect.ORACLE, "X_TEST_CASE_85", Test.TEST);
	}

	@Override
	public UniqueKey<XTestCase_85Record> getMainKey() {
		return Keys.PK_X_TEST_CASE_85;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<UniqueKey<XTestCase_85Record>> getKeys() {
		return Arrays.<UniqueKey<XTestCase_85Record>>asList(Keys.PK_X_TEST_CASE_85);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<ForeignKey<XTestCase_85Record, ?>> getReferences() {
		return Arrays.<ForeignKey<XTestCase_85Record, ?>>asList(Keys.FK_X_TEST_CASE_85);
	}
}