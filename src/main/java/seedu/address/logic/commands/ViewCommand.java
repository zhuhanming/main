package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Displayable;
import seedu.address.model.Model;
import seedu.address.model.deadline.Deadline;
import seedu.address.model.event.Event;
import seedu.address.model.module.Module;
import seedu.address.ui.DetailsWindow;
import seedu.address.ui.SlideWindowEvent;

/**
 * Allows the user to view details of a specific event / module in the list
 */
public class ViewCommand extends Command {

    public static final String COMMAND_WORD = "view";
    public static final String MESSAGE_VIEW_SUCCESS = "Here you go";
    private Index index;
    private SlideWindowEvent slideWindowEvent;
    private DetailsWindow detailsWindow;

    public ViewCommand(Index index) {
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        ObservableList<?> lastShownList = model.getFilteredFocusedList();
        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_VIEW_DISPLAYED_INDEX);
        }

        Displayable itemToView = (Displayable) lastShownList.get(index.getZeroBased());
        model.setFocusedDisplayable(itemToView);
        CommandResult commandResult;
        // start of view for right panel ;
        if (itemToView instanceof Module) {
            Module module = model.findModule((Module) itemToView);
            List<Event> eventList = module.getEvents();
            commandResult = returnCommResultBasedOnModuleSelected(module, eventList);
        } else {
            assert itemToView instanceof Event;
            Event event = (Event) itemToView;
            List<Deadline> deadlineList = event.getDeadlines();
            commandResult = returnCommResultBasedOnEventSelected(event, deadlineList);
        }
        return commandResult;
    }

    /**
     * Returns commandResult with a Deadline list and an Event attached together.
     *
     * @param event        Event to view.
     * @param deadlineList List of deadlines to view.
     * @return View command result.
     */
    private CommandResult returnCommResultBasedOnEventSelected(Event event, List<Deadline> deadlineList) {
        requireAllNonNull(event, deadlineList);
        return new CommandResult(MESSAGE_VIEW_SUCCESS, event, deadlineList, index);
    }

    /**
     * Returns commandResult with a Event list and a Module attached together.
     *
     * @param module    Module to view.
     * @param eventList List of Events to view.
     * @return View command result.
     */
    private CommandResult returnCommResultBasedOnModuleSelected(Module module, List<Event> eventList) {
        requireAllNonNull(module, eventList);
        return new CommandResult(MESSAGE_VIEW_SUCCESS, module, eventList, index);

    }
}
