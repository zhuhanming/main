package modulo.logic.commands;

import static java.util.Objects.requireNonNull;

import modulo.model.DisplayableType;
import modulo.model.Model;

/**
 * Lists all persons in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";
    public static final String MODULE_MESSAGE_SUCCESS = "Showing Modules!";
    public static final String EVENT_MESSAGE_SUCCESS = "Showing Events!";
    public static final String MODULE_SHOW_ALL_MESSAGE_SUCCESS = "Showing All Modules!";
    public static final String EVENT_SHOW_ALL_MESSAGE_SUCCESS = "Showing All Events!";
    private DisplayableType displayableType;
    private boolean toShowAll;

    public ListCommand(DisplayableType displayableType, boolean toShowAll) {
        this.displayableType = displayableType;
        this.toShowAll = toShowAll;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        if (toShowAll) {
            model.setFilteredFocusedListShowAll(displayableType);
            return new CommandResult(displayableType == DisplayableType.MODULE ? MODULE_SHOW_ALL_MESSAGE_SUCCESS
                    : EVENT_SHOW_ALL_MESSAGE_SUCCESS, false, false, true, false, null);
        } else {
            model.setFilteredFocusedList(displayableType);
            return new CommandResult(displayableType == DisplayableType.MODULE ? MODULE_MESSAGE_SUCCESS
                    : EVENT_MESSAGE_SUCCESS, false, false, true, false, null);
        }

    }
}
