package expiryeliminator.commands;

import expiryeliminator.data.IngredientList;
import expiryeliminator.data.exception.NotFoundException;
import expiryeliminator.util.TestUtil;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ListIngredientsExpiredCommandTest {

    @Test
    public void listIngredientsExpiredCommand_sampleIngredientList_expectExpiredIngredientString() throws NotFoundException {
        IngredientList ingredientList = TestUtil.generateIngredientList();
        assert ingredientList != null;
        Command command = new ListIngredientsExpiredCommand();
        String message =  String.format(ListIngredientsExpiredCommand.MESSAGE_SHOW_WHOLE_LIST,
                ingredientList.findExpiredIngredients());

        assertEquals(command.execute(ingredientList, null), message);
    }
}
