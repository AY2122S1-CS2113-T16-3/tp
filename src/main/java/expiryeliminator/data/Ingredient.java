package expiryeliminator.data;

/**
 * Represents an ingredient.
 */
public class Ingredient {
    private final String name;
    private String unit;

    /**
     * Initialises an ingredient without specifying a unit.
     *
     * @param name The name of the ingredient.
     */
    Ingredient(String name) {
        assert name != null && !name.isBlank();
        this.name = name;
    }

    /**
     * Initialises an ingredient with a unit.
     *
     * @param name The name of the ingredient.
     * @param unit The unit for the ingredient.
     */
    public Ingredient(String name, String unit) {
        this(name);
        this.unit = unit;
    }

    /**
     * Returns the name of the ingredient.
     *
     * @return The name of the ingredient.
     */
    String getName() {
        return name;
    }

    /**
     * Returns the unit for the ingredient.
     *
     * @return The unit for the ingredient.
     */
    public String getUnit() {
        return unit;
    }

    /**
     * Sets the unit for the ingredient.
     *
     * @param unit The unit for the ingredient.
     */
    void setUnit(String unit) {
        this.unit = unit;
    }

    @Override
    public String toString() {
        return name;
    }
}
