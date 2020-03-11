package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.entity.CalendarItem;
import seedu.address.model.entity.Deadline;
import seedu.address.model.entity.Event;
import seedu.address.model.entity.Module;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.*;

public class AddDeadlineCommand extends Command {

    public static final String COMMAND_WORD = "deadline";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a deadline to the calendar. "
            + "Parameters: "
            + PREFIX_MODULE + "MODULE "
            + PREFIX_EVENT + "EVENT_NAME "
            + PREFIX_NAME + "NAME "
            + "[" + PREFIX_REPEAT + "YES/NO]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_MODULE + "CS2103 "
            + PREFIX_EVENT + "Tutorial "
            + PREFIX_NAME + "Complete tutorial questions "
            + PREFIX_REPEAT + "YES";

    public static final String MESSAGE_SUCCESS = "New deadline added: %1$s";
    public static final String MESSAGE_DUPLICATE_DEADLINE = "This deadline already exists in the calendar";
    public static final String MESSAGE_EVENT_DOESNT_EXIST = "The specified event does not exist in the calendar";

    private final Deadline toAdd;
    private final Event parentEvent;
    private final boolean isRepeated;


    /**
     * Creates an AddDeadlineCommand to add the specified {@code Deadline}
     */
    public AddDeadlineCommand(Deadline deadline, boolean isRepeated) {
        requireNonNull(deadline);
        this.toAdd = deadline;
        this.parentEvent = null;
        this.isRepeated = isRepeated;
    }

    public AddDeadlineCommand(Deadline deadline, Event event, boolean isRepeated) {
        requireNonNull(deadline);
        this.toAdd = deadline;
        this.parentEvent = event;
        this.isRepeated = isRepeated;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        System.out.println(toAdd.getParentEvent().toDebugString());

        requireNonNull(model);

        if (model.hasCalendarItem(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_DEADLINE);
        }
        System.out.println(toAdd.getParentEvent().getCalendarItemType());
        List<Event> events = model.findAllEvents(toAdd.getParentEvent());
        System.out.println(events.size());
        if (events.size() == 0) {
            throw new CommandException(MESSAGE_EVENT_DOESNT_EXIST);
        }

        Collections.sort(events, new Comparator<Event>() {
            public int compare(Event event1, Event event2) {
                return event1.getEventStart().isBefore(event2.getEventStart()) ? -1 : (event2.getEventStart().isBefore(event1.getEventStart()) ? 1 : 0);
            }
        });

        for (int i = 0; i < events.size(); i++) {
            if(!events.get(i).getIsOver()) {
                Deadline currentToAdd = new Deadline(toAdd.getDeadlineName());
                currentToAdd.setParentEvent(events.get(i));
                model.addCalendarItem(currentToAdd);
                System.out.println("Added: " + toAdd.toDebugString());
                if (!isRepeated) {
                    break;
                }
            }
        }

        System.out.println(model.checkCurrentCalendar());
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddDeadlineCommand // instanceof handles nulls
                && toAdd.equals(((AddDeadlineCommand) other).toAdd));
    }
}
