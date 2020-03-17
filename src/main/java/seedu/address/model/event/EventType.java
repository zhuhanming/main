package seedu.address.model.event;

import java.util.List;

/**
 * Types of events allowed.
 */
public enum EventType {
    TUTORIAL("Do tutorial homework", "tut"),
    LECTURE("Prepare for lecture in advance", "lec"),
    SECTIONAL("Prepare for sectional in advance", "section", "sectional"),
    SEMINAR("Prepare for seminar in advance", "sem"),
    RECITATION("Do recitation homework", "recit"),
    WORKSHOP("Prepare for workshop in advance", "workshop", "work"),
    LABORATORY("Prepare for laboratory in advance", "lab");

    private static final String MESSAGE_CONSTRAINTS = "Your event type is not recognised!";

    private String[] recognisedNames;
    private String defaultDeadlineDescription;

    EventType(String defaultDeadlineDescription, String... names) {
        this.defaultDeadlineDescription = defaultDeadlineDescription;
        this.recognisedNames = names;
    }

    public static String getMessageConstraints() {
        return MESSAGE_CONSTRAINTS;
    }

    public boolean hasEventType(String eventType) {
        return List.of(this.recognisedNames).stream().anyMatch(eventType::contains);
    }

    public String getDefaultDeadlineDescription() {
        return this.defaultDeadlineDescription;
    }
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

