package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.Displayable;
import seedu.address.model.DisplayableType;
import seedu.address.model.Model;
import seedu.address.model.deadline.Deadline;
import seedu.address.model.event.Event;
import seedu.address.model.module.Module;
import seedu.address.model.predicate.SameItemPredicate;
import seedu.address.ui.SlideWindowDeadlineList;
import seedu.address.ui.SlideWindowEvent;

/**
 * Allows the user to view details of a specific event / module in the list
 */
public class ViewCommand extends Command {

    public static final String COMMAND_WORD = "view";
    public static final String MESSAGE_VIEW_SUCCESS = "Here you go";
    private Index index;
    private SlideWindowEvent slideWindowEvent;
    private SlideWindowDeadlineList slideWindowDeadlineList;

    public ViewCommand(Index index) {

        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        @SuppressWarnings("unchecked")
        FilteredList<Displayable> lastShownList = (FilteredList<Displayable>) model.getFilteredFocusedList();
        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_VIEW_DISPLAYED_INDEX);
        }

        Displayable itemToView = lastShownList.get(index.getZeroBased());

        String itemToViewString = itemToView.toString();
        System.out.println("item to view " + itemToViewString);
        SameItemPredicate sameItemPredicate = new SameItemPredicate(
                Arrays.asList(itemToViewString.trim()));
        model.setFocusedDisplayable(itemToView);
        model.updateFilteredDisplayableList(sameItemPredicate);

        CommandResult commandResult;
        // start of view for right panel ;
        DisplayableType displayableType = ParserUtil.parseDisplayableTypeRightPanel(itemToViewString);
        if (displayableType.toString().equals("MODULE")) {
            Module module = model.findModule((Module) itemToView);
            List<Event> eventList = module.getEvents();
            commandResult = returnCommResultBasedOnModuleSelected(itemToView, eventList);
        } else {
            Event event = (Event) itemToView;
            List<Event> eventList = model.findAllEvents(event);
            List<Deadline> deadlineList = new ArrayList<Deadline>();
            for (int i = 0; i < eventList.size(); i++) {
                if (eventList.get(i).getEventType().equals(event.getEventType())) {
                    deadlineList.add(eventList.get(i).getDeadlines().get(0));
                }
            }
            commandResult = returnCommResultBasedOnEventSelected(itemToView, deadlineList);
        }


        return commandResult;
    }

    /**
     * @param displayable
     * @return
     */
    private CommandResult returnCommResultBasedOnEventSelected(Displayable displayable, List<Deadline> deadlineList) {
        if (displayable != null) {
            System.out.println("displayable is in return ... " + displayable.toString());
            return new CommandResult(MESSAGE_VIEW_SUCCESS, false, false, true, false, displayable, deadlineList, null);
        } else {
            return new CommandResult(MESSAGE_VIEW_SUCCESS);
        }
    }

    private CommandResult returnCommResultBasedOnModuleSelected(Displayable displayable, List<Event> eventList) {
        if (displayable != null) {
            System.out.println("displayable is in return ... " + displayable.toString());
            return new CommandResult(MESSAGE_VIEW_SUCCESS, false, false, true, false, displayable, null, eventList);
        } else {
            return new CommandResult(MESSAGE_VIEW_SUCCESS);
        }
    }
}
