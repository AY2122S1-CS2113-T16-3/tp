package expiryeliminator.commands;

import java.time.LocalDate;

import expiryeliminator.data.IngredientRepository;
import expiryeliminator.data.IngredientStorage;
import expiryeliminator.data.RecipeList;
import expiryeliminator.data.exception.DuplicateDataException;
import expiryeliminator.storage.SaveLists;

/**
 * Adds an ingredient, together with its associated quantity and expiry date.
 */
public class AddIngredientCommand extends Command {
    /** Unique word associated with the command. */
    public static final String COMMAND_WORD = "add";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an ingredient to the ingredient repository. "
            + "Expiry date must be yyyy-mm-dd.\n"
            + "Quantity must be an integer. Ingredient names are case insensitive and must be unique.\n"
            + "Parameters: i/INGREDIENT q/QUANTITY e/EXPIRY_DATE\n"
            + "Example: " + COMMAND_WORD + " i/Red Apple q/5 e/2021-12-25";

    private static final String MESSAGE_INGREDIENT_ADDED = "I've added this ingredient:\n" + "\n%1$s\n"
            + "Now you have %2$s ingredient(s)";
    private static final String MESSAGE_INGREDIENT_ALREADY_EXISTS = "Unable to add ingredient: %1$s\n"
            + "You already have it in your list";

    private final String name;
    private final String unit;
    private int quantity;
    private LocalDate expiryDate;


    /**
     * Instantiates ingredient with the specified name and unit.
     *
     * @param name Name of ingredient to be added.
     * @param unit Unit for ingredient to be added.
     */
    public AddIngredientCommand(String name, String unit) {
        assert name != null && !name.isBlank() : "Ingredient name cannot be null and cannot be blank";
        this.name = name;
        this.unit = unit;
    }

    /**
     * Instantiates ingredient with the specified name, unit, quantity, and expiry date.
     *
     * @param name Name of ingredient to be added.
     * @param unit Unit for ingredient to be added.
     * @param quantity Quantity of ingredient to be added.
     * @param expiryDate Expiry date of ingredient to be added.
     */
    public AddIngredientCommand(String name, String unit, int quantity, LocalDate expiryDate) {
        assert name != null && !name.isBlank() : "Ingredient name cannot be null and cannot be blank";
        assert quantity >= 0 : "Quantity cannot be negative";
        assert expiryDate != null : "Expiry date cannot be null";
        this.name = name;
        this.unit = unit;
        this.quantity = quantity;
        this.expiryDate = expiryDate;
    }

    @Override
    public String execute(IngredientRepository ingredients, RecipeList recipes) {
        assert ingredients != null : "Ingredient repository cannot be null";
        final IngredientStorage ingredientStorage;
        try {
            if (expiryDate == null) {
                ingredientStorage = ingredients.add(name, unit);
            } else {
                ingredientStorage = ingredients.add(name, unit, quantity, expiryDate);
            }
            SaveLists.saveIngredientRepoToFile(ingredients);
        } catch (DuplicateDataException e) {
            return String.format(MESSAGE_INGREDIENT_ALREADY_EXISTS, name);
        }
        return String.format(MESSAGE_INGREDIENT_ADDED, ingredientStorage, ingredients.size());
    }
}
