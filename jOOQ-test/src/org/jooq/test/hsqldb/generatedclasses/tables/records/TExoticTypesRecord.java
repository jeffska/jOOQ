/**
 * This class is generated by jOOQ
 */
package org.jooq.test.hsqldb.generatedclasses.tables.records;

/**
 * This class is generated by jOOQ.
 */
@java.lang.SuppressWarnings("all")
public class TExoticTypesRecord extends org.jooq.impl.UpdatableRecordImpl<org.jooq.test.hsqldb.generatedclasses.tables.records.TExoticTypesRecord> implements org.jooq.Record2<java.lang.Integer, java.util.UUID>, org.jooq.test.hsqldb.generatedclasses.tables.interfaces.ITExoticTypes {

	private static final long serialVersionUID = 1168411230;

	/**
	 * Setter for <code>PUBLIC.T_EXOTIC_TYPES.ID</code>. 
	 */
	@Override
	public void setId(java.lang.Integer value) {
		setValue(org.jooq.test.hsqldb.generatedclasses.tables.TExoticTypes.T_EXOTIC_TYPES.ID, value);
	}

	/**
	 * Getter for <code>PUBLIC.T_EXOTIC_TYPES.ID</code>. 
	 */
	@Override
	public java.lang.Integer getId() {
		return getValue(org.jooq.test.hsqldb.generatedclasses.tables.TExoticTypes.T_EXOTIC_TYPES.ID);
	}

	/**
	 * Setter for <code>PUBLIC.T_EXOTIC_TYPES.UU</code>. 
	 */
	@Override
	public void setUu(java.util.UUID value) {
		setValue(org.jooq.test.hsqldb.generatedclasses.tables.TExoticTypes.T_EXOTIC_TYPES.UU, value);
	}

	/**
	 * Getter for <code>PUBLIC.T_EXOTIC_TYPES.UU</code>. 
	 */
	@Override
	public java.util.UUID getUu() {
		return getValue(org.jooq.test.hsqldb.generatedclasses.tables.TExoticTypes.T_EXOTIC_TYPES.UU);
	}

	// -------------------------------------------------------------------------
	// Primary key information
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Record1<java.lang.Integer> key() {
		return (org.jooq.Record1) super.key();
	}

	// -------------------------------------------------------------------------
	// Record2 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Row2<java.lang.Integer, java.util.UUID> fieldsRow() {
		return org.jooq.impl.Factory.row(field1(), field2());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Row2<java.lang.Integer, java.util.UUID> valuesRow() {
		return org.jooq.impl.Factory.row(value1(), value2());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Integer> field1() {
		return org.jooq.test.hsqldb.generatedclasses.tables.TExoticTypes.T_EXOTIC_TYPES.ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.util.UUID> field2() {
		return org.jooq.test.hsqldb.generatedclasses.tables.TExoticTypes.T_EXOTIC_TYPES.UU;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.Integer value1() {
		return getId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.util.UUID value2() {
		return getUu();
	}

	// -------------------------------------------------------------------------
	// FROM and INTO
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void from(org.jooq.test.hsqldb.generatedclasses.tables.interfaces.ITExoticTypes from) {
		setId(from.getId());
		setUu(from.getUu());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <E extends org.jooq.test.hsqldb.generatedclasses.tables.interfaces.ITExoticTypes> E into(E into) {
		into.from(this);
		return into;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached TExoticTypesRecord
	 */
	public TExoticTypesRecord() {
		super(org.jooq.test.hsqldb.generatedclasses.tables.TExoticTypes.T_EXOTIC_TYPES);
	}
}
