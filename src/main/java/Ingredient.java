public class Ingredient {

    private int id;
    private final String name;
    private final int quantity;
    private final String measurement;

    public Ingredient(String name, int quantity, String measurement) {
        this.name = name;
        this.quantity = quantity;
        this.measurement = measurement;
    }

    @Override
    public String toString() {
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
