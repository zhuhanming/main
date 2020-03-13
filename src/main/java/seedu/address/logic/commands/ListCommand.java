package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_CALENDAR_ITEMS;

/**
 * Lists all persons in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all Module Events";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredCalendarItemList(PREDICATE_SHOW_ALL_CALENDAR_ITEMS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
