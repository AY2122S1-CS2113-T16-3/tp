package expiryeliminator.storage;

import expiryeliminator.data.IngredientRepository;
import expiryeliminator.data.RecipeList;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SaveLists {
    public static void saveRecipeListToFile(RecipeList recipes) {
        String pathName = "./data/";
        String fileName = "recipeList.txt";
        flushFile(pathName, fileName);
        try {
            String textToAppend = recipes.getWholeRecipeList();
            appendToFile(pathName + fileName, textToAppend);
        } catch (IOException e) {
            System.out.println("An recipeList IO error has occurred: " + e.getMessage());
        }
    }

    public static void saveIngredientRepoToFile(IngredientRepository ingredients) {
        String pathName = "./data/";
        String fileName = "ingredientRepo.txt";
        flushFile(pathName, fileName);
        try {
            String textToAppend = ingredients.printWholeList();
            appendToFile(pathName + fileName, textToAppend);
        } catch (IOException e) {
            System.out.println("An IngredientList IO error has occurred: " + e.getMessage());
        }
    }

    public static void flushFile(String pathName, String fileName) {
        File file = new File(pathName + fileName);
        try {
            FileWriter fw = new FileWriter(file);
            fw.write("");
        } catch (IOException e) {
            System.out.println("An flush IO error has occurred: " + e.getMessage());
        }
    }

    public static void createFileOrFolder(String pathName, String fileName) {
        try {
            Path path = Paths.get(pathName);
            Files.createDirectory(path);
        } catch (FileAlreadyExistsException ignored) {
            System.out.println("");
        } catch (IOException e) {
            System.out.println("An createFile IO error has occurred: " + e.getMessage());
        }

        try {
            Path file = Paths.get(pathName + fileName);
            Files.createFile(file);
        } catch (FileAlreadyExistsException ignored) {
            System.out.println("");
        } catch (IOException e) {
            System.out.println("An createFile IO error has occurred: " + e.getMessage());
        }
    }

    public static void appendToFile(String filePath, String textToAppend) throws IOException {
        FileWriter fw = new FileWriter(filePath, true);
        fw.write(textToAppend);
        fw.close();
    }
}
