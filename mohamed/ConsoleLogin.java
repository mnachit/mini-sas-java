package mohamed;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ConsoleLogin {

    public void main() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            try {
                System.out.println("1. Register");
                System.out.println("2. Login");
                System.out.println("3. Exit");
                System.out.print("Choose an option: ");

                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        registerUser(scanner);
                        System.exit(0);
                    case 2:
                        login(scanner);
                        System.exit(0);
                    case 3:
                        System.out.println("Exiting the program.");
                        scanner.close();
                        System.exit(0);
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter an integer.");
                scanner.nextLine();
            }
        }
    }

    public static void registerUser(Scanner input) {
        System.out.print("First Name: ");
        String firstName = input.nextLine();
        System.out.print("Last Name: ");
        String lastName = input.nextLine();
        System.out.print("Username: ");
        String username = input.nextLine();
        System.out.print("Password: ");
        String password = input.nextLine();

        // Insert user data into the database
        boolean insertionSuccessful = insertUserData(firstName, lastName, username, password);

        if (!insertionSuccessful) {
            System.out.println("Error: Registration failed.");
        } else {
            home.index();
        }
    }

    public static void login(Scanner input) {
        System.out.print("username: ");
        String username = input.nextLine();
        System.out.print("Password: ");
        String password = input.nextLine();
        
        boolean check = checklogin(username, password);

        if (!check) {
            System.out.println("Error: login failed.");
        } else {
        	Connection conn = null; // Declare the Connection variable outside the try block
            PreparedStatement stmt = null;
            ResultSet rs = null;

            try {
                conn = testconx.connection();
                String sql = "SELECT * FROM user WHERE username = ? AND password = ?";
                stmt = conn.prepareStatement(sql);
                stmt.setString(1, username);
                stmt.setString(2, password);

                // Execute the query
                rs = stmt.executeQuery();

                if (rs.next()) {
                    // Check the user's type
                    String userType = rs.getString("type");
                    if ("admin".equals(userType)) {
                        // User is an admin, so allow access to home.index()
                        home.index();
                    } else {
                        System.out.println("Error: You do not have permission to access this function.");
                    }
                }
            }
            catch (SQLException e) {
                System.out.println("Error inserting data into the database: " + e.getMessage());
            }
        }
        // Perform login validation (you can implement this logic here)
    }

    public static boolean insertUserData(String firstName, String lastName, String username, String password) {

        try  {
        	Connection conn=testconx.connection();
            String query = "INSERT INTO user (first_name, last_name, username, password) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(query);

            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, username);
            preparedStatement.setString(4, password);

            int rowsAffected = preparedStatement.executeUpdate();
            
            return rowsAffected > 0; // Return true if at least one row was affected (insertion successful)
        } catch (SQLException e) {
            System.out.println("Error inserting data into the database: " + e.getMessage());
            return false; // Return false in case of an error
        }
    }
    
    public static boolean checklogin(String username, String password) {
        Connection conn = null; // Declare the Connection variable outside the try block
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            // Get a database connection (You should replace this with your actual connection method)
            conn = testconx.connection();

            // Define your SQL query for checking the login
            String sql = "SELECT * FROM user WHERE username = ? AND password = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);

            // Execute the query
            rs = stmt.executeQuery();

            // Check if there is a matching user with the provided username and password
            if (rs.next()) {
                // Login successful, a matching user was found
                return true;
            } else {
                // Login failed, no matching user found
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Error checking login: " + e.getMessage());
            return false; // Return false in case of an error
        } finally {
            // Close the database resources in the reverse order they were opened
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.out.println("Error closing database resources: " + e.getMessage());
            }
        }
    }

}
