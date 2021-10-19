package expiryeliminator.data;

/**
 * Represents a recipe.
 */
public class Recipe {
    private final String name;
    private IngredientList ingredients;

    /**
     * Initialises a recipe.
     *
     * @param name        The name of the recipe
     * @param ingredients The ingredients for the recipe
     */
    public Recipe(String name, IngredientList ingredients) {
        this.name = name;
        this.ingredients = ingredients;
    }

    /**
     * Returns the name of the recipe.
     *
     * @return Name of the recipe.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the ingredients for the recipe.
     *
     * @return Ingredients for the recipe.
     */
    public IngredientList getIngredients() {
        return ingredients;
    }

    @Override
    public String toString() {
        StringBuilder ingredientsWithQuantities = new StringBuilder(name + "\n");
        for (Ingredient ingredient : ingredients.getIngredients()) {
            if (ingredient.getQuantity() != 0) {
                ingredientsWithQuantities.append("- ").append(ingredient.getName())
                        .append(" (qty: ").append(ingredient.getQuantity()).append(")\n");
            }
        }
        return ingredientsWithQuantities.toString();
    }

    //TODO(vincentlauhl): Allow users to modify recipe.
}
