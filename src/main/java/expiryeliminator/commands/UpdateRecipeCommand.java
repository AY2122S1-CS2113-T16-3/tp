package expiryeliminator.commands;

import expiryeliminator.data.IngredientList;
import expiryeliminator.data.Ingredient;
import expiryeliminator.data.Recipe;
import expiryeliminator.data.RecipeList;
import expiryeliminator.data.exception.NotFoundException;
import expiryeliminator.storage.saveList;

import java.lang.Object;

public class UpdateRecipeCommand extends Command {
    /**
     * Unique word associated with the command.
     */
    public static final String COMMAND_WORD = "update recipe";

    public static final String MESSAGE_RECIPE_UPDATED = "I've updated this recipe:\n" + "%1$s";
    public static final String RECIPE_UPDATE_FAIL = "Unable to update this recipe:\n" + "%1$s"
            + "No matching recipes or ingredients found, please check your input again\n";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Updates the quantity of ingredients"
            + " in the recipe list.\n"
            + "Parameters: r/RECIPE NAME i/INGREDIENT q/QUANTITY i/INGREDIENT q/QUANTITY ...\n"
            + "Example: " + COMMAND_WORD
            + " r/Chicken Soup i/Chicken q/1 i/Salt q/20 i/Ginger q/2";

    private final Recipe recipe;

    public UpdateRecipeCommand(String name, IngredientList ingredients) {
        recipe = new Recipe(name, ingredients);
    }


    @Override
    public String execute(IngredientList ingredients, RecipeList recipes) throws NotFoundException {
        try {
            recipes = recipes.updateRecipe(recipe.getIngredients(), recipes, recipe);
            if (recipes != null) {
                saveList.saveRecipeListToFile(recipes);
                return String.format(MESSAGE_RECIPE_UPDATED, recipe);
            } else return String.format(RECIPE_UPDATE_FAIL, recipe);
        } catch (NotFoundException e) {
            return String.format(RECIPE_UPDATE_FAIL, recipe);
        }

    }
}
