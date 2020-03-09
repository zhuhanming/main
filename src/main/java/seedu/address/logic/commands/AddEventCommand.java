package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.entity.Deadline;
import seedu.address.model.entity.Event;
import seedu.address.model.entity.Module;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.*;

public class AddEventCommand extends Command {
    public static final String COMMAND_WORD = "event";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a event to the calendar. "
            + "Parameters: "
            + PREFIX_MODULE + "MODULE "
            + PREFIX_NAME + "Event Type "
            + PREFIX_START_DATE + "Event Start Date"
            + PREFIX_DURATION + "Event Duration "
            + PREFIX_REPEAT + "Is Event Repeated?"
            + "[" + PREFIX_REPEAT + "YES/NO]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_MODULE + "CS2103 "
            + PREFIX_NAME + "Tutorial 3 "
            + PREFIX_START_DATE + "Tue 8:30 "
            + PREFIX_DURATION + "2 hours "
            + PREFIX_REPEAT + "Yes ";

    public static final String MESSAGE_SUCCESS = "New event added: %1$s";
    public static final String MESSAGE_DUPLICATE_EVENT = "This event in this module is already exists in the calendar";
    public static final String MESSAGE_MODULE_DOESNT_EXIST = "The specified module does not exist in the calendar";

    private final Event toAdd;
    private final Module parentModule;
    private final Deadline deadline;


    public AddEventCommand(Event event) {
        requireNonNull(event);
        toAdd = event;
        parentModule = null;
        deadline = null;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasCalendarItem(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_EVENT);
        }

        System.out.println("module +++++    "+ toAdd.getParentModule().getModuleName());
        Module module = (Module) model.findCalendarItem(toAdd.getParentModule());
        System.out.println("module retrieve "+module);
        if (module == null) {
            throw new CommandException(MESSAGE_MODULE_DOESNT_EXIST);
        }
        // need to check is same event already exists?
        module.addEvents(toAdd);
        toAdd.setParentModule(module);
//        toAdd.setDueTime();
        model.addCalendarItem(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddEventCommand // instanceof handles nulls
                && toAdd.equals(((AddEventCommand) other).toAdd));
    }

}
