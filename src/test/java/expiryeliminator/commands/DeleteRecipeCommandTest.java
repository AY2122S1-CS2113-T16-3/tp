package expiryeliminator.commands;

import expiryeliminator.util.TestUtil;
import expiryeliminator.data.RecipeList;

import expiryeliminator.data.exception.NotFoundException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DeleteRecipeCommandTest {
    @Test
    public void deleteRecipeCommand_recipeNameNotInList_recipeNotFoundErrorMessage() throws NotFoundException {
        RecipeList recipes = TestUtil.generateRecipeList();
        Command command = new DeleteRecipeCommand("Duck soup");
        assertEquals(command.execute(null, recipes), DeleteRecipeCommand.MESSAGE_RECIPE_NOT_FOUND);
    }

    @Test
    public void deleteRecipeCommand_recipeNameInList_recipeDeletedMessage() throws NotFoundException {
        RecipeList recipes = TestUtil.generateRecipeList();
        Command command = new DeleteRecipeCommand(TestUtil.EXAMPLE_RECIPE_NAME);
        String message = String.format(DeleteRecipeCommand.MESSAGE_RECIPE_DELETED, TestUtil.generateRecipe(), 0);
        assertEquals(command.execute(null, recipes), message);
    }
}
