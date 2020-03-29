package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.DisplayableType;
import seedu.address.model.Model;

/**
 * Lists all persons in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";
    public static final String MODULE_MESSAGE_SUCCESS = "Showing Modules!";
    public static final String EVENT_MESSAGE_SUCCESS = "Showing Events!";
    private DisplayableType displayableType;

    public ListCommand(DisplayableType displayableType) {
        this.displayableType = displayableType;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setFilteredFocusedList(displayableType);
        return new CommandResult(displayableType == DisplayableType.MODULE ? MODULE_MESSAGE_SUCCESS
                : EVENT_MESSAGE_SUCCESS, false, false, true, false, null);
    }
}
