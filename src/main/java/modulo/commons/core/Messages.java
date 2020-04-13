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
    public static final String MESSAGE_CANNOT_ADD_DEADLINE_TO_MODULE = "Please select an event to add the deadline to!";
    public static final String MESSAGE_NO_MODULE_DELETED = "There are no modules containing %1$s.\n"
            + "No modules have been deleted.";


    // EVENT-RELATED
    public static final String MESSAGE_EVENT_DOES_NOT_EXIST = "The event you want does not exist!";
    public static final String MESSAGE_DUPLICATE_EVENT = "You cannot have two events of the same name under one "
            + "module!";
    public static final String MESSAGE_CANNOT_ADD_EVENT_TO_EVENT = "Please select a module to add the event to!";
    public static final String MESSAGE_INVALID_EVENT_TYPE = "Event type is not recognised!";
    public static final String MESSAGE_EVENT_NOT_SELECTED = "Please select an event to delete deadlines from!";
    public static final String MESSAGE_NO_EVENT_DELETED = "There are no events containing %1$s.\n"
            + "No events have been deleted";
    public static final String MESSAGE_EVENT_TOO_LONG = "Your event cannot be longer than a day!";

    // DEADLINE-RELATED
    public static final String MESSAGE_DEADLINE_DOES_NOT_EXIST = "The deadline you want does not exist!";
    public static final String MESSAGE_DUPLICATE_DEADLINE = "A deadline of the same name already exists for this "
            + "event!";
    public static final String MESSAGE_CANNOT_COMPLETE_EVENT = "You cannot complete an event!";
    public static final String MESSAGE_CANNOT_CREATE_DEADLINE_FOR_CUSTOM_EVENTS = "You cannot create repeated "
            + "deadlines for events which do not have a recognised type!\nEnter help to read the user guide and "
            + "see the full list of recognised event types!";

    // COMMAND
    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command.";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";

    // ACADEMIC YEAR
    public static final String INVALID_ACADEMIC_SEMESTER = "The semester number is not valid!";

    // INDEX
    public static final String MESSAGE_INVALID_VIEW_INDEX = "The index you want to view is invalid!";
    public static final String MESSAGE_INVALID_DELETE_INDEX = "The index you want to delete is invalid!";
    public static final String MESSAGE_INVALID_INDEX = "A positive integer is require for the index.";
    public static final String MESSAGE_INVALID_DONE_INDEX = "The index you want to set as done is invalid!";

    // DATETIME-RELATED
    public static final String MESSAGE_ENDDATE_MUST_AFTER_STARTDATE = "Start date/time must be before "
            + "the ending date/time!\n" + "Start DateTime: %1$s is after\n"
            + "End DateTime: %2$s";

    // GENERAL
    public static final String MESSAGE_INVALID_DATE_RANGE = "The specified date range is invalid!";
    public static final String MESSAGE_EXPORT_FAILED = "Failed to export Modulo!";
    public static final String MESSAGE_ERROR = "Error! Please click the 'Help' button for more info.";
    public static final String MESSAGE_INVALID_DATE_FORMAT = "Please enter a valid date in the format: yyyy-MM-dd.";
    public static final String MESSAGE_INVALID_DATETIME_FORMAT = "Please enter a valid date time in the format: "
            + "yyyy-MM-dd HH:mm.";
    public static final String MESSAGE_INVALID_REPEAT_VALUE = "Please enter a valid input for repeat : YES/NO.";
    public static final String MESSAGE_INVALID_DISPLAYABLE_TYPE = "Type of item (module/event) to view is not "
            + "recognised!";


    // ===================================
    // ===== [[ SUCCESS-MESSAGES ]] ======
    // ===================================

    // MODULE-RELATED
    public static final String MESSAGE_MODULE_ADDED = "New module added: %1$s";
    public static final String MESSAGE_MODULE_LISTED_OVERVIEW = "%1$d modules listed containing %2$s!";
    public static final String MESSAGE_SHOWING_MODULES = "Showing modules!";
    public static final String MESSAGE_SINGLE_MODULE_DELETE_SUCCESS = "Deleted module:\n%1$s";
    public static final String MESSAGE_MUTIPLE_MODULES_DELETE_SUCCESS = "%1$s module(s) containing: "
            + "%2$s have been deleted";
    public static final String MESSAGE_DELETE_ALL_MODULES_SUCCESS = "Deleted all %1$s module(s)!";

    // EVENT-RELATED
    public static final String MESSAGE_EVENT_ADDED = "New event added: %1$s";
    public static final String MESSAGE_EVENT_LISTED_OVERVIEW = "%1$d event(s) listed containing %2$s!";
    public static final String MESSAGE_SHOWING_EVENTS = "Showing events!";
    public static final String MESSAGE_SINGLE_EVENT_DELETE_SUCCESS = "Deleted event:\n%1$s";
    public static final String MESSAGE_MUTIPLE_EVENTS_DELETE_SUCCESS = "%1$s event(s) containing: %2$s has been "
            + "deleted";
    public static final String MESSAGE_DELETE_ALL_EVENTS_SUCCESS = "Deleted all %1$s event(s)!";

    // DEADLINE-RELATED
    public static final String MESSAGE_DEADLINE_ADDED = "New deadline added: %1$s";
    public static final String MESSAGE_COMPLETED_DEADLINE = "Completed the deadline:\n%1$s";
    public static final String MESSAGE_UNCOMPLETED_DEADLINE = "Marked this deadline incomplete:\n%1$s";
    public static final String MESSAGE_COMPLETED_DEADLINE_DELETE_SUCCESS = "Deleted deadline:\n%1$s. "
            + "\nGood job! It was completed!";
    public static final String MESSAGE_INCOMPLETE_DEADLINE_DELETE_SUCCESS = "Deleted deadline:\n%1$s! "
            + "\nI noticed that it was incomplete - Don't give up!";
    public static final String MESSAGE_ALL_DEADLINE_DELETE_SUCCESS = "All deadlines for %1$s has been deleted!";

    // GENERAL
    public static final String MESSAGE_MODULO_CLEARED = "Modulo has been cleared!";
    public static final String MESSAGE_EXIT_ACKNOWLEDGEMENT = "Exiting Modulo as requested...";
    public static final String MESSAGE_EXPORT_SUCCESS = "Modulo successfully exported as a .ics file!";
    public static final String MESSAGE_SHOWING_HELP = "Opened help window.";
    public static final String MESSAGE_VIEW_ITEM_SUCCESS = "%1$s is in view!";
    public static final String MESSAGE_CLEARED_VIEW = "Cleared your view!";
    public static final String MESSAGE_ZERO_ITEMS_LISTED = "No items containing %1$s has been found";
}
