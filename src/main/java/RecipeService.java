import java.util.List;

public class RecipeService {

    public static final int MAX_COOKING_TIME = 320;
    private final RecipeRepository repository;

    public RecipeService(RecipeRepository repository) {
        this.repository = repository;
    }

    public List<Recipe> getAllRecipes() {
        return repository.getAllRecipes();
    }

    private void addRecipe(Recipe recipe) {
        repository.saveRecipe(recipe);
    }

    public void deleteRecipeById(int id) {
        repository.deleteRecipeById(id);
    }

    public Recipe createRecipe(String name, int cookingTime, List<Ingredient> ingredients, String instructions) {
        if (name.isBlank()) {
            throw new IllegalArgumentException("Recipe name cannot be blank");
        }

        if (cookingTime <=0 || cookingTime > MAX_COOKING_TIME) {
            throw new IllegalArgumentException("Cooking time must be greater than 0 and less than 321");
        }

        Recipe recipe = new Recipe(name, cookingTime, ingredients, instructions);
        addRecipe(recipe);
        return recipe;
    }
}
