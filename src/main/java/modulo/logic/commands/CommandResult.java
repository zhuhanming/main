package modulo.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

import modulo.commons.core.index.Index;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    private final String feedbackToUser;

    /**
     * Help information should be shown to the user.
     */
    private final boolean toShowHelp;

    /**
     * The application should exit.
     */
    private final boolean toExit;

    /**
     * Application should display a list of module events.
     */
    private final boolean toUpdateLeftPanel;

    /**
     * Application should refresh side panel.
     */
    private final boolean toUpdateRightPanel;

    /**
     * Index to show for UI usage.
     */
    private final Index indexToShow;


    /**
     * Constructs a {@code CommandResult} with all fields.
     *
     * @param feedbackToUser     Feedback to show to user.
     * @param toShowHelp         Whether the help panel should show.
     * @param toExit             Whether to exit the app.
     * @param toUpdateLeftPanel  Whether to update the left panel.
     * @param toUpdateRightPanel Whether to update the right panel.
     * @param indexToShow        The index to highlight for UI.
     */
    public CommandResult(String feedbackToUser, boolean toShowHelp, boolean toExit, boolean toUpdateLeftPanel,
                         boolean toUpdateRightPanel, Index indexToShow) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.toShowHelp = toShowHelp;
        this.toExit = toExit;
        this.toUpdateLeftPanel = toUpdateLeftPanel;
        this.toUpdateRightPanel = toUpdateRightPanel;
        this.indexToShow = indexToShow;
    }

    /**
     * Constructs a {@code CommandResult} for Viewing.
     *
     * @param feedbackToUser Feedback to show to the user.
     * @param indexToShow    Index to focus on.
     */
    public CommandResult(String feedbackToUser, Index indexToShow) {
        this(feedbackToUser, false, false, false, true, indexToShow);
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser}, and other fields set to their
     * default value.
     *
     * @param feedbackToUser Feedback to show to user.
     */
    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, false, false, false, false, null);
    }

    /**
     * Returns the feedback to show to user.
     *
     * @return Feedback to show to user.
     */
    public String getFeedbackToUser() {
        return feedbackToUser;
    }

    /**
     * Returns a boolean value for whether to show help.
     *
     * @return A boolean value for whether to show help.
     */
    public boolean isToShowHelp() {
        return toShowHelp;
    }

    /**
     * Returns a boolean value for whether to exit the application.
     *
     * @return A boolean value for whether to exit the application.
     */
    public boolean isToExit() {
        return toExit;
    }

    /**
     * Returns a boolean value for whether to update the left Ui panel.
     *
     * @return A boolean value for whether to update the left Ui panel.
     */
    public boolean isToUpdateLeftPanel() {
        return toUpdateLeftPanel;
    }

    /**
     * Returns a boolean value for whether to update the right Ui panel.
     *
     * @return A boolean value for whether to update the right Ui panel.
     */
    public boolean isToUpdateRightPanel() {
        return toUpdateRightPanel;
    }

    /**
     * Returns an Index to highlight for the left Ui panel.
     *
     * @return An Index to highlight for the left Ui panel.
     */
    public Index getIndexToShow() {
        return indexToShow;
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
                && toShowHelp == otherCommandResult.toShowHelp
                && toExit == otherCommandResult.toExit
                && toUpdateRightPanel == otherCommandResult.toUpdateRightPanel
                && toUpdateLeftPanel == otherCommandResult.toUpdateLeftPanel
                && indexToShow == (otherCommandResult.indexToShow);
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, toShowHelp, toExit, toUpdateLeftPanel, toUpdateRightPanel, indexToShow);
    }
}
