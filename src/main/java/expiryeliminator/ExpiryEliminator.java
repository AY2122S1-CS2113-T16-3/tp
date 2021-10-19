package expiryeliminator;

import java.util.logging.Level;
import java.util.logging.Logger;

import expiryeliminator.commands.Command;
import expiryeliminator.common.LogsCenter;
import expiryeliminator.data.IngredientList;
import expiryeliminator.data.RecipeList;
import expiryeliminator.data.exception.NotFoundException;
import expiryeliminator.parser.Parser;
import expiryeliminator.ui.Ui;
import expiryeliminator.storage.saveList;
import expiryeliminator.storage.loadList;

/**
 * Entry point of the ExpiryEliminator application.
 * Initialises the application and starts the interaction with the user.
 */
public class ExpiryEliminator {
    private static Ui ui;
    private static IngredientList ingredients;
    private static RecipeList recipes;
    private static final Logger logger = LogsCenter.getLogger(ExpiryEliminator.class);

    /**
     * Initialises the ExpiryEliminator application.
     */
    public ExpiryEliminator() {
        ui = new Ui();
        ingredients = new IngredientList();
        recipes = new RecipeList();
    }

    //@@author bernardboey-reused
    // Reused from https://github.com/bernardboey/ip/blob/master/src/main/java/duke/ui/Ui.java
    // with minor modifications

    /**
     * Runs the program.
     */
    public void run() throws NotFoundException {
        loadList.loadRecipeList(recipes);
        loadList.loadIngredientList(ingredients);
        ui.showGreeting();
        boolean isExit = false;
        while (!isExit) {
            final String userInput = ui.getUserInput();
            logger.log(Level.INFO, "Received used input: " + userInput);
            final Command command = Parser.parseCommand(userInput);
            isExit = command.isExit();
            final String feedback = command.execute(ingredients, recipes);
            ui.showToUser(feedback);
        }
    }
    //@author

    /**
     * Main entry-point for the ExpiryEliminator application.
     */
    public static void main(String[] args) throws NotFoundException {
        logger.log(Level.INFO, "Starting program.");
        new ExpiryEliminator().run();
        logger.log(Level.INFO, "Exiting program.");
    }
}
