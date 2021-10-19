package expiryeliminator.commands;

import expiryeliminator.data.Ingredient;
import expiryeliminator.data.IngredientList;
import expiryeliminator.data.exception.NotFoundException;
import expiryeliminator.util.TestUtil;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ViewIngredientCommandTest {

    @Test
    public void viewIngredientCommand_sampleIngredientList_expectRedAppleString() throws NotFoundException {
        IngredientList ingredientList = TestUtil.generateIngredientList();
        assert ingredientList != null;
        Command command = new ViewIngredientCommand("Red Apple");

        LocalDate pastDate = LocalDate.of(2021,10,8);
        Ingredient ingredient = new Ingredient("Red Apple", 1,pastDate);
        String message =  String.format(ViewIngredientCommand.MESSAGE_SHOW_INGREDIENT, ingredient);

        assertEquals(command.execute(ingredientList, null), message);
    }
}
