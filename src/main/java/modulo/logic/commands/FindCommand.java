package modulo.logic.commands;

import static java.util.Objects.requireNonNull;
import static modulo.commons.core.Messages.MESSAGE_EVENT_LISTED_OVERVIEW;
import static modulo.commons.core.Messages.MESSAGE_MODULE_LISTED_OVERVIEW;
import static modulo.commons.core.Messages.MESSAGE_ZERO_ITEMS_LISTED;

import modulo.logic.predicate.NameContainsKeywordsPredicate;
import modulo.model.Model;
import modulo.model.displayable.DisplayableType;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords. Keyword matching is
 * case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all modules / events whose names"
            + "\ncontain any of the specified keywords.\n"
            + "Example: " + COMMAND_WORD + " CS";
    public static final String RETURN_TO_ORIGINAL_LIST_INSTRUCTION = "\nTo return to the original list view, try:\n"
            + "list events / list modules";

    private final NameContainsKeywordsPredicate predicate;

    public FindCommand(NameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredDisplayableList(predicate);

        if (model.getFilteredFocusedList().size() == 0) {
            return new CommandResult(
                    String.format(MESSAGE_ZERO_ITEMS_LISTED, predicate.toString())
                            + RETURN_TO_ORIGINAL_LIST_INSTRUCTION, false, false, true, true, null, null);
        }
        if (model.getCurrentDisplayableType() == DisplayableType.EVENT) {
            return new CommandResult(
                    String.format(MESSAGE_EVENT_LISTED_OVERVIEW, model.getFilteredFocusedList().size(),
                            predicate.toString()) + RETURN_TO_ORIGINAL_LIST_INSTRUCTION,
                    false, false, true, true, null, null);
        } else {
            return new CommandResult(
                    String.format(MESSAGE_MODULE_LISTED_OVERVIEW, model.getFilteredFocusedList().size(),
                            predicate.toString()) + RETURN_TO_ORIGINAL_LIST_INSTRUCTION,
                    false, false, true, true, null, null);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && predicate.equals(((FindCommand) other).predicate)); // state check
    }
}
