package modulo.logic.commands;

import static modulo.logic.commands.CommandTestUtil.assertCommandFailure;
import static modulo.logic.commands.CommandTestUtil.assertCommandSuccess;
import static modulo.testutil.TypicalIndexesUtils.INDEX_FIRST_ITEM;
import static modulo.testutil.TypicalIndexesUtils.INDEX_SECOND_ITEM;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import modulo.commons.core.Messages;
import modulo.commons.core.index.Index;
import modulo.logic.predicate.NameContainsKeywordsPredicate;
import modulo.model.Model;
import modulo.model.ModelManager;
import modulo.model.UserPrefs;
import modulo.model.deadline.Deadline;
import modulo.model.displayable.Displayable;
import modulo.model.displayable.DisplayableType;
import modulo.model.event.Event;
import modulo.model.module.Module;
import modulo.model.util.SampleDataUtil;


/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model = new ModelManager(SampleDataUtil.getSampleCalendar(), new UserPrefs());

    @Test
    public void executeValidIndexDeletion_unFilteredEventList_success() {
        model.updateFilteredEventList(Model.PREDICATE_SHOW_ALL_EVENTS);
        model.setFilteredFocusedList(DisplayableType.EVENT);
        ObservableList<? extends Displayable> lastShownList = model.getFilteredFocusedList();
        Event eventToDelete = (Event) lastShownList.get(INDEX_FIRST_ITEM.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_ITEM, false);

        CommandResult expectedResult = new CommandResult(String.format(
                Messages.MESSAGE_SINGLE_EVENT_DELETE_SUCCESS, eventToDelete),
                false, false, true, true, null, null);

        Model expectedModel = new ModelManager(model.getModulo(), model.getUserPrefs());
        expectedModel.updateFilteredEventList(Model.PREDICATE_SHOW_ALL_EVENTS);
        expectedModel.setFilteredFocusedList(DisplayableType.EVENT);
        expectedModel.deleteEvent(eventToDelete);

        assertCommandSuccess(deleteCommand, model, expectedResult, expectedModel);
    }

    @Test
    public void executeValidIndexDeletion_unFilteredModuleList_success() {
        model.updateFilteredModuleList(Model.PREDICATE_SHOW_ALL_MODULES);
        model.setFilteredFocusedList(DisplayableType.MODULE);
        ObservableList<? extends Displayable> lastShownList = model.getFilteredFocusedList();
        Module moduleToDelete = (Module) lastShownList.get(INDEX_FIRST_ITEM.getZeroBased());

        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_ITEM, false);
        deleteCommand.deleteEventsOfModule(model, moduleToDelete);

        CommandResult expectedResult = new CommandResult(String.format(
                Messages.MESSAGE_SINGLE_MODULE_DELETE_SUCCESS, moduleToDelete),
                false, false, true, true, null, null);

        Model expectedModel = new ModelManager(model.getModulo(), model.getUserPrefs());
        expectedModel.updateFilteredModuleList(Model.PREDICATE_SHOW_ALL_MODULES);
        expectedModel.setFilteredFocusedList(DisplayableType.MODULE);
        expectedModel.deleteModule(moduleToDelete);

        assertCommandSuccess(deleteCommand, model, expectedResult, expectedModel);
    }


    @Test
    public void executeInvalidIndexDeletion_unFilteredEventList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredEventList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex, false);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_DELETE_INDEX);
    }


    @Test
    public void executeInvalidIndexDeletion_unFilteredModuleList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredModuleList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex, false);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_DELETE_INDEX);
    }

    @Test
    public void executeValidIndexDeletion_filteredUpcomingEventList_success() {
        model.updateFilteredEventList(Model.PREDICATE_SHOW_UPCOMING_EVENTS);
        model.setFilteredFocusedList(DisplayableType.EVENT);
        ObservableList<? extends Displayable> lastShownList = model.getFilteredFocusedList();
        Event eventToDelete = (Event) lastShownList.get(INDEX_FIRST_ITEM.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_ITEM, false);

        CommandResult expectedResult = new CommandResult(String.format(
                Messages.MESSAGE_SINGLE_EVENT_DELETE_SUCCESS, eventToDelete),
                false, false, true, true, null, null);

        Model expectedModel = new ModelManager(model.getModulo(), model.getUserPrefs());
        expectedModel.updateFilteredEventList(Model.PREDICATE_SHOW_ALL_EVENTS);
        expectedModel.setFilteredFocusedList(DisplayableType.EVENT);
        expectedModel.deleteEvent(eventToDelete);

        assertCommandSuccess(deleteCommand, model, expectedResult, expectedModel);
    }

    @Test
    public void executeValidMultipleItemsDeletion_unFilteredEventList_success() {
        model.updateFilteredEventList(Model.PREDICATE_SHOW_ALL_EVENTS);
        model.setFilteredFocusedList(DisplayableType.EVENT);

        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate("tut");
        DeleteCommand deleteCommand = new DeleteCommand(predicate, false);

        Model expectedModel = new ModelManager(model.getModulo(), model.getUserPrefs());
        expectedModel.updateFilteredEventList(Model.PREDICATE_SHOW_ALL_EVENTS);
        expectedModel.setFilteredFocusedList(DisplayableType.EVENT);

        Object[] list = expectedModel.getFilteredDisplayableList(predicate);
        int numberOfItemsDeleted = 0;
        for (Object eventObject : list) {
            numberOfItemsDeleted++;
            expectedModel.deleteEvent((Event) eventObject);
        }
        CommandResult expectedResult = new CommandResult(String.format(
                Messages.MESSAGE_MUTIPLE_EVENTS_DELETE_SUCCESS, numberOfItemsDeleted, predicate.toString()),
                false, false, true, true, null, null);

        assertCommandSuccess(deleteCommand, model, expectedResult, expectedModel);
    }

    @Test
    public void executeValidMultipleItemsDeletion_filteredModuleList_success() {
        model.updateFilteredModuleList(Model.PREDICATE_SHOW_ALL_MODULES);
        model.setFilteredFocusedList(DisplayableType.MODULE);

        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate("cs");
        DeleteCommand deleteCommand = new DeleteCommand(predicate, false);

        Model expectedModel = new ModelManager(model.getModulo(), model.getUserPrefs());
        expectedModel.updateFilteredModuleList(Model.PREDICATE_SHOW_ALL_MODULES);
        expectedModel.setFilteredFocusedList(DisplayableType.MODULE);

        Object[] list = expectedModel.getFilteredDisplayableList(predicate);
        int numberOfItemsDeleted = 0;

        for (Object moduleObject : list) {
            numberOfItemsDeleted++;
            Module module = (Module) moduleObject;
            deleteCommand.deleteEventsOfModule(expectedModel, module);
            expectedModel.deleteModule(module);
        }

        CommandResult expectedResult = new CommandResult(String.format(
                Messages.MESSAGE_MUTIPLE_MODULES_DELETE_SUCCESS, numberOfItemsDeleted, predicate.toString()),
                false, false, true, true, null, null);


        assertCommandSuccess(deleteCommand, model, expectedResult, expectedModel);
    }

    @Test
    public void executeInvalidIndexDeletion_filteredUpcomingEventList_throwsCommandException() {
        model.updateFilteredEventList(Model.PREDICATE_SHOW_UPCOMING_EVENTS);
        model.setFilteredFocusedList(DisplayableType.EVENT);

        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredEventList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex, false);
        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_DELETE_INDEX);
    }

    @Test
    public void executeValidIndexDeadlineDeletion_unFilteredEventListEventNotSelected_throwsCommandException() {
        model.updateFilteredEventList(Model.PREDICATE_SHOW_ALL_EVENTS);
        model.setFilteredFocusedList(DisplayableType.EVENT);

        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_ITEM, true);
        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_EVENT_NOT_SELECTED);
    }

    @Test
    public void executeValidIndexDeadlineDeletion_unFilteredModuleListEventNotSelected_throwsCommandException() {
        model.updateFilteredModuleList(Model.PREDICATE_SHOW_ALL_MODULES);
        model.unsetFocusedDisplayable();
        model.setFilteredFocusedList(DisplayableType.MODULE);

        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_ITEM, true);
        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_EVENT_NOT_SELECTED);
    }

    @Test
    public void executeValidIndexDeadlineDeletion_filteredUpcomingEventList_success() {
        model.updateFilteredEventList(Model.PREDICATE_SHOW_UPCOMING_EVENTS);
        model.setFilteredFocusedList(DisplayableType.EVENT);
        model.setFocusedDisplayable(model.getFilteredEventList().get(0));

        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_ITEM, true);


        Model expectedModel = new ModelManager(model.getModulo(), model.getUserPrefs());
        expectedModel.updateFilteredEventList(Model.PREDICATE_SHOW_UPCOMING_EVENTS);
        expectedModel.setFilteredFocusedList(DisplayableType.EVENT);
        expectedModel.setFocusedDisplayable(expectedModel.getFilteredEventList().get(0));

        Event displayedEvent = (Event) expectedModel.getFocusedDisplayable();
        Deadline itemToDelete = displayedEvent.getDeadlines().get(INDEX_FIRST_ITEM.getZeroBased());
        CommandResult expectedResult = new CommandResult(String.format(
                Messages.MESSAGE_INCOMPLETE_DEADLINE_DELETE_SUCCESS, itemToDelete),
                false, false, true, true, null, null);

        assertCommandSuccess(deleteCommand, model, expectedResult, expectedModel);
    }

    @Test
    public void executeInvalidIndexDeadlineDeletion_filteredUpcomingEventList_throwsCommandException() {
        model.updateFilteredEventList(Model.PREDICATE_SHOW_UPCOMING_EVENTS);
        model.setFilteredFocusedList(DisplayableType.EVENT);
        model.setFocusedDisplayable(model.getFilteredEventList().get(0));

        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredEventList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex, true);
        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_DELETE_INDEX);
    }

    @Test
    public void executeValidDeleteAllEvents_unFilteredUpcomingEventList_success() {
        model.updateFilteredEventList(Model.PREDICATE_SHOW_ALL_EVENTS);
        model.setFilteredFocusedList(DisplayableType.EVENT);

        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate("");
        DeleteCommand deleteCommand = new DeleteCommand(predicate, false);

        Model expectedModel = new ModelManager(model.getModulo(), model.getUserPrefs());
        expectedModel.updateFilteredEventList(Model.PREDICATE_SHOW_ALL_EVENTS);
        expectedModel.setFilteredFocusedList(DisplayableType.EVENT);

        Object[] list = expectedModel.getFilteredDisplayableList(predicate);
        int numberOfItemsDeleted = 0;
        for (Object eventObject : list) {
            numberOfItemsDeleted++;
            expectedModel.deleteEvent((Event) eventObject);
        }
        CommandResult expectedResult = new CommandResult(String.format(
                Messages.MESSAGE_DELETE_ALL_EVENTS_SUCCESS, numberOfItemsDeleted, predicate.toString()),
                false, false, true, true, null, null);


        assertCommandSuccess(deleteCommand, model, expectedResult, expectedModel);
    }
    @Test
    public void executeValidDeleteAllModules_unFilteredModuleList_success() {
        model.updateFilteredModuleList(Model.PREDICATE_SHOW_ALL_MODULES);
        model.setFilteredFocusedList(DisplayableType.MODULE);

        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate("");
        DeleteCommand deleteCommand = new DeleteCommand(predicate, false);

        Model expectedModel = new ModelManager(model.getModulo(), model.getUserPrefs());
        expectedModel.updateFilteredModuleList(Model.PREDICATE_SHOW_ALL_MODULES);
        expectedModel.setFilteredFocusedList(DisplayableType.MODULE);

        Object[] list = expectedModel.getFilteredDisplayableList(predicate);
        int numberOfItemsDeleted = 0;
        for (Object moduleObject : list) {
            numberOfItemsDeleted++;
            Module module = (Module) moduleObject;
            deleteCommand.deleteEventsOfModule(expectedModel, module);
            expectedModel.deleteModule(module);
        }
        CommandResult expectedResult = new CommandResult(String.format(
                Messages.MESSAGE_DELETE_ALL_MODULES_SUCCESS, numberOfItemsDeleted, predicate.toString()),
                false, false, true, true, null, null);


        assertCommandSuccess(deleteCommand, model, expectedResult, expectedModel);
    }

    @Test
    public void executeValidDeleteAllDeadlines_filteredUpcomingEventList_success() {
        model.updateFilteredEventList(Model.PREDICATE_SHOW_UPCOMING_EVENTS);
        model.setFilteredFocusedList(DisplayableType.EVENT);
        model.setFocusedDisplayable(model.getFilteredEventList().get(0));

        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate("");

        DeleteCommand deleteCommand = new DeleteCommand(predicate, true);


        Model expectedModel = new ModelManager(model.getModulo(), model.getUserPrefs());
        expectedModel.updateFilteredEventList(Model.PREDICATE_SHOW_UPCOMING_EVENTS);
        expectedModel.setFilteredFocusedList(DisplayableType.EVENT);
        expectedModel.setFocusedDisplayable(expectedModel.getFilteredEventList().get(0));

        Event displayedEvent = (Event) expectedModel.getFocusedDisplayable();
        CommandResult expectedResult = new CommandResult(String.format(Messages.MESSAGE_ALL_DEADLINE_DELETE_SUCCESS,
                displayedEvent), false, false, true, true, null, null);

        assertCommandSuccess(deleteCommand, model, expectedResult, expectedModel);
    }

    @Test
    public void executeInvalidPredicate_noEventDeleted_throwCommandException() {
        model.updateFilteredEventList(Model.PREDICATE_SHOW_ALL_EVENTS);
        model.setFilteredFocusedList(DisplayableType.EVENT);

        NameContainsKeywordsPredicate predicate =
                new NameContainsKeywordsPredicate("invalidArgument");
        DeleteCommand deleteCommand = new DeleteCommand(predicate, false);

        Model expectedModel = new ModelManager(model.getModulo(), model.getUserPrefs());
        expectedModel.updateFilteredEventList(Model.PREDICATE_SHOW_ALL_EVENTS);
        expectedModel.setFilteredFocusedList(DisplayableType.EVENT);

        assertCommandFailure(deleteCommand, model, String.format(Messages.MESSAGE_NO_EVENT_DELETED,
                predicate.toString()));
    }

    @Test
    public void executeInvalidPredicate_noModuleDeleted_throwCommandException() {
        model.updateFilteredModuleList(Model.PREDICATE_SHOW_ALL_MODULES);
        model.setFilteredFocusedList(DisplayableType.MODULE);

        NameContainsKeywordsPredicate predicate =
                new NameContainsKeywordsPredicate("invalidArgument");
        DeleteCommand deleteCommand = new DeleteCommand(predicate, false);

        Model expectedModel = new ModelManager(model.getModulo(), model.getUserPrefs());
        expectedModel.updateFilteredModuleList(Model.PREDICATE_SHOW_ALL_MODULES);
        expectedModel.setFilteredFocusedList(DisplayableType.MODULE);

        assertCommandFailure(deleteCommand, model, String.format(Messages.MESSAGE_NO_MODULE_DELETED,
                predicate.toString()));
    }


    @Test
    public void equals() {

        DeleteCommand deleteFirstCommand = new DeleteCommand(INDEX_FIRST_ITEM, false);
        DeleteCommand deleteSecondCommand = new DeleteCommand(INDEX_SECOND_ITEM, false);
        DeleteCommand deleteDeadlineFirstCommand = new DeleteCommand(INDEX_FIRST_ITEM, true);
        DeleteCommand deleteDeadlineSecondCommand = new DeleteCommand(INDEX_SECOND_ITEM, true);

        DeleteCommand deleteDeadlineMultipleItemsCommand = new DeleteCommand(
                new NameContainsKeywordsPredicate("valid"), true);
        DeleteCommand deleteMultipleItemsCommand = new DeleteCommand(
                new NameContainsKeywordsPredicate("otherArg"), true);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same object -> returns true
        assertTrue(deleteDeadlineFirstCommand.equals(deleteDeadlineFirstCommand));

        // same variables -> returns true
        DeleteCommand deleteDeadlineFirstCommandCopy = new DeleteCommand(INDEX_FIRST_ITEM, true);
        assertTrue(deleteDeadlineFirstCommand.equals(deleteDeadlineFirstCommandCopy));

        // same variables -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(INDEX_FIRST_ITEM, false);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different index -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));

        // different item to delete -> returns false
        assertFalse(deleteDeadlineFirstCommand.equals(deleteFirstCommand));

        // different deadline index -> returns false
        assertFalse(deleteDeadlineFirstCommand.equals(deleteDeadlineSecondCommand));

        // different predicate -> returns false
        assertFalse(deleteDeadlineMultipleItemsCommand.equals(deleteMultipleItemsCommand));
    }
}
