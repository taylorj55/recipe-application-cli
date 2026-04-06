public class Ingredient {

    private int id;
    private String name;
    private int quantity;
    private String measurement;

    public Ingredient(String name, int quantity, String measurement) {
        this.name = name;
        this.quantity = quantity;
        this.measurement = measurement;
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
