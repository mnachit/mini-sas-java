package mohamed;

import java.util.Scanner;
import mohamed.home;
import conn.Connec;
import model.User;

import java.util.Date;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.sql.*;
//import java.sql.PreparedStatement;
//import java.sql.SQLException;
import model.User;


public class reservation {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	public static void clearScreen() {
	    for (int i = 0; i < 50; i++) {
	        System.out.println();
	    }
	}
	
	public boolean makeReservation(int isbn, int userId, int isbn1) {
	    Connec conn = new Connec();
	    PreparedStatement preparedStatement = null;
	    
	    Date currentDate = new Date();
	    Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 7);

        // Convert the Calendar date to a java.util.Date
        java.util.Date utilReturnDate = calendar.getTime();

        // Convert the java.util.Date to java.sql.Date
        java.sql.Date returnDate = new java.sql.Date(utilReturnDate.getTime());
        
	    try {
	        String sql = "INSERT INTO reservation (isbn, user, date) VALUES (?, ?, ?)";

	        preparedStatement = conn.connection().prepareStatement(sql);

	        preparedStatement.setInt(1, isbn);
	        preparedStatement.setInt(2, userId);
	        preparedStatement.setDate(3, returnDate);

	        // Execute the INSERT statement
	        int rowsInserted = preparedStatement.executeUpdate();

	        // Check if the reservation was successfully inserted
	        if (rowsInserted > 0) {
	            try {
	                Connec conn1 = new Connec();
	                String updateQuery = "UPDATE livre SET quantite = quantite - 1 WHERE isbn = ?";
	                PreparedStatement updateStatement = conn.connection().prepareStatement(updateQuery);
	                updateStatement.setInt(1, isbn1);
//	                System.out.printf("%d",isbn);
	                int updatedRows = updateStatement.executeUpdate();

	                if (updatedRows > 0) {
	                    System.out.println("Reservation successful.");
	                    return true;
	                } else {
	                    System.out.println("Reservation successful, but failed to update quantity.");
	                    return false;
	                }
	            } catch (SQLException e) {
	                System.err.println("Error updating quantity: " + e.getMessage());
	                return false;
	            }
	        }

	        else {
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
        	System.out.println("2. Search for a book by its title or author\n");
        	System.out.println("3. Borrow a book\n");
        	System.out.println("4. Return a borrowed book\n");
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
                case 3:
                	emprunter(scanner);
                case 5:
                    System.out.println("Exiting the program.");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
	}
	
	public void emprunter(Scanner scanner)
	{
		home Managerhome = new home();
		Managerhome.show(scanner);
		System.out.println("\n----------------------------------------------------------------------------");
		System.out.println("1. Enter ISBN :\n");
		
		int isbn = scanner.nextInt();
        scanner.nextLine();
        
        
        try {
            String query = "SELECT quantite FROM livre WHERE isbn = ?";
            PreparedStatement preparedStatement = Connec.connection().prepareStatement(query);
            preparedStatement.setInt(1, isbn);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int quantite = resultSet.getInt("quantite");
                if (quantite > 0) {
                	showbyisbn(isbn);
                    System.out.println("The book is available.");
                    
                    Date currentDate = new Date();
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(currentDate);

                    // Add 7 days to the current date
                    calendar.add(Calendar.DAY_OF_MONTH, 7);
                    Date returnDate = calendar.getTime();

                    // Format the dates as strings
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    String datenow = dateFormat.format(currentDate);
                    String returnDateStr = dateFormat.format(returnDate);

                    // Print the message
                    System.out.println("1. Are you sure? You must return the book on: " + returnDateStr);
                    System.out.println("\n\n2. Try again !:\n");
                    System.out.println("\n\n3. Go to menu ?:\n");
                    
                    int N1 = scanner.nextInt();
                    scanner.nextLine();
                    
                    switch (N1) {
                    case 1:
                    	try {
                    	    String query1 = "SELECT * FROM livre WHERE isbn = ?";
                    	    PreparedStatement preparedStatement1 = Connec.connection().prepareStatement(query1);
                    	    preparedStatement1.setInt(1, isbn);
                    	    ResultSet resultSet1 = preparedStatement1.executeQuery(); 

                    	    if (resultSet1.next()) {
                    	        int num = resultSet1.getInt("id");
//                    	        User user = new User();
                    	        int dd = model.User.getId();
                            	
                            	System.out.printf("%d",dd);
//                    	        makeReservation(num,2, isbn);
                    	        // You can call makeReservation(num, 2) here if needed.
                    	    } else {
                    	        System.out.println("No book found with the given ISBN.");
                    	    }

                    	    System.exit(0);
                    	} catch (SQLException e) {
                    	    System.out.println("Error: " + e.getMessage());
                    	}

                    case 2:
                    	emprunter(scanner);
                    case 3:
                    	index(scanner);
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
                } else {
                    System.out.println("The book is not available (quantity = 0).\n\n1. Try again !\n\n2. Go to menu !\n\n3. Logout");
                    
                    int N2 = scanner.nextInt();
                    scanner.nextLine();
                    
                    System.out.print("Choose an option: \n\n\n");
                    switch (N2) {
                    case 1:
                    	emprunter(scanner);    
                    case 2:
                    	index(scanner);
                    case 3:
                    	System.out.println("Exiting the program.");
                        scanner.close();
                        System.exit(0);
                    }
                }
            } else {
                System.out.println("No book found with the given ISBN.");
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        // d
        
        
    // fin   
		
	}
	
	
	public void showbyisbn(int isbnToSearch)
	{
		try {
	        // Create and execute a SQL query to retrieve a book by ISBN
	        String query = "SELECT * FROM livre WHERE isbn = ?";
	        PreparedStatement preparedStatement = Connec.connection().prepareStatement(query);
	        preparedStatement.setInt(1, isbnToSearch);
	        ResultSet resultSet = preparedStatement.executeQuery();

	        // Check if any books match the ISBN
	        if (!resultSet.next()) {
	            System.out.println("No book found with ISBN: " + isbnToSearch);
	        } else {
	            // Print the book record
	            System.out.println("----------------------------------------------------------------------------");
	            System.out.printf("| %-5s | %-10s | %-15s | %-15s | %-15s |\n", "ID", "Quantity", "ISBN", "Title", "Author");
	            System.out.println("----------------------------------------------------------------------------");

	            do {
	                int id = resultSet.getInt("id");
	                int quantity = resultSet.getInt("quantite");
	                int isbn = resultSet.getInt("isbn");
	                String titre = resultSet.getString("titre");
	                String auteur = resultSet.getString("auteur");

	                System.out.printf("| %-5d | %-10d | %-15d | %-15s | %-15s |\n", id, quantity, isbn, titre, auteur);
	            } while (resultSet.next());

	            System.out.println("----------------------------------------------------------------------------");
	        }

	    } catch (SQLException e) {
	        System.out.println("Error: " + e.getMessage());
	    }

//	    return true;
	}

}
