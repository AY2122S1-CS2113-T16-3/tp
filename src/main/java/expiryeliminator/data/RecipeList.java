package expiryeliminator.data;

import expiryeliminator.data.exception.DuplicateDataException;
import expiryeliminator.data.exception.NotFoundException;

import java.util.HashMap;

/**
 * Represents the recipe list and contains methods to add and remove recipes.
 */
public class RecipeList {
    private final HashMap<String, Recipe> recipes;

    /**
     * Constructs Recipe List with no recipes.
     */
    public RecipeList() {
        recipes = new HashMap<>();
    }

    /**
     * Adds a recipe to the recipe list.
     *
     * @param recipe Recipe to be added
     * @throws DuplicateDataException If the recipe already exists.
     */
    public void add(Recipe recipe) throws DuplicateDataException {
        if (recipes.containsKey(recipe.getName())) {
            throw new DuplicateDataException();
        } else {
            recipes.put(recipe.getName(), recipe);
        }
    }

    /**
     * Removes a recipe from the recipe list.
     *
     * @param name name of the recipe to be removed
     * @return The recipe removed.
     * @throws NotFoundException If there are no such recipes in the list
     */
    public Recipe remove(String name) throws NotFoundException {
        Recipe recipe = recipes.get(name);
        if (recipe == null) {
            throw new NotFoundException();
        }
        recipes.remove(name);
        return recipe;
    }

    /**
     * Returns the number of recipes in the list.
     *
     * @return Number of recipes in the list.
     */
    public int size() {
        return recipes.size();
    }

    /**
     * Returns a string representation of the whole recipe list.
     *
     * @return returns a string representing all the recipes.
     */
    public String getWholeRecipeList() {
        StringBuilder wholeRecipeList = new StringBuilder();
        for (Recipe recipe : recipes.values()) {
            wholeRecipeList.append(recipe.toString()).append("\n");
        }
        return wholeRecipeList.toString();
    }

    /**
     * Looks for the recipe that the user is looking for.
     *
     * @param recipeDescription The recipe name the user is searching for.
     * @return the recipe object that the user is searching for.
     */
    public Recipe findRecipe(String recipeDescription) throws NotFoundException {
        final Recipe recipe = recipes.get(recipeDescription);
        if (recipe == null) {
            throw new NotFoundException();
        }
        return recipe;
    }

    public RecipeList updateRecipe(IngredientList ingredients, RecipeList recipes, Recipe recipe)
            throws NotFoundException {
        Recipe matchedRecipe = recipes.findRecipe(recipe.getName());
        boolean hasMatchingIngredient = false;
        if (matchedRecipe != null) {
            for (Ingredient targetIngredient : ingredients.getIngredients()) {
                for (Ingredient originalIngredient : matchedRecipe.getIngredients().getIngredients()) {
                    if (targetIngredient.getName().equals(originalIngredient.getName())) {
                        hasMatchingIngredient = true;
                        originalIngredient.setQuantity(targetIngredient.getQuantity());
                    }
                }
                if (!hasMatchingIngredient) {
                    return null;
                }
            }
            recipes.recipes.put(recipe.getName(), matchedRecipe);
            return recipes;
        } else {
            return null;
        }
    }
}
