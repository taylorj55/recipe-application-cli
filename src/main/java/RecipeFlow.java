import java.util.ArrayList;
import java.util.List;

public class RecipeFlow {

    //TODO you have ConsoleMenu and RecipeService as method params in this class, a better approach would be to inject them through the constructor like you did in RecipeService for the RecipeRepository

    public void createRecipeFlow(ConsoleMenu menu, RecipeService recipeService) {
        String name = getRecipeName(menu);
        int cookingTime = getCookingTime(menu);

        //TODO separate the flow into two branching methods so that you can use either of these methods
//        List<Ingredient> ingredients = getIngredientValues(menu); //this will get the ingredients by asking for each component separately
        List<Ingredient> ingredients = getIngredientValuesWholeString(menu); //this will get a string like 'chicken 300 grams and split it up into each part

        String instructions = menu.getUserString("Enter the instruction: ");

        recipeService.createRecipe(name, cookingTime, ingredients, instructions);
        System.out.println("Recipe added");
    }

    //TODO, I think because of your improving skills, getIngredientValuesWholeString contains much better value validation. For example, ingredient name can be blank here!
    private List<Ingredient> getIngredientValues(ConsoleMenu menu) {
        List<Ingredient> ingredients = new ArrayList<>();
        while (true) {
            String ingredientName = menu.getUserString("Enter the ingredient name: ");
            int quantity = menu.getUserInt("Enter quantity: ");
            String measurement = menu.getUserString("Enter measurement: ");
            String addMore = menu.getUserString("Add another ingredient? (y/n): ");

            ingredients.add(new Ingredient(ingredientName, quantity, measurement));

            if (!addMore.equalsIgnoreCase("y"))
                break;
        }
        return ingredients;
    }

    //GOOD I think you need to appreciate how much you have developed already as an engineer by comparing this method to the above one. Good input validation, good error handling and feedback. Excellent progress!
    private List<Ingredient> getIngredientValuesWholeString(ConsoleMenu menu) {
        List<Ingredient> ingredients = new ArrayList<>();
        String splitValue = menu.getUserString("Please enter a value to separate ingredient components by (e.g. -): ");
        while (splitValue.isBlank()) {
            splitValue = menu.getUserString("Separator cannot be blank, please try again: ");
        }

        while (true) {
            String[] parts = menu.getUserString("Enter the ingredient details:").split(splitValue);
            if (parts.length != 3) {
                System.out.println("Invalid ingredient format. Please enter: name" + splitValue + "quantity" + splitValue +"measurement");
                continue;
            }

            String ingredientName = parts[0].trim();
            String quantity = parts[1].trim();
            String unit = parts[2].trim();

            if (ingredientName.isBlank() || quantity.isBlank() || unit.isBlank()) {
                System.out.println("Error all fields must not be blank");
                continue;
            }

            //TODO This prompt to the user is done before validation on the previous ingredient, causing potential for logic issues if the previous ingredient fails.
            String addMore = menu.getUserString("Add another ingredient? (y/n): ");

            try {
                int value = Integer.parseInt(quantity);
                ingredients.add(new Ingredient(ingredientName, value, unit));
            } catch (NumberFormatException e) {
                System.out.println("Error: '" + quantity + "' is not a valid number.");
                continue;
            }

            if (!addMore.equalsIgnoreCase("y"))
                break;
        }
        return ingredients;
    }

    private int getCookingTime(ConsoleMenu menu) {
        int cookingTime = -1;

        while (cookingTime < 0 || cookingTime > RecipeService.MAX_COOKING_TIME) {
            cookingTime = menu.getUserInt("Enter recipe cooking time: ");

            //GOOD Specific, helpful error messages!
            if (cookingTime > RecipeService.MAX_COOKING_TIME) {
                System.out.println("Cooking time maximum value is " + RecipeService.MAX_COOKING_TIME);
            } else if (cookingTime < 0) {
                System.out.println("Cooking time cannot be negative/blank");
            }
        }
        return cookingTime;
    }

    private String getRecipeName(ConsoleMenu menu) {
        String name = menu.getUserString("Enter recipe Name: ");

        while (name.isBlank()) {
            printValidationMessage("name");
            name = menu.getUserString("Enter recipe Name: ");
        }
        return name;
    }

    //GOOD excellent use of the DRY principle, Don't Repeat Yourself, extract it to a method instead!
    private static void printValidationMessage(String recipeComponent) {
        System.out.println("Recipe " + recipeComponent + " cannot be blank.");
    }

    public void viewAllRecipes(RecipeService recipeService) {
        System.out.println("All Recipes:");
        List<Recipe> recipes = recipeService.getAllRecipes();
        printAllRecipes(recipes);
    }

    private void printAllRecipes(List<Recipe> recipes) {
        if (recipes.isEmpty()) {
            System.out.println("No recipes found.");
        } else {
            for (Recipe recipe : recipes) {
                System.out.println(recipe.getId());
                System.out.println("Name: " + recipe.getName());
                System.out.println("Cooking time (minutes): " + recipe.getCookingTime());
                System.out.println("Ingredients: ");
                for (Ingredient ingredient : recipe.getIngredients()) {
                    System.out.println(ingredient);
                }
                System.out.println("Instructions: " + recipe.getInstructions());
            }
        }
    }

    public void deleteRecipeById(ConsoleMenu menu, RecipeService recipeService) {
        int id = menu.getUserInt("Enter the ID for the recipe you would like to delete:");
        recipeService.deleteRecipeById(id);
    }
}