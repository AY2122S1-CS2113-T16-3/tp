package expiryeliminator.commands;

import java.util.ArrayList;

import expiryeliminator.storage.SaveLists;
import expiryeliminator.data.IngredientRepository;
import expiryeliminator.data.Recipe;
import expiryeliminator.data.RecipeList;
import expiryeliminator.data.exception.DuplicateDataException;
import expiryeliminator.data.exception.IllegalValueException;

/**
 * Adds a recipe, together with the ingredients needed.
 */
public class AddRecipeCommand extends Command {
    /** Unique word associated with the command. */
    public static final String COMMAND_WORD = "add recipe";

    public static final String MESSAGE_RECIPE_ADDED = "I've added this recipe:\n" + "\n%1$s\n"
            + "Now you have %2$s recipe(s)";
    public static final String MESSAGE_RECIPE_ADDED_WITH_REMINDER = "I've added this recipe:\n" + "\n%1$s\n"
            + "Now you have %2$s recipe(s)\n" + "\nI've also added these ingredients:\n" + "\n%3$s\n"
            + "Please update the unit if required.";
    public static final String MESSAGE_RECIPE_ALREADY_EXISTS = "Unable to add recipe: %1$s\n"
            + "You already have it in your list";
    public static final String MESSAGE_DUPLICATE_INGREDIENT = "Unable to add recipe: %1$s\n"
            + "There is a duplicate ingredient: %2$s.";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a recipe with its constituent ingredients"
            + " to the recipe list.\n"
            + "You can add any number of ingredients with its required quantities.\n"
            + "Parameters: r/RECIPE NAME i/INGREDIENT q/QUANTITY i/INGREDIENT q/QUANTITY ...\n"
            + "Example: " + COMMAND_WORD
            + " r/Chicken Soup i/Chicken q/1 i/Salt q/20 i/Ginger q/2";
    public static final String MESSAGE_ILLEGAL_VALUE_ERROR = "Quantity of ingredients for recipe cannot be zero.";

    private final String name;
    private final ArrayList<String> ingredientNames;
    private final ArrayList<Integer> quantities;

    public AddRecipeCommand(String name, ArrayList<String> ingredientNames, ArrayList<Integer> quantities) {
        assert name != null : "Recipe name cannot be null";
        assert ingredientNames != null : "Ingredient list cannot be null";
        assert quantities != null : "Quantity list cannot be null";
        assert ingredientNames.size() != 0 : "Ingredient list cannot be empty";
        assert ingredientNames.size() == quantities.size() : "Quantity list cannot be empty";
        this.name = name;
        this.ingredientNames = ingredientNames;
        this.quantities = quantities;
    }

    @Override
    public String execute(IngredientRepository ingredients, RecipeList recipes) {
        assert ingredients != null : "Ingredient repository cannot be null";
        assert recipes != null : "Recipe list cannot be null";

        final Recipe recipe = new Recipe(name);
        String message = "";

        for (int i = 0; i < ingredientNames.size(); i++) {
            try {
                message += recipe.add(ingredientNames.get(i), quantities.get(i), ingredients);
            } catch (DuplicateDataException e) {
                return String.format(MESSAGE_DUPLICATE_INGREDIENT, name, ingredientNames.get(i));
            } catch (IllegalValueException e) {
                return MESSAGE_ILLEGAL_VALUE_ERROR;
            }
        }

        try {
            recipes.add(recipe);
            SaveLists.saveRecipeListToFile(recipes);
        } catch (DuplicateDataException e) {
            return String.format(MESSAGE_RECIPE_ALREADY_EXISTS, name);
        }
        if (message.equals("")) {
            return String.format(MESSAGE_RECIPE_ADDED, recipe, recipes.size());
        }
        return String.format(MESSAGE_RECIPE_ADDED_WITH_REMINDER, recipe, recipes.size(),message);
    }
}
