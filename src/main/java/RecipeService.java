import java.util.List;

public class RecipeService {

    static int MAX_COOKING_TIME = 320;
    private final RecipeRepository repository;

    public RecipeService(RecipeRepository repository) {
        this.repository = repository;
    }

    public List<Recipe> getAllRecipes() {
        return repository.getAllRecipes();
    }

    private void addRecipe(Recipe recipe) {
        repository.addRecipe(recipe);
    }


    public Recipe createRecipe(String name, int cookingTime, String ingredients, String instructions) {
        if (name.isBlank()) {
            throw new IllegalArgumentException("Recipe name cannot be blank");
        }

        if (cookingTime <=0 || cookingTime > MAX_COOKING_TIME) {
            throw new IllegalArgumentException("Cooking time must be greater than 0 and less than 321");
        }

        Recipe recipe = new Recipe(name, cookingTime, ingredients, instructions);
        addRecipe(recipe);
        System.out.println("Recipe added");
        return recipe;
    }

    public void viewAllRecipes(RecipeService recipeService) {
        List<Recipe> recipes = recipeService.getAllRecipes();

        if (recipes.isEmpty()) {
            System.out.println("No recipes found.");
        } else {
            for (Recipe recipe : recipes) {
                System.out.println(recipe.getId());
                System.out.println("Name: " + recipe.getName());
                System.out.println("Cooking time (minutes): " + recipe.getCookingTime());
                System.out.println("Ingredients: " + recipe.getIngredients());
                System.out.println("Instructions: " + recipe.getInstructions());
            }
        }
    }

}
