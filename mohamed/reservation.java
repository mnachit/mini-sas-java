package mohamed;

import java.util.Scanner;
import mohamed.home;
import conn.Connec;

import java.sql.*;
//import java.sql.PreparedStatement;
//import java.sql.SQLException;



public class reservation {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	public static void clearScreen() {
	    for (int i = 0; i < 50; i++) {
	        System.out.println();
	    }
	}
	
	public boolean makeReservation(String isbn, int userId) {
		
		Connec conn = new Connec();
        PreparedStatement preparedStatement = null;

        try {
            // Define the SQL query to insert a reservation
            String sql = "INSERT INTO reservation (isbn, user_id) VALUES (?, ?)";

            // Create a PreparedStatement
            preparedStatement = conn.connection().prepareStatement(sql);

            // Set the parameters (ISBN and user ID)
            preparedStatement.setString(1, isbn);
            preparedStatement.setInt(2, userId);

            // Execute the INSERT statement
            int rowsInserted = preparedStatement.executeUpdate();

            // Check if the reservation was successfully inserted
            if (rowsInserted > 0) {
                System.out.println("Reservation successful.");
                return true;
            } else {
                System.out.println("Reservation failed.");
                return false;
            }
        } catch (SQLException e) {
            System.err.println("Error making reservation: " + e.getMessage());
            return false;
        } finally {
            // Close the PreparedStatement
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
	
	public void searchBooks(Scanner scanner) {
		
		Connec conn = new Connec();
//		clearScreen();
        System.out.println("Choose search criteria:");
        System.out.println("1. Search by title");
        System.out.println("2. Search by author");
        System.out.print("Enter your choice (1 or 2): \n -->");

        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume the newline

        System.out.print("Enter search query: ");
        String searchQuery = scanner.nextLine();

        String sql = "";
        if (choice == 1) {
            sql = "SELECT * FROM livre WHERE titre LIKE ?";
        } else if (choice == 2) {
            sql = "SELECT * FROM livre WHERE auteur LIKE ?";
        } else {
            System.out.println("Invalid choice. Please choose 1 or 2.");
            return;
        }

        try {
            PreparedStatement preparedStatement = conn.connection().prepareStatement(sql);
            preparedStatement.setString(1, "%" + searchQuery + "%");

            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("----------------------------------------------------------------------------");
		    System.out.printf("| %-5s | %-10s | %-15s | %-15s | %-15s |\n", "ID", "Quantity", "ISBN", "Title", "Author");
		    System.out.println("----------------------------------------------------------------------------");
            
            while (resultSet.next()) {
		        int id = resultSet.getInt("id");
		        int quantity = resultSet.getInt("quantite");
		        int isbn = resultSet.getInt("isbn");
		        String titre = resultSet.getString("titre");
		        String auteur = resultSet.getString("auteur");

		        System.out.printf("| %-5d | %-10d | %-15d | %-15s | %-15s |\n", id, quantity, isbn, titre, auteur);
		    }
            
            System.out.println("----------------------------------------------------------------------------\n");
            
            while (true) {
            	System.out.println("\n1. Go to menu");
                System.out.println("\n2. logout");
                int ss = scanner.nextInt();
                scanner.nextLine();

                switch (ss) {
                    case 1:
                    	index(scanner);
                        System.exit(0);
                    case 2:
                    	System.out.println("Exiting the program.");
                        scanner.close();
                        System.exit(0);
                    default:
                        System.out.println("Invalid choice. Please try again.");
//                        return true;
            }
            }
        } catch (SQLException e) {
            System.err.println("Error executing SQL query: " + e.getMessage());
        }
    }
	
	
	public void index(Scanner scanner)
	{
//		Scanner scanner = new Scanner(System.in);

        while (true) {
        	clearScreen();
        	System.out.println("\n----------------------------------------------------------------------------");
            System.out.println("1. Display the list of borrowed books\n");
            System.out.println("2. rechercher un livre par son titre ou son auteur\n");
            System.out.println("3. emprunter un livre\n");
            System.out.println("4. retourner un livre emprunté\n");
            System.out.println("5. Exit");
            System.out.println("----------------------------------------------------------------------------\n");
            System.out.print("Choose an option: \n\n\n");

            int choice = scanner.nextInt();
            scanner.nextLine();

            home Managerhome = new home();
            switch (choice) {
                case 1:
                	Managerhome.show(scanner);
                	Managerhome.showU(scanner);
                    System.exit(0);
                case 2:
                	searchBooks(scanner);
                case 5:
                    System.out.println("Exiting the program.");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
	}

}
