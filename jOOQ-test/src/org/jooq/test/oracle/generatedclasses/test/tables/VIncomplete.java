/**
 * This class is generated by jOOQ
 */
package org.jooq.test.oracle.generatedclasses.test.tables;

/**
 * This class is generated by jOOQ.
 */
@java.lang.SuppressWarnings("all")
public class VIncomplete extends org.jooq.impl.TableImpl<org.jooq.test.oracle.generatedclasses.test.tables.records.VIncompleteRecord> {

	private static final long serialVersionUID = 1452793158;

	/**
	 * The singleton instance of <code>TEST.V_INCOMPLETE</code>
	 */
	public static final org.jooq.test.oracle.generatedclasses.test.tables.VIncomplete V_INCOMPLETE = new org.jooq.test.oracle.generatedclasses.test.tables.VIncomplete();

	/**
	 * The class holding records for this type
	 */
	@Override
	public java.lang.Class<org.jooq.test.oracle.generatedclasses.test.tables.records.VIncompleteRecord> getRecordType() {
		return org.jooq.test.oracle.generatedclasses.test.tables.records.VIncompleteRecord.class;
	}

	/**
	 * The table column <code>TEST.V_INCOMPLETE.ID</code>
	 * <p>
	 * The SQL type of this item (UNDEFINED) could not be mapped.<br/>
	 * Deserialising this field might not work!
	 */
	public final org.jooq.TableField<org.jooq.test.oracle.generatedclasses.test.tables.records.VIncompleteRecord, java.lang.Object> ID = createField("ID", org.jooq.util.oracle.OracleDataType.getDefaultDataType("UNDEFINED"), this);

	/**
	 * The table column <code>TEST.V_INCOMPLETE.AUTHOR_ID</code>
	 * <p>
	 * The SQL type of this item (UNDEFINED) could not be mapped.<br/>
	 * Deserialising this field might not work!
	 */
	public final org.jooq.TableField<org.jooq.test.oracle.generatedclasses.test.tables.records.VIncompleteRecord, java.lang.Object> AUTHOR_ID = createField("AUTHOR_ID", org.jooq.util.oracle.OracleDataType.getDefaultDataType("UNDEFINED"), this);

	/**
	 * The table column <code>TEST.V_INCOMPLETE.CO_AUTHOR_ID</code>
	 * <p>
	 * The SQL type of this item (UNDEFINED) could not be mapped.<br/>
	 * Deserialising this field might not work!
	 */
	public final org.jooq.TableField<org.jooq.test.oracle.generatedclasses.test.tables.records.VIncompleteRecord, java.lang.Object> CO_AUTHOR_ID = createField("CO_AUTHOR_ID", org.jooq.util.oracle.OracleDataType.getDefaultDataType("UNDEFINED"), this);

	/**
	 * The table column <code>TEST.V_INCOMPLETE.DETAILS_ID</code>
	 * <p>
	 * The SQL type of this item (UNDEFINED) could not be mapped.<br/>
	 * Deserialising this field might not work!
	 */
	public final org.jooq.TableField<org.jooq.test.oracle.generatedclasses.test.tables.records.VIncompleteRecord, java.lang.Object> DETAILS_ID = createField("DETAILS_ID", org.jooq.util.oracle.OracleDataType.getDefaultDataType("UNDEFINED"), this);

	/**
	 * The table column <code>TEST.V_INCOMPLETE.TITLE</code>
	 * <p>
	 * The SQL type of this item (UNDEFINED) could not be mapped.<br/>
	 * Deserialising this field might not work!
	 */
	public final org.jooq.TableField<org.jooq.test.oracle.generatedclasses.test.tables.records.VIncompleteRecord, java.lang.Object> TITLE = createField("TITLE", org.jooq.util.oracle.OracleDataType.getDefaultDataType("UNDEFINED"), this);

	/**
	 * The table column <code>TEST.V_INCOMPLETE.PUBLISHED_IN</code>
	 * <p>
	 * The SQL type of this item (UNDEFINED) could not be mapped.<br/>
	 * Deserialising this field might not work!
	 */
	public final org.jooq.TableField<org.jooq.test.oracle.generatedclasses.test.tables.records.VIncompleteRecord, java.lang.Object> PUBLISHED_IN = createField("PUBLISHED_IN", org.jooq.util.oracle.OracleDataType.getDefaultDataType("UNDEFINED"), this);

	/**
	 * The table column <code>TEST.V_INCOMPLETE.LANGUAGE_ID</code>
	 * <p>
	 * The SQL type of this item (UNDEFINED) could not be mapped.<br/>
	 * Deserialising this field might not work!
	 */
	public final org.jooq.TableField<org.jooq.test.oracle.generatedclasses.test.tables.records.VIncompleteRecord, java.lang.Object> LANGUAGE_ID = createField("LANGUAGE_ID", org.jooq.util.oracle.OracleDataType.getDefaultDataType("UNDEFINED"), this);

	/**
	 * The table column <code>TEST.V_INCOMPLETE.CONTENT_TEXT</code>
	 * <p>
	 * The SQL type of this item (UNDEFINED) could not be mapped.<br/>
	 * Deserialising this field might not work!
	 */
	public final org.jooq.TableField<org.jooq.test.oracle.generatedclasses.test.tables.records.VIncompleteRecord, java.lang.Object> CONTENT_TEXT = createField("CONTENT_TEXT", org.jooq.util.oracle.OracleDataType.getDefaultDataType("UNDEFINED"), this);

	/**
	 * The table column <code>TEST.V_INCOMPLETE.CONTENT_PDF</code>
	 * <p>
	 * The SQL type of this item (UNDEFINED) could not be mapped.<br/>
	 * Deserialising this field might not work!
	 */
	public final org.jooq.TableField<org.jooq.test.oracle.generatedclasses.test.tables.records.VIncompleteRecord, java.lang.Object> CONTENT_PDF = createField("CONTENT_PDF", org.jooq.util.oracle.OracleDataType.getDefaultDataType("UNDEFINED"), this);

	/**
	 * Create a <code>TEST.V_INCOMPLETE</code> table reference
	 */
	public VIncomplete() {
		super("V_INCOMPLETE", org.jooq.test.oracle.generatedclasses.test.Test.TEST);
	}

	/**
	 * Create an aliased <code>TEST.V_INCOMPLETE</code> table reference
	 */
	public VIncomplete(java.lang.String alias) {
		super(alias, org.jooq.test.oracle.generatedclasses.test.Test.TEST, org.jooq.test.oracle.generatedclasses.test.tables.VIncomplete.V_INCOMPLETE);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.test.oracle.generatedclasses.test.tables.VIncomplete as(java.lang.String alias) {
		return new org.jooq.test.oracle.generatedclasses.test.tables.VIncomplete(alias);
	}
}
