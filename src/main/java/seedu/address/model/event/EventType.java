package seedu.address.model.event;

import java.util.List;

/**
 * Types of events allowed.
 */
public enum EventType {
    PACKAGED_TUTORIAL("Do tutorial homework", "Packaged Tutorial", "packaged tut", "package tut"),
    TUTORIAL_TYPE_2("Do tutorial homework", "Tutorial Type Two", "type"),
    PACKAGED_LECTURE("Prepare for lecture in advance", "Packaged Lecture", "packaged lec", "package lec"),
    DESIGN_LECTURE("Prepare for lecture in advance", "Design Lecture", "design lec", "design lec"),
    TUTORIAL("Do tutorial homework", "Tutorial", "tut"),
    LECTURE("Prepare for lecture in advance", "Lecture", "lec"),
    SECTIONAL("Prepare for sectional in advance", "Sectional Teaching", "section", "sectional"),
    SEMINAR("Prepare for seminar in advance", "Seminar-Style Module Class", "sem"),
    RECITATION("Do recitation homework", "Recitation", "recit"),
    WORKSHOP("Prepare for workshop in advance", "Workshop", "workshop", "work"),
    LABORATORY("Prepare for laboratory in advance", "Laboratory", "lab"),
    USER_ADDED("Prepare for this event", "");

    private static final String MESSAGE_CONSTRAINTS = "Your event type is not recognised!";

    private String[] recognisedNames;
    private String defaultDeadlineDescription;
    private String genericName;

    EventType(String defaultDeadlineDescription, String genericName, String... names) {
        this.defaultDeadlineDescription = defaultDeadlineDescription;
        this.genericName = genericName;
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

    public String toString() {
        return this.genericName;
    }

    /**
     * Parses given string into an {@code EventType}.
     *
     * @param eventString String to parse.
     * @return An appropriate event type or null if not recognised.
     */
    public static EventType parseEventType(String eventString) {
        String cleanedEventType = eventString.toLowerCase().trim();
        for (EventType e : EventType.values()) {
            if (e.hasEventType(cleanedEventType)) {
                return e;
            }
        }
        return USER_ADDED;
    }
}
