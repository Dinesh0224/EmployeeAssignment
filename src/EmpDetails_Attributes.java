import java.sql.Date;

public class EmpDetails_Attributes {

	private String empID;
	private String empName;
	private String email;
	private String phoneNumber;
	private String hireDate;
	private String jobID;
	private String salary;
	private String commissionPct;
	private String managerID;
	private String departmentID;
	private Date recordInsertDate;

	public String getEmpID() {
		return empID;
	}

	public void setEmpID(String empID) {
		this.empID = empID;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone_number() {
		return phoneNumber;
	}

	public void setPhone_number(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getHire_Date() {
		return hireDate;
	}

	public void setHire_Date(String hireDate) {
		this.hireDate = hireDate;
	}

	public String getJob_Id() {
		return jobID;
	}

	public void setJob_Id(String jobID) {
		this.jobID = jobID;
	}

	public String getSalary() {
		return salary;
	}

	public void setSalary(String salary) {
		this.salary = salary;
	}

	public String getCommission_Pct() {
		return commissionPct;
	}

	public void setCommission_Pct(String commissionPct) {
		this.commissionPct = commissionPct;
	}

	public String getManager_Id() {
		return managerID;
	}

	public void setManager_Id(String managerID) {
		this.managerID = managerID;
	}

	public String getDepartment_Id() {
		return departmentID;
	}

	public void setDepartment_Id(String departmentID) {
		this.departmentID = departmentID;
	}

	public Date getRecord_insDate() {
		return recordInsertDate;
	}

	public void setRecord_insDate(Date recordInsertDate) {
		this.recordInsertDate = recordInsertDate;
	}

}
