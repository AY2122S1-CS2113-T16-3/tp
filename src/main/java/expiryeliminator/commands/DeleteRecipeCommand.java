package expiryeliminator.commands;

import expiryeliminator.storage.SaveLists;
import expiryeliminator.data.IngredientRepository;
import expiryeliminator.data.Recipe;
import expiryeliminator.data.RecipeList;
import expiryeliminator.data.exception.NotFoundException;

/**
 * Deletes a recipe from the recipe list.
 */
public class DeleteRecipeCommand extends Command {

    public static final String COMMAND_WORD = "delete recipe";

    private final String recipeName;
    public static final String MESSAGE_RECIPE_NOT_FOUND = "You don't have this recipe in your list :(";
    public static final String MESSAGE_RECIPE_DELETED = "I've deleted this recipe for you:\n" + "%1$s"
            + "Now you have %2$s recipe(s)";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes the recipe with the given name in the list,"
            + "if it exists.\n"
            + "Parameters: r/RECIPE NAME\n"
            + "Example: " + COMMAND_WORD
            + " r/Chicken Soup";

    public DeleteRecipeCommand(String recipeName) {
        this.recipeName = recipeName;
    }

    public String execute(IngredientRepository ingredients, RecipeList recipes) {
        try {
            Recipe deletedRecipe = recipes.remove(recipeName);
            SaveLists.saveRecipeListToFile(recipes);
            return String.format(MESSAGE_RECIPE_DELETED, deletedRecipe, recipes.size());
        } catch (NotFoundException e) {
            return MESSAGE_RECIPE_NOT_FOUND;
        }
    }
}
