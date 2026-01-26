import java.util.List;

public class RecipeService {

    private final RecipeRepository repository;

    public RecipeService(RecipeRepository repository) {
        this.repository = repository;
    }

    public List<Recipe> getAllRecipes() {
        return repository.getAllRecipes();
    }

    public void addRecipe(Recipe recipe) {
        repository.addRecipe(recipe);
    }
}
