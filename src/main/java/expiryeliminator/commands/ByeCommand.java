package expiryeliminator.commands;

//@@author bernardboey-reused
// Reused from https://github.com/bernardboey/ip/blob/master/src/main/java/duke/commands/ByeCommand.java
// with minor modifications

import expiryeliminator.data.IngredientList;
import expiryeliminator.data.RecipeList;

/**
 * Terminates the program.
 */
public class ByeCommand extends Command {
    /** Unique word associated with the command. */
    public static final String COMMAND_WORD = "bye";

    private static final String MESSAGE_FAREWELL = "Bye. Hope to see you again soon!";

    @Override
    public String execute(IngredientList ingredientList, RecipeList recipes) {
        return MESSAGE_FAREWELL;
    }

    @Override
    public boolean isExit() {
        return true;
    }
}
//@@author
