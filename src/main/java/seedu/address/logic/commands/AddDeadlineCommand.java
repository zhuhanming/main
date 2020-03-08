package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.entity.Deadline;
import seedu.address.model.entity.Event;
import seedu.address.model.entity.Module;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.*;

public class AddDeadlineCommand extends Command {

    public static final String COMMAND_WORD = "deadline";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a deadline to the calendar. "
            + "Parameters: "
            + PREFIX_MODULE + "MODULE "
            + PREFIX_EVENT + "EVENT "
            + PREFIX_NAME + "NAME "
            + "[" + PREFIX_REPEAT + "YES/NO]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_MODULE + "CS2103 "
            + PREFIX_EVENT + "Tutorial 3 "
            + PREFIX_NAME + "Complete tutorial questions ";

    public static final String MESSAGE_SUCCESS = "New deadline added: %1$s";
    public static final String MESSAGE_DUPLICATE_DEADLINE = "This deadline already exists in the calendar";
    public static final String MESSAGE_EVENT_DOESNT_EXIST = "The specified event does not exist in the calendar";

    private final Deadline toAdd;
    private final Event parentEvent;
    private final Module module;


    /**
     * Creates an AddDeadlineCommand to add the specified {@code Deadline}
     */
    public AddDeadlineCommand(Deadline deadline) {
        requireNonNull(deadline);
        toAdd = deadline;
        parentEvent = null;
        module = null;
    }

    public AddDeadlineCommand(Deadline deadline, Event event) {
        requireNonNull(deadline);
        toAdd = deadline;
        parentEvent = event;
        module = event.getModule();
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasCalendarItem(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_DEADLINE);
        }

        Event event = (Event) model.findCalendarItem(toAdd.getParentEvent());

        if (event == null) {
            throw new CommandException(MESSAGE_EVENT_DOESNT_EXIST);
        }
        toAdd.setParentEvent(event);
        toAdd.setDueTime();
        model.addCalendarItem(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddDeadlineCommand // instanceof handles nulls
                && toAdd.equals(((AddDeadlineCommand) other).toAdd));
    }
}
