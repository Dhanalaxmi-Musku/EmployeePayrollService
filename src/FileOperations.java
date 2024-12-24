import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class FileOperations {
	 private Scanner scanner;
	    private String currentDirectory;

	    public FileOperations() {
	        scanner = new Scanner(System.in);
	        currentDirectory = "./";
	    }

	    public void displayMenu() {
	        while (true) {
	            System.out.println("=== File Operations Menu ===");
	            System.out.println("1. Check if File/Directory Exists");
	            System.out.println("2. Create Directory");
	            System.out.println("3. Create Empty File");
	            System.out.println("4. Delete File");
	            System.out.println("5. List Files and Directories");
	            System.out.println("6. Change Current Directory");
	            System.out.println("7. Exit");
	            System.out.print("Enter your choice: ");

	            int choice = scanner.nextInt();
	            scanner.nextLine(); // consume newline

	            try {
	                switch (choice) {
	                    case 1:
	                        checkFileExists();
	                        break;
	                    case 2:
	                        createDirectory();
	                        break;
	                    case 3:
	                        createEmptyFile();
	                        break;
	                    case 4:
	                        deleteFile();
	                        break;
	                    case 5:
	                        listFilesAndDirectories();
	                        break;
	                    case 6:
	                        changeCurrentDirectory();
	                        break;
	                    case 7:
	                        System.out.println("Exiting program...");
	                        return;
	                    default:
	                        System.out.println("Invalid choice. Please try again.");
	                }
	            } catch (IOException e) {
	                System.out.println("Error: " + e.getMessage());
	            }
	        }
	    }

	    private void checkFileExists() {
	        System.out.print("Enter file/directory name: ");
	        String name = scanner.nextLine();
	        Path path = Paths.get(currentDirectory, name);
	        System.out.println(path);
	        System.out.println("Exists: " + Files.exists(path));
	        if (Files.exists(path)) {
	            System.out.println("Is Directory: " + Files.isDirectory(path));
	            System.out.println("Is File: " + Files.isRegularFile(path));
	        }
	    }

	    private void createDirectory() throws IOException {
	        System.out.print("Enter directory name: ");
	        String name = scanner.nextLine();
	        Path path = Paths.get(currentDirectory, name);
	        Files.createDirectories(path);
	        System.out.println("Directory created successfully.");
	    }

	    private void createEmptyFile() throws IOException {
	        System.out.print("Enter file name: ");
	        String name = scanner.nextLine();
	        Path path = Paths.get(currentDirectory, name);
	        Files.createFile(path);
	        System.out.println("Empty file created successfully.");
	    }

	    private void deleteFile() throws IOException {
	        System.out.print("Enter file/directory name to delete: ");
	        String name = scanner.nextLine();
	        Path path = Paths.get(currentDirectory, name);
	        Files.delete(path);
	        System.out.println("Deleted successfully.");
	    }

	    private void listFilesAndDirectories() throws IOException {
	        System.out.println("\nContents of " + currentDirectory + ":");
	        try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(currentDirectory))) {
	            for (Path path : stream) {
	                String type = Files.isDirectory(path) ? "Directory" : "File";
	                System.out.printf("%s: %s%n", type, path.getFileName());
	            }
	        }
	    }

	    private void changeCurrentDirectory() {
	        System.out.print("Enter new directory path: ");
	        String newDir = scanner.nextLine();
	        Path path = Paths.get(newDir);
	        if (Files.isDirectory(path)) {
	            currentDirectory = newDir;
	            System.out.println("Current directory changed to: " + currentDirectory);
	        } else {
	            System.out.println("Invalid directory path!");
	        }
	    }
}


