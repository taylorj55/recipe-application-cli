import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RecipeRepository {

    public void saveRecipe(Recipe recipe) {
        //TODO you have correctly noted that Exception is a bad catch statement, you have two options here, catch SQLExceptions or preferably write a custom Exception to better allow for logical responses
        //TODO There is a potential for partial data completion here, the principle is called Atomicity, if the recipe saves successfully but the ingredients dont, currently you do not do anything so you end up with partially saved data. HINT: look at AutoCommit() vs manually telling he connection when to commit and rolling back on a failed transaction
        try (Connection connection = DatabaseManager.getConnection()) {
            int recipeId = saveRecipeData(connection, recipe);

            saveIngredients(connection, recipeId, recipe.getIngredients());
        } catch (Exception e) { //specific exception catch
            e.printStackTrace();
        }
    }


    private int saveRecipeData(Connection connection, Recipe recipe) throws SQLException {
        String recipeSql = "INSERT INTO recipes (name, cooking_time, instructions) VALUES (?, ?, ?)";

        //TODO missing a try-wrapper
        //Good using RETURN_GENERATED_KEYS is the correct approach, good job
        PreparedStatement statement = connection.prepareStatement(
                recipeSql,
                PreparedStatement.RETURN_GENERATED_KEYS
        );

        statement.setString(1, recipe.getName());
        statement.setInt(2, recipe.getCookingTime());
        statement.setString(3, recipe.getInstructions());

        statement.executeUpdate();

        ResultSet keys = statement.getGeneratedKeys();

        if (keys.next()) {
            return keys.getInt(1);
        }
        throw new RuntimeException("Failed to save recipe, no ID returned");  //specific exception catch
    }

    private void saveIngredients(Connection connection, int recipeId, List<Ingredient> ingredients) throws SQLException {
        String ingredientSql = "INSERT INTO ingredients (recipe_id, name, quantity, measurement) VALUES (?, ?, ?, ?)";

        //TODO Missing a try-wrapper
        PreparedStatement ingredientStmt = connection.prepareStatement(ingredientSql);

        //TODO you save each ingredient separately, on a small scale thats fine, but if you scaled up enough it would cause problems with database connections being busy. HINT: look at add and execute batch methods
        for (Ingredient ingredient : ingredients) {
            ingredientStmt.setInt(1, recipeId);
            ingredientStmt.setString(2, ingredient.getName());
            ingredientStmt.setInt(3, ingredient.getQuantity());
            ingredientStmt.setString(4, ingredient.getMeasurement());

            ingredientStmt.executeUpdate();
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

                List<Ingredient> ingredients = getIngredientsByRecipeId(connection, id);
                recipe.setIngredients(ingredients);
                recipes.add(recipe);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return recipes;
    }

    private List<Ingredient> getIngredientsByRecipeId(Connection connection, int recipeId) {
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

    //TODO you have ingredients that are linked to a recipe ID, and when you delete the recipe the ingredients are not deleted, but left as orphaned rows. HINT: Look at the CASCADE keyword and if you have set up a foreign key constraint
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