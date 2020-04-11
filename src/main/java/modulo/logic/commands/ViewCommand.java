package modulo.logic.commands;

import static java.util.Objects.requireNonNull;
import static modulo.commons.core.Messages.MESSAGE_CLEARED_VIEW;
import static modulo.commons.core.Messages.MESSAGE_INVALID_VIEW_INDEX;
import static modulo.commons.core.Messages.MESSAGE_VIEW_ITEM_SUCCESS;

import javafx.collections.ObservableList;
import modulo.commons.core.index.Index;
import modulo.logic.commands.exceptions.CommandException;
import modulo.model.Model;
import modulo.model.displayable.Displayable;

/**
 * Allows the user to view details of a specific event / module in the list.
 */
public class ViewCommand extends Command {

    public static final String COMMAND_WORD = "view";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": View a module or event. "
            + "Parameters: "
            + "INDEX "
            + "\n"
            + "Example: " + COMMAND_WORD + " 1 ";

    private Index index;

    /**
     * Creates a {@code ViewCommand} that sets the item at the specified index into view.
     *
     * @param index Index of item to view.
     */
    public ViewCommand(Index index) {
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (index == null) {
            model.unsetFocusedDisplayable();
            return new CommandResult(MESSAGE_CLEARED_VIEW, false, false, true, true, null, null);
        }

        ObservableList<?> lastShownList = model.getFilteredFocusedList();
        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(MESSAGE_INVALID_VIEW_INDEX);
        }

        Displayable itemToView = (Displayable) lastShownList.get(index.getZeroBased());
        model.setFocusedDisplayable(itemToView);
        return new CommandResult(String.format(MESSAGE_VIEW_ITEM_SUCCESS, itemToView), index);
    }

    /**
     * Compares if another object is equal to this ViewCommand.
     *
     * @param other another object to be compared to this ViewCommand.
     * @return true if the object is equal to this ViewCommand, false otherwise.
     */
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ViewCommand // instanceof handles nulls
                && ((ViewCommand) other).index.getZeroBased() == this.index.getZeroBased());
    }
}
