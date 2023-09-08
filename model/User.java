package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import conn.Connec;
import mohamed.home;
import mohamed.ConsoleLogin;
import mohamed.reservation;

public class User {
	
	public static void login(Scanner input) {
        System.out.print("username: ");
        String username = input.nextLine();
        System.out.print("Password: ");
        String password = input.nextLine();
        
        CheckUser Manager = new CheckUser();
        home Managerhome = new home();
        ConsoleLogin Managerlog = new ConsoleLogin();
        
      
        boolean check = Manager.checklogin(username, password);

        if (!check) {
            System.out.println("Error: login failed.\n\n");
            Managerlog.main();
        } else {
        	Connection conn = null; // Declare the Connection variable outside the try block
            PreparedStatement stmt = null;
            ResultSet rs = null;

            try {
                conn = Connec.connection();
                String sql = "SELECT * FROM user WHERE username = ? AND password = ?";
                stmt = conn.prepareStatement(sql);
                stmt.setString(1, username);
                stmt.setString(2, password);

                rs = stmt.executeQuery();

                if (rs.next()) {
                    String userType = rs.getString("type");
                    if ("admin".equals(userType)) {
                    	Managerhome.index();
                    } else {
                    	reservation user = new reservation();
                    	user.index(input);
                    }
                }
            }
            catch (SQLException e) {
                System.out.println("Error inserting data into the database: " + e.getMessage());
            }
        }
        // Perform login validation (you can implement this logic here)
    }
}
