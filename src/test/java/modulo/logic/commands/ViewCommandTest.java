package modulo.logic.commands;

import static modulo.logic.commands.CommandTestUtil.assertCommandFailure;
import static modulo.logic.commands.CommandTestUtil.assertCommandSuccess;
import static modulo.logic.commands.CommandTestUtil.showEventAtIndex;
import static modulo.logic.commands.CommandTestUtil.showModuleAtIndex;
import static modulo.testutil.TypicalIndexesUtils.INDEX_FIRST_ITEM;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import modulo.commons.core.Messages;
import modulo.commons.core.index.Index;
import modulo.model.Model;
import modulo.model.ModelManager;
import modulo.model.UserPrefs;
import modulo.model.displayable.Displayable;
import modulo.model.displayable.DisplayableType;
import modulo.testutil.module.TypicalModules;

public class ViewCommandTest {

    private final Model model = new ModelManager(TypicalModules.getTypicalModulo(), new UserPrefs());

    @Test
    public void executeViewValidIndex_unFilteredList_success() {


        ObservableList<? extends Displayable> lastShownList = model.getFilteredFocusedList();
        Displayable itemToView = lastShownList.get(INDEX_FIRST_ITEM.getZeroBased());
        model.setFocusedDisplayable(itemToView);
        ViewCommand viewCommand = new ViewCommand(INDEX_FIRST_ITEM);
        CommandResult expectedResult = new CommandResult(String.format(
                Messages.MESSAGE_VIEW_ITEM_SUCCESS, itemToView),
                INDEX_FIRST_ITEM);
        Model expectedModel = new ModelManager(model.getModulo(), model.getUserPrefs());
        expectedModel.setFocusedDisplayable(itemToView);
        assertCommandSuccess(viewCommand, model, expectedResult, expectedModel);
    }

    @Test
    public void executeInvalidViewIndex_unFilteredEventList_throwsCommandException() {

        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredEventList().size() + 1);
        ViewCommand viewCommand = new ViewCommand(outOfBoundIndex);
        assertCommandFailure(viewCommand, model, Messages.MESSAGE_INVALID_VIEW_INDEX);
    }

    @Test
    public void executeInvalidViewIndex_unFilteredModuleList_throwsCommandException() {

        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredModuleList().size() + 1);
        ViewCommand viewCommand = new ViewCommand(outOfBoundIndex);

        assertCommandFailure(viewCommand, model, Messages.MESSAGE_INVALID_VIEW_INDEX);
    }

    @Test
    public void executeViewValidIndex_unEventFilteredList_success() {
        showEventAtIndex(model, INDEX_FIRST_ITEM);
        model.updateFilteredEventList(Model.PREDICATE_SHOW_ALL_EVENTS);
        model.setFilteredFocusedList(DisplayableType.EVENT);
        ObservableList<? extends Displayable> lastShownList = model.getFilteredFocusedList();
        Displayable itemToView = lastShownList.get(INDEX_FIRST_ITEM.getZeroBased());
        model.setFocusedDisplayable(itemToView);
        ViewCommand viewCommand = new ViewCommand(INDEX_FIRST_ITEM);
        CommandResult expectedResult = new CommandResult(String.format(
                Messages.MESSAGE_VIEW_ITEM_SUCCESS, itemToView),
                INDEX_FIRST_ITEM);
        Model expectedModel = new ModelManager(model.getModulo(), model.getUserPrefs());
        expectedModel.updateFilteredEventList(Model.PREDICATE_SHOW_ALL_EVENTS);
        expectedModel.setFilteredFocusedList(DisplayableType.EVENT);
        expectedModel.setFocusedDisplayable(itemToView);
        assertCommandSuccess(viewCommand, model, expectedResult, expectedModel);
    }

    @Test
    public void executeViewValidIndex_unModuleFilteredList_success() {
        showModuleAtIndex(model, INDEX_FIRST_ITEM);
        model.updateFilteredModuleList(Model.PREDICATE_SHOW_ALL_MODULES);
        model.setFilteredFocusedList(DisplayableType.MODULE);
        ObservableList<? extends Displayable> lastShownList = model.getFilteredFocusedList();
        Displayable itemToView = lastShownList.get(INDEX_FIRST_ITEM.getZeroBased());
        model.setFocusedDisplayable(itemToView);
        ViewCommand viewCommand = new ViewCommand(INDEX_FIRST_ITEM);
        CommandResult expectedResult = new CommandResult(String.format(
                Messages.MESSAGE_VIEW_ITEM_SUCCESS, itemToView),
                INDEX_FIRST_ITEM);
        Model expectedModel = new ModelManager(model.getModulo(), model.getUserPrefs());
        expectedModel.updateFilteredModuleList(Model.PREDICATE_SHOW_ALL_MODULES);
        expectedModel.setFilteredFocusedList(DisplayableType.MODULE);
        expectedModel.setFocusedDisplayable(itemToView);
        assertCommandSuccess(viewCommand, model, expectedResult, expectedModel);
    }


}
