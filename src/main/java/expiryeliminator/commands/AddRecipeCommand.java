package expiryeliminator.commands;

import expiryeliminator.data.IngredientList;
import expiryeliminator.data.Recipe;
import expiryeliminator.data.RecipeList;
import expiryeliminator.data.exception.DuplicateDataException;
import expiryeliminator.storage.SaveList;

/**
 * Adds a recipe, together with the ingredients needed.
 */
public class AddRecipeCommand extends Command {
    /**
     * Unique word associated with the command.
     */
    public static final String COMMAND_WORD = "add recipe";

    public static final String MESSAGE_RECIPE_ADDED = "I've added this recipe:\n" + "%1$s"
            + "Now you have %2$s recipe(s)";
    public static final String MESSAGE_RECIPE_ALREADY_EXISTS = "Unable to add recipe: %1$s\n"
            + "You already have it in your list";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a recipe with its constituent ingredients"
            + " to the recipe list.\n"
            + "You can add any number of ingredients with its required quantities.\n"
            + "Parameters: r/RECIPE NAME i/INGREDIENT q/QUANTITY i/INGREDIENT q/QUANTITY ...\n"
            + "Example: " + COMMAND_WORD
            + " r/Chicken Soup i/Chicken q/1 i/Salt q/20 i/Ginger q/2";

    private final Recipe recipe;

    public AddRecipeCommand(String name, IngredientList ingredients) {
        recipe = new Recipe(name, ingredients);
    }

    @Override
    public String execute(IngredientList ingredients, RecipeList recipes) {
        try {
            recipes.add(recipe);
            SaveList.saveRecipeListToFile(recipes);
        } catch (DuplicateDataException e) {
            return String.format(MESSAGE_RECIPE_ALREADY_EXISTS, recipe.getName());
        }
        return String.format(MESSAGE_RECIPE_ADDED, recipe, recipes.size());
    }
}
