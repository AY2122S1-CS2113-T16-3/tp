package expiryeliminator.commands;

import expiryeliminator.data.IngredientList;
import expiryeliminator.data.exception.NotFoundException;
import expiryeliminator.util.TestUtil;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ListIngredientsExpiringCommandTest {

    @Test
    public void listIngredientsExpiringCommand_sampleIngredientList_expectExpiringIngredientString() throws NotFoundException {
        IngredientList ingredientList = TestUtil.generateIngredientList();
        assert ingredientList != null;
        Command command = new ListIngredientExpiringCommand();
        String message =  String.format(ListIngredientExpiringCommand.MESSAGE_SHOW_WHOLE_LIST,
                ingredientList.findExpiringIngredients());

        assertEquals(command.execute(ingredientList, null), message);
    }


}
