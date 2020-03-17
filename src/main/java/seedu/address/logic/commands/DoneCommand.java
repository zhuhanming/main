package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.deadline.Deadline;
import seedu.address.model.event.Event;
import seedu.address.model.module.Module;

/**
 * Set a deadline to be done
 */
public class DoneCommand extends Command {

    public static final String COMMAND_WORD = "done";


    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Complete a deadline "
            + "Parameters: "
            + "INDEX "
            + PREFIX_MODULE + "MODULE "
            + PREFIX_EVENT + "EVENT_NAME"
            + "\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_MODULE + "CS2103 "
            + PREFIX_EVENT + "Tutorial";

    public static final String MESSAGE_SUCCESS = "Completed the deadline: %1$s";
    public static final String MESSAGE_MODULE_DOES_NOT_EXIST = "The specified module does not exist in the calendar";
    public static final String MESSAGE_EVENT_DOES_NOT_EXIST = "The specified event does not exist in the module";
    public static final String MESSAGE_DEADLINE_DOES_NOT_EXIST = "The specified event does not have the indexed "
            + "deadline";
    private final Module toCheckModule;
    private final Event toCheckEvent;
    private final Index index;

    public DoneCommand(Module module, Event event, Index index) {
        requireAllNonNull(module, event, index);
        this.toCheckModule = module;
        this.toCheckEvent = event;
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasModule(toCheckModule.getModuleCode(), toCheckModule.getAcademicYear())) {
            throw new CommandException(MESSAGE_MODULE_DOES_NOT_EXIST);
        }

        if (!model.hasEvent(toCheckEvent)) {
            throw new CommandException(MESSAGE_EVENT_DOES_NOT_EXIST);
        }

        Event event = model.findEvent(toCheckEvent);
        Deadline deadlineToComplete;
        List<Deadline> deadlineList = event.getDeadlines();
        try {
            deadlineToComplete = deadlineList.get(index.getZeroBased());
            deadlineToComplete.setCompleted(true);
            return new CommandResult(String.format(MESSAGE_SUCCESS, deadlineToComplete));
        } catch (IndexOutOfBoundsException e) {
            throw new CommandException(MESSAGE_DEADLINE_DOES_NOT_EXIST);
        }
    }
}
