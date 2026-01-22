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
                """);
    }

    public String getUserString(String prompt) {
        System.out.println(prompt);
        return scanner.nextLine();
    }

    public  int getUserInt(String prompt) {
        System.out.println(prompt);
        String input = scanner.nextLine();
        return Integer.parseInt(input);
    }
}
