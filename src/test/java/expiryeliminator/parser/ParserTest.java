package expiryeliminator.parser;

import static expiryeliminator.parser.Parser.parseCommand;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import expiryeliminator.data.exception.NotFoundException;
import org.junit.jupiter.api.Test;

import expiryeliminator.commands.AddRecipeCommand;
import expiryeliminator.commands.DeleteRecipeCommand;

class ParserTest {
    @Test
    public void prepareAddRecipe_incorrectFormats_ErrorMessage() throws NotFoundException {
        String[] tests = {"add recipe r/chicken soup i/chicken q/",
                          "add recipe r/chicken soup i/ q/1",
                          "add recipe r/chicken soup",
                          "add recipe r/Apple Pie",
                          "add recipe i/Red Apple q/4 i/Green Apple q/4"};

        for (String test : tests) {
            assertEquals(parseCommand(test).execute(null, null),
                    String.format(Parser.MESSAGE_INVALID_COMMAND_FORMAT, AddRecipeCommand.MESSAGE_USAGE));
        }
    }

    @Test
    public void prepareAddRecipe_quantityNotANumber_ErrorMessage() throws NotFoundException {
        String test = "add recipe r/Apple Pie i/Red Apple q/4 i/Green Apple q/four";
        assertEquals(String.format(Parser.MESSAGE_INVALID_ARGUMENT_FORMAT, "Quantity must be a valid number."),
                parseCommand(test).execute(null, null));
    }

    @Test
    public void prepareAddRecipe_missingQuantityOrIngredient_ErrorMessage() throws NotFoundException {
        String[] tests = {"add recipe r/chicken soup i/chicken q/1 i/salt",
                          "add recipe r/chicken soup i/chicken q/1 q/20",
                          "add recipe r/Apple Pie i/Red Apple q/4 i/Green Apple",
                          "add recipe r/Apple Pie i/Red Apple q/4 q/4"};

        for (String test : tests) {
            assertEquals("Should have same number of ingredient names and quantities",
                    parseCommand(test).execute(null, null));
        }
    }

    @Test
    public void prepareAddRecipe_correctInput_AddRecipeCommand() {
        String test = "add recipe r/Chicken Soup i/chicken q/1 i/salt q/20";
        assertTrue(parseCommand(test) instanceof AddRecipeCommand);
    }

    @Test
    public void prepareAddRecipe_ingredientWithZeroQuantity_ErrorMessage() throws NotFoundException {
        String test = "add recipe r/Chicken Soup i/chicken q/0 i/salt q/20";
        assertEquals("Quantity of ingredients for recipe cannot be zero.",
                parseCommand(test).execute(null, null));
    }

    @Test
    public void prepareDeleteRecipe_correctInput_DeleteRecipeCommand() {
        String test = "delete recipe r/Chicken Soup";
        assertTrue(parseCommand(test) instanceof DeleteRecipeCommand);
    }

    @Test
    public void prepareDeleteRecipe_incorrectFormat_ErrorMessage() throws NotFoundException {
        String test = "delete recipe r/";
        assertEquals(parseCommand(test).execute(null, null),
                String.format(Parser.MESSAGE_INVALID_COMMAND_FORMAT, DeleteRecipeCommand.MESSAGE_USAGE));
    }
}
