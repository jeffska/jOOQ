/**
 * This class is generated by jOOQ
 */
package org.jooq.examples.sqlserver.adventureworks.production.tables.records;

/**
 * This class is generated by jOOQ.
 */
@java.lang.SuppressWarnings("all")
@javax.persistence.Entity
@javax.persistence.Table(name = "UnitMeasure", schema = "Production")
public class UnitMeasure extends org.jooq.impl.UpdatableRecordImpl<org.jooq.examples.sqlserver.adventureworks.production.tables.records.UnitMeasure> implements org.jooq.Record3<java.lang.String, java.lang.String, java.sql.Timestamp> {

	private static final long serialVersionUID = -228740870;

	/**
	 * Setter for <code>Production.UnitMeasure.UnitMeasureCode</code>. 
	 */
	public void setUnitMeasureCode(java.lang.String value) {
		setValue(org.jooq.examples.sqlserver.adventureworks.production.tables.UnitMeasure.UnitMeasure.UnitMeasureCode, value);
	}

	/**
	 * Getter for <code>Production.UnitMeasure.UnitMeasureCode</code>. 
	 */
	@javax.persistence.Id
	@javax.persistence.Column(name = "UnitMeasureCode", unique = true, nullable = false, length = 3)
	public java.lang.String getUnitMeasureCode() {
		return getValue(org.jooq.examples.sqlserver.adventureworks.production.tables.UnitMeasure.UnitMeasure.UnitMeasureCode);
	}

	/**
	 * Setter for <code>Production.UnitMeasure.Name</code>. 
	 */
	public void setName(java.lang.String value) {
		setValue(org.jooq.examples.sqlserver.adventureworks.production.tables.UnitMeasure.UnitMeasure.Name, value);
	}

	/**
	 * Getter for <code>Production.UnitMeasure.Name</code>. 
	 */
	@javax.persistence.Column(name = "Name", nullable = false, length = 50)
	public java.lang.String getName() {
		return getValue(org.jooq.examples.sqlserver.adventureworks.production.tables.UnitMeasure.UnitMeasure.Name);
	}

	/**
	 * Setter for <code>Production.UnitMeasure.ModifiedDate</code>. 
	 */
	public void setModifiedDate(java.sql.Timestamp value) {
		setValue(org.jooq.examples.sqlserver.adventureworks.production.tables.UnitMeasure.UnitMeasure.ModifiedDate, value);
	}

	/**
	 * Getter for <code>Production.UnitMeasure.ModifiedDate</code>. 
	 */
	@javax.persistence.Column(name = "ModifiedDate", nullable = false)
	public java.sql.Timestamp getModifiedDate() {
		return getValue(org.jooq.examples.sqlserver.adventureworks.production.tables.UnitMeasure.UnitMeasure.ModifiedDate);
	}

	// -------------------------------------------------------------------------
	// Primary key information
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Record1<java.lang.String> key() {
		return (org.jooq.Record1) super.key();
	}

	// -------------------------------------------------------------------------
	// Record3 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Row3<java.lang.String, java.lang.String, java.sql.Timestamp> fieldsRow() {
		return org.jooq.impl.Factory.row(field1(), field2(), field3());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Row3<java.lang.String, java.lang.String, java.sql.Timestamp> valuesRow() {
		return org.jooq.impl.Factory.row(value1(), value2(), value3());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field1() {
		return org.jooq.examples.sqlserver.adventureworks.production.tables.UnitMeasure.UnitMeasure.UnitMeasureCode;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field2() {
		return org.jooq.examples.sqlserver.adventureworks.production.tables.UnitMeasure.UnitMeasure.Name;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.sql.Timestamp> field3() {
		return org.jooq.examples.sqlserver.adventureworks.production.tables.UnitMeasure.UnitMeasure.ModifiedDate;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value1() {
		return getUnitMeasureCode();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value2() {
		return getName();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.sql.Timestamp value3() {
		return getModifiedDate();
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached UnitMeasure
	 */
	public UnitMeasure() {
		super(org.jooq.examples.sqlserver.adventureworks.production.tables.UnitMeasure.UnitMeasure);
	}
}
