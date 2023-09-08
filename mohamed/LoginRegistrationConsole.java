package mohamed;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class LoginRegistrationConsole {
    private static Map<String, String> userDatabase = new HashMap<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    registerUser(scanner);
                    break;
                case 2:
                    loginUser(scanner);
                    break;
                case 3:
                    System.out.println("Exiting the program.");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void registerUser(Scanner scanner) {
        System.out.print("Enter a username: ");
        String username = scanner.nextLine();

        if (userDatabase.containsKey(username)) {
            System.out.println("Username already exists. Please choose a different one.");
        } else {
            System.out.print("Enter a password: ");
            String password = scanner.nextLine();
            userDatabase.put(username, password);
            System.out.println("Registration successful!");
        }
    }

    private static void loginUser(Scanner scanner) {
        System.out.print("Enter your username: ");
        String username = scanner.nextLine();

        if (userDatabase.containsKey(username)) {
            System.out.print("Enter your password: ");
            String password = scanner.nextLine();

            if (userDatabase.get(username).equals(password)) {
                System.out.println("Login successful!");
            } else {
                System.out.println("Incorrect password. Please try again.");
            }
        } else {
            System.out.println("Username not found. Please register first.");
        }
    }
}

