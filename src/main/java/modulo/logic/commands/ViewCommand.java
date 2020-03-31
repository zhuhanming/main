package modulo.logic.commands;

import static java.util.Objects.requireNonNull;

import javafx.collections.ObservableList;
import modulo.commons.core.Messages;
import modulo.commons.core.index.Index;
import modulo.logic.commands.exceptions.CommandException;
import modulo.model.Displayable;
import modulo.model.Model;

/**
 * Allows the user to view details of a specific event / module in the list
 */
public class ViewCommand extends Command {

    public static final String COMMAND_WORD = "view";
    public static final String MESSAGE_VIEW_SUCCESS = "%1$s \n is in view!";
    private Index index;

    public ViewCommand(Index index) {
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        ObservableList<?> lastShownList = model.getFilteredFocusedList();
        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_VIEW_DISPLAYED_INDEX);
        }

        Displayable itemToView = (Displayable) lastShownList.get(index.getZeroBased());
        model.setFocusedDisplayable(itemToView);
        return new CommandResult(String.format(MESSAGE_VIEW_SUCCESS, itemToView), index);
    }
}
