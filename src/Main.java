import java.io.IOException;
import java.util.Arrays;
import java.util.List;

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
        /*System.out.println("Watch Service");
        WatchService w=new WatchService(true);
        new Thread(() -> {
			try {
				w.watchDirectory("D:\\GE\\employeePayrollService\\payroll");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}).start();
        //Thread.sleep(100); 
        w.stop();*/
        System.out.println("\nWriting to File");
        List<Employee> employees = Arrays.asList(
            new Employee(1, "John", 50000),
            new Employee(2, "Jane", 60000)
        );
        EmployeePayrollService uc4Service = new EmployeePayrollService();
        uc4Service.writeEmployeePayrollToFile(employees);
        System.out.println("\nReading and Printing File");
        EmployeePayrollService uc5_6Service = new EmployeePayrollService();
        System.out.println("Printing payroll file:");
        uc5_6Service.printPayrollFile();
        System.out.println("Number of entries: " + uc5_6Service.getEntryCount());
        System.out.println("Reading payroll file:");
        uc5_6Service.readPayrollFile().forEach(System.out::println);
		}
		
		 catch (Exception e) {
	            System.out.println("Error: " + e.getMessage());
	            e.printStackTrace();
	        }
		
	}

}
