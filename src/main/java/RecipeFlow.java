public class RecipeFlow {

    public void createRecipeFlow(ConsoleMenu menu, RecipeService recipeService) {
        int cookingTime = -1;
        String name = menu.getUserString("Enter recipe Name: ");

        while (name.isBlank()) {
            printValidationMessage("name");
            name = menu.getUserString("Enter recipe Name: ");
        }

        while (cookingTime < 0 || cookingTime > RecipeService.MAX_COOKING_TIME) {
            cookingTime = menu.getUserInt("Enter recipe cooking time: ");

            if (cookingTime > RecipeService.MAX_COOKING_TIME) {
                System.out.println("Cooking time maximum value is " + RecipeService.MAX_COOKING_TIME);
            } else if (cookingTime < 0) {
                System.out.println("Cooking time cannot be negative/blank");
            }
        }

        String ingredients = menu.getUserString("Enter the ingredients in a comma separated list on 1 line: ");

        while (ingredients.isBlank()) {
            printValidationMessage("ingredients");
            ingredients = menu.getUserString("Enter the ingredients: ");
        }
        String instructions = menu.getUserString("Enter the instruction: ");

        recipeService.createRecipe(name, cookingTime, ingredients, instructions);
        System.out.println("Recipe added");
    }

    private static void printValidationMessage(String recipeComponent) {
        System.out.println("Recipe " + recipeComponent + " cannot be blank.");
    }
}