package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REPEAT;

import java.util.List;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.deadline.Deadline;
import seedu.address.model.event.Event;


/**
 * Adds a deadline to the address book.
 */
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
    private final boolean isRepeated;


    /**
     * Creates an AddDeadlineCommand to add the specified {@code Deadline}
     */
    public AddDeadlineCommand(Deadline deadline, boolean isRepeated) {
        requireNonNull(deadline);
        this.toAdd = deadline;
        this.isRepeated = isRepeated;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        System.out.println(toAdd.getParentEvent().toDebugString());

        requireNonNull(model);

        //if (model.hasEvent(toAdd)) {
        //    throw new CommandException(MESSAGE_DUPLICATE_DEADLINE);
        //}
        List<Event> events = model.findAllEvents(toAdd.getParentEvent());

        if (events.size() == 0) {
            throw new CommandException(MESSAGE_EVENT_DOESNT_EXIST);
        }

        events.sort((event1, event2) -> event1.getEventStart().isBefore(event2.getEventStart())
                ? -1 : (event2.getEventStart().isBefore(event1.getEventStart()) ? 1 : 0));

        for (Event event : events) {
            if (!event.getIsOver()) {
                Deadline currentToAdd = new Deadline(toAdd.getName(), event);
                event.addDeadline(currentToAdd);
                // model.addCalendarItem(currentToAdd);
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
