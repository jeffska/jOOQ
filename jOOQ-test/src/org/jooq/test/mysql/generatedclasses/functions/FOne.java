/**
 * This class is generated by jOOQ
 */
package org.jooq.test.mysql.generatedclasses.functions;


import javax.annotation.Generated;

import org.jooq.SQLDialect;
import org.jooq.impl.StoredFunctionImpl;
import org.jooq.test.mysql.generatedclasses.Test;
import org.jooq.util.mysql.MySQLDataType;


/**
 * This class is generated by jOOQ.
 *
 * 1 constant value
 */
@Generated(value    = "http://jooq.sourceforge.net",
           comments = "This class is generated by jOOQ")
public class FOne extends StoredFunctionImpl<Integer> {

	private static final long serialVersionUID = -901453353;


	/**
	 * Create a new function call instance
	 */
	public FOne() {
		super(SQLDialect.MYSQL, "f_one", Test.TEST, MySQLDataType.INT);
	}
}