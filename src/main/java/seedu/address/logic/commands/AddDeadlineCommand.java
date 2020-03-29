package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REPEAT;

import java.util.List;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Displayable;
import seedu.address.model.Model;
import seedu.address.model.Name;
import seedu.address.model.deadline.Deadline;
import seedu.address.model.event.Event;
import seedu.address.model.module.Module;


/**
 * Adds a deadline to the address book.
 */
public class AddDeadlineCommand extends Command {

    public static final String COMMAND_WORD = "deadline";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a deadline to the calendar. "
            + "Parameters: "
            + "(if viewing Event) "
            + PREFIX_NAME + "NAME "
            + "[" + PREFIX_REPEAT + "YES/NO] "
            + "Parameters: (else) "
            + PREFIX_MODULE + "MODULE "
            + PREFIX_EVENT + "EVENT_NAME "
            + PREFIX_NAME + "NAME "
            + "[" + PREFIX_REPEAT + "YES/NO]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_MODULE + "CS2103 "
            + PREFIX_EVENT + "Tutorial "
            + PREFIX_NAME + "Complete tutorial questions "
            + PREFIX_REPEAT + "YES";

    public static final String MESSAGE_SUCCESS = "New deadline added: %1$s";
    public static final String MESSAGE_DUPLICATE_DEADLINE = "This deadline already exists in the calendar!";
    public static final String MESSAGE_EVENT_DOESNT_EXIST = "The specified event does not exist in the calendar!";
    public static final String MESSAGE_CANNOT_ADD_TO_MODULE = "You cannot add deadlines to modules!";

    private final Deadline toAdd;
    private final Event parentEvent;
    private final boolean isRepeated;
    private final Name name;


    /**
     * Creates an AddDeadlineCommand to add the specified {@code Deadline}
     */
    public AddDeadlineCommand(Deadline deadline, Event parentEvent, boolean isRepeated) {
        requireNonNull(deadline);
        this.toAdd = deadline;
        this.parentEvent = parentEvent;
        this.isRepeated = isRepeated;
        this.name = null;
    }

    public AddDeadlineCommand(Name name, boolean isRepeated) {
        requireNonNull(name);
        this.name = name;
        this.isRepeated = isRepeated;
        this.toAdd = null;
        this.parentEvent = null;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Event parentEvent = this.parentEvent;
        Deadline toAdd = this.toAdd;

        if (parentEvent == null) {
            Displayable focusedDisplayable = model.getFocusedDisplayable();
            if (focusedDisplayable instanceof Module) {
                throw new CommandException(MESSAGE_CANNOT_ADD_TO_MODULE);
            }
            if (focusedDisplayable == null) {
                throw new CommandException(MESSAGE_EVENT_DOESNT_EXIST);
            }
            parentEvent = (Event) focusedDisplayable;
            toAdd = new Deadline(name, parentEvent);
            if (!isRepeated) {
                if (parentEvent.containsDeadline(toAdd)) {
                    throw new CommandException(MESSAGE_DUPLICATE_DEADLINE);
                }
                parentEvent.addDeadline(toAdd);
                return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd), false, false, false, true, null);
            }
        } else {
            if (!model.hasEvent(parentEvent)) {
                throw new CommandException(MESSAGE_EVENT_DOESNT_EXIST);
            }
            if (!isRepeated) {
                Event actualParentEvent = model.findEvent(parentEvent);
                if (actualParentEvent.containsDeadline(toAdd)) {
                    throw new CommandException(MESSAGE_DUPLICATE_DEADLINE);
                }
                Deadline actualDeadline = new Deadline(toAdd.getName(), actualParentEvent);
                actualParentEvent.addDeadline(actualDeadline);
                return new CommandResult(String.format(MESSAGE_SUCCESS, actualDeadline),
                        false, false, false, true, null);
            }
        }

        List<Event> events = model.findAllEvents(parentEvent);
        events.sort((event1, event2) -> event1.getEventStart().isBefore(event2.getEventStart())
                ? -1 : (event2.getEventStart().isBefore(event1.getEventStart()) ? 1 : 0));

        for (Event event : events) {
            if (!event.getIsOver() && !event.containsDeadline(toAdd)) {
                Deadline currentToAdd = new Deadline(toAdd.getName(), event);
                event.addDeadline(currentToAdd);
            }
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd), false, false, false, true, null);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddDeadlineCommand // instanceof handles nulls
                && toAdd.equals(((AddDeadlineCommand) other).toAdd));
    }
}
