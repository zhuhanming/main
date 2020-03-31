package modulo.logic.commands;

import static java.util.Objects.requireNonNull;

import modulo.model.predicate.NameContainsKeywordsPredicate;
import modulo.commons.core.Messages;
import modulo.model.DisplayableType;
import modulo.model.Model;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all modules / event whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " CS";

    private final NameContainsKeywordsPredicate predicate;

    public FindCommand(NameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredDisplayableList(predicate);

        if (model.getCurrentDisplayableType() == DisplayableType.EVENT) {
            return new CommandResult(
                    String.format(Messages.MESSAGE_EVENT_LISTED_OVERVIEW, model.getFilteredFocusedList().size()),
                    false, false, true, true, null);
        } else if (model.getCurrentDisplayableType() == DisplayableType.MODULE) {
            return new CommandResult(
                    String.format(Messages.MESSAGE_MODULE_LISTED_OVERVIEW, model.getFilteredFocusedList().size()),
                    false, false, true, true, null);
        } else {
            return new CommandResult(
                    String.format(Messages.MESSAGE_ITEM_LISTED_OVERVIEW, model.getFilteredFocusedList().size()),
                    false, false, true, true, null);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && predicate.equals(((FindCommand) other).predicate)); // state check
    }
}
