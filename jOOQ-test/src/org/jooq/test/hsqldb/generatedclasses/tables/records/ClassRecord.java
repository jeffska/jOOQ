/**
 * This class is generated by jOOQ
 */
package org.jooq.test.hsqldb.generatedclasses.tables.records;

/**
 * This class is generated by jOOQ.
 */
@javax.annotation.Generated(value    = "http://jooq.sourceforge.net",
                            comments = "This class is generated by jOOQ")
public class ClassRecord extends org.jooq.impl.TableRecordImpl<org.jooq.test.hsqldb.generatedclasses.tables.records.ClassRecord> {

	private static final long serialVersionUID = -1166756562;

	/**
	 * An uncommented item
	 * 
	 * This item causes a name clash. That is why an underline character was appended to the Java field name
	 */
	public void setClass_(java.lang.Integer value) {
		setValue(org.jooq.test.hsqldb.generatedclasses.tables.Class.CLASS_, value);
	}

	/**
	 * An uncommented item
	 * 
	 * This item causes a name clash. That is why an underline character was appended to the Java field name
	 */
	public java.lang.Integer getClass_() {
		return getValue(org.jooq.test.hsqldb.generatedclasses.tables.Class.CLASS_);
	}

	/**
	 * Create a detached ClassRecord
	 */
	public ClassRecord() {
		super(org.jooq.test.hsqldb.generatedclasses.tables.Class.CLASS);
	}

	/**
	 * Create an attached ClassRecord
	 * @deprecated - (#363) use the other constructor instead
	 */
	@Deprecated
	public ClassRecord(org.jooq.Configuration configuration) {
		super(org.jooq.test.hsqldb.generatedclasses.tables.Class.CLASS, configuration);
	}
}