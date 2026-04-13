import java.util.List;

public class Recipe {

    private int id;
    private String name;
    private int cookingTime;
    private List<Ingredient> ingredients;
    private String instructions;

    public Recipe(String name, int cookingTime, List<Ingredient> ingredients, String instructions) {
        this.name = name;
        this.cookingTime = cookingTime;
        this.ingredients = ingredients;
        this.instructions = instructions;
    }

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




}
