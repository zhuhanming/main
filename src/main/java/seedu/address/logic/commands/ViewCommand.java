package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import javafx.collections.transformation.FilteredList;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.SameItemPredicate;
import seedu.address.model.Displayable;
import seedu.address.model.Model;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.core.Messages;

import java.util.Arrays;

/**
 * Allows the user to view details of a specific event / module in the list
 */
public class ViewCommand extends Command {
    private Index index;

    public static final String COMMAND_WORD = "view";

    public static final String MESSAGE_VIEW_SUCCESS = "Here you go";

    public ViewCommand(Index index) {
        this.index = index;
    }
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        FilteredList<Displayable> lastShownList = (FilteredList<Displayable>) model.getFilteredFocusedList();

        if (index.getZeroBased() >= lastShownList.size()) {
             throw new CommandException(Messages.MESSAGE_INVALID_VIEW_DISPLAYED_INDEX);
        }

        Displayable itemToView = lastShownList.get(index.getZeroBased());

        String itemToViewString = itemToView.toString();
        System.out.println("ITEM IS " + itemToViewString);
        SameItemPredicate sameItemPredicate = new SameItemPredicate(Arrays.asList(itemToViewString.trim()));

        model.setFocusedDisplayable(itemToView);
        model.updateFilteredDisplayableList(sameItemPredicate);

        return new CommandResult(MESSAGE_VIEW_SUCCESS);
    }
}
