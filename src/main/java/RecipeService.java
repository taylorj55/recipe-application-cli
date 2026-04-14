import java.util.List;

public class RecipeService {

    public static final int MAX_COOKING_TIME = 320;
    private final RecipeRepository repository;

    //GOOD I love to see the dependency injection here
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
        //TODO deleteRecipeById returns a boolean value for success/failure but that value is currently ignored, it should be either removed or preferably used to feedback
        repository.deleteRecipeById(id);
    }

    //GOOD having the validation here for the business logic (cooking time etc.) is the correct approach, allowing the flow to focus on user prompting and its dynamic if you later add a RESTFUL or alternate UI path through the application.
    public Recipe createRecipe(String name, int cookingTime, List<Ingredient> ingredients, String instructions) {
        //TODO what about null values passed? And ingredients/instructions are not checked, are they optional?
        if (name.isBlank()) {
            throw new IllegalArgumentException("Recipe name cannot be blank");
        }

        if (cookingTime <=0 || cookingTime > MAX_COOKING_TIME) {
            //TODO You have a static to track magic numbers, use them in your logging too or else that 321 number is going to get out of date if MAX_COOKING_TIME ever changes
            throw new IllegalArgumentException("Cooking time must be greater than 0 and less than 321");
        }

        Recipe recipe = new Recipe(name, cookingTime, ingredients, instructions);
        addRecipe(recipe);
        return recipe;
    }
}
