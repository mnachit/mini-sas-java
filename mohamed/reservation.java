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
	    for (int i = 0; i < 6; i++) {
	        System.out.println();
	    }
	}
	
	public boolean makeReservation(int isbn, int userId, int isbn1) {
	    Connec conn = new Connec();
	    PreparedStatement preparedStatement = null;
	    
	    Date currentDate = new Date();
	    Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 0);
        java.util.Date utilReturnDate = calendar.getTime();
        java.sql.Date returnDate = new java.sql.Date(utilReturnDate.getTime());
        
        Calendar calendar1 = Calendar.getInstance();
        calendar1.add(Calendar.DAY_OF_MONTH, 8);
        java.util.Date utilReturnDate1 = calendar1.getTime();
        java.sql.Date returnDate1 = new java.sql.Date(utilReturnDate1.getTime());
        
	    try {
	        String sql = "INSERT INTO reservation (isbn, user, date, date_limit) VALUES (?, ?, ?, ?)";

	        preparedStatement = conn.connection().prepareStatement(sql);

	        preparedStatement.setInt(1, isbn);
	        preparedStatement.setInt(2, userId);
	        preparedStatement.setDate(3, returnDate);
	        preparedStatement.setDate(4, returnDate1);

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

	
	public void searchBooks(Scanner scanner, int iduser) {
		
		Connec conn = new Connec();
//		clearScreen();
		System.out.println("                                        +--------------------------+");
		System.out.println("                                        |   Choose search criteria |");
		System.out.println("                                        +--------------------------+");
		System.out.println("                                        | 1. Search by title       |");
		System.out.println("                                        | 2. Search by author      |");
		System.out.println("                                        +--------------------------+\n");
		System.out.print("  Enter your choice (1 or 2):\n\n");


        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume the newline

        System.out.print("Enter search query:     =>");
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
            System.out.println("                                  ----------------------------------------------------------------------------");
		    System.out.printf("                                  | %-5s | %-10s | %-15s | %-15s | %-15s |\n", "ID", "Quantity", "ISBN", "Title", "Author");
		    System.out.println("                                  ----------------------------------------------------------------------------");
            
            while (resultSet.next()) {
		        int id = resultSet.getInt("id");
		        int quantity = resultSet.getInt("quantite");
		        int isbn = resultSet.getInt("isbn");
		        String titre = resultSet.getString("titre");
		        String auteur = resultSet.getString("auteur");

		        System.out.printf("                                  | %-5d | %-10d | %-15d | %-15s | %-15s |\n", id, quantity, isbn, titre, auteur);
		    }
            
            System.out.println("                                  ----------------------------------------------------------------------------\n");
            
            while (true) {
            	System.out.println("\n                                  +-----------------------+");
            	System.out.println("                                  |        Main Menu      |");
            	System.out.println("                                  +-----------------------+");
            	System.out.println("                                  | 1. Go to menu         |");
            	System.out.println("                                  | 2. Logout             |");
            	System.out.println("                                  +-----------------------+");
            	System.out.print("Enter your choice (1 or 2): =>  ");

                int ss = scanner.nextInt();
                scanner.nextLine();

                switch (ss) {
                    case 1:
                    	index(scanner, iduser);
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
	
	
	public void index(Scanner scanner, int id)
	{
//		Scanner scanner = new Scanner(System.in);

        while (true) {
        	clearScreen();
        	System.out.printf(""
        			+ "\n"
        			+ "                                                                                                                           \n"
        			+ "                                                                                                                           \n"
        			+ "                          ,--,                                ____                    ,---,                                \n"
        			+ "                        ,--.'|                              ,'  , `.                ,--.' |                                \n"
        			+ "         .---.          |  | :               ,---.       ,-+-,.' _ |                |  |  :                __  ,-.         \n"
        			+ "        /. ./|          :  : '              '   ,'\\   ,-+-. ;   , ||                :  :  :              ,' ,'/ /|         \n"
        			+ "     .-'-. ' |   ,---.  |  ' |      ,---.  /   /   | ,--.'|'   |  || ,---.          :  |  |,--.   ,---.  '  | |' | ,---.   \n"
        			+ "    /___/ \\: |  /     \\ '  | |     /     \\.   ; ,. :|   |  ,', |  |,/     \\         |  :  '   |  /     \\ |  |   ,'/     \\  \n"
        			+ " .-'.. '   ' . /    /  ||  | :    /    / ''   | |: :|   | /  | |--'/    /  |        |  |   /' : /    /  |'  :  / /    /  | \n"
        			+ "/___/ \\:     '.    ' / |'  : |__ .    ' / '   | .; :|   : |  | ,  .    ' / |        '  :  | | |.    ' / ||  | ' .    ' / | \n"
        			+ ".   \\  ' .\\   '   ;   /||  | '.'|'   ; :__|   :    ||   : |  |/   '   ;   /|        |  |  ' | :'   ;   /|;  : | '   ;   /| \n"
        			+ " \\   \\   ' \\ |'   |  / |;  :    ;'   | '.'|\\   \\  / |   | |`-'    '   |  / |        |  :  :_:,''   |  / ||  , ; '   |  / | \n"
        			+ "  \\   \\  |--\" |   :    ||  ,   / |   :    : `----'  |   ;/        |   :    |        |  | ,'    |   :    | ---'  |   :    | \n"
        			+ "   \\   \\ |     \\   \\  /  ---`-'   \\   \\  /          '---'          \\   \\  /         `--''       \\   \\  /         \\   \\  /  \n"
        			+ "    '---\"       `----'             `----'                           `----'                       `----'           `----'   \n"
        			+ "                                                                                                                           \n"
        			+ "");
        	System.out.println("\n                                        -----------------------------------------------");
        	System.out.println("                                        1. Display the list of borrowed books\n");
        	System.out.println("                                        2. Search for a book by its title or author\n");
        	System.out.println("                                        3. Borrow a book\n");
        	System.out.println("                                        4. Return a borrowed book\n");
        	System.out.println("                                        5. Exit");
        	System.out.println("                                          -----------------------------------------------\n");
        	System.out.print("Choose an option: => \n\n");

        	
            int choice = scanner.nextInt();
            scanner.nextLine();

            home Managerhome = new home();
            switch (choice) {
                case 1:
//                	Managerhome.show(scanner);
                	Managerhome.showU(scanner, id);
                    System.exit(0);
                case 2:
                	searchBooks(scanner,id);
                case 3:
                	emprunter(scanner, id);
                case 4:
                	returnbook(scanner, id);
                	
                case 5:
                    System.out.println(""
                    		+ "\n"
                    		+ "                                                                                                                             \n"
                    		+ "                                                                   dddddddd                                                  \n"
                    		+ "        GGGGGGGGGGGGG                                              d::::::d     BBBBBBBBBBBBBBBBB                            \n"
                    		+ "     GGG::::::::::::G                                              d::::::d     B::::::::::::::::B                           \n"
                    		+ "   GG:::::::::::::::G                                              d::::::d     B::::::BBBBBB:::::B                          \n"
                    		+ "  G:::::GGGGGGGG::::G                                              d:::::d      BB:::::B     B:::::B                         \n"
                    		+ " G:::::G       GGGGGG   ooooooooooo      ooooooooooo       ddddddddd:::::d        B::::B     B:::::Byyyyyyy           yyyyyyy\n"
                    		+ "G:::::G               oo:::::::::::oo  oo:::::::::::oo   dd::::::::::::::d        B::::B     B:::::B y:::::y         y:::::y \n"
                    		+ "G:::::G              o:::::::::::::::oo:::::::::::::::o d::::::::::::::::d        B::::BBBBBB:::::B   y:::::y       y:::::y  \n"
                    		+ "G:::::G    GGGGGGGGGGo:::::ooooo:::::oo:::::ooooo:::::od:::::::ddddd:::::d        B:::::::::::::BB     y:::::y     y:::::y   \n"
                    		+ "G:::::G    G::::::::Go::::o     o::::oo::::o     o::::od::::::d    d:::::d        B::::BBBBBB:::::B     y:::::y   y:::::y    \n"
                    		+ "G:::::G    GGGGG::::Go::::o     o::::oo::::o     o::::od:::::d     d:::::d        B::::B     B:::::B     y:::::y y:::::y     \n"
                    		+ "G:::::G        G::::Go::::o     o::::oo::::o     o::::od:::::d     d:::::d        B::::B     B:::::B      y:::::y:::::y      \n"
                    		+ " G:::::G       G::::Go::::o     o::::oo::::o     o::::od:::::d     d:::::d        B::::B     B:::::B       y:::::::::y       \n"
                    		+ "  G:::::GGGGGGGG::::Go:::::ooooo:::::oo:::::ooooo:::::od::::::ddddd::::::dd     BB:::::BBBBBB::::::B        y:::::::y        \n"
                    		+ "   GG:::::::::::::::Go:::::::::::::::oo:::::::::::::::o d:::::::::::::::::d     B:::::::::::::::::B          y:::::y         \n"
                    		+ "     GGG::::::GGG:::G oo:::::::::::oo  oo:::::::::::oo   d:::::::::ddd::::d     B::::::::::::::::B          y:::::y          \n"
                    		+ "        GGGGGG   GGGG   ooooooooooo      ooooooooooo      ddddddddd   ddddd     BBBBBBBBBBBBBBBBB          y:::::y           \n"
                    		+ "                                                                                                          y:::::y            \n"
                    		+ "                                                                                                         y:::::y             \n"
                    		+ "                                                                                                        y:::::y              \n"
                    		+ "                                                                                                       y:::::y               \n"
                    		+ "                                                                                                      yyyyyyy                \n"
                    		+ "                                                                                                                             \n"
                    		+ "                                                                                                                             \n"
                    		+ "");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
	}
	
//	public void returnbook(Scanner scanner)
//	{
//		
//	}
	
	public void returnbook(Scanner scanner, int userId) {
		String query3 = "SELECT l.isbn, r.date, r.statut, r.user FROM reservation r JOIN livre l ON r.isbn = l.id WHERE r.user = ? AND r.statut = 'reserve'";
		try {
		    PreparedStatement preparedStatement3 = Connec.connection().prepareStatement(query3);
		    preparedStatement3.setInt(1, userId);
		    ResultSet resultSet3 = preparedStatement3.executeQuery();

		    // Print table header
		    System.out.println("                                  +--------------------------------+-------------+--------------+");
		    System.out.println("                                  |           ISBN                 | Date return |  Status      |");
		    System.out.println("                                  +--------------------------------+-------------+--------------+");

		    // Iterate through the ResultSet and print rows
		    while (resultSet3.next()) {
		        int isbn = resultSet3.getInt("isbn");
		        String date = resultSet3.getString("date");
		        String status = resultSet3.getString("statut");

		        System.out.printf("                                  | %-30d | %-7s  | %-10s   |\n", isbn, date, status);
		    }

		    // Print table footer
		    System.out.println("                                  +--------------------------------+---------+------------------+");
		    
		    
		    System.out.println("\n----------------------------------------------------------------");
		    System.out.println("1. Enter ISBN: => ");
		    
		    int isbnvalid = scanner.nextInt();
		    scanner.nextLine();
		    
		    String query4 = "SELECT id FROM livre WHERE isbn = ?";
		    PreparedStatement preparedStatement4 = Connec.connection().prepareStatement(query4);
		    preparedStatement4.setInt(1, isbnvalid);
		    ResultSet resultSet4 = preparedStatement4.executeQuery();
		    
		    if (resultSet4.next()) {
		    	
		    	Date currentDate = new Date();
			    Calendar calendar = Calendar.getInstance();
		        calendar.add(Calendar.DAY_OF_MONTH, 0);
		        java.util.Date utilReturnDate = calendar.getTime();
		        java.sql.Date returnDate = new java.sql.Date(utilReturnDate.getTime());
		        
		    	int sibnupdate = resultSet4.getInt("id");
		        // Update the reservation status to 'unavailable' for the specified ISBN and 'reserve' status
		    	String updateQuery = "UPDATE reservation SET statut = 'unavailable', date_return = ? WHERE isbn = ? AND statut = 'reserve'";
		        PreparedStatement preparedStatement5 = Connec.connection().prepareStatement(updateQuery);
		        preparedStatement5.setDate(1, returnDate);
		        preparedStatement5.setInt(2, sibnupdate);
		        int rowsUpdated = preparedStatement5.executeUpdate();

		        if (rowsUpdated > 0) {
		        	System.out.println("                                  +-----------------------------+");
		        	System.out.println("                                  |         Menu Options        |");
		        	System.out.println("                                  +-----------------------------+");
		        	System.out.println("                                  | 1. Go to Menu               |");
		        	System.out.println("                                  | 2. Logout                   |");
		        	System.out.println("                                  +-----------------------------+");
		        	System.out.print("\n\nPlease select an option (1 or 2): =>");
		        	
		        	
		        	int Menu = scanner.nextInt();
				    scanner.nextLine();
				    
				    switch(Menu)
				    {
				    	case 1:
				    		reservation user = new reservation();
	                    	user.index(scanner, userId);
				    	case 2:
				    		System.out.println("Exiting the program.");
		                    scanner.close();
		                    System.exit(0);

		                default:
		                    System.out.println("Invalid choice. Please try again.");
				    }

		        } else {
		            System.out.println("No reservations found for the given ISBN and status.");
		        }
		    } else {
		        System.out.println("No book found with the given ISBN.");
		    }
		} catch (SQLException e) {
		    System.out.println("Error: " + e.getMessage());
		}

	}

	public void emprunter(Scanner scanner, int iduser)
	{
		home Managerhome = new home();
		Managerhome.show(scanner);
		System.out.println("\n                                        ----------------------------------------------------------------------------");
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
                    
                    Date currentDate = new Date();
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(currentDate);

                    // Add 7 days to the current date
                    calendar.add(Calendar.DAY_OF_MONTH, 8);
                    Date returnDate = calendar.getTime();

                    // Format the dates as strings
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    String datenow = dateFormat.format(currentDate);
                    String returnDateStr = dateFormat.format(returnDate);

                    // Print the message
                    System.out.println("                                       ------------------------------------------------------------");
                    System.out.println("                                       |                Return Book Confirmation                  |");
                    System.out.println("                                       ------------------------------------------------------------");
                    System.out.println("                                       |                  The book is available.                  |");
                    System.out.println("                                       |                                                          |");
                    System.out.println("                                       | 1. Are you sure? You must return the book on: " + returnDateStr + " |");
                    System.out.println("                                       | 2. Try again!                                            |");
                    System.out.println("                                       | 3. Go to menu?                                           |");
                    System.out.println("                                       ------------------------------------------------------------");


                    
                    System.out.print("Choose an option: => ");
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
//                    	        int dd = user.getId();
                            	
//                            	System.out.printf("%d",iduser);
                    	        makeReservation(num,iduser , isbn);
                    	        // You can call makeReservation(num, 2) here if needed.
                    	    } else {
                    	        System.out.println("No book found with the given ISBN.");
                    	    }

                    	    System.exit(0);
                    	} catch (SQLException e) {
                    	    System.out.println("Error: " + e.getMessage());
                    	}

                    case 2:
                    	emprunter(scanner, iduser);
                    case 3:
                    	index(scanner, iduser);
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
                } else {
                	System.out.println("                                       +-----------------------------------------------------+");
                	System.out.println("                                       |               Book Availability Alert               |");
                	System.out.println("                                       +-----------------------------------------------------+");
                	System.out.println("                                       | The book is not available (quantity = 0).           |");
                	System.out.println("                                       |                                                     |");
                	System.out.println("                                       | 1. Try again!                                       |");
                	System.out.println("                                       | 2. Go to menu!                                      |");
                	System.out.println("                                       | 3. Logout                                           |");
                	System.out.println("                                       +-----------------------------------------------------+");

                    
                	System.out.print("\n\nChoose an option: => ");
                    int N2 = scanner.nextInt();
                    scanner.nextLine();
                    
                    switch (N2) {
                    case 1:
                    	emprunter(scanner, iduser);    
                    case 2:
                    	index(scanner, iduser);
                    case 3:
                    	System.out.println("Exiting the program.");
                        scanner.close();
                        System.exit(0);
                    }
                }
            } else {
                System.out.println("No book found with the given ISBN.");
                System.out.println("                                       +-----------------------------------------------------+");
            	System.out.println("                                       |        No book found with the given ISBN            |");
            	System.out.println("                                       +-----------------------------------------------------+");
            	System.out.println("                                       |                                                     |");
            	System.out.println("                                       | 1. Try again!                                       |");
            	System.out.println("                                       | 2. Go to menu!                                      |");
            	System.out.println("                                       | 3. Logout                                           |");
            	System.out.println("                                       +-----------------------------------------------------+");
            	
            	System.out.print("\n\nChoose an option: => ");
                int N2 = scanner.nextInt();
                scanner.nextLine();
                
                switch (N2) {
                case 1:
                	emprunter(scanner, iduser);    
                case 2:
                	index(scanner, iduser);
                case 3:
                	System.out.println("Exiting the program.");
                    scanner.close();
                    System.exit(0);
                }
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
	            System.out.println("                                       ----------------------------------------------------------------------------");
	            System.out.printf("                                       | %-5s | %-10s | %-15s | %-15s | %-15s |\n", "ID", "Quantity", "ISBN", "Title", "Author");
	            System.out.println("                                       ----------------------------------------------------------------------------");

	            do {
	                int id = resultSet.getInt("id");
	                int quantity = resultSet.getInt("quantite");
	                int isbn = resultSet.getInt("isbn");
	                String titre = resultSet.getString("titre");
	                String auteur = resultSet.getString("auteur");

	                System.out.printf("                                       | %-5d | %-10d | %-15d | %-15s | %-15s |\n", id, quantity, isbn, titre, auteur);
	            } while (resultSet.next());

	            System.out.println("                                       ----------------------------------------------------------------------------");
	        }

	    } catch (SQLException e) {
	        System.out.println("Error: " + e.getMessage());
	    }

//	    return true;
	}

}
