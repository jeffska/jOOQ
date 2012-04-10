/**
 * This class is generated by jOOQ
 */
package org.jooq.examples.sqlserver.adventureworks.humanresources.tables.records;

/**
 * This class is generated by jOOQ.
 */
@javax.persistence.Entity
@javax.persistence.Table(name = "JobCandidate", schema = "HumanResources")
public class JobcandidateRecord extends org.jooq.impl.UpdatableRecordImpl<org.jooq.examples.sqlserver.adventureworks.humanresources.tables.records.JobcandidateRecord> {

	private static final long serialVersionUID = -591303533;

	/**
	 * An uncommented item
	 * 
	 * PRIMARY KEY
	 */
	public void setJobcandidateid(java.lang.Integer value) {
		setValue(org.jooq.examples.sqlserver.adventureworks.humanresources.tables.Jobcandidate.JOBCANDIDATE.JOBCANDIDATEID, value);
	}

	/**
	 * An uncommented item
	 * 
	 * PRIMARY KEY
	 */
	@javax.persistence.Id
	@javax.persistence.Column(name = "JobCandidateID", unique = true)
	public java.lang.Integer getJobcandidateid() {
		return getValue(org.jooq.examples.sqlserver.adventureworks.humanresources.tables.Jobcandidate.JOBCANDIDATE.JOBCANDIDATEID);
	}

	/**
	 * An uncommented item
	 * <p>
	 * <code><pre>
	 * CONSTRAINT FK_JobCandidate_Employee_EmployeeID
	 * FOREIGN KEY (EmployeeID)
	 * REFERENCES HumanResources.Employee (EmployeeID)
	 * </pre></code>
	 */
	public void setEmployeeid(java.lang.Integer value) {
		setValue(org.jooq.examples.sqlserver.adventureworks.humanresources.tables.Jobcandidate.JOBCANDIDATE.EMPLOYEEID, value);
	}

	/**
	 * An uncommented item
	 * <p>
	 * <code><pre>
	 * CONSTRAINT FK_JobCandidate_Employee_EmployeeID
	 * FOREIGN KEY (EmployeeID)
	 * REFERENCES HumanResources.Employee (EmployeeID)
	 * </pre></code>
	 */
	@javax.persistence.Column(name = "EmployeeID")
	public java.lang.Integer getEmployeeid() {
		return getValue(org.jooq.examples.sqlserver.adventureworks.humanresources.tables.Jobcandidate.JOBCANDIDATE.EMPLOYEEID);
	}

	/**
	 * An uncommented item
	 * 
	 * The SQL type of this item (xml, ) could not be mapped.<br/>
	 * Deserialising this field might not work!
	 */
	public void setResume(java.lang.Object value) {
		setValue(org.jooq.examples.sqlserver.adventureworks.humanresources.tables.Jobcandidate.JOBCANDIDATE.RESUME, value);
	}

	/**
	 * An uncommented item
	 * 
	 * The SQL type of this item (xml, ) could not be mapped.<br/>
	 * Deserialising this field might not work!
	 */
	@javax.persistence.Column(name = "Resume")
	public java.lang.Object getResume() {
		return getValue(org.jooq.examples.sqlserver.adventureworks.humanresources.tables.Jobcandidate.JOBCANDIDATE.RESUME);
	}

	/**
	 * An uncommented item
	 */
	public void setModifieddate(java.sql.Timestamp value) {
		setValue(org.jooq.examples.sqlserver.adventureworks.humanresources.tables.Jobcandidate.JOBCANDIDATE.MODIFIEDDATE, value);
	}

	/**
	 * An uncommented item
	 */
	@javax.persistence.Column(name = "ModifiedDate")
	public java.sql.Timestamp getModifieddate() {
		return getValue(org.jooq.examples.sqlserver.adventureworks.humanresources.tables.Jobcandidate.JOBCANDIDATE.MODIFIEDDATE);
	}

	/**
	 * Create a detached JobcandidateRecord
	 */
	public JobcandidateRecord() {
		super(org.jooq.examples.sqlserver.adventureworks.humanresources.tables.Jobcandidate.JOBCANDIDATE);
	}
}