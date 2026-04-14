import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RecipeServiceTest {

    RecipeRepository repository;
    RecipeService service;
    String validName = "Pasta";
    int validCookingTime = 15;
    List<Ingredient> validIngredients = List.of(
            new Ingredient("Pasta", 200, "grams"),
            new Ingredient("Sauce", 300, "ml")
    );
    String validInstructions = "Cook pasta and mushrooms separately. Once cooked mix together and add sauce and onions";

    @BeforeEach
    void initTest(){
        //TODO just a warning that this test is creating a real RecipeRepository, connecting to the real database, you would never do that in an actual repo because it would slow down the test running and potentially create DB bloat. A stretch goal when you next have a few hours spare would be to look up a basic Mockito tutorial (I can find you one if you let me know ahead of time) and implement Mocking to isolate the tests from the actual database
         repository = new RecipeRepository();
         service = new RecipeService(repository);
    }

    @Test
    void testCreateRecipe_withValidInput() {
        Recipe recipe = service.createRecipe(validName, validCookingTime, validIngredients, validInstructions);

        assertNotNull(recipe);
        assertEquals(validName, recipe.getName());
        assertEquals(validCookingTime, recipe.getCookingTime());
        assertEquals(validInstructions, recipe.getInstructions());

        assertEquals(2, recipe.getIngredients().size());
        assertEquals("Pasta", recipe.getIngredients().getFirst().getName());
        assertEquals(200, recipe.getIngredients().getFirst().getQuantity());
        assertEquals("grams", recipe.getIngredients().getFirst().getMeasurement());

        assertEquals("Sauce", recipe.getIngredients().getLast().getName());
        assertEquals(300, recipe.getIngredients().getLast().getQuantity());
        assertEquals("ml", recipe.getIngredients().getLast().getMeasurement());
    }

    @Test
    void testCreateRecipe_withNoName() {
        assertThrows(IllegalArgumentException.class, () -> service.createRecipe("", validCookingTime, validIngredients, validInstructions));
    }

    @Test
    void testCreateRecipe_withInvalidCookingTime() {
        assertThrows(IllegalArgumentException.class, () -> service.createRecipe(validName, 0, validIngredients, validInstructions));
    }

}