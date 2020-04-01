package modulo.commons.core;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_EVENT_DISPLAYED_INDEX = "The event index provided is invalid";
    public static final String MESSAGE_INVALID_DEADLINE_DISPLAYED_INDEX = "The deadline index provided is invalid";
    public static final String MESSAGE_INVALID_VIEW_DISPLAYED_INDEX = "The index you want to view is invalid";
    public static final String MESSAGE_INVALID_DELETE_INDEX = "The index you want to delete is invalid";
    public static final String MESSAGE_EVENT_LISTED_OVERVIEW = "%1$d events listed!";
    public static final String MESSAGE_MODULE_LISTED_OVERVIEW = "%1$d modules listed!";
    public static final String MESSAGE_ITEM_LISTED_OVERVIEW = "%1$d items listed!";
    public static final String MESSAGE_INVALID_DATE_RANGE = "Invalid datetime range! \n%1$s";
    public static final String MESSAGE_SINGLE_MODULE_DELETE_SUCCESS = "Deleted module: %1$s";
    public static final String MESSAGE_SINGLE_EVENT_DELETE_SUCCESS = "Deleted module: %1$s";
    public static final String MESSAGE_MUTIPLE_MODULES_DELETE_SUCCESS = "%1$s Modules containing: "
            + "%2$s has been deleted";
    public static final String MESSAGE_MUTIPLE_EVENTS_DELETE_SUCCESS = "%1$s Events containing: %2$s has been deleted";
    public static final String MESSAGE_ERROR = "Error please click the 'Help' button for more info";
    public static final String MESSAGE_DELETE_ALL_MODULES_SUCCESS = "Deleted all %1$s modules!";
    public static final String MESSAGE_DELETE_ALL_EVENTS_SUCCESS = "Deleted all %1$s events!";
    public static final String MESSAGE_COMPLETED_DEADLINE_DELETE_SUCCESS = "Deleted deadline: %1$s. "
            + "\nGood job it is completed!";
    public static final String MESSAGE_INCOMPLETE_DEADLINE_DELETE_SUCCESS = "Deleted deadline: %1$s! "
            + "\nI notice its uncompleted - Dont give up!";
    public static final String MESSAGE_EVENT_NOT_SELECTED = "Please select an event to delete deadlines from!";
    // ERROR MESSAGES

    // MODULE-RELATED
    public static final String MESSAGE_MODULE_DOES_NOT_EXIST = "The module you want does not exist!";

}
