package seedu.address.model.entity;

/**
 * Types of events allowed.
 */
public enum EventType {
    TUTORIAL("Do homework for tutorial."),
    LECTURE("Read lecture slides before lecture."),
    LAB("Do homework for lab.");

    private CalendarItemName defaultDeadlineDescription;

    EventType(String defaultDeadlineDescription) {
        this.defaultDeadlineDescription = new CalendarItemName(defaultDeadlineDescription);
    }

    public CalendarItemName getDefaultDeadlineDescription() {
        return defaultDeadlineDescription;
    }
}
