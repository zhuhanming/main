package modulo.logic.commands;

import static modulo.commons.core.Messages.MESSAGE_INVALID_VIEW_INDEX;
import static modulo.commons.core.Messages.MESSAGE_VIEW_ITEM_SUCCESS;
import static modulo.logic.commands.CommandTestUtil.assertCommandFailure;
import static modulo.logic.commands.CommandTestUtil.assertCommandSuccess;
import static modulo.logic.commands.CommandTestUtil.showEventAtIndex;
import static modulo.testutil.TypicalIndexesUtils.INDEX_FIRST_ITEM;
import static modulo.testutil.TypicalIndexesUtils.INDEX_THOUSANDTH_ITEM;
import static modulo.testutil.module.TypicalModules.getTypicalModulo;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import modulo.commons.core.Messages;
import modulo.commons.core.index.Index;
import modulo.model.Model;
import modulo.model.ModelManager;
import modulo.model.UserPrefs;
import modulo.model.event.Event;
import modulo.model.module.Module;


public class ViewCommandTest {
    private Model model = new ModelManager(getTypicalModulo(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Module moduleToView = model.getFilteredModuleList().get(INDEX_FIRST_ITEM.getZeroBased());
        ViewCommand viewCommand = new ViewCommand(INDEX_FIRST_ITEM);

        String expectedMessage = String.format(MESSAGE_INVALID_VIEW_INDEX, moduleToView);
        CommandResult commandResult = new CommandResult(expectedMessage, false, false, false, false, null, "");

        ModelManager expectedModel = new ModelManager(model.getModulo(), new UserPrefs());
        assertCommandSuccess(viewCommand, model, commandResult, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredModuleList().size() + 1);
        ViewCommand viewCommand = new ViewCommand(outOfBoundIndex);

        assertCommandFailure(viewCommand, model, Messages.MESSAGE_INVALID_VIEW_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showEventAtIndex(model, INDEX_FIRST_ITEM);

        Event eventToView = model.getFilteredEventList().get(INDEX_FIRST_ITEM.getZeroBased());
        ViewCommand viewCommand = new ViewCommand(INDEX_FIRST_ITEM);

        String expectedMessage = String.format(MESSAGE_VIEW_ITEM_SUCCESS, eventToView);
        CommandResult commandResult = new CommandResult(expectedMessage, false, false, false, false, null, "");
        Model expectedModel = new ModelManager(model.getModulo(), new UserPrefs());
//        expectedModel.deletePerson(personToDelete);
//        showNoPerson(expectedModel);

        assertCommandSuccess(viewCommand, model, commandResult, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showEventAtIndex(model, INDEX_FIRST_ITEM);

        Index outOfBoundIndex = INDEX_THOUSANDTH_ITEM;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getFilteredEventList().size());

        ViewCommand viewCommand = new ViewCommand(outOfBoundIndex);

        assertCommandFailure(viewCommand, model, Messages.MESSAGE_INVALID_VIEW_INDEX);
    }

    @Test
    public void equals() {
        ViewCommand viewFirstCommand = new ViewCommand(INDEX_FIRST_ITEM);
        ViewCommand viewSecondCommand = new ViewCommand(INDEX_FIRST_ITEM);

        // same object -> returns true
        assertTrue(viewFirstCommand.equals(viewFirstCommand));

        // same values -> returns true
        ViewCommand viewFirstCommandCopy = new ViewCommand(INDEX_FIRST_ITEM);
        assertTrue(viewFirstCommand.equals(viewFirstCommandCopy));

        // different types -> returns false
        assertFalse(viewFirstCommand.equals(1));

        // null -> returns false
        assertFalse(viewFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(viewFirstCommand.equals(viewSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.

     private void showNoPerson(Model model) {
     model.updateFilteredDisplayableList(p -> false);
     assertTrue(model.getFilteredDisplayableList().isEmpty());
     }
     */
}
