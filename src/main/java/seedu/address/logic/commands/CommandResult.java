package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Objects;

import seedu.address.commons.core.index.Index;
import seedu.address.model.Displayable;
import seedu.address.model.deadline.Deadline;
import seedu.address.model.event.Event;
import seedu.address.model.module.Module;

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

    /**
     * Displayable to display on the right.
     */
    private final Displayable slideWindowEvent;

    /**
     * Deadline list to use for population of right panel
     */
    private final List<Deadline> deadlineList;

    /**
     * Event list to use for population of right panel.
     */
    private final List<Event> eventList;

    /**
     * Index to show for UI usage.
     */
    private final Index indexToShow;

    /**
     * Application should update visible deadline.
     */
    private final boolean isVisibleDeadlineChanged;

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser,
                         boolean showHelp, boolean exit, boolean showsEventList, boolean showsModuleList,
                         Displayable slideWindowEvent, List<Deadline> deadlineList, List<Event> eventList,
                         Index indexToShow, boolean isVisibleDeadlineChanged) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = showHelp;
        this.exit = exit;
        this.showsEventList = showsEventList;
        this.showsModuleList = showsModuleList;
        this.slideWindowEvent = slideWindowEvent;
        this.deadlineList = deadlineList;
        this.eventList = eventList;
        this.indexToShow = indexToShow;
        this.isVisibleDeadlineChanged = isVisibleDeadlineChanged;
    }

    /**
     * Constructs a {@code CommandResult} for Event Viewing.
     */
    public CommandResult(String feedbackToUser, Event event, List<Deadline> deadlines,
                         Index indexToShow) {
        this(feedbackToUser, false, false, true, false, event, deadlines, null, indexToShow, false);
    }

    /**
     * Constructs a {@code CommandResult} for Module Viewing.
     */
    public CommandResult(String feedbackToUser, Module module, List<Event> events,
                         Index indexToShow) {
        this(feedbackToUser, false, false, true, false, module, null, events, indexToShow, false);
    }

    /**
     * Constructs a default {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit, boolean isVisibleDeadlineChanged) {
        this(feedbackToUser, showHelp, exit, false, false, null, null, null, null, isVisibleDeadlineChanged);
    }

    /**
     * Constructs a default {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, boolean showsEventList, boolean showsModuleList) {
        this(feedbackToUser, false, false, showsEventList, showsModuleList, null, null, null, null, false);
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser}, and other fields set to their
     * default value.
     */
    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, false, false, false, false, null, null, null, null, false);
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

    public Index getIndexToShow() {
        return indexToShow;
    }

    public boolean isVisibleDeadlineChanged() {
        return isVisibleDeadlineChanged;
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
