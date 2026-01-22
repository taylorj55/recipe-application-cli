import java.util.ArrayList;
import java.util.List;

public class RecipeRepository {

    private final List<Recipe> recipes = new ArrayList<>();

    public List<Recipe> getAllRecipes() {
        return recipes;
    }

    public void addRecipe(Recipe recipe) {
        recipes.add(recipe);
    }
}
