package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import javafx.collections.ObservableList;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Displayable;
import seedu.address.model.DisplayableType;
import seedu.address.model.Model;
import seedu.address.commons.core.Messages;
import seedu.address.model.event.Event;
import seedu.address.model.module.Module;
import seedu.address.model.predicate.NameContainsKeywordsPredicate;

/**
 * Deletes a person identified using it's displayed index from the address book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";
    private static final String predicateStringForDeleteAll = "[]";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the person identified by the index number used in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";




    private final Index targetIndex;
    private final NameContainsKeywordsPredicate predicate;

    public DeleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
        this.predicate = null;
    }

    public DeleteCommand(NameContainsKeywordsPredicate predicate) {
        this.targetIndex = null;
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        DisplayableType displayableType = model.getCurrentDisplayableType();
        if (targetIndex != null) {
            ObservableList<? extends Displayable> lastShownList = model.getFilteredFocusedList();
            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_VIEW_DISPLAYED_INDEX);
            }
            Displayable itemToDelete = lastShownList.get(targetIndex.getZeroBased());
            if (displayableType == DisplayableType.EVENT) {
                Event event = (Event)itemToDelete;
                model.deleteEvent(event);
                return new CommandResult(String.format(Messages.MESSAGE_SINGLE_EVENT_DELETE_SUCCESS, itemToDelete));
            } else if (displayableType == DisplayableType.MODULE) {
                Module module = (Module)itemToDelete;
                deleteEventsOfModule(model, module);
                model.deleteModule((Module)itemToDelete);
                return new CommandResult(String.format(Messages.MESSAGE_SINGLE_MODULE_DELETE_SUCCESS, itemToDelete));
            }
        } else {
            Object[] list = model.getFilteredDisplayableList(predicate);
            int numberOfItemsDeleted = 0;
            if (displayableType == DisplayableType.EVENT) {
                for (Object eventObject : list) {
                    numberOfItemsDeleted++;
                    model.deleteEvent((Event)eventObject);
                }
                if (predicate.toString().equals(predicateStringForDeleteAll)) {
                    return new CommandResult(String.format(Messages.MESSAGE_DELETE_ALL_EVENTS_SUCCESS, numberOfItemsDeleted));
                }
                return new CommandResult(String.format(
                        Messages.MESSAGE_MUTIPLE_EVENTS_DELETE_SUCCESS, numberOfItemsDeleted, predicate.toString()));

            } else if (displayableType == DisplayableType.MODULE) {
                for (Object moduleObject : list) {
                    numberOfItemsDeleted++;
                    Module module = (Module)moduleObject;
                    deleteEventsOfModule(model, module);
                    model.deleteModule(module);
                }
                if (predicate.toString().equals(predicateStringForDeleteAll)) {
                    return new CommandResult(String.format(Messages.MESSAGE_DELETE_ALL_MODULES_SUCCESS, numberOfItemsDeleted));
                }
                return new CommandResult(String.format(
                        Messages.MESSAGE_MUTIPLE_MODULES_DELETE_SUCCESS, numberOfItemsDeleted, predicate.toString()));
            }
        }
        return new CommandResult(Messages.MESSAGE_ERROR);
    }

    private void deleteEventsOfModule(Model model, Module module) {
        int count = 0;
        while (count < model.getFilteredEventList().size()) {

            Event event = model.getFilteredEventList().get(count);
            if (event.getParentModule().equals(((Module)module))) {
                model.deleteEvent(event);
            } else {
                count++;
            }
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteCommand) other).targetIndex)); // state check
    }
}
