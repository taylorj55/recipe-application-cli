import java.util.Scanner;


public class Main {

    public static void main(String[] args) {
        ConsoleMenu menu = new ConsoleMenu();
        RecipeRepository repository = new RecipeRepository();
        RecipeService recipeService = new RecipeService(repository);
        boolean running = true;

        Recipe sampleRecipe = new Recipe("Pizza", "Dough, Sauce, Cheese, Pepperoni", "Roll dough, add sauce and cheese on top. Finish with pepperoni slices.");
        repository.addRecipe(sampleRecipe);

        while (running) {
            menu.displayMenu();
            int action = menu.getUserInt("Please select an option: ");

            switch (action) {
                case 1:
                    viewAllRecipes(recipeService);
                    break;
                case 2:
                    addRecipe(menu, recipeService);
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

    private static void viewAllRecipes(RecipeService recipeService) {
        var recipes = recipeService.getAllRecipes();

        if (recipes.isEmpty()) {
            System.out.println("No recipes found.");
        } else {
            for (var recipe : recipes) {
                System.out.println("Recipe: " + recipe.getName());
            }
        }
    }

    private static void addRecipe(ConsoleMenu menu, RecipeService recipeService) {
        String name = menu.getUserString("Enter recipe Name: ");
        String ingredients = menu.getUserString("Enter the ingredients in a comma separated list on 1 line: ");
        String instructions = menu.getUserString("Enter the instruction on 1 line: ");
        System.out.println("Recipe name: " + name);
        System.out.println("Recipe ingredients: " + ingredients);
        System.out.println("Recipe instructions: " + instructions);;
    }

}
