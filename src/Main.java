import java.io.IOException;

public class Main {
	public static void main(String[] args) {            
		System.out.println("Console Operations");
        EmployeePayrollService uc1Service = new EmployeePayrollService();
        uc1Service.readEmployeeData();
        uc1Service.writeEmployeeData();
        System.out.println("File Operations");
        FileOperations menu = new FileOperations();
        menu.displayMenu();
	}

}
