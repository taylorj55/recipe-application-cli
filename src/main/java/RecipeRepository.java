import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class RecipeRepository {

    public void addRecipe(Recipe recipe) {
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO recipes (name, cooking_time, instructions) VALUES (?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS
             )) {

            statement.setString(1, recipe.getName());
            statement.setInt(2, recipe.getCookingTime());
            statement.setString(3, recipe.getInstructions());

            statement.executeUpdate();

            ResultSet keys = statement.getGeneratedKeys();
            int recipeId = 0;

            if (keys.next()) {
                recipeId = keys.getInt(1);
            }

            String ingredientSql = "INSERT INTO ingredients (recipe_id, name, quantity, measurement) VALUES (?, ?, ?, ?)";

            PreparedStatement ingredientStmt = connection.prepareStatement(ingredientSql);

            for (Ingredient ingredient : recipe.getIngredients()) {
                ingredientStmt.setInt(1, recipeId);
                ingredientStmt.setString(2, ingredient.getName());
                ingredientStmt.setInt(3, ingredient.getQuantity());
                ingredientStmt.setString(4, ingredient.getMeasurement());

                ingredientStmt.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Recipe> getAllRecipes() {
        List<Recipe> recipes = new ArrayList<>();

        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT id, name, cooking_time, instructions FROM recipes"
             );
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                Recipe recipe = new Recipe(
                        id,
                        resultSet.getString("name"),
                        resultSet.getInt("cooking_time"),
                        resultSet.getString("instructions")
                );

                List<Ingredient> ingredients = getIngredientByRecipeId(connection, id);
                recipe.setIngredients(ingredients);
                recipes.add(recipe);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return recipes;
    }

    private List<Ingredient> getIngredientByRecipeId(Connection connection, int recipeId) {
        List<Ingredient> ingredients = new ArrayList<>();

        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT name, quantity, measurement FROM ingredients WHERE recipe_id = ?"
            );

            statement.setInt(1, recipeId);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Ingredient ingredient = new Ingredient(
                        resultSet.getString("name"),
                        resultSet.getInt("quantity"),
                        resultSet.getString("measurement")
                );

                ingredients.add(ingredient);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ingredients;
    }

    public boolean deleteRecipeById(int id) {
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "DELETE FROM recipes WHERE id = ?"
             )) {

            statement.setInt(1, id);

            int rowsAffected = statement.executeUpdate();

            return rowsAffected > 0;
        } catch (Exception e) {
            throw new RuntimeException("Error deleting recipe with id: " + id, e);
        }
    }
}