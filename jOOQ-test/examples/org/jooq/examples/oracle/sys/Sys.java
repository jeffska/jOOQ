/**
 * This class is generated by jOOQ
 */
package org.jooq.examples.oracle.sys;

/**
 * This class is generated by jOOQ.
 */
@java.lang.SuppressWarnings("all")
public class Sys extends org.jooq.impl.SchemaImpl {

	private static final long serialVersionUID = 2044126579;

	/**
	 * The singleton instance of <code>SYS</code>
	 */
	public static final Sys SYS = new Sys();

	/**
	 * No further instances allowed
	 */
	private Sys() {
		super("SYS");
	}

	@Override
	public final java.util.List<org.jooq.UDT<?>> getUDTs() {
		return java.util.Arrays.<org.jooq.UDT<?>>asList(
			org.jooq.examples.oracle.sys.udt.DbmsXplanType.DBMS_XPLAN_TYPE);
	}
}
