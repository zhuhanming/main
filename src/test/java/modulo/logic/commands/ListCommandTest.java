package modulo.logic.commands;

import static modulo.commons.core.Messages.MESSAGE_SHOWING_EVENTS;
import static modulo.commons.core.Messages.MESSAGE_SHOWING_MODULES;
import static modulo.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.Test;

import modulo.model.Model;
import modulo.model.ModelManager;
import modulo.model.UserPrefs;
import modulo.model.displayable.DisplayableType;
import modulo.testutil.module.TypicalModules;


/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteCommand}.
 */
public class ListCommandTest {

    private Model model = new ModelManager(TypicalModules.getTypicalModulo(), new UserPrefs());

    @Test
    public void execute_validListEventInput_success() {
        Model expectedModel = new ModelManager(model.getModulo(), model.getUserPrefs());
        expectedModel.setFilteredFocusedList(DisplayableType.EVENT);

        CommandResult expectedResult = new CommandResult(
                MESSAGE_SHOWING_EVENTS, false, false, true, false, null, null);

        ListCommand listCommand = new ListCommand(DisplayableType.EVENT);
        assertCommandSuccess(listCommand, model, expectedResult, expectedModel);
    }

    @Test
    public void execute_validListModuleInput_success() {
        Model expectedModel = new ModelManager(model.getModulo(), model.getUserPrefs());
        expectedModel.setFilteredFocusedList(DisplayableType.MODULE);

        CommandResult expectedResult = new CommandResult(
                MESSAGE_SHOWING_MODULES, false, false, true, false, null, null);

        ListCommand listCommand = new ListCommand(DisplayableType.MODULE);
        assertCommandSuccess(listCommand, model, expectedResult, expectedModel);
    }
}
