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
                     "SELECT name, cooking_time, ingredients, instructions FROM recipes"
             );
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Recipe recipe = new Recipe(
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
}