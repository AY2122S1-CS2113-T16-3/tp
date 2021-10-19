package expiryeliminator.commands;

import expiryeliminator.data.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import expiryeliminator.util.TestUtil;
import expiryeliminator.data.RecipeList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ViewRecipeCommandTest {

    @Test
    public void viewRecipeCommand_noMatchingRecipeName() throws NotFoundException {
        RecipeList recipes = TestUtil.generateRecipeList();
        Command command = new ViewRecipeCommand(TestUtil.RANDOM_INPUT_RECIPE_NAME);
        String errorMessage = ViewRecipeCommand.MESSAGE_RECIPE_NOT_FOUND;
        assertEquals(command.execute(null, recipes), errorMessage);
    }

    @Test
    public void viewRecipeCommand_hasMatchingRecipeName() throws NotFoundException {
        RecipeList recipes = TestUtil.generateRecipeList();
        Command command = new ViewRecipeCommand(TestUtil.EXAMPLE_RECIPE_NAME);
        String confirmMessage = String.format(ViewRecipeCommand.MESSAGE_SHOW_RECIPE, TestUtil.generateRecipe());
        assertEquals(command.execute(null, recipes), confirmMessage);
    }
}
