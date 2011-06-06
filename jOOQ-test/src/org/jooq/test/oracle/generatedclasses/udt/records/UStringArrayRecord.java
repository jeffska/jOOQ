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
public class UStringArrayRecord extends ArrayRecordImpl<String> {

	private static final long serialVersionUID = 980299389;

	public UStringArrayRecord(Configuration configuration) {
		super("TEST.U_STRING_ARRAY", OracleDataType.VARCHAR2, configuration);
	}

	public UStringArrayRecord(Configuration configuration, String... array) {
		this(configuration);
		set(array);
	}

	public UStringArrayRecord(Configuration configuration, List<? extends String> list) {
		this(configuration);
		setList(list);
	}
}