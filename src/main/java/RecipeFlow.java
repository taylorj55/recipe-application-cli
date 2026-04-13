import java.util.ArrayList;
import java.util.List;

public class RecipeFlow {

    public void createRecipeFlow(ConsoleMenu menu, RecipeService recipeService) {
        String name = getRecipeName(menu);
        int cookingTime = getCookingTime(menu);

//        List<Ingredient> ingredients = getIngredientValues(menu); //this will get the ingredients by asking for each component separately
        List<Ingredient> ingredients = getIngredientValuesWholeString(menu); //this will get a string like 'chicken 300 grams and split it up into each part

        String instructions = menu.getUserString("Enter the instruction: ");

        recipeService.createRecipe(name, cookingTime, ingredients, instructions);
        System.out.println("Recipe added");
    }

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

    private List<Ingredient> getIngredientValuesWholeString(ConsoleMenu menu) {
        List<Ingredient> ingredients = new ArrayList<>();
        String splitValue = menu.getUserString("Please enter a value to separate ingredient components by: ");

        while (true) {
            String[] parts = menu.getUserString("Enter the ingredient details:").split(splitValue);
            if (parts.length != 3) {
                System.out.println("Invalid ingredient format. Please enter: name" + splitValue + "quantity" + splitValue +"measurement");
                continue;
            }
            String ingredientName = parts[0];
            int quantity = Integer.parseInt(parts[1]);
            String measurement = parts[2];
            String addMore = menu.getUserString("Add another ingredient? (y/n): ");

            ingredients.add(new Ingredient(ingredientName, quantity, measurement));

            if (!addMore.equalsIgnoreCase("y"))
                break;
        }
        return ingredients;
    }

    private int getCookingTime(ConsoleMenu menu) {
        int cookingTime = -1;

        while (cookingTime < 0 || cookingTime > RecipeService.MAX_COOKING_TIME) {
            cookingTime = menu.getUserInt("Enter recipe cooking time: ");

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