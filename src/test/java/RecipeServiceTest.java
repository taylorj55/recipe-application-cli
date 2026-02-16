import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RecipeServiceTest {

    RecipeRepository repository;
    RecipeService service;
    String validName = "Pasta";
    int validCookingTime = 15;
    String validIngredients = "Pasta, sauce, onions";
    String validInstructions = "Cook pasta and mushrooms separately. Once cooked mix together and add sauce and onions";

    @BeforeEach
    void initTest(){
         repository = new RecipeRepository();
         service = new RecipeService(repository);
    }

    @Test
    void testCreateRecipe_withValidInput() {
        Recipe recipe = service.createRecipe(validName, validCookingTime, validIngredients,  validInstructions);

        assertNotNull(recipe);
        assertEquals(validName, recipe.getName());
        assertEquals(validCookingTime, recipe.getCookingTime());
        assertEquals(validIngredients, recipe.getIngredients());
        assertEquals(validInstructions, recipe.getInstructions());
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