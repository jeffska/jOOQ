/**
 * This class is generated by jOOQ
 */
package org.jooq.test.oracle.generatedclasses.test.tables.pojos;

/**
 * This class is generated by jOOQ.
 */
@java.lang.SuppressWarnings("all")
@javax.persistence.Entity
@javax.persistence.Table(name = "T_785", schema = "TEST")
public class T_785 implements java.io.Serializable {

	private static final long serialVersionUID = 591561166;

	private java.lang.Integer id;

	@javax.validation.constraints.Size(max = 50)
	private java.lang.String  name;

	@javax.validation.constraints.Size(max = 50)
	private java.lang.String  value;

	@javax.persistence.Column(name = "ID", precision = 7)
	public java.lang.Integer getId() {
		return this.id;
	}

	public void setId(java.lang.Integer id) {
		this.id = id;
	}

	@javax.persistence.Column(name = "NAME", length = 50)
	public java.lang.String getName() {
		return this.name;
	}

	public void setName(java.lang.String name) {
		this.name = name;
	}

	@javax.persistence.Column(name = "VALUE", length = 50)
	public java.lang.String getValue() {
		return this.value;
	}

	public void setValue(java.lang.String value) {
		this.value = value;
	}
}
