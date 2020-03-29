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
    public static final String MESSAGE_SUCCESS = "Lists";
    private DisplayableType displayableType;

    public ListCommand(DisplayableType displayableType) {
        this.displayableType = displayableType;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setFilteredFocusedList(displayableType);
        CommandResult commandResult = returnCommResultBasedOnType(displayableType);
        return commandResult;
    }

    /**
     * Returns a CommandResult based on the displayable type.
     *
     * @param displayableType the displayable type of the object.
     * @return a CommandResult based on the displayable type.
     */
    private CommandResult returnCommResultBasedOnType(DisplayableType displayableType) {
        if (displayableType == DisplayableType.EVENT) {
            return new CommandResult(EVENT_MESSAGE_SUCCESS, false, false, true, false, null, null, null, null);
        } else {
            assert displayableType == DisplayableType.MODULE;
            return new CommandResult(MODULE_MESSAGE_SUCCESS, false, false, false, true, null, null, null, null);
        }
    }
}
