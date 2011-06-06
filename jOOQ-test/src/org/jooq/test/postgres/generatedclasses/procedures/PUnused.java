/**
 * This class is generated by jOOQ
 */
package org.jooq.test.postgres.generatedclasses.procedures;


import javax.annotation.Generated;

import org.jooq.Parameter;
import org.jooq.SQLDialect;
import org.jooq.impl.ParameterImpl;
import org.jooq.impl.StoredProcedureImpl;
import org.jooq.test.postgres.generatedclasses.Public;
import org.jooq.util.postgres.PostgresDataType;


/**
 * This class is generated by jOOQ.
 */
@Generated(value    = "http://jooq.sourceforge.net",
           comments = "This class is generated by jOOQ")
public class PUnused extends StoredProcedureImpl {

	private static final long serialVersionUID = 1496070696;


	/**
	 * An uncommented item
	 */
	public static final Parameter<String> IN1 = new ParameterImpl<String>(SQLDialect.POSTGRES, "in1", PostgresDataType.CHARACTERVARYING);

	/**
	 * An uncommented item
	 */
	public static final Parameter<Integer> OUT1 = new ParameterImpl<Integer>(SQLDialect.POSTGRES, "out1", PostgresDataType.INTEGER);

	/**
	 * An uncommented item
	 */
	public static final Parameter<Integer> OUT2 = new ParameterImpl<Integer>(SQLDialect.POSTGRES, "out2", PostgresDataType.INTEGER);

	/**
	 * Create a new procedure call instance
	 */
	public PUnused() {
		super(SQLDialect.POSTGRES, "p_unused", Public.PUBLIC);

		addInParameter(IN1);
		addOutParameter(OUT1);
		addInOutParameter(OUT2);
	}

	public void setIn1(String value) {
		setValue(IN1, value);
	}

	public void setOut2(Integer value) {
		setValue(OUT2, value);
	}

	public Integer getOut1() {
		return getValue(OUT1);
	}

	public Integer getOut2() {
		return getValue(OUT2);
	}
}