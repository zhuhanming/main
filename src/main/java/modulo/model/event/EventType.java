package modulo.model.event;

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

    /**
     * Constructs each of the event type enums once.
     *
     * @param defaultDeadlineDescription Default description for the first deadline created.
     * @param genericName                Generic name for event population.
     * @param names                      Names for user input parsing.
     */
    EventType(String defaultDeadlineDescription, String genericName, String... names) {
        this.defaultDeadlineDescription = defaultDeadlineDescription;
        this.genericName = genericName;
        this.recognisedNames = names;
    }

    /**
     * Returns the message on the constraints for event types.
     *
     * @return Message on constraints.
     */
    public static String getMessageConstraints() {
        return MESSAGE_CONSTRAINTS;
    }

    /**
     * Checks if the given string matches the recognised names for the event type.
     *
     * @param eventType String to check.
     * @return If the event type contains this string.
     */
    private boolean hasEventType(String eventType) {
        return List.of(this.recognisedNames).stream().anyMatch(eventType::contains);
    }

    /**
     * Returns the default description for the first deadline.
     *
     * @return Default description for the first deadline.
     */
    public String getDefaultDeadlineDescription() {
        return this.defaultDeadlineDescription;
    }

    /**
     * Returns the generic name of the event type.
     *
     * @return Generic name of the event type.
     */
    public String toString() {
        return this.genericName;
    }

    /**
     * Parses given string into an {@code EventType}. If not recognised by any default event types, {@code USER_ADDED}
     * is returned.
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
