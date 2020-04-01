package modulo.logic.commands;

import static java.util.Objects.requireNonNull;

import javafx.collections.ObservableList;
import modulo.commons.core.Messages;
import modulo.commons.core.index.Index;
import modulo.logic.commands.exceptions.CommandException;
import modulo.model.Displayable;
import modulo.model.DisplayableType;
import modulo.model.Model;
import modulo.model.deadline.Deadline;
import modulo.model.event.Event;
import modulo.model.module.Module;
import modulo.model.predicate.NameContainsKeywordsPredicate;

/**
 * Deletes a person identified using it's displayed index from the address book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";
    private static final String predicateStringForDeleteAll = "\"\"";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the module or event identified by the index number used in the displayed list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";


    private final Index targetIndex;
    private final NameContainsKeywordsPredicate predicate;
    private final boolean isDeadline;

    public DeleteCommand(Index targetIndex, boolean isDeadline) {
        this.targetIndex = targetIndex;
        this.predicate = null;
        this.isDeadline = isDeadline;
    }

    public DeleteCommand(NameContainsKeywordsPredicate predicate) {
        this.targetIndex = null;
        this.predicate = predicate;
        this.isDeadline = false;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        DisplayableType displayableType = model.getCurrentDisplayableType();
        if (targetIndex != null) {
            if (!isDeadline) {
                ObservableList<? extends Displayable> lastShownList = model.getFilteredFocusedList();
                if (targetIndex.getZeroBased() >= lastShownList.size()) {
                    throw new CommandException(Messages.MESSAGE_INVALID_DELETE_INDEX);
                }

                Displayable itemToDelete = lastShownList.get(targetIndex.getZeroBased());
                unsetFocusDisplayableIfEqual(model, itemToDelete);

                if (displayableType == DisplayableType.EVENT) {
                    Event event = (Event) itemToDelete;
                    model.deleteEvent(event);
                    return new CommandResult(String.format(Messages.MESSAGE_SINGLE_EVENT_DELETE_SUCCESS, itemToDelete),
                            false, false, true, true, null);
                } else if (displayableType == DisplayableType.MODULE) {
                    Module module = (Module) itemToDelete;
                    deleteEventsOfModule(model, module);
                    model.deleteModule((Module) itemToDelete);
                    return new CommandResult(String.format(Messages.MESSAGE_SINGLE_MODULE_DELETE_SUCCESS, itemToDelete),
                            false, false, true, true, null);
                }
            } else {
                try {
                    Event displayedEvent = (Event) model.getFocusedDisplayable();
                    int index = targetIndex.getZeroBased();
                    Deadline itemToDelete = displayedEvent.getDeadlines().get(index);
                    displayedEvent.removeDeadline(itemToDelete);
                    return (itemToDelete.isCompleted()) ? new CommandResult(String.format(
                            Messages.MESSAGE_COMPLETED_DEADLINE_DELETE_SUCCESS, itemToDelete),
                            false, false, true, true, null)
                            : new CommandResult(String.format(
                                    Messages.MESSAGE_INCOMPLETE_DEADLINE_DELETE_SUCCESS, itemToDelete),
                                    false, false, true, true, null);


                } catch (ClassCastException e) {
                    return new CommandResult(Messages.MESSAGE_EVENT_NOT_SELECTED);
                } catch (NullPointerException e) {
                    return new CommandResult(Messages.MESSAGE_EVENT_NOT_SELECTED);
                } catch (IndexOutOfBoundsException e) {
                    return new CommandResult(Messages.MESSAGE_INVALID_DELETE_INDEX);
                }
            }
        } else {
            Object[] list = model.getFilteredDisplayableList(predicate);
            int numberOfItemsDeleted = 0;
            if (displayableType == DisplayableType.EVENT) {
                for (Object eventObject : list) {
                    unsetFocusDisplayableIfEqual(model, (Displayable) eventObject);
                    numberOfItemsDeleted++;
                    model.deleteEvent((Event) eventObject);
                }
                if (predicate.toString().equals(predicateStringForDeleteAll)) {
                    return new CommandResult(String.format(Messages.MESSAGE_DELETE_ALL_EVENTS_SUCCESS,
                            numberOfItemsDeleted),
                            false, false, true, true, null);
                } else {
                    return new CommandResult(String.format(
                            Messages.MESSAGE_MUTIPLE_EVENTS_DELETE_SUCCESS, numberOfItemsDeleted, predicate.toString()),
                            false, false, true, true, null);
                }
            } else if (displayableType == DisplayableType.MODULE) {
                for (Object moduleObject : list) {
                    unsetFocusDisplayableIfEqual(model, (Displayable) moduleObject);
                    numberOfItemsDeleted++;
                    Module module = (Module) moduleObject;
                    deleteEventsOfModule(model, module);
                    model.deleteModule(module);
                }
                if (predicate.toString().equals(predicateStringForDeleteAll)) {
                    model.unsetFocusedDisplayable();
                    return new CommandResult(String.format(Messages.MESSAGE_DELETE_ALL_MODULES_SUCCESS,
                            numberOfItemsDeleted),
                            false, false, true, true, null);
                }
                return new CommandResult(String.format(
                        Messages.MESSAGE_MUTIPLE_MODULES_DELETE_SUCCESS, numberOfItemsDeleted,
                        predicate.toString()), false, false, true, true, null);
            }
        }
        return new CommandResult(Messages.MESSAGE_ERROR);
    }

    /**
     * Deletes the events inside a module when the module is deleted.
     *
     * @param model  Original Model object.
     * @param module Module that contains the events to be deleted.
     */
    private void deleteEventsOfModule(Model model, Module module) {
        int count = 0;
        while (count < model.getFilteredEventList().size()) {

            Event event = model.getFilteredEventList().get(count);
            if (event.getParentModule().equals(((Module) module))) {
                model.deleteEvent(event);
            } else {
                count++;
            }
        }
    }

    private void unsetFocusDisplayableIfEqual(Model model, Displayable displayable) {
        if (model.getFocusedDisplayable() != null && model.isSameFocusedDisplayable(displayable)) {
            model.unsetFocusedDisplayable();
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteCommand) other).targetIndex)); // state check
    }
}
