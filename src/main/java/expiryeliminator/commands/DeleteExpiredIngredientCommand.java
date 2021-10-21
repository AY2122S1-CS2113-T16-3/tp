package expiryeliminator.commands;

import expiryeliminator.data.IngredientRepository;
import expiryeliminator.data.RecipeList;

/**
 * Deletes all expired ingredients one shot
 */
public class DeleteExpiredIngredientCommand extends Command {

    /** Unique word associated with the command. */
    public static final String COMMAND_WORD = "delete expired";
    public static final String MESSAGE_USAGE =
            COMMAND_WORD + ": Deletes all expired ingredient from the ingredient repository.\n"
                    + "Example: " + COMMAND_WORD;

    private static final String MESSAGE_DELETED_ALL_EXPIRED = "All expired ingredients have been deleted!";

    @Override
    public String execute(IngredientRepository ingredients, RecipeList recipes) {
        ingredients.deleteExpiredIngredients();
        return MESSAGE_DELETED_ALL_EXPIRED;
    }
}
