/**
 * This class is generated by jOOQ
 */
package org.jooq.test.postgres.generatedclasses.tables;

/**
 * This class is generated by jOOQ.
 */
@java.lang.SuppressWarnings("all")
public class TExoticTypes extends org.jooq.impl.UpdatableTableImpl<org.jooq.test.postgres.generatedclasses.tables.records.TExoticTypesRecord> {

	private static final long serialVersionUID = 1482408730;

	/**
	 * The singleton instance of <code>public.t_exotic_types</code>
	 */
	public static final org.jooq.test.postgres.generatedclasses.tables.TExoticTypes T_EXOTIC_TYPES = new org.jooq.test.postgres.generatedclasses.tables.TExoticTypes();

	/**
	 * The class holding records for this type
	 */
	@Override
	public java.lang.Class<org.jooq.test.postgres.generatedclasses.tables.records.TExoticTypesRecord> getRecordType() {
		return org.jooq.test.postgres.generatedclasses.tables.records.TExoticTypesRecord.class;
	}

	/**
	 * The column <code>public.t_exotic_types.id</code>. 
	 */
	public final org.jooq.TableField<org.jooq.test.postgres.generatedclasses.tables.records.TExoticTypesRecord, java.lang.Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER, this);

	/**
	 * The column <code>public.t_exotic_types.uu</code>. 
	 */
	public final org.jooq.TableField<org.jooq.test.postgres.generatedclasses.tables.records.TExoticTypesRecord, java.util.UUID> UU = createField("uu", org.jooq.impl.SQLDataType.UUID, this);

	/**
	 * Create a <code>public.t_exotic_types</code> table reference
	 */
	public TExoticTypes() {
		super("t_exotic_types", org.jooq.test.postgres.generatedclasses.Public.PUBLIC);
	}

	/**
	 * Create an aliased <code>public.t_exotic_types</code> table reference
	 */
	public TExoticTypes(java.lang.String alias) {
		super(alias, org.jooq.test.postgres.generatedclasses.Public.PUBLIC, org.jooq.test.postgres.generatedclasses.tables.TExoticTypes.T_EXOTIC_TYPES);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.UniqueKey<org.jooq.test.postgres.generatedclasses.tables.records.TExoticTypesRecord> getMainKey() {
		return org.jooq.test.postgres.generatedclasses.Keys.PK_T_EXOTIC_TYPES;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.util.List<org.jooq.UniqueKey<org.jooq.test.postgres.generatedclasses.tables.records.TExoticTypesRecord>> getKeys() {
		return java.util.Arrays.<org.jooq.UniqueKey<org.jooq.test.postgres.generatedclasses.tables.records.TExoticTypesRecord>>asList(org.jooq.test.postgres.generatedclasses.Keys.PK_T_EXOTIC_TYPES);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.test.postgres.generatedclasses.tables.TExoticTypes as(java.lang.String alias) {
		return new org.jooq.test.postgres.generatedclasses.tables.TExoticTypes(alias);
	}
}
