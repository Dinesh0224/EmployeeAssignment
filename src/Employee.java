import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.*;
import java.util.regex.*;

public class Employee {

	public static void main(String args[]) {
		File file = new File("employee.csv");
		File SuccessReport = new File("Success Report.csv");
		File FailureReport = new File("Failure Report.csv");
		try {
			DataBase_Connection.makeEmptyDB();
			Scanner s = new Scanner(file);
			s.nextLine();
			while (s.hasNextLine()) {
				injectDB(s.nextLine());
			}
		} catch (FileNotFoundException e) {
			System.out.println("Exception->" + e);
			e.printStackTrace();
		}

		// Writing data into Success & Failure report file
		try {
			List<EmpReport_Attributes> successList = DataBase_Connection.getReport(true);
			List<EmpReport_Attributes> failedList = DataBase_Connection.getReport(false);

			BufferedWriter w = new BufferedWriter(new FileWriter(SuccessReport));
			w.write("EmployeeID,Name,Email,Job Description,Department Name,Manager Name");
			w.newLine();

			for (EmpReport_Attributes empreport : successList) {
				w.write(empreport.getEmpID() + "," + empreport.getEmpName() + "," + empreport.getEmail() + ","
						+ empreport.getJobDescription() + "," + empreport.getDeptName() + ","
						+ empreport.getManagerName());
				w.newLine();
			}
			w.close();

			System.out.println("The Success Report is generated.......!\n");

			w = new BufferedWriter(new FileWriter(FailureReport));
			w.write("EmployeeID,Name,Email,Job Description,Department Name,Manager Name");
			w.newLine();

			for (EmpReport_Attributes empfailreport : failedList) {
				w.write(empfailreport.getEmpID() + "," + empfailreport.getEmpName() + "," + empfailreport.getEmail()
						+ "," + empfailreport.getJobDescription() + "," + empfailreport.getDeptName() + ","
						+ empfailreport.getManagerName());
				w.newLine();
			}
			w.close();

			System.out.println("The Failure Report is generated........!");

		} catch (Exception e) {
			System.out.println("Exception->" + e);
		}
	}

	// Insert into database

	public static void injectDB(String data) {
		data = data.replaceAll(",,", ",-,");
		data = data.replaceAll(",,", ",-,");
		if (data.charAt(data.length() - 1) == ',') {
			data = data + "-";
		}

		String datas[] = data.split(",");
		EmpDetails_Attributes success_record = new EmpDetails_Attributes();
		EmpDetails_Attributes failure_record = new EmpDetails_Attributes();

		// employee ID
		if (isNull(datas[0])) {
			// Inserting all failure data
			failure_record.setEmpID(datas[0]);
			failure_record.setEmpName(datas[1]);
			failure_record.setEmail(datas[2]);
			failure_record.setPhone_number(datas[3]);
			failure_record.setHire_Date(datas[4]);
			failure_record.setJob_Id(datas[5]);
			failure_record.setSalary(datas[6]);
			failure_record.setCommission_Pct(datas[7]);
			failure_record.setManager_Id(datas[8]);
			failure_record.setDepartment_Id(datas[9]);
			DataBase_Connection.injectDB(failure_record, false);
		} else {
			success_record.setEmpID(datas[0]);
			failure_record.setEmpID(datas[0]);

			// employee Name
			if (isNameOnlyAlphabet(datas[2])) {
				if (isNull(datas[1])) {
					success_record.setEmpName(datas[2]);
					;
					failure_record.setEmpName("-");
				} else {
					success_record.setEmpName(datas[1] + " " + datas[2]);
					failure_record.setEmpName("-");
				}
			} else {
				failure_record.setEmpName(datas[1] + " " + datas[2]);
			}

			// Email
			String email = isValidEmail(datas[3]);
			if (email.equalsIgnoreCase("false")) {
				failure_record.setEmail(datas[3]);
				success_record.setEmail("-");
			} else {
				success_record.setEmail(email);
				failure_record.setEmail("-");
			}

			// PhoneNumber
			if (isValidPhoneNumber(datas[4])) {
				success_record.setPhone_number(datas[4]);
				failure_record.setPhone_number("-");
			} else {
				success_record.setPhone_number("-");
				failure_record.setPhone_number(datas[4]);
			}

			// Hire Date
			if (isValidDate(datas[5])) {
				success_record.setHire_Date(datas[5]);
				failure_record.setHire_Date("-");
			} else {
				success_record.setHire_Date("-");
				failure_record.setHire_Date(datas[5]);
			}

			// Job ID
			if (isNull(datas[6])) {
				success_record.setJob_Id("-");
				failure_record.setJob_Id("-");
			} else {
				success_record.setJob_Id(datas[6]);
				failure_record.setJob_Id(datas[6]);
			}

			// Salary
			if (isValidSalary(datas[7])) {
				success_record.setSalary(datas[7]);
				failure_record.setSalary("-");
			} else {
				failure_record.setSalary(datas[7]);
				success_record.setSalary("-");
			}

			// COMMISSION_PCT
			success_record.setCommission_Pct(datas[8]);
			failure_record.setCommission_Pct("-");

			// MANAGER_ID
			if (isNull(datas[9])) {
				success_record.setManager_Id(datas[0]);
				failure_record.setManager_Id(datas[0]);
			} else {
				success_record.setManager_Id(datas[9]);
				failure_record.setManager_Id(datas[9]);
			}

			// DEPARTMENT_ID
			if (isNull(datas[10])) {
				success_record.setDepartment_Id("-");
				failure_record.setDepartment_Id("-");
			} else {
				success_record.setDepartment_Id(datas[10]);
				failure_record.setDepartment_Id(datas[10]);
			}
			if (!(failure_record.getEmpName().equalsIgnoreCase("-") && failure_record.getEmail().equalsIgnoreCase("-")
					&& failure_record.getPhone_number().equalsIgnoreCase("-")
					&& failure_record.getHire_Date().equalsIgnoreCase("-")
					&& failure_record.getSalary().equalsIgnoreCase("-"))) {
				// Inserting Failure records
				DataBase_Connection.injectDB(failure_record, false);
			}
			// Inserting Success records
			DataBase_Connection.injectDB(success_record, true);
		}

	}

	public static boolean isNull(String val) {
		if (val.trim().equalsIgnoreCase("") || val.equals("null") || val.equals(null)
				|| val.trim().equalsIgnoreCase("-")) {
			return true;
		}
		return false;
	}

	public static boolean isNameOnlyAlphabet(String val) {
		return ((!val.equals("")) && (val != null) && (val.matches("^[a-zA-Z ]*$")));
	}

	public static String isValidEmail(String val) {
		if (isNull(val)) {
			return "false";
		} else {
			if (val.length() <= 50) {
				if (isNameOnlyAlphabet(val)) {
					return val + "@abc.com";
				}
				String emailReg = "^[a-zA-Z0-9_+&*-]+(?:\\." + "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-z"
						+ "A-Z]{2,7}$";

				Pattern p = Pattern.compile(emailReg);
				if (p.matcher(val).matches()) {
					return val;
				} else {
					return "false";
				}
			} else {
				return "false";
			}
		}
	}

	public static boolean isValidPhoneNumber(String val) {
		if (isNull(val)) {
			return false;
		} else {
			if (val.length() == 12) {
				if (val.charAt(3) == '.' && val.charAt(7) == '.') {
					String Phonereg = "^[\\.0-9]*$";
					Pattern p = Pattern.compile(Phonereg);
					return p.matcher(val).matches();
				} else {
					return false;
				}
			} else {
				return false;
			}
		}
	}

	public static boolean isValidDate(String val) {
		String Datereg = "([0-2][0-9]||3[0-1])-(0[0-9]||1[0-2])-((19|20)\\d\\d)";
		Pattern p = Pattern.compile(Datereg);
		Matcher m = p.matcher((CharSequence) val);
		return m.matches();
	}

	public static boolean isValidSalary(String val) {
		if (isNull(val)) {
			return false;
		} else {
			String Salaryreg = "^([0-9]*)(.[[0-9]+]?)?$";
			Pattern p = Pattern.compile(Salaryreg);
			Matcher m = p.matcher((CharSequence) val);
			return m.matches();
		}
	}
}
