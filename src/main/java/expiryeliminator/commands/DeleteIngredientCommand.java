package expiryeliminator.commands;

import expiryeliminator.data.Ingredient;
import expiryeliminator.data.IngredientList;
import expiryeliminator.data.RecipeList;
import expiryeliminator.data.exception.NotFoundException;
import expiryeliminator.storage.saveList;

/**
 * Deletes an ingredient.
 */
public class DeleteIngredientCommand extends Command {
    /**
     * Unique word associated with the command.
     */
    public static final String COMMAND_WORD = "delete";
    public static final String MESSAGE_USAGE =
            COMMAND_WORD + ": Deletes an ingredient from the ingredient repository.\n"
                    + "Parameters: i/INGREDIENT\n"
                    + "Example: " + COMMAND_WORD + " i/Red Apple";

    private static final String MESSAGE_INGREDIENT_NOT_FOUND = "Sorry. No matching ingredients found!";
    private static final String MESSAGE_INGREDIENT_DELETED = "I've deleted this ingredient for you:\n" + "%1$s\n"
            + "Now you have %2$s ingredient(s)";

    private final String ingredientName;

    /**
     * Initialises command and stores relevant parameters.
     *
     * @param ingredientName Name of ingredient to be deleted.
     */
    public DeleteIngredientCommand(String ingredientName) {
        assert ingredientName != null && !ingredientName.isBlank()
                : "Ingredient name cannot be null and cannot be blank";
        this.ingredientName = ingredientName;
    }

    @Override
    public String execute(IngredientList ingredients, RecipeList recipes) {
        assert ingredients != null : "Ingredient list cannot be null";
        final Ingredient ingredient;
        try {
            ingredient = ingredients.remove(ingredientName);
            saveList.saveIngredientListToFile(ingredients);
        } catch (NotFoundException e) {
            return MESSAGE_INGREDIENT_NOT_FOUND;
        }
        return String.format(MESSAGE_INGREDIENT_DELETED, ingredient, ingredients.size());
    }
}
