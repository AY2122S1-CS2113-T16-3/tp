package expiryeliminator.commands;

import expiryeliminator.data.Ingredient;
import expiryeliminator.data.IngredientList;
import expiryeliminator.data.RecipeList;
import expiryeliminator.data.exception.NotFoundException;
import expiryeliminator.storage.saveList;

/**
 * Decrement ingredient by a specified quantity.
 */
public class DecrementCommand extends Command {
    /**
     * Unique word associated with the command.
     */
    public static final String COMMAND_WORD = "decrement";
    public static final String MESSAGE_USAGE =
            COMMAND_WORD + ": Decreases the quantity of a specified ingredient by a specified amount.\n"
                    + "Parameters: i/INGREDIENT q/QUANTITY\n"
                    + "Example: " + COMMAND_WORD + " i/Red Apple q/5";

    private static final String MESSAGE_INGREDIENT_NOT_FOUND = "Sorry. No matching ingredients found!";
    private static final String MESSAGE_QUANTITY_NEGATIVE = "Sorry, you currently only have %1$s of this ingredient.\n"
            + "You cannot decrease it by %2$s.\n" + "%3$s";
    private static final String MESSAGE_INGREDIENT_DECREMENTED = "I've decremented this ingredient by %1$s:\n" + "%2$s";

    private final String ingredientName;
    private final int quantity;

    /**
     * Initialises command and stores relevant parameters.
     *
     * @param ingredientName Name of ingredient to be incremented.
     * @param quantity       Quantity to increment by.
     */
    public DecrementCommand(String ingredientName, int quantity) {
        assert ingredientName != null && !ingredientName.isBlank()
                : "Ingredient name cannot be null and cannot be blank";
        assert quantity >= 0 : "Quantity cannot be negative";
        this.ingredientName = ingredientName;
        this.quantity = quantity;
    }

    @Override
    public String execute(IngredientList ingredients, RecipeList recipes) {
        assert ingredients != null : "Ingredient list cannot be null";
        final Ingredient ingredient;
        try {
            ingredient = ingredients.find(ingredientName);
        } catch (NotFoundException e) {
            return MESSAGE_INGREDIENT_NOT_FOUND;
        }
        final int newQuantity = ingredient.getQuantity() - quantity;
        if (newQuantity < 0) {
            return String.format(MESSAGE_QUANTITY_NEGATIVE, ingredient.getQuantity(), quantity, ingredient);
        }
        ingredient.setQuantity(newQuantity);
        saveList.saveIngredientListToFile(ingredients);
        return String.format(MESSAGE_INGREDIENT_DECREMENTED, quantity, ingredient);
    }
}
