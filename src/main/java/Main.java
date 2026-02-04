import java.util.List;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) {
        ConsoleMenu menu = new ConsoleMenu();
        RecipeRepository repository = new RecipeRepository();
        RecipeService recipeService = new RecipeService(repository);
        boolean running = true;

        Recipe sampleRecipe = new Recipe("Pizza", 20 ,"Dough, Sauce, Cheese, Pepperoni", "Roll dough, add sauce and cheese on top. Finish with pepperoni slices.");
        repository.addRecipe(sampleRecipe);

        while (running) {
            menu.displayMenu();
            int action = menu.getUserInt("Please enter a number to select an action: ");

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
        List<Recipe> recipes = recipeService.getAllRecipes();

        if (recipes.isEmpty()) {
            System.out.println("No recipes found.");
        } else {
            for (var recipe : recipes) {
                System.out.println("Name: " + recipe.getName());
                System.out.println("Cooking time (minutes): " + recipe.getCookingTime());
                System.out.println("Ingredients: " + recipe.getIngredients());
                System.out.println("Instructions: " + recipe.getInstructions());
            }
        }
    }

    private static void addRecipe(ConsoleMenu menu, RecipeService recipeService) {
        String name = menu.getUserString("Enter recipe Name: ");

        while (name.isBlank()) {
            printValidationMessage("name");
            name = menu.getUserString("Enter recipe Name: ");
        }

        int cookingTime = menu.getUserInt("Enter recipe cooking time: ");

        if (cookingTime > 320) {
            System.out.println("Cooking time maximum value is 320");
            cookingTime = menu.getUserInt("Enter recipe cooking time: ");
        }

        String ingredients = menu.getUserString("Enter the ingredients in a comma separated list on 1 line: ");

        while(ingredients.isBlank()) {
            printValidationMessage("ingredients");
            ingredients = menu.getUserString("Enter the ingredients: ");
        }
        String instructions = menu.getUserString("Enter the instruction: ");

        Recipe recipe = new Recipe(name, cookingTime, ingredients, instructions);
        recipeService.addRecipe(recipe);
        System.out.println("Recipe added");
    }

    private static void printValidationMessage(String recipeComponent) {
        System.out.println("Recipe " + recipeComponent + " cannot be blank.");
    }

}
