/**
 * This class is generated by jOOQ
 */
package org.jooq.test.mysql.generatedclasses.tables;

/**
 * This class is generated by jOOQ.
 */
@javax.annotation.Generated(value    = "http://jooq.sourceforge.net",
                            comments = "This class is generated by jOOQ")
public class TTriggers extends org.jooq.impl.UpdatableTableImpl<org.jooq.test.mysql.generatedclasses.tables.records.TTriggersRecord> {

	private static final long serialVersionUID = 755517789;

	/**
	 * The singleton instance of t_triggers
	 */
	public static final org.jooq.test.mysql.generatedclasses.tables.TTriggers T_TRIGGERS = new org.jooq.test.mysql.generatedclasses.tables.TTriggers();

	/**
	 * The class holding records for this type
	 */
	private static final java.lang.Class<org.jooq.test.mysql.generatedclasses.tables.records.TTriggersRecord> __RECORD_TYPE = org.jooq.test.mysql.generatedclasses.tables.records.TTriggersRecord.class;

	/**
	 * The class holding records for this type
	 */
	@Override
	public java.lang.Class<org.jooq.test.mysql.generatedclasses.tables.records.TTriggersRecord> getRecordType() {
		return __RECORD_TYPE;
	}

	/**
	 * An uncommented item
	 * 
	 * PRIMARY KEY
	 */
	public static final org.jooq.TableField<org.jooq.test.mysql.generatedclasses.tables.records.TTriggersRecord, java.lang.Integer> ID_GENERATED = new org.jooq.impl.TableFieldImpl<org.jooq.test.mysql.generatedclasses.tables.records.TTriggersRecord, java.lang.Integer>("id_generated", org.jooq.impl.SQLDataType.INTEGER, T_TRIGGERS);

	/**
	 * An uncommented item
	 */
	public static final org.jooq.TableField<org.jooq.test.mysql.generatedclasses.tables.records.TTriggersRecord, java.lang.Integer> ID = new org.jooq.impl.TableFieldImpl<org.jooq.test.mysql.generatedclasses.tables.records.TTriggersRecord, java.lang.Integer>("id", org.jooq.impl.SQLDataType.INTEGER, T_TRIGGERS);

	/**
	 * An uncommented item
	 */
	public static final org.jooq.TableField<org.jooq.test.mysql.generatedclasses.tables.records.TTriggersRecord, java.lang.Integer> COUNTER = new org.jooq.impl.TableFieldImpl<org.jooq.test.mysql.generatedclasses.tables.records.TTriggersRecord, java.lang.Integer>("counter", org.jooq.impl.SQLDataType.INTEGER, T_TRIGGERS);

	/**
	 * No further instances allowed
	 */
	private TTriggers() {
		super("t_triggers", org.jooq.test.mysql.generatedclasses.Test.TEST);
	}

	@Override
	public org.jooq.Identity<org.jooq.test.mysql.generatedclasses.tables.records.TTriggersRecord, java.lang.Integer> getIdentity() {
		return org.jooq.test.mysql.generatedclasses.Keys.IDENTITY_t_triggers;
	}

	@Override
	public org.jooq.UniqueKey<org.jooq.test.mysql.generatedclasses.tables.records.TTriggersRecord> getMainKey() {
		return org.jooq.test.mysql.generatedclasses.Keys.KEY_t_triggers_PRIMARY;
	}

	@Override
	@SuppressWarnings("unchecked")
	public java.util.List<org.jooq.UniqueKey<org.jooq.test.mysql.generatedclasses.tables.records.TTriggersRecord>> getKeys() {
		return java.util.Arrays.<org.jooq.UniqueKey<org.jooq.test.mysql.generatedclasses.tables.records.TTriggersRecord>>asList(org.jooq.test.mysql.generatedclasses.Keys.KEY_t_triggers_PRIMARY);
	}
}