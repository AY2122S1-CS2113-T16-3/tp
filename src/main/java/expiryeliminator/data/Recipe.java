package expiryeliminator.data;

import java.util.TreeMap;

import expiryeliminator.data.exception.DuplicateDataException;
import expiryeliminator.data.exception.IllegalValueException;

/**
 * Represents a recipe.
 */
public class Recipe {
    private String name;
    private final TreeMap<String, IngredientQuantity> ingredientQuantities = new TreeMap<>();

    /**
     * Initialises a recipe.
     *
     * @param name The name of the recipe
     */
    public Recipe(String name) {
        this.name = name;
    }

    /**
     * Returns the name of the recipe.
     *
     * @return Name of the recipe.
     */
    public String getName() {
        return name;
    }

    public void setName(String inputName) {
        name = inputName;
    }


    /**
     * Returns the ingredients and the respective quantities of the recipe.
     *
     * @return Ingredients and the respective quantities of the recipe.
     */
    public TreeMap<String, IngredientQuantity> getIngredientQuantities() {
        return ingredientQuantities;
    }

    public void setIngredientQuantities(String ingredientName, IngredientQuantity ingredientQuantity) {
        ingredientQuantities.put(ingredientName, ingredientQuantity);
    }

    /**
     * Adds an ingredient and its associated quantity to the recipe.
     *
     * @param ingredientName Name of ingredient to be added.
     * @param quantity Quantity of ingredient to be added.
     * @param ingredientRepository Ingredient repository.
     * @throws DuplicateDataException If ingredient already exists in the recipe.
     * @throws IllegalValueException If quantity is less than or equal to 0.
     */
    public String add(String ingredientName, int quantity, IngredientRepository ingredientRepository)
            throws DuplicateDataException, IllegalValueException {
        String ingredientNameIfNotInList = "";
        final IngredientStorage ingredientStorage = ingredientRepository.findWithNullReturn(ingredientName);
        Ingredient ingredient;
        if (ingredientStorage == null) {
            ingredientRepository.add(ingredientName);
            ingredientNameIfNotInList = ingredientName + "\n";
            ingredient = new Ingredient(ingredientName);
        } else {
            ingredient = ingredientStorage.getIngredient();
        }
        final IngredientQuantity ingredientQuantity = new IngredientQuantity(ingredient, quantity);
        if (ingredientQuantities.containsKey(ingredientQuantity.getName())) {
            throw new DuplicateDataException();
        }
        ingredientQuantities.put(ingredientQuantity.getName(), ingredientQuantity);
        return ingredientNameIfNotInList;
    }

    @Override
    public String toString() {
        StringBuilder ingredientsWithQuantities = new StringBuilder(name + "\n");
        for (IngredientQuantity ingredientQuantity : ingredientQuantities.values()) {
            ingredientsWithQuantities.append("- ").append(ingredientQuantity).append("\n");
        }
        return ingredientsWithQuantities.toString();
    }

    //TODO(vincentlauhl): Allow users to modify recipe.
}
