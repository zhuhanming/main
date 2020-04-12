package modulo.logic.commands;

import static java.util.Objects.requireNonNull;
import static modulo.commons.core.Messages.MESSAGE_SHOWING_EVENTS;
import static modulo.commons.core.Messages.MESSAGE_SHOWING_MODULES;

import modulo.model.Model;
import modulo.model.displayable.DisplayableType;

/**
 * Displays the specified list to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists out the specified items.\n"
            + "Parameters: "
            + "events/modules "
            + "\n"
            + "Example: " + COMMAND_WORD + " events";

    private DisplayableType displayableType;

    /**
     * Creates a {@code ListCommand} that lists out the items of the given displayable type.
     *
     * @param displayableType Type of item to display.
     */
    public ListCommand(DisplayableType displayableType) {
        this.displayableType = displayableType;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setFilteredFocusedList(displayableType);
        return new CommandResult(displayableType == DisplayableType.MODULE ? MESSAGE_SHOWING_MODULES
                : MESSAGE_SHOWING_EVENTS, false, false, true, false, null, null);
    }


    /**
     * Compares if another object is equal to this ListCommand.
     *
     * @param other another object to be compared to this ListCommand.
     * @return true if the object is equal to this ViewCommand, false otherwise.
     */
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || ((other instanceof ListCommand // instanceof handles nulls
                && ((ListCommand) other).displayableType == this.displayableType));
    }
}
