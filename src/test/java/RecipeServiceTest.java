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