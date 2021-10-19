package expiryeliminator.storage;

import expiryeliminator.data.IngredientList;
import expiryeliminator.data.RecipeList;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class saveList {
    /**
     * This function is to save tasks to a specific file path
     */
    public static void saveRecipeListToFile(RecipeList recipes) {
        String pathName = "./data/";
        String fileName = "recipeList.txt";
        flushFile(pathName, fileName);
        try {
            String textToAppend = recipes.getWholeRecipeList();
            appendToFile(pathName + fileName, textToAppend);
        } catch (IOException e) {
            System.out.println("An IO error has occurred: " + e.getMessage());
        }
    }

    public static void saveIngredientListToFile(IngredientList ingredients) {
        String pathName = "./data/";
        String fileName = "ingredientList.txt";
        flushFile(pathName, fileName);
        try {
            String textToAppend = ingredients.printWholeList();
            appendToFile(pathName + fileName, textToAppend);
        } catch (IOException e) {
            System.out.println("An IO error has occurred: " + e.getMessage());
        }
    }

    public static void flushFile(String pathName, String fileName) {
        File file = new File(pathName + fileName);
        try {
            FileWriter fw = new FileWriter(file);
            fw.write("");
        } catch (IOException e) {
            System.out.println("An IO error has occurred: " + e.getMessage());
        }
    }

    /**
     * @param pathName pathName is the relative path without the file name
     * @param fileName only the file name
     */
    public static void createFileOrFolder(String pathName, String fileName) {
        try {
            Path path = Paths.get(pathName);
            Files.createDirectory(path);
            Path file = Paths.get(pathName + fileName);
            Files.createFile(file);
        } catch (FileAlreadyExistsException ignored) {
        } catch (IOException e) {
            System.out.println("An IO error has occurred: " + e.getMessage());
        }
    }

    /**
     * @param filePath     the whole path with both relative path and file name
     * @param textToAppend the text to be appended to the end of the file
     * @throws IOException exception when there is an I/O error
     */
    public static void appendToFile(String filePath, String textToAppend) throws IOException {
        FileWriter fw = new FileWriter(filePath, true);
        fw.write(textToAppend);
        fw.close();
    }
}
