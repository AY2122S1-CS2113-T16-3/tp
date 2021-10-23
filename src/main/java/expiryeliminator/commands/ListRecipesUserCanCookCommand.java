package expiryeliminator.commands;

import expiryeliminator.data.IngredientRepository;
import expiryeliminator.data.RecipeList;
import expiryeliminator.data.IngredientQuantity;
import expiryeliminator.data.IngredientStorage;
import expiryeliminator.data.Recipe;

import java.util.TreeMap;

/**
 * Lists recipes that can be cooked with the current amount of ingredients.
 */
public class ListRecipesUserCanCookCommand extends Command {
    /** Unique word associated with the command. */
    public static final String COMMAND_WORD = "list recipes i can cook";

    public static final String MESSAGE_NOT_ENOUGH_INGREDIENTS = "You don't have enough ingredients to cook "
            + "any recipes :(";
    public static final String MESSAGE_RECIPES_WITH_ENOUGH_INGREDIENTS_TO_COOK = "Here are the recipes you can cook "
            + "with the ingredients you have:\n"
            + "\n%1$s";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists recipes that you can cook with the ingredients "
            + "you currently have.\n"
            + "Example: " + COMMAND_WORD;

    private boolean allIngredientsAreSufficient(TreeMap<String, IngredientQuantity> ingredientsFromRecipe,
                                                IngredientRepository ingredients) {
        for (IngredientQuantity i : ingredientsFromRecipe.values()) {
            IngredientStorage ingredient = ingredients.findWithNullReturn(i.getName());
            assert ingredient != null : "Ingredient should be in the repository after the recipe is added";
            if (ingredient.getQuantity() < i.getQuantity()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String execute(IngredientRepository ingredients, RecipeList recipes) {
        String recipeList = "";
        for (Recipe r : recipes.getRecipes().values()) {
            if (allIngredientsAreSufficient(r.getIngredientQuantities(),ingredients)) {
                recipeList += r + "\n";
            }
        }

        if (recipes.size() == 0) {
            return ListRecipeCommand.MESSAGE_EMPTY_RECIPE_LIST;
        } else if (recipeList.isEmpty()) {
            return MESSAGE_NOT_ENOUGH_INGREDIENTS;
        }
        return String.format(MESSAGE_RECIPES_WITH_ENOUGH_INGREDIENTS_TO_COOK,recipeList);
    }
}
