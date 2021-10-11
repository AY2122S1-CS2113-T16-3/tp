package expiryeliminator.parser;

import expiryeliminator.commands.AddRecipeCommand;
import expiryeliminator.commands.DeleteRecipeCommand;
import expiryeliminator.util.TestUtil;
import expiryeliminator.data.RecipeList;

import org.junit.jupiter.api.Test;

import static expiryeliminator.parser.Parser.parseCommand;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ParserTest {
    @Test
    public void prepareAddRecipe_incorrectFormats_ErrorMessage() {
        String[] tests = {"add recipe r/chicken soup i/chicken q/",
                "add recipe r/chicken soup i/ q/1",
                "add recipe r/chicken soup",
                "add recipe r/Apple Pie",
                "add recipe i/Red Apple q/4 i/Green Apple q/4"};

        for (String test : tests) {
            assertEquals(parseCommand(test).execute(null,null),
                    "Wrong format for add recipe command");
        }
    }

    @Test
    public void prepareAddRecipe_quantityNotANumber_ErrorMessage() {
        String test = "add recipe r/Apple Pie i/Red Apple q/4 i/Green Apple q/four";
        assertEquals("Quantity must be a valid number",
                    parseCommand(test).execute(null,null));
    }

    @Test
    public void prepareAddRecipe_missingQuantityOrIngredient_ErrorMessage() {
        String[] tests = {"add recipe r/chicken soup i/chicken q/1 i/salt" ,
                "add recipe r/chicken soup i/chicken q/1 q/20",
                "add recipe r/Apple Pie i/Red Apple q/4 i/Green Apple",
                "add recipe r/Apple Pie i/Red Apple q/4 q/4"};

        for (String test : tests) {
            assertEquals("Should have same number of ingredient names and quantities",
                    parseCommand(test).execute(null,null));
        }
    }

    @Test
    public void prepareAddRecipe_correctInput_AddRecipeCommand() {
        String test = "add recipe r/Chicken Soup i/chicken q/1 i/salt q/20";
        assertTrue(parseCommand(test) instanceof AddRecipeCommand);
    }

    @Test
    public void prepareDeleteRecipe_correctInput_DeleteRecipeCommand() {
        String test = "delete recipe r/Chicken Soup";
        assertTrue(parseCommand(test) instanceof DeleteRecipeCommand);
    }

    @Test
    public void prepareDeleteRecipe_incorrectFormat_ErrorMessage() {
        String test = "delete recipe r/";
        assertEquals(parseCommand(test).execute(null,null),
                "Wrong format for delete recipe command");
    }
}
