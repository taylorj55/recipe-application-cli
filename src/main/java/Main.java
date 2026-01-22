import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        ConsoleMenu menu = new ConsoleMenu();
        boolean running = true;

        while (running) {
            menu.displayMenu();
            int action = menu.getUserAction();

            switch (action) {
                case 1:
                    System.out.println("View all recipes (not implemented)");
                    break;
                case 2:
                    System.out.println("Add a new recipe (not implemented)");
                    break;
                case 3:
                    System.out.println("Search recipes (not implemented)");
                    break;
                case 4:
                    System.out.println("Exiting the application");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid action, please select a number from the list provided.");
            }
        }
    }
}
