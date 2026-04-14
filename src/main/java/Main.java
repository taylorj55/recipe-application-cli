public class Main {

    //TODO throwing exception blanketly across the entire method is not good practice, instead try putting the DatabaseManager call in a try-catch block to better log exactly what fails.
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
                    recipeFlow.viewAllRecipes(recipeService);
                    break;
                case 2:
                    recipeFlow.createRecipeFlow(menu, recipeService);
                    break;
                case 3:
                    System.out.println("Search recipes (not implemented)");
                    break;
                case 4:
                    //GOOD: I like the UX consideration of listing all the recipes so the user doesnt have to have the recipe ID memorised before deleting
                    recipeFlow.viewAllRecipes(recipeService);
                    recipeFlow.deleteRecipeById(menu, recipeService);
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
}
