package mohamed;

import java.util.Scanner;

import conn.Connec;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;


public class home {
	public static void clearScreen() {
	    for (int i = 0; i < 50; i++) {
	        System.out.println();
	    }
	}
	
	public void test(Scanner scanner)
	{
		home Managerlog = new home();
		while (true) {
        	System.out.println("\n1. Go to menu");
            System.out.println("\n2. logout");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                	Managerlog.index();
                    System.exit(0);
                case 2:
                	System.out.println("Exiting the program.");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
        }
        }
//		return true;
	}
	public void index()
	{
		Scanner scanner = new Scanner(System.in);

        while (true) {
        	clearScreen();
        	System.out.println("\n----------------------------------------------------------------------------");
            System.out.println("1. Add a new book");
            System.out.println("2. Display the list of borrowed books");
            System.out.println("3. Delete a book");
            System.out.println("4. Edit information in an existing book");
            System.out.println("5. Generate a report containing statistics");
            System.out.println("6. View the full list of available books");
            System.out.println("7. Search for a book by its title or author");
            System.out.println("8. Exit");
            System.out.println("----------------------------------------------------------------------------\n");
            System.out.print("Choose an option: \n\n\n");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addnewbook(scanner);
                    System.exit(0);
                case 2:
                    show(scanner);
                    test(scanner);
                    System.exit(0);
                case 3:
         
	                deleteLivreByISBN(scanner);
	                System.exit(0);
                case 4:
                  updateBookByISBN(scanner);
                  System.exit(0);
                case 5:
//                  login(scanner);
                  System.exit(0);
                case 6:
//                  login(scanner);
                  System.exit(0);
                case 7:
//                  login(scanner);
                  System.exit(0);
                case 8:
//                  login(scanner);
                  System.exit(0);
                case 9:
                    System.out.println("Exiting the program.");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
	}
	
	public static void index1() {
        // Your index function logic here
        System.out.println("Welcome to the Home page!");
    }
	
	public static void menu(Scanner scanner)
	{
		home Managerlog = new home();
		while (true) {
        	
        	System.out.println("\n1. Go to menu");
            System.out.println("\n2. logout");
            System.out.print("\nChoose an option: -->  ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                	Managerlog.index();
                    System.exit(0);
                case 2:
                	System.out.println("Exiting the program.");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
                    return true;
        }
        }
	}
	
	public static boolean addnewbook(Scanner scanner)
	{
		
		home Managerlog = new home();
		clearScreen();
		// Collect book information from the user
		System.out.println("\n\n----------------------------------------------------------------------------");
        System.out.print("Enter the Quantity: \n\n");
        int quantity = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        System.out.print("Enter the ISBN: \n\n");
        int isbn = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        System.out.print("Enter the Titre: \n\n");
        String titre = scanner.nextLine();
        
        System.out.print("Enter the Auteur: \n\n");
        String auteur = scanner.nextLine();
        
        try {
        	Connec conn = new Connec();
        	String query = "INSERT INTO livre (quantite, isbn, titre, auteur, admin) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = Connec.connection().prepareStatement(query);

            // Set the values for the SQL query
            preparedStatement.setInt(1, quantity);
            preparedStatement.setInt(2, isbn);
            preparedStatement.setString(3, titre);
            preparedStatement.setString(4, auteur);
            preparedStatement.setString(5, "4");

            // Execute the query
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
            	
                System.out.println("\n-------------- Book added successfully! --------------\n");
                while (true) {
                	clearScreen();
                	System.out.println("\n1. Go to menu");
                    System.out.println("\n2. logout");
                    int choice = scanner.nextInt();
                    scanner.nextLine();

                    switch (choice) {
                        case 1:
                        	Managerlog.index();
                            System.exit(0);
                        case 2:
                        	System.out.println("Exiting the program.");
                            scanner.close();
                            System.exit(0);
                        default:
                            System.out.println("Invalid choice. Please try again.");
                }
                }
                
                
            } else {
            	
                System.out.println("Error: Failed to add the book. \n \n");
                Managerlog.index();
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            Managerlog.index();
        }
        
        return true;
	}
	
	public static boolean showall(Scanner scanner)
	{
		Connec conn = new Connec();
		
		try {
		    // Create and execute a SQL query to retrieve all books
		    String query = "SELECT * FROM livre";
		    PreparedStatement preparedStatement = Connec.connection().prepareStatement(query);
		    ResultSet resultSet = preparedStatement.executeQuery();

		    // Print the book records
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
		    

	    } catch (SQLException e) {
	        System.out.println("Error: " + e.getMessage());
	    }
		
		return true;
	}
	public boolean show(Scanner scanner)
	{
		
		clearScreen();
		showall(scanner);
		return true;
	}
	
	public boolean showU(Scanner scanner)
	{
		reservation Managerlog = new reservation();
		clearScreen();
		showall(scanner);
		while (true) {
        	System.out.println("\n1. Go to menu");
            System.out.println("\n2. logout");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                	Managerlog.index(scanner);
                    System.exit(0);
                case 2:
                	System.out.println("Exiting the program.");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
                    return true;
        }
        }
	}
	
	public static boolean deleteLivreByISBN(Scanner scanner) {
		
		showall(scanner);
		System.out.print("\n\nEnter the ISBN: ");
        int isbn = scanner.nextInt();
        scanner.nextLine();
        
	    PreparedStatement preparedStatement = null;
	    Connec conn = new Connec();
	    home Managerlog = new home();
	    
	    try {
	        String sql = "DELETE FROM livre WHERE isbn=?";
	        preparedStatement = Connec.connection().prepareStatement(sql);
	        preparedStatement.setInt(1, isbn);

	        // Execute the delete operation
	        int rowsDeleted = preparedStatement.executeUpdate();

	        if (rowsDeleted > 0) {
	        	clearScreen();
	            System.out.println("----------> The livre with ISBN " + isbn + " was deleted successfully!<----\n\n");
	            
	        } else {
	            System.out.println("----------\n\nNo livre found with ISBN " + isbn);
	            System.out.println("\n\n----------\n\n");
	            Managerlog.deleteLivreByISBN(scanner);
	            
	            return false;
	        }
	    } catch (SQLException e) {
	        System.out.println("Error: " + e.getMessage());
	        Managerlog.index();
	    } finally {
	        try {
	            // Close the resources
	            if (preparedStatement != null) {
	                preparedStatement.close();
	            }
	            if (Connec.connection() != null) {
	            	Connec.connection().close();
	            }
	        } catch (SQLException e) {
	            System.out.println("Error closing resources: " + e.getMessage());
	        }
	    }

	    return false;
	}
	
	public static boolean updateBookByISBN(Scanner scanner) {
	    try {
	    	clearScreen();
	    	home Managerlog = new home();
	    	showall(scanner);
	    	
	        // Prompt the user for the ISBN of the book to update
	        System.out.print("\n\nEnter the ISBN of the book to update: ");
//	        int isbn = scanner.nextInt();
//	        scanner.nextLine(); // Consume the newline character
	        System.out.print("\n-->  ");

            int isbn = scanner.nextInt();
            scanner.nextLine();

	        // Check if the book exists in the database
	        Connec conn = new Connec();
	        String query = "SELECT * FROM livre WHERE isbn = ?";
	        PreparedStatement checkStatement = Connec.connection().prepareStatement(query);
	        checkStatement.setInt(1, isbn);
	        ResultSet resultSet = checkStatement.executeQuery();

	        if (resultSet.next()) {
	            // The book with the given ISBN exists; prompt for updated information
	            System.out.print("\nEnter the new quantity: ");
	            System.out.print("\n-->  ");
	            int newQuantity = scanner.nextInt();
	            scanner.nextLine(); // Consume the newline character
	            
	            

	            System.out.print("\nEnter the new title: ");
	            System.out.print("\n-->  ");
	            String newTitle = scanner.nextLine();
	            
	            System.out.print("\n-->  ");
	            System.out.print("\nEnter the new author: ");
	            String newAuthor = scanner.nextLine();

	            // Execute an SQL UPDATE statement to update the book's information
	            String updateQuery = "UPDATE livre SET quantite = ?, titre = ?, auteur = ? WHERE isbn = ?";
	            PreparedStatement updateStatement = Connec.connection().prepareStatement(updateQuery);
	            updateStatement.setInt(1, newQuantity);
	            updateStatement.setString(2, newTitle);
	            updateStatement.setString(3, newAuthor);
	            updateStatement.setInt(4, isbn);

	            int rowsUpdated = updateStatement.executeUpdate();

	            if (rowsUpdated > 0) {
	                System.out.println("Book information updated successfully!\n");
	                
	                Managerlog.index();
	            } else {
	                System.out.println("Error: Failed to update book information.\n");
	                
	                updateBookByISBN(scanner);
	            }
	        } else {
	            System.out.println("Error: Book with ISBN " + isbn + " not found.\n");
	            updateBookByISBN(scanner);
	        }

	        Connec.connection().close();
	    } catch (SQLException e) {
	        System.out.println("Error: " + e.getMessage());
	    }

	    return true;
	}
}
