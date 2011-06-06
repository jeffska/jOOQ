/**
 * This class is generated by jOOQ
 */
package org.jooq.test.oracle.generatedclasses.udt.records;


import java.util.List;

import javax.annotation.Generated;

import org.jooq.Configuration;
import org.jooq.impl.ArrayRecordImpl;
import org.jooq.util.oracle.OracleDataType;


/**
 * This class is generated by jOOQ.
 */
@Generated(value    = "http://jooq.sourceforge.net",
           comments = "This class is generated by jOOQ")
public class UNumberLongArrayRecord extends ArrayRecordImpl<Long> {

	private static final long serialVersionUID = -752226329;

	public UNumberLongArrayRecord(Configuration configuration) {
		super("TEST.U_NUMBER_LONG_ARRAY", OracleDataType.NUMBER.asNumberDataType(Long.class), configuration);
	}

	public UNumberLongArrayRecord(Configuration configuration, Long... array) {
		this(configuration);
		set(array);
	}

	public UNumberLongArrayRecord(Configuration configuration, List<? extends Long> list) {
		this(configuration);
		setList(list);
	}
}