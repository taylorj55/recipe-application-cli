import java.util.Scanner;

public class ConsoleMenu {

    private final Scanner scanner = new Scanner(System.in);

    public void displayMenu() {
        System.out.print("""
                Welcome to the Recipe App!
                1. View all recipes
                2. Add a new recipe
                3. Search recipes
                4. Exit
                Please select an option:
                """);
    }

    public int getUserAction() {
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter a number matching the action you wish to perform:");
            scanner.next(); // Clear the invalid input
        }
        return scanner.nextInt();
    }
}
