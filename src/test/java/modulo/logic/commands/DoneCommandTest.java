package modulo.logic.commands;

import static modulo.commons.core.Messages.MESSAGE_COMPLETED_DEADLINE;
import static modulo.logic.commands.CommandTestUtil.assertCommandSuccess;
import static modulo.testutil.TypicalIndexesUtils.INDEX_FIRST_ITEM;
import static modulo.testutil.deadline.TypicalDeadlines.DO_TUTORIAL;
import static modulo.testutil.event.TypicalEvents.TUTORIAL_1;
import static modulo.testutil.module.TypicalModules.getTypicalModulo;

import org.junit.jupiter.api.Test;

import modulo.logic.parser.DoneCommandParser;
import modulo.model.Model;
import modulo.model.ModelManager;
import modulo.model.UserPrefs;
import modulo.model.module.Module;

public class DoneCommandTest {
    private DoneCommandParser doneCommandParser = new DoneCommandParser();
    private Model model = new ModelManager(getTypicalModulo(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Module moduleRetrieved = model.getFilteredModuleList().get(INDEX_FIRST_ITEM.getZeroBased());
//        Event eventRetrieved = model.getFilteredEventList().get(INDEX_FIRST_ITEM.getZeroBased());
//        Deadline deadline = eventRetrieved.getDeadlines().get(INDEX_FIRST_ITEM.getOneBased());

        DoneCommand doneCommand = new DoneCommand(moduleRetrieved, TUTORIAL_1, INDEX_FIRST_ITEM);

        String expectedMessage = String.format(MESSAGE_COMPLETED_DEADLINE, DO_TUTORIAL);
        CommandResult commandResult = new CommandResult(expectedMessage, false, false,
                false, false, null, null);
        ModelManager expectedModel = new ModelManager(model.getModulo(), new UserPrefs());

        assertCommandSuccess(doneCommand, model, commandResult, expectedModel);
    }
}
