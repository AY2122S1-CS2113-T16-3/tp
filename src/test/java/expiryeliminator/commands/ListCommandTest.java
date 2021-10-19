package expiryeliminator.commands;


import expiryeliminator.data.IngredientList;
import expiryeliminator.data.exception.NotFoundException;
import expiryeliminator.util.TestUtil;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ListCommandTest {


    @Test
    public void listCommand_sampleIngredientList_expectListString() throws NotFoundException {
        IngredientList ingredientList = TestUtil.generateIngredientList();
        assert ingredientList != null;
        Command command = new ListCommand();
        String message = String.format(ListCommand.MESSAGE_SHOW_WHOLE_LIST, ingredientList.printWholeList(), 3);
        assertEquals(command.execute(ingredientList, null), message);
    }

    @Test
    public void listCommand_emptyIngredientList_emptyIngredientListMessage() throws NotFoundException {
        IngredientList ingredientList = TestUtil.generateEmptyIngredientList();
        Command command = new ListCommand();
        String message = ListCommand.MESSAGE_EMPTY_INGREDIENT_LIST;
        assertEquals(command.execute(ingredientList, null), message);
    }



}
