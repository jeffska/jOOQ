/**
 * This class is generated by jOOQ
 */
package org.jooq.test.oracle.generatedclasses.procedures;


import javax.annotation.Generated;

import org.jooq.Parameter;
import org.jooq.SQLDialect;
import org.jooq.impl.ParameterImpl;
import org.jooq.impl.StoredProcedureImpl;
import org.jooq.test.oracle.generatedclasses.Test;
import org.jooq.test.oracle.generatedclasses.udt.UAddressType;
import org.jooq.test.oracle.generatedclasses.udt.records.UAddressTypeRecord;
import org.jooq.util.oracle.OracleDataType;


/**
 * This class is generated by jOOQ.
 */
@Generated(value    = "http://jooq.sourceforge.net",
           comments = "This class is generated by jOOQ")
public class PEnhanceAddress1 extends StoredProcedureImpl {

	private static final long serialVersionUID = -979715896;


	/**
	 * An uncommented item
	 */
	public static final Parameter<UAddressTypeRecord> ADDRESS = new ParameterImpl<UAddressTypeRecord>(SQLDialect.ORACLE, "ADDRESS", UAddressType.U_ADDRESS_TYPE.getDataType());

	/**
	 * An uncommented item
	 */
	public static final Parameter<String> NO = new ParameterImpl<String>(SQLDialect.ORACLE, "NO", OracleDataType.VARCHAR2);

	/**
	 * Create a new procedure call instance
	 */
	public PEnhanceAddress1() {
		super(SQLDialect.ORACLE, "P_ENHANCE_ADDRESS1", Test.TEST);

		addInParameter(ADDRESS);
		addOutParameter(NO);
	}

	public void setAddress(UAddressTypeRecord value) {
		setValue(ADDRESS, value);
	}

	public String getNo() {
		return getValue(NO);
	}
}