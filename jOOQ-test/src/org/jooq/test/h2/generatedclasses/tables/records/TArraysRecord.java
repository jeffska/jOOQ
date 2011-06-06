/**
 * This class is generated by jOOQ
 */
package org.jooq.test.h2.generatedclasses.tables.records;


import javax.annotation.Generated;

import org.jooq.Configuration;
import org.jooq.impl.UpdatableRecordImpl;
import org.jooq.test.h2.generatedclasses.tables.TArrays;


/**
 * This class is generated by jOOQ.
 */
@Generated(value    = "http://jooq.sourceforge.net",
           comments = "This class is generated by jOOQ")
public class TArraysRecord extends UpdatableRecordImpl<TArraysRecord> {

	private static final long serialVersionUID = -50129087;

	/**
	 * An uncommented item
	 * 
	 * PRIMARY KEY
	 */
	public void setId(Integer value) {
		setValue(TArrays.ID, value);
	}

	/**
	 * An uncommented item
	 * 
	 * PRIMARY KEY
	 */
	public Integer getId() {
		return getValue(TArrays.ID);
	}

	/**
	 * An uncommented item
	 * 
	 * The SQL type of this item (ARRAY) could not be mapped.<br/>
	 * Deserialising this field might not work!
	 */
	public void setStringArray(Object[] value) {
		setValue(TArrays.STRING_ARRAY, value);
	}

	/**
	 * An uncommented item
	 * 
	 * The SQL type of this item (ARRAY) could not be mapped.<br/>
	 * Deserialising this field might not work!
	 */
	public Object[] getStringArray() {
		return getValue(TArrays.STRING_ARRAY);
	}

	/**
	 * An uncommented item
	 * 
	 * The SQL type of this item (ARRAY) could not be mapped.<br/>
	 * Deserialising this field might not work!
	 */
	public void setNumberArray(Object[] value) {
		setValue(TArrays.NUMBER_ARRAY, value);
	}

	/**
	 * An uncommented item
	 * 
	 * The SQL type of this item (ARRAY) could not be mapped.<br/>
	 * Deserialising this field might not work!
	 */
	public Object[] getNumberArray() {
		return getValue(TArrays.NUMBER_ARRAY);
	}

	/**
	 * An uncommented item
	 * 
	 * The SQL type of this item (ARRAY) could not be mapped.<br/>
	 * Deserialising this field might not work!
	 */
	public void setDateArray(Object[] value) {
		setValue(TArrays.DATE_ARRAY, value);
	}

	/**
	 * An uncommented item
	 * 
	 * The SQL type of this item (ARRAY) could not be mapped.<br/>
	 * Deserialising this field might not work!
	 */
	public Object[] getDateArray() {
		return getValue(TArrays.DATE_ARRAY);
	}
	/**
	 * Create a detached TArraysRecord
	 */
	public TArraysRecord() {
		super(TArrays.T_ARRAYS);
	}

	/**
	 * Create an attached TArraysRecord
	 */
	public TArraysRecord(Configuration configuration) {
		super(TArrays.T_ARRAYS, configuration);
	}
}