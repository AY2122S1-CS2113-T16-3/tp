package expiryeliminator.commands;

import expiryeliminator.data.Ingredient;
import expiryeliminator.data.IngredientList;
import expiryeliminator.data.RecipeList;
import expiryeliminator.data.exception.NotFoundException;
import expiryeliminator.storage.saveList;

/**
 * Increment ingredient by a specified quantity.
 */
public class IncrementCommand extends Command {
    /**
     * Unique word associated with the command.
     */
    public static final String COMMAND_WORD = "increment";
    public static final String MESSAGE_USAGE =
            COMMAND_WORD + ": Increases the quantity of a specified ingredient by a specified amount.\n"
                    + "Parameters: i/INGREDIENT q/QUANTITY\n"
                    + "Example: " + COMMAND_WORD + " i/Red Apple q/3";

    private static final String MESSAGE_INGREDIENT_NOT_FOUND = "Sorry. No matching ingredients found!";
    private static final String MESSAGE_INGREDIENT_INCREMENTED = "I've incremented this ingredient by %1$s:\n" + "%2$s";

    private final String ingredientName;
    private final int quantity;

    /**
     * Initialises command and stores relevant parameters.
     *
     * @param ingredientName Name of ingredient to be incremented.
     * @param quantity       Quantity to increment by.
     */
    public IncrementCommand(String ingredientName, int quantity) {
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
        ingredient.setQuantity(ingredient.getQuantity() + quantity);
        saveList.saveIngredientListToFile(ingredients);
        return String.format(MESSAGE_INGREDIENT_INCREMENTED, quantity, ingredient);
    }
}
