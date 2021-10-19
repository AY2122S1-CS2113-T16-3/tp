package expiryeliminator.storage;

import expiryeliminator.data.Ingredient;
import expiryeliminator.data.IngredientList;
import expiryeliminator.data.Recipe;
import expiryeliminator.data.RecipeList;
import expiryeliminator.data.exception.DuplicateDataException;
import java.time.LocalDate;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class LoadList {
    public static void loadRecipeList(RecipeList recipes) {
        String pathName = "./data/";
        String fileName = "recipeList.txt";
        File file = new File(pathName + fileName);
        try {
            boolean isFirst = true;
            Scanner sc = new Scanner(file);
            String recipeName = null;
            IngredientList ingredients = new IngredientList();
            int ingredientNameSeparator;
            String ingredientName;
            int quantityStart;
            int quantityEnd;
            int ingredientQuantity;
            while (sc.hasNext()) {
                String line = sc.nextLine();
                if (!line.isBlank()) {
                    if (!line.contains("-")) {  //name of a recipe
                        if (isFirst) {
                            recipeName = line;
                            isFirst = false;
                        } else {
                            Recipe loadedRecipe = new Recipe(recipeName, ingredients);
                            recipes.add(loadedRecipe);
                            ingredients = new IngredientList();
                            recipeName = line;
                        }
                    } else {    //name of ingredients and qty
                        ingredientNameSeparator = line.indexOf("(") - 1;
                        ingredientName = line.substring(2, ingredientNameSeparator);
                        quantityStart = line.indexOf(":") + 2;
                        quantityEnd = line.indexOf(")");
                        ingredientQuantity = Integer.parseInt(line.substring(quantityStart, quantityEnd));
                        Ingredient ingredient = new Ingredient(ingredientName, ingredientQuantity, null);
                        ingredients.add(ingredient);
                        if (!sc.hasNext()) {
                            Recipe loadedRecipe = new Recipe(recipeName, ingredients);
                            recipes.add(loadedRecipe);
                        }
                    }
                }
            }
        } catch (DuplicateDataException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            SaveList.createFileOrFolder(pathName, fileName);
        }
    }

    public static void loadIngredientList(IngredientList ingredients) {
        String pathName = "./data/";
        String fileName = "ingredientList.txt";
        File file = new File(pathName + fileName);
        try {
            Scanner sc = new Scanner(file);
            while (sc.hasNext()) {
                String line = sc.nextLine();
                int ingredientNameSeparator = line.indexOf("(") - 1;
                String ingredientName = line.substring(0, ingredientNameSeparator);
                int quantityStart = line.indexOf(":") + 2;
                int quantityEnd = line.indexOf(")");
                int ingredientQuantity = Integer.parseInt(line.substring(quantityStart, quantityEnd));
                int expiryDateStart = line.indexOf("expiry:") + 8;
                int expiryDateEnd = line.length() - 1;
                String expiryDateString = line.substring(expiryDateStart, expiryDateEnd);
                LocalDate expiryDate = LocalDate.parse(expiryDateString);
                Ingredient ingredient = new Ingredient(ingredientName, ingredientQuantity, expiryDate);
                ingredients.add(ingredient);
            }
        } catch (DuplicateDataException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            SaveList.createFileOrFolder(pathName, fileName);
        }
    }
}