package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import conn.Connec;

public class CheckUser {
	
	public boolean checklogin(String username, String password) {
        Connection conn = null; // Declare the Connection variable outside the try block
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            // Get a database connection (You should replace this with your actual connection method)
            conn = Connec.connection();

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
