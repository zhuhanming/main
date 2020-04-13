package modulo.logic.commands;

import static java.util.Objects.requireNonNull;
import static modulo.commons.core.Messages.MESSAGE_CANNOT_COMPLETE_EVENT;
import static modulo.commons.core.Messages.MESSAGE_COMPLETED_DEADLINE;
import static modulo.commons.core.Messages.MESSAGE_DEADLINE_DOES_NOT_EXIST;
import static modulo.commons.core.Messages.MESSAGE_EVENT_DOES_NOT_EXIST;
import static modulo.commons.core.Messages.MESSAGE_INVALID_DONE_INDEX;
import static modulo.commons.core.Messages.MESSAGE_MODULE_DOES_NOT_EXIST;
import static modulo.commons.core.Messages.MESSAGE_UNCOMPLETED_DEADLINE;
import static modulo.commons.util.CollectionUtil.requireAllNonNull;
import static modulo.logic.parser.CliSyntax.PREFIX_EVENT;
import static modulo.logic.parser.CliSyntax.PREFIX_MODULE;

import java.util.List;

import modulo.commons.core.Messages;
import modulo.commons.core.index.Index;
import modulo.logic.commands.exceptions.CommandException;
import modulo.model.Model;
import modulo.model.deadline.Deadline;
import modulo.model.displayable.Displayable;
import modulo.model.event.Event;
import modulo.model.module.Module;

/**
 * Marks a deadline as complete or incomplete.
 */
public class DoneCommand extends Command {

    public static final String COMMAND_WORD = "done";


    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Complete a deadline.\n"
            + "Parameters: "
            + "INDEX "
            + PREFIX_MODULE + "MODULE_CODE "
            + PREFIX_EVENT + "EVENT_NAME"
            + "\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_MODULE + "CS2103 "
            + PREFIX_EVENT + "Tutorial 2";

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
                return returnResult_focusedDisplayableEvent(focusedDisplayable);
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
            return new CommandResult(String.format(isDeadlineOriginallyComplete ? MESSAGE_UNCOMPLETED_DEADLINE
                    : MESSAGE_COMPLETED_DEADLINE, deadlineToComplete), false, false, true, true, null, null);
        } catch (IndexOutOfBoundsException e) {
            throw new CommandException(MESSAGE_DEADLINE_DOES_NOT_EXIST);
        }
    }


    /**
     * Returns a CommandResult should the deadline be within range when the focusedDispisplayable is an event,
     * otherwise throws a CommandException.
     *
     * @param displayable The current displayable item in focus.
     * @return CommandResult should the deadline be completable.
     * @throws CommandException Should the index of the deadline be out of range.
     */
    public CommandResult returnResult_focusedDisplayableEvent(Displayable displayable) throws CommandException {
        Deadline deadlineToComplete;
        try {
            deadlineToComplete = ((Event) displayable).getDeadlines().get(index.getZeroBased());
        } catch (IndexOutOfBoundsException e) {
            throw new CommandException(MESSAGE_INVALID_DONE_INDEX);
        }
        boolean isDeadlineOriginallyComplete = deadlineToComplete.isCompleted();
        deadlineToComplete.setCompleted(!isDeadlineOriginallyComplete);
        return new CommandResult(String.format(isDeadlineOriginallyComplete ? MESSAGE_UNCOMPLETED_DEADLINE
                : MESSAGE_COMPLETED_DEADLINE, deadlineToComplete), false, false, true, true, null, null);
    }


    @Override
    public boolean equals(Object other) {
        if (this.toCheckEvent == null && ((DoneCommand) other).toCheckEvent == null
                && this.toCheckModule == null && ((DoneCommand) other).toCheckModule == null) {
            return this.index.getZeroBased() == ((DoneCommand) other).index.getZeroBased();
        }
        return other == this // short circuit if same object
                || ((this.toCheckEvent.equals(((DoneCommand) other).toCheckEvent))
                && (this.toCheckModule.equals(((DoneCommand) other).toCheckModule))
                && (this.index.getZeroBased() == ((DoneCommand) other).index.getZeroBased()));
    }
}
