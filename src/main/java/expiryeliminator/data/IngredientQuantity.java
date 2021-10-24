package expiryeliminator.data;

import expiryeliminator.data.exception.IllegalValueException;

/**
 * Represents an ingredient and it's associated quantity.
 */
public class IngredientQuantity {
    private final Ingredient ingredient;
    private int quantity;

    /**
     * Initialises ingredient and quantity.
     *
     * @param ingredient Ingredient object.
     * @param quantity Quantity of ingredient.
     * @throws IllegalValueException If quantity is less than or equal to 0.
     */
    public IngredientQuantity(Ingredient ingredient, int quantity) throws IllegalValueException {
        assert ingredient != null : "Ingredient cannot be null";
        this.ingredient = ingredient;
        setQuantity(quantity);
    }

    /**
     * Returns name of ingredient.
     *
     * @return Name of ingredient.
     */
    public String getName() {
        return ingredient.getName();
    }

    /**
     * Returns unit for ingredient.
     *
     * @return Unit for ingredient.
     */
    public String getUnit() {
        return ingredient.getUnit();
    }

    /**
     * Returns quantity of ingredient.
     *
     * @return Quantity of ingredient.
     */
    public int getQuantity() {
        return quantity;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    /**
     * Sets quantity associated with the ingredient.
     *
     * @param quantity New value for quantity.
     * @throws IllegalValueException If quantity is less than or equal to 0.
     */
    void setQuantity(int quantity) throws IllegalValueException {
        if (quantity <= 0) {
            throw new IllegalValueException();
        }
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return ingredient
                + " (qty: " + quantity
                + (ingredient.getUnit() == null ? "" : " " + ingredient.getUnit())
                + ")";
    }
}
