package seedu.address.model.event;

/**
 * Types of events allowed.
 */
public enum EventType {
    TUTORIAL,
    LECTURE,
    LAB;
}
/*
** Commented because of NoClassDefFoundError caused when having other stuff in EventType

("Do homework for tutorial."),
("Read lecture slides before lecture."),
("Do homework for lab.");


private CalendarItemName defaultDeadlineDescription;

EventType(String defaultDeadlineDescription) {
    this.defaultDeadlineDescription = new CalendarItemName(defaultDeadlineDescription);
}

public CalendarItemName getDefaultDeadlineDescription() {
    return defaultDeadlineDescription;
}*/

