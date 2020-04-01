package modulo.commons.core;

/**
 * Container for user visible messages.
 */
public class Messages {

    // ===================================
    // ====== [[ ERROR MESSAGES ]] ======
    // ===================================

    // MODULE-RELATED
    public static final String MESSAGE_MODULE_DOES_NOT_EXIST = "The module you want does not exist!";
    public static final String MESSAGE_DUPLICATE_MODULE = "This module has been added before!";
    public static final String MESSAGE_CANNOT_ADD_DEADLINE_TO_MODULE = "You cannot add deadlines to modules!";

    // EVENT-RELATED
    public static final String MESSAGE_EVENT_DOES_NOT_EXIST = "The event you want does not exist!";
    public static final String MESSAGE_DUPLICATE_EVENT = "This event already exists for this module!";
    public static final String MESSAGE_CANNOT_ADD_EVENT_TO_EVENT = "You cannot add events to events!";
    public static final String MESSAGE_INVALID_EVENT_TYPE = "Event type is not recognised!";
    public static final String MESSAGE_EVENT_NOT_SELECTED = "Please select an event to delete deadlines from!";

    // DEADLINE-RELATED
    public static final String MESSAGE_DEADLINE_DOES_NOT_EXIST = "The deadline you want does not exist!";
    public static final String MESSAGE_DUPLICATE_DEADLINE = "This deadline already exists for this event!";
    public static final String MESSAGE_CANNOT_COMPLETE_EVENT = "You cannot complete an event!";

    // COMMAND
    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";

    // ACADEMIC YEAR
    public static final String INVALID_ACADEMIC_SEMESTER = "The semester number is not valid!";

    // INDEX
    public static final String MESSAGE_INVALID_VIEW_INDEX = "The index you want to view is invalid";
    public static final String MESSAGE_INVALID_DELETE_INDEX = "The index you want to delete is invalid";
    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    // GENERAL
    public static final String MESSAGE_INVALID_DATE_RANGE = "The specified date range is invalid!";
    public static final String MESSAGE_EXPORT_FAILED = "Failed to export Modulo!";
    public static final String MESSAGE_ERROR = "Error! Please click the 'Help' button for more info";
    public static final String MESSAGE_INVALID_DATE_FORMAT = "Please enter a valid date in the format: yyyy-MM-dd";
    public static final String MESSAGE_INVALID_DATETIME_FORMAT = "Please enter a valid date time in the format: "
            + "yyyy-MM-dd HH:mm";
    public static final String MESSAGE_INVALID_REPEAT_VALUE = "Please enter a valid input for repeat : YES/NO";
    public static final String MESSAGE_INVALID_DISPLAYABLE_TYPE = "Type of item (module/event) to view is not "
            + "recognised!";

    // ===================================
    // ===== [[ SUCCESS-MESSAGES ]] ======
    // ===================================

    // MODULE-RELATED
    public static final String MESSAGE_MODULE_ADDED = "New module added: %1$s";
    public static final String MESSAGE_MODULE_LISTED_OVERVIEW = "%1$d modules listed!";
    public static final String MESSAGE_SHOWING_MODULES = "Showing modules!";
    public static final String MESSAGE_SHOWING_ALL_MODULES = "Showing all modules!";
    public static final String MESSAGE_SINGLE_MODULE_DELETE_SUCCESS = "Deleted module: %1$s";
    public static final String MESSAGE_MUTIPLE_MODULES_DELETE_SUCCESS = "%1$s Modules containing: "
            + "%2$s has been deleted";
    public static final String MESSAGE_DELETE_ALL_MODULES_SUCCESS = "Deleted all %1$s modules!";

    // EVENT-RELATED
    public static final String MESSAGE_EVENT_ADDED = "New event added: %1$s";
    public static final String MESSAGE_EVENT_LISTED_OVERVIEW = "%1$d events listed!";
    public static final String MESSAGE_SHOWING_EVENTS = "Showing Events!";
    public static final String MESSAGE_SHOWING_ALL_EVENTS = "Showing All Events!";
    public static final String MESSAGE_SINGLE_EVENT_DELETE_SUCCESS = "Deleted module: %1$s";
    public static final String MESSAGE_MUTIPLE_EVENTS_DELETE_SUCCESS = "%1$s Events containing: %2$s has been deleted";
    public static final String MESSAGE_DELETE_ALL_EVENTS_SUCCESS = "Deleted all %1$s events!";

    // DEADLINE-RELATED
    public static final String MESSAGE_DEADLINE_ADDED = "New deadline added: %1$s";
    public static final String MESSAGE_COMPLETED_DEADLINE = "Completed the deadline: %1$s";
    public static final String MESSAGE_UNCOMPLETED_DEADLINE = "Marked this deadline incomplete: %1$s";
    public static final String MESSAGE_COMPLETED_DEADLINE_DELETE_SUCCESS = "Deleted deadline: %1$s. "
            + "\nGood job it is completed!";
    public static final String MESSAGE_INCOMPLETE_DEADLINE_DELETE_SUCCESS = "Deleted deadline: %1$s! "
            + "\nI notice its uncompleted - Dont give up!";

    // GENERAL
    public static final String MESSAGE_MODULO_CLEARED = "Modulo has been cleared!";
    public static final String MESSAGE_EXIT_ACKNOWLEDGEMENT = "Exiting Modulo as requested...";
    public static final String MESSAGE_EXPORT_SUCCESS = "Modulo successfully exported as a .ics file!";
    public static final String MESSAGE_ITEM_LISTED_OVERVIEW = "%1$d items listed!";
    public static final String MESSAGE_SHOWING_HELP = "Opened help window.";
    public static final String MESSAGE_VIEW_ITEM_SUCCESS = "%1$s \nis in view!";
    public static final String MESSAGE_CLEARED_VIEW = "Cleared your view!";

}
