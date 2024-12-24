import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EmployeePayrollService {
private List<Employee> employeeList;
    
    public EmployeePayrollService() {
        employeeList = new ArrayList<>();
    }

    public void readEmployeeData() {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Enter Employee ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        
        System.out.println("Enter Employee Name: ");
        String name = scanner.nextLine();
        
        System.out.println("Enter Employee Salary: ");
        double salary = scanner.nextDouble();
        
        employeeList.add(new Employee(id, name, salary));
    }

    public void writeEmployeeData() {
        for (Employee employee : employeeList) {
            System.out.println(employee);
        }
    }
     private static final String PAYROLL_FILE = "payrollfile.txt";
    
    public void writeEmployeePayrollToFile(List<Employee> employees) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(PAYROLL_FILE))) {
            for (Employee employee : employees) {
                writer.write(employee.toString());
                writer.newLine();
            }
        }
    }
    public void printPayrollFile() throws IOException {
        Files.lines(Paths.get(PAYROLL_FILE)).forEach(System.out::println);
    }
    
    public List<String> readPayrollFile() throws IOException {
        return Files.readAllLines(Paths.get(PAYROLL_FILE));
    }
    
    public long getEntryCount() throws IOException {
        return Files.lines(Paths.get(PAYROLL_FILE)).count();
    }

}
