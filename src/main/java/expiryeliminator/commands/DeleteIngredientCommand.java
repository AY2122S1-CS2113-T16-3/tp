package expiryeliminator.commands;

import expiryeliminator.Storage.SaveLists;
import expiryeliminator.data.IngredientRepository;
import expiryeliminator.data.IngredientStorage;
import expiryeliminator.data.RecipeList;
import expiryeliminator.data.exception.NotFoundException;

/**
 * Deletes an ingredient.
 */
public class DeleteIngredientCommand extends Command {
    /** Unique word associated with the command. */
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
    public String execute(IngredientRepository ingredients, RecipeList recipes) {
        // TODO: Don't allow deleting ingredient if there is a recipe that uses that ingredient

        assert ingredients != null : "Ingredient repository cannot be null";
        final IngredientStorage ingredient;
        try {
            ingredient = ingredients.remove(ingredientName);
            SaveLists.saveIngredientRepoToFile(ingredients);
        } catch (NotFoundException e) {
            return MESSAGE_INGREDIENT_NOT_FOUND;
        }
        return String.format(MESSAGE_INGREDIENT_DELETED, ingredient, ingredients.size());
    }
}
