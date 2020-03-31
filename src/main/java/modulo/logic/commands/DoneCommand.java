package modulo.logic.commands;

import static java.util.Objects.requireNonNull;
import static modulo.commons.util.CollectionUtil.requireAllNonNull;
import static modulo.logic.parser.CliSyntax.PREFIX_EVENT;
import static modulo.logic.parser.CliSyntax.PREFIX_MODULE;

import java.util.List;

import modulo.commons.core.Messages;
import modulo.commons.core.index.Index;
import modulo.logic.commands.exceptions.CommandException;
import modulo.model.Displayable;
import modulo.model.Model;
import modulo.model.deadline.Deadline;
import modulo.model.event.Event;
import modulo.model.module.Module;

/**
 * Set a deadline to be done
 */
public class DoneCommand extends Command {

    public static final String COMMAND_WORD = "done";


    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Complete a deadline. "
            + "Parameters: "
            + "(if viewing Event) "
            + "INDEX "
            + "Parameters: (else) "
            + "INDEX "
            + PREFIX_MODULE + "MODULE "
            + PREFIX_EVENT + "EVENT_NAME"
            + "\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_MODULE + "CS2103 "
            + PREFIX_EVENT + "Tutorial";

    public static final String MESSAGE_SUCCESS_COMPLETE = "Completed the deadline: %1$s";
    public static final String MESSAGE_SUCCESS_INCOMPLETE = "Marked this deadline incomplete: %1$s";
    public static final String MESSAGE_MODULE_DOES_NOT_EXIST = "The specified module does not exist in the calendar";
    public static final String MESSAGE_EVENT_DOES_NOT_EXIST = "The specified event does not exist in the module";
    public static final String MESSAGE_DEADLINE_DOES_NOT_EXIST = "The specified event does not have the indexed "
            + "deadline";
    public static final String MESSAGE_CANNOT_COMPLETE_EVENT = "You cannot complete an event!";
    private final Module toCheckModule;
    private final Event toCheckEvent;
    private final Index index;

    public DoneCommand(Module module, Event event, Index index) {
        requireAllNonNull(index);
        this.toCheckModule = module;
        this.toCheckEvent = event;
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Displayable focusedDisplayable = model.getFocusedDisplayable();
        Deadline deadlineToComplete;
        boolean isDeadlineOriginallyComplete;

        if (toCheckEvent == null && toCheckModule == null) {
            if (focusedDisplayable instanceof Event) {
                deadlineToComplete = ((Event) focusedDisplayable).getDeadlines().get(index.getZeroBased());
                isDeadlineOriginallyComplete = deadlineToComplete.isCompleted();
                deadlineToComplete.setCompleted(!isDeadlineOriginallyComplete);
                return new CommandResult(String.format(isDeadlineOriginallyComplete ? MESSAGE_SUCCESS_INCOMPLETE
                        : MESSAGE_SUCCESS_COMPLETE, deadlineToComplete), false, false, false, true, null);
            } else if (focusedDisplayable != null) {
                throw new CommandException(MESSAGE_CANNOT_COMPLETE_EVENT);
            } else {
                throw new CommandException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                        DoneCommand.MESSAGE_USAGE));
            }
        }


        if (!model.hasModule(toCheckModule.getModuleCode(), toCheckModule.getAcademicYear())) {
            throw new CommandException(MESSAGE_MODULE_DOES_NOT_EXIST);
        }

        if (!model.hasEvent(toCheckEvent)) {
            throw new CommandException(MESSAGE_EVENT_DOES_NOT_EXIST);
        }

        Event event = model.findEvent(toCheckEvent);
        List<Deadline> deadlineList = event.getDeadlines();
        try {
            deadlineToComplete = deadlineList.get(index.getZeroBased());
            isDeadlineOriginallyComplete = deadlineToComplete.isCompleted();
            deadlineToComplete.setCompleted(!isDeadlineOriginallyComplete);
            return new CommandResult(String.format(isDeadlineOriginallyComplete ? MESSAGE_SUCCESS_INCOMPLETE
                    : MESSAGE_SUCCESS_COMPLETE, deadlineToComplete), false, false, false, true, null);
        } catch (IndexOutOfBoundsException e) {
            throw new CommandException(MESSAGE_DEADLINE_DOES_NOT_EXIST);
        }
    }
}
