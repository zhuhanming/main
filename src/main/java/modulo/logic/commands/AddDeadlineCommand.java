package modulo.logic.commands;

import static java.util.Objects.requireNonNull;
import static modulo.logic.parser.CliSyntax.PREFIX_EVENT;
import static modulo.logic.parser.CliSyntax.PREFIX_MODULE;
import static modulo.logic.parser.CliSyntax.PREFIX_NAME;
import static modulo.logic.parser.CliSyntax.PREFIX_REPEAT;

import java.util.List;

import modulo.logic.commands.exceptions.CommandException;
import modulo.model.Displayable;
import modulo.model.Model;
import modulo.model.Name;
import modulo.model.deadline.Deadline;
import modulo.model.event.Event;
import modulo.model.module.Module;


/**
 * Adds a deadline to Modulo.
 */
public class AddDeadlineCommand extends Command {

    public static final String COMMAND_WORD = "deadline";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a deadline to Modulo. "
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
    public static final String MESSAGE_DUPLICATE_DEADLINE = "This deadline already exists in Modulo!";
    public static final String MESSAGE_EVENT_DOESNT_EXIST = "The specified event does not exist in Modulo!";
    public static final String MESSAGE_CANNOT_ADD_TO_MODULE = "You cannot add deadlines to modules!";

    private final Event parentEvent;
    private final boolean isRepeated;
    private final Name name;


    /**
     * Creates an AddDeadlineCommand to add a deadline to a specified {@code Event}.
     *
     * @param name        Name of deadline to add.
     * @param parentEvent Event that the deadline belongs to.
     * @param isRepeated  Whether the deadline should repeat.
     */
    public AddDeadlineCommand(Name name, Event parentEvent, boolean isRepeated) {
        requireNonNull(name);
        this.name = name;
        this.parentEvent = parentEvent;
        this.isRepeated = isRepeated;
    }

    /**
     * Creates an AddDeadlineCommand to add a deadline to the focused Event.
     *
     * @param name       Name of the deadline.
     * @param isRepeated Whether the deadline should repeat.
     */
    public AddDeadlineCommand(Name name, boolean isRepeated) {
        requireNonNull(name);
        this.name = name;
        this.isRepeated = isRepeated;
        this.parentEvent = null;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Deadline toAdd;

        // Adding the deadline to the focused event.
        if (parentEvent == null) {
            Displayable focusedDisplayable = model.getFocusedDisplayable();

            if (focusedDisplayable instanceof Module) {
                throw new CommandException(MESSAGE_CANNOT_ADD_TO_MODULE);
            }

            if (focusedDisplayable == null) {
                throw new CommandException(MESSAGE_EVENT_DOESNT_EXIST);
            }

            Event parentEvent = (Event) focusedDisplayable;
            toAdd = new Deadline(name, parentEvent);

            if (!isRepeated) {
                if (parentEvent.containsDeadline(toAdd)) {
                    throw new CommandException(MESSAGE_DUPLICATE_DEADLINE);
                }
                parentEvent.addDeadline(toAdd);
                return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd), false, false, false, true, null);
            }
        } else {
            // Add deadline to a referenced Event.
            if (!model.hasEvent(parentEvent)) {
                throw new CommandException(MESSAGE_EVENT_DOESNT_EXIST);
            }
            if (!isRepeated) {
                Event actualParentEvent = model.findEvent(parentEvent);
                toAdd = new Deadline(name, actualParentEvent);
                if (actualParentEvent.containsDeadline(toAdd)) {
                    throw new CommandException(MESSAGE_DUPLICATE_DEADLINE);
                }
                actualParentEvent.addDeadline(toAdd);
                return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd), false, false, false, true, null);
            }
        }

        List<Event> events = model.findAllEvents(parentEvent);
        Deadline referenceDeadline = new Deadline(name, parentEvent);

        for (Event event : events) {
            if (!event.getIsOver() && !event.containsDeadline(referenceDeadline)) {
                Deadline currentToAdd = new Deadline(name, event);
                event.addDeadline(currentToAdd);
            }
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, referenceDeadline), false, false, false, true, null);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddDeadlineCommand // instanceof handles nulls
                && name.equals(((AddDeadlineCommand) other).name)
                && isRepeated == ((AddDeadlineCommand) other).isRepeated);
    }
}
