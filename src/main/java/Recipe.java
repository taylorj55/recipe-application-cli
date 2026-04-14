import java.util.List;

public class Recipe {

    private int id;
    //TODO Variable is only set once, so can be declared Final
    private String name;
    //TODO Variable is only set once, so can be declared Final
    private int cookingTime;
    private List<Ingredient> ingredients;
    //TODO Variable is only set once, so can be declared Final
    private String instructions;

    public Recipe(String name, int cookingTime, List<Ingredient> ingredients, String instructions) {
        this.name = name;
        this.cookingTime = cookingTime;
        this.ingredients = ingredients;
        this.instructions = instructions;
    }

    //TODO the logic flow for this constructor ends in a loop that tries to print out .ingredients (RecipeFlow.printAllRecipes()), that will throw a NullPointerException. Or it should, you quite aggressively use catch/throws Exception so that is probably swallowing it. General rule of thumb is to always instantiate collections like Sets, Lists etc. as empty lists if not needed so that if they are mistakenly iterated over, it is safe.
    public Recipe(int id, String name, int cookingTime, String instructions) {
        this.id = id;
        this.name = name;
        this.cookingTime = cookingTime;
        this.instructions = instructions;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCookingTime() {
        return cookingTime;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public String getInstructions() {
        return instructions;
    }

    //TODO Writing a toString() override method here would make the RecipeFlow.printAllRecipes() a lot simpler.


}
