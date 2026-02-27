public class Main {

    public static void main(String[] args) throws Exception {
        DatabaseManager.initDatabase();

        ConsoleMenu menu = new ConsoleMenu();
        RecipeRepository repository = new RecipeRepository();
        RecipeService recipeService = new RecipeService(repository);
        RecipeFlow recipeFlow = new RecipeFlow();
        boolean running = true;

        System.out.println("Welcome to the Recipe App!");

        while (running) {
            menu.displayMenu();
            int action = menu.getUserInt("Please enter a number to select an action: ");

            switch (action) {
                case 1:
                    recipeService.viewAllRecipes(recipeService);
                    break;
                case 2:
                    recipeFlow.createRecipeFlow(menu, recipeService);
                    break;
                case 3:
                    System.out.println("Search recipes (not implemented)");
                    break;
                case 4:
                    System.out.println("All Recipes:");
                    recipeService.viewAllRecipes(recipeService);
                    deleteRecipeById(menu, recipeService);
                    break;
                case 5:
                    System.out.println("Exiting the application");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid action, please select a number from the list provided.");
            }
        }
    }

    private static void deleteRecipeById(ConsoleMenu menu, RecipeService recipeService) {
        int id = menu.getUserInt("Enter the ID for the recipe you would like to delete:");
        recipeService.deleteRecipeById(id);
    }
}
