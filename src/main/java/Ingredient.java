public class Ingredient {

    //TODO When you get to it in the advanced feature set, redesign Ingredient to be a Record class type

    //TODO Keep and use, or remove unused global var?
    //GOOD Good use of the final here to make Ingredients immutable, may cause a little pain when doing edit functionality but for the current build good practice.
    private int id;
    private final String name;
    private final int quantity;
    private final String measurement;

    public Ingredient(String name, int quantity, String measurement) {
        this.name = name;
        this.quantity = quantity;
        this.measurement = measurement;
    }

    //GOOD I like you using toString overrides in the data layer to provide formatted data, its a good practice
    @Override
    public String toString() {
        //TODO it doesnt actually matter which you choose, but stick to either this.name like getName() or stick to just name like in this method, same for the other variables
        return name + " - " + quantity + " " + measurement;
    }

    public String getName() {
        return this.name;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public String getMeasurement() {
        return measurement;
    }
}
