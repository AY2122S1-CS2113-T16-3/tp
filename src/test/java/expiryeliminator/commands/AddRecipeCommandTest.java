package expiryeliminator.commands;

import expiryeliminator.data.exception.NotFoundException;
import expiryeliminator.util.TestUtil;
import expiryeliminator.data.RecipeList;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AddRecipeCommandTest {
    @Test
    public void addRecipeCommand_duplicateRecipeName_recipeAlreadyExistsError() throws NotFoundException {
        RecipeList recipes = TestUtil.generateRecipeList();
        Command command = new AddRecipeCommand(TestUtil.EXAMPLE_RECIPE_NAME,
                TestUtil.generateIngredientListForRecipe());
        String errorMessage = String.format(AddRecipeCommand.MESSAGE_RECIPE_ALREADY_EXISTS,
                TestUtil.EXAMPLE_RECIPE_NAME);
        assertEquals(command.execute(null, recipes), errorMessage);
    }

    @Test
    public void addRecipeCommand_correctInput_recipeAddedSuccessfully() throws NotFoundException {
        RecipeList recipes = new RecipeList();
        Command command = new AddRecipeCommand(TestUtil.EXAMPLE_RECIPE_NAME,
                TestUtil.generateIngredientListForRecipe());
        String successMessage = String.format(AddRecipeCommand.MESSAGE_RECIPE_ADDED, TestUtil.generateRecipe(), 1);
        assertEquals(command.execute(null, recipes), successMessage);
    }
}
