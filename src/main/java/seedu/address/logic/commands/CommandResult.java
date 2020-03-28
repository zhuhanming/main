package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Objects;

import seedu.address.model.Displayable;
import seedu.address.model.deadline.Deadline;
import seedu.address.model.event.Event;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    private final String feedbackToUser;

    /**
     * Help information should be shown to the user.
     */
    private final boolean showHelp;

    /**
     * The application should exit.
     */
    private final boolean exit;

    /**
     * Application should display a list of module events.
     */
    private final boolean showsEventList;

    /**
     * Application should display a list of modules.
     */
    private final boolean showsModuleList;

    private Displayable slideWindowEvent;

    private List<Deadline> deadlineList;

    private List<Event> eventList;

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser,
                         boolean showHelp, boolean exit, boolean showsEventList, boolean showsModuleList,
                         Displayable slideWindowEvent, List<Deadline> deadlineList, List<Event> eventList) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = showHelp;
        this.exit = exit;
        this.showsEventList = showsEventList;
        this.showsModuleList = showsModuleList;
        this.slideWindowEvent = slideWindowEvent;
        this.deadlineList = deadlineList;
        this.eventList = eventList;
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, false, false, false, false, null, null, null);
    }

    public String getFeedbackToUser() {
        return feedbackToUser;
    }

    public boolean isShowHelp() {
        return showHelp;
    }

    public boolean isExit() {
        return exit;
    }

    public boolean isEventList() {
        return showsEventList;
    }

    public boolean isModuleList() {
        return showsModuleList;
    }

    public Displayable getSlideWindowEvent() {
        return slideWindowEvent;
    }

    public List<Deadline> getSlideWindowDeadlineList() {
        return deadlineList;
    }

    public List<Event> getSlideWindowEventList() {
        return eventList;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CommandResult)) {
            return false;
        }

        CommandResult otherCommandResult = (CommandResult) other;
        return feedbackToUser.equals(otherCommandResult.feedbackToUser)
                && showHelp == otherCommandResult.showHelp
                && exit == otherCommandResult.exit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, showHelp, exit);
    }

}
