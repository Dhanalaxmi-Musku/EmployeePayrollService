import java.io.IOException;

public class Main {
	public static void main(String[] args) { 
		try {
		System.out.println("Console Operations");
        EmployeePayrollService uc1Service = new EmployeePayrollService();
        uc1Service.readEmployeeData();
        uc1Service.writeEmployeeData();
        System.out.println("File Operations");
        FileOperations menu = new FileOperations();
        menu.displayMenu();
        System.out.println("Watch Service");
        WatchService w=new WatchService(true);
        new Thread(() -> {
			try {
				w.watchDirectory("D:\\GE\\employeePayrollService\\payroll");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}).start();
		}
		 catch (Exception e) {
	            System.out.println("Error: " + e.getMessage());
	            e.printStackTrace();
	        }
	}

}
