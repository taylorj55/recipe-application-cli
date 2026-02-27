import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class RecipeRepository {

    public void addRecipe(Recipe recipe) {
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO recipes (name, cooking_time, ingredients, instructions) VALUES (?, ?, ?, ?)"
             )) {

            statement.setString(1, recipe.getName());
            statement.setInt(2, recipe.getCookingTime());
            statement.setString(3, recipe.getIngredients());
            statement.setString(4, recipe.getInstructions());

            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Recipe> getAllRecipes() {
        List<Recipe> recipes = new ArrayList<>();

        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT id, name, cooking_time, ingredients, instructions FROM recipes"
             );
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                Recipe recipe = new Recipe(
                        id,
                        resultSet.getString("name"),
                        resultSet.getInt("cooking_time"),
                        resultSet.getString("ingredients"),
                        resultSet.getString("instructions")
                );
                recipes.add(recipe);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return recipes;
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