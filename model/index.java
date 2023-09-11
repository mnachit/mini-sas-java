package model;
import java.util.Scanner;



public class index {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("          \n"
            		+ "                                                                                                            \n"
            		+ "          ____                                                     ____                                     \n"
            		+ "        ,'  , `.                                                 ,'  , `.                                   \n"
            		+ "     ,-+-,.' _ |              ,--,                            ,-+-,.' _ |                                   \n"
            		+ "  ,-+-. ;   , ||            ,--.'|         ,---,           ,-+-. ;   , ||              ,---,          ,--,  \n"
            		+ " ,--.'|'   |  ;|            |  |,      ,-+-. /  |         ,--.'|'   |  ;|          ,-+-. /  |       ,'_ /|  \n"
            		+ "|   |  ,', |  ':  ,--.--.   `--'_     ,--.'|'   |        |   |  ,', |  ':  ,---.  ,--.'|'   |  .--. |  | :  \n"
            		+ "|   | /  | |  || /       \\  ,' ,'|   |   |  ,\"' |        |   | /  | |  || /     \\|   |  ,\"' |,'_ /| :  . |  \n"
            		+ "'   | :  | :  |,.--.  .-. | '  | |   |   | /  | |        '   | :  | :  |,/    /  |   | /  | ||  ' | |  . .  \n"
            		+ ";   . |  ; |--'  \\__\\/: . . |  | :   |   | |  | |        ;   . |  ; |--'.    ' / |   | |  | ||  | ' |  | |  \n"
            		+ "|   : |  | ,     ,\" .--.; | '  : |__ |   | |  |/         |   : |  | ,   '   ;   /|   | |  |/ :  | : ;  ; |  \n"
            		+ "|   : '  |/     /  /  ,.  | |  | '.'||   | |--'          |   : '  |/    '   |  / |   | |--'  '  :  `--'   \\ \n"
            		+ ";   | |`-'     ;  :   .'   \\;  :    ;|   |/              ;   | |`-'     |   :    |   |/      :  ,      .-./ \n"
            		+ "|   ;/         |  ,     .-./|  ,   / '---'               |   ;/          \\   \\  /'---'        `--`----'     \n"
            		+ "'---'           `--`---'     ---`-'                      '---'            `----'                            \n"
            		+ "                                                                                                            \n"
            		+ "          ");
            System.out.println("                                        +-----------------------------+");
            System.out.println("                                        | 1. Register                |");
            System.out.println("                                        | 2. Login                   |");
            System.out.println("                                        | 3. Exit                    |");
            System.out.println("                                        +-----------------------------+");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
            case 1:
//                registerUser(scanner);
                System.exit(0);
            case 2:
                model.User.login(scanner);
                System.exit(0);
            case 3:
                System.out.println("Exiting the program.");
                scanner.close();
                System.exit(0);
            default:
                System.out.println("Invalid choice. Please try again.");
        } 
        }
    }
}
