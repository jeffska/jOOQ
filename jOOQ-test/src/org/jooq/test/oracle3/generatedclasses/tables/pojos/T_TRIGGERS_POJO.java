/**
 * This class is generated by jOOQ
 */
package org.jooq.test.oracle3.generatedclasses.tables.pojos;

/**
 * This class is generated by jOOQ.
 */
@javax.persistence.Entity
@javax.persistence.Table(name = "T_TRIGGERS", schema = "TEST")
public class T_TRIGGERS_POJO implements java.io.Serializable {

	private static final long serialVersionUID = 621046022;

	private java.lang.Integer ID_GENERATED;
	private java.lang.Integer ID;
	private java.lang.Integer COUNTER;

	@javax.persistence.Id
	@javax.persistence.Column(name = "ID_GENERATED", unique = true, nullable = false, precision = 7)
	public java.lang.Integer getID_GENERATED() {
		return this.ID_GENERATED;
	}

	public void setID_GENERATED(java.lang.Integer ID_GENERATED) {
		this.ID_GENERATED = ID_GENERATED;
	}

	@javax.persistence.Column(name = "ID", nullable = false, precision = 7)
	public java.lang.Integer getID() {
		return this.ID;
	}

	public void setID(java.lang.Integer ID) {
		this.ID = ID;
	}

	@javax.persistence.Column(name = "COUNTER", nullable = false, precision = 7)
	public java.lang.Integer getCOUNTER() {
		return this.COUNTER;
	}

	public void setCOUNTER(java.lang.Integer COUNTER) {
		this.COUNTER = COUNTER;
	}
}