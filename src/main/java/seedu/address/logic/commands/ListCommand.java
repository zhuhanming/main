package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.DisplayableType;
import seedu.address.model.Model;

/**
 * Lists all persons in the address book to the user.
 */
public class ListCommand extends Command {
    private DisplayableType displayableType;

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all Module Events";

    public ListCommand(DisplayableType displayableType) {
        this.displayableType = displayableType;
    }
    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        System.out.println(displayableType.toString());
        model.setFilteredFocusedList(displayableType);
        //model.updateFilteredEventList(PREDICATE_SHOW_ALL_EVENTS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
