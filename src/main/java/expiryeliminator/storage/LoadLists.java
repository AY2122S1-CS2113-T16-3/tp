package expiryeliminator.storage;

import expiryeliminator.data.Ingredient;
import expiryeliminator.data.IngredientQuantity;
import expiryeliminator.data.IngredientRepository;
import expiryeliminator.data.Recipe;
import expiryeliminator.data.RecipeList;
import expiryeliminator.data.exception.DuplicateDataException;
import expiryeliminator.data.exception.IllegalValueException;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.Scanner;

public class LoadLists {
    public static void loadRecipeList(RecipeList recipes) {
        String pathName = "./data/";
        String fileName = "recipeList.txt";
        File file = new File(pathName + fileName);
        try {
            Scanner sc = new Scanner(file);
            Recipe currentRecipe = new Recipe(null);
            storeCurrentRecipeFromList(recipes, sc, currentRecipe);
        } catch (DuplicateDataException | IllegalValueException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            SaveLists.createFileOrFolder(pathName, fileName);
        }
    }

    private static void storeCurrentRecipeFromList(RecipeList recipes, Scanner sc, Recipe currentRecipe)
            throws IllegalValueException, DuplicateDataException {
        while (sc.hasNext()) {
            String line = sc.nextLine();
            if (!line.isBlank()) {
                if (!line.contains("-")) {
                    currentRecipe.setName(line);
                } else {
                    storeCurrentIngredientOfRecipe(currentRecipe, line);
                    if (!sc.hasNext()) {
                        recipes.add(currentRecipe);
                    }
                }
            } else {
                if (currentRecipe.getName() != null) {
                    recipes.add(currentRecipe);
                    currentRecipe = new Recipe(null);
                }
            }
        }
    }

    private static void storeCurrentIngredientOfRecipe(Recipe currentRecipe, String line)
            throws IllegalValueException {
        int ingredientNameSeparator = line.indexOf("(") - 1;
        String ingredientName = line.substring(2, ingredientNameSeparator);
        String theDigits = line.replaceAll("[^0-9]", "");
        int lengthOfQuantity = theDigits.length();
        int quantityStartIndex = line.indexOf(theDigits);
        int unitStartIndex = quantityStartIndex + lengthOfQuantity;
        int unitEndIndex = line.indexOf(")");
        int quantity = Integer.parseInt(theDigits);
        String unit = line.substring(unitStartIndex, unitEndIndex);
        if (unit.isBlank()) {
            unit = null;
        }
        Ingredient ingredient = new Ingredient(ingredientName, unit);
        IngredientQuantity ingredientQuantity = new IngredientQuantity(ingredient, quantity);
        currentRecipe.setIngredientQuantities(ingredientName, ingredientQuantity);
    }

    public static void loadIngredientRepo(IngredientRepository ingredients) {
        String pathName = "./data/";
        String fileName = "ingredientRepo.txt";
        File file = new File(pathName + fileName);
        try {
            Scanner sc = new Scanner(file);
            loadCurrentIngredientFromRepo(ingredients, sc);
        } catch (FileNotFoundException e) {
            SaveLists.createFileOrFolder(pathName, fileName);
        } catch (DuplicateDataException e) {
            e.printStackTrace();
        }
    }

    private static int getQuantityWithBatch(String line, int expiryDateStart) {
        int quantityWithBatch;
        String quantityWithUnit = line.substring(2, expiryDateStart - 2);
        String theDigits = quantityWithUnit.replaceAll("[^0-9]", "");
        quantityWithBatch = Integer.parseInt(theDigits);
        return quantityWithBatch;
    }

    private static String getUnit(String line) {
        String unit;
        String theDigits = line.replaceAll("[^0-9]", "");
        int lengthOfQuantity = theDigits.length();
        int quantityStartIndex = line.indexOf(theDigits);
        int unitStartIndex = quantityStartIndex + lengthOfQuantity;
        int unitEndIndex = line.indexOf(")");
        unit = line.substring(unitStartIndex, unitEndIndex);
        if (unit.isBlank()) {
            unit = null;
        } else {
            unit = unit.substring(1);
        }
        return unit;
    }

    private static void loadCurrentIngredientFromRepo(IngredientRepository ingredients, Scanner sc)
            throws DuplicateDataException {
        LocalDate expiryDate = null;
        String unit = null;
        String ingredientName = null;
        String expiryDateString;
        int quantityWithBatch = 0;
        while (sc.hasNext()) {
            String line = sc.nextLine();
            if (!line.isBlank()) {
                if (!line.contains("-")) {
                    int ingredientNameSeparator = line.indexOf("(") - 1;
                    ingredientName = line.substring(0, ingredientNameSeparator);
                    unit = getUnit(line);
                } else {
                    int expiryDateStart = line.indexOf("(") + 1;
                    int expiryDateEnd = line.indexOf(")");
                    expiryDateString = line.substring(expiryDateStart, expiryDateEnd);
                    expiryDate = LocalDate.parse(expiryDateString);
                    quantityWithBatch = getQuantityWithBatch(line, expiryDateStart);
                    if (!sc.hasNext()) {
                        if (ingredientName != null) {
                            ingredients.add(ingredientName, unit, quantityWithBatch, expiryDate);
                            expiryDate = null;
                            unit = null;
                            ingredientName = null;
                        }
                    }
                }
            } else {
                if (ingredientName != null) {
                    ingredients.add(ingredientName, unit, quantityWithBatch, expiryDate);
                    expiryDate = null;
                    unit = null;
                    ingredientName = null;
                }
            }
        }
    }
}
