import java.sql.*;
import java.util.*;

public class DataBase_Connection {

	static String url = "jdbc:mysql://localhost:3306/userrecords";

	public static void injectDB(EmpDetails_Attributes data, boolean flag) {
		try {
			String db_query = "";
			if (flag == true) {
				db_query = "Insert into employeedetails values('" + data.getEmpID() + "','" + data.getEmpName() + "','"
						+ data.getEmail() + "','" + data.getPhone_number() + "','" + data.getHire_Date() + "','"
						+ data.getJob_Id() + "','" + data.getSalary() + "','" + data.getCommission_Pct() + "','"
						+ data.getManager_Id() + "','" + data.getDepartment_Id() + "'," + "current_date()" + ")";
			} else {
				db_query = "Insert into employeedetails_Failed values('" + data.getEmpID() + "','" + data.getEmpName()
						+ "','" + data.getEmail() + "','" + data.getPhone_number() + "','" + data.getHire_Date() + "','"
						+ data.getJob_Id() + "','" + data.getSalary() + "','" + data.getCommission_Pct() + "','"
						+ data.getManager_Id() + "','" + data.getDepartment_Id() + "'," + "current_date()" + ")";
			}
			System.out.println(db_query);
			try (Connection conn = DriverManager.getConnection(url, "root", "myselfdinesh@123")) {
				PreparedStatement stmt = conn.prepareStatement(db_query);
				stmt.executeUpdate();
				System.out.println("Data inserted Successfully....!\n");
				conn.close();
			} catch (SQLException e) {
				System.out.println("The connection is failed.......!");
				e.printStackTrace();
			}

		} catch (Exception e) {
			System.out.println("The connection is failed.......!");
			e.printStackTrace();
		}
	}

	public static List<EmpReport_Attributes> getReport(boolean flag) {
		List<EmpReport_Attributes> empAttributeList = new ArrayList<EmpReport_Attributes>();
		try {
			String db_query = "";
			if (flag == true) {
				db_query = "SELECT  empDetail.employee_Id,empDetail.employee_Name,empDetail.email,empJob.job_description,get_name(empDetail.manager_Id) as ManagerName,"
						+ "dept.department_desc\r\n" + "   FROM employeedetails empDetail"
						+ "   LEFT JOIN Employee_Job empJob ON empDetail.job_Id = empJob.job_Id"
						+ "   Left JOIN Employee_Department dept on empDetail.department_Id=dept.department_Id";
			} else {
				db_query = "SELECT  empFail.employee_Id,get_name(empFail.employee_Id) as EmployeeName,empFail.email,empJob.job_description,get_name(empFail.manager_Id) as ManagerName,"
						+ "dept.department_desc\r\n" + "   FROM EmployeeDetails_Failed empFail\r\n"
						+ "   left join EmployeeDetails empDetail on empFail.employee_Id=empDetail.employee_Id"
						+ "   LEFT JOIN Employee_Job empJob ON empFail.job_Id = empJob.job_Id"
						+ "   Left Join Employee_Department dept on empFail.department_Id=dept.department_Id";
			}
			try (Connection conn = DriverManager.getConnection(url, "root", "myselfdinesh@123")) {

				Statement stmt = conn.createStatement();
				ResultSet result = stmt.executeQuery(db_query);
				while (result.next()) {
					EmpReport_Attributes empReport = new EmpReport_Attributes();
					empReport.setEmpID(result.getString(1));
					empReport.setEmpName(result.getString(2));
					empReport.setEmail(result.getString(3));
					empReport.setJobDescription(result.getString(4));
					empReport.setManagerName(result.getString(5));
					empReport.setDeptName(result.getString(6));
					empAttributeList.add(empReport);
				}

				conn.close();
			}

		} catch (SQLException e) {
			System.out.println("The connection is failed.......!");
			e.printStackTrace();
		}
		return empAttributeList;
	}

	public static void makeEmptyDB() {
		String deleteQuery1 = "delete from employeedetails";
		String deleteQuery2 = "delete from employeedetails_failed";
		try (Connection conn = DriverManager.getConnection(url, "root", "myselfdinesh@123")) {
			PreparedStatement stmt = conn.prepareStatement(deleteQuery1);
			stmt.executeUpdate();
			System.out.println("The old Records deleted from EmployeeDetails....! \n");
			stmt = conn.prepareStatement(deleteQuery2);
			stmt.executeUpdate();
			System.out.println("The old Records deleted from EmployeeDetails_failed.....! \n");
			conn.close();
		} catch (SQLException e) {
			System.out.println("The connection is failed.......!");
			e.printStackTrace();
		}
	}
}
