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
            int action = menu.getUserAction();

            switch (action) {
                case 1:
                    viewAllRecipes(recipeService);
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

}
