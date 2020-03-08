package seedu.address.model.entity;

import seedu.address.model.person.Person;

import java.time.LocalDateTime;
import java.time.temporal.TemporalAmount;
import java.util.List;

public class Event extends CalendarItem {

    private CalendarItemName eventName;
    // List of Enum class
    private Enum eventType;
    private List<Deadline> deadlines;
    private Boolean isOver;
    private LocalDateTime eventStart;
    private LocalDateTime eventEnd;
    private TemporalAmount duration;
    private Module parentModule;


    public Event() {
    }

    public Event(CalendarItemName eventName, Enum eventType, LocalDateTime eventStart, TemporalAmount duration) {
        this.eventName = eventName;
        this.eventType = eventType;
        this.eventStart = eventStart;
        this.duration = duration;
        // create deadline here also ??
    }

    public Event(CalendarItemName eventName) {
        this.eventName = eventName;
    }
    /**
     * Returns true if a given string is a valid name.
     */
//    public static boolean isValidEvent(CalendarItemName test) {
//        return test.matches(VALIDATION_REGEX);
//    }

    public Enum getEventType() {
        return eventType;
    }

    public void setEventType(Enum eventType) {
        this.eventType = eventType;
    }

    public CalendarItemName getEventName() {
        return eventName;
    }

    public void setEventName(CalendarItemName eventName) {
        this.eventName = eventName;
    }

    public List<Deadline> getDeadlines() {
        return deadlines;
    }

    public void setDeadlines(List<Deadline> deadlines) {
        this.deadlines = deadlines;
    }

    public Boolean getOver() {
        return isOver;
    }

    public void setOver(Boolean over) {
        isOver = over;
    }

    public TemporalAmount getDuration() {
        return duration;
    }

    public void setDuration(TemporalAmount duration) {
        this.duration = duration;
    }

    public Module getParentModule() {
        return parentModule;
    }

    public void setParentModule(Module module) {
        this.parentModule = module;
    }

    public LocalDateTime getEventStart() {
        return eventStart;
    }

    public void setEventStart(LocalDateTime eventStart) {
        this.eventStart = eventStart;
    }

    public LocalDateTime getEventEnd() {
        return eventEnd;
    }

    public void setEventEnd(LocalDateTime eventEnd) {
        this.eventEnd = eventEnd;
    }

    public boolean isSameEvent(Event otherEvent) {
        if (otherEvent == this) {
            return true;
        }

        return otherEvent != null
                && otherEvent.getEventName().equals(getEventName())
                && (otherEvent.getModule().equals(getModule()));
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Person)) {
            return false;
        }

        Event otherEvent = (Event) other;
        return otherEvent.getEventName().equals(getEventName())
                && otherEvent.getModule().equals(getModule())
                && otherEvent.getDeadlines().equals(getDeadlines())
                && otherEvent.getDuration().equals(getDuration())
                && otherEvent.getEventEnd().equals(getEventEnd())
                && otherEvent.getEventStart().equals(getEventStart())
                && otherEvent.getOver().equals(getOver())
                && otherEvent.getEventType().equals(getEventType());
    }


    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getEventName())
                .append(" Module: ")
                .append(getModule().toString())
                .append(" Start: ")
                .append(getEventStart())
                .append(" End: ")
                .append(getEventEnd())
                .append(" EventType: ")
                .append(getEventType())
                .append((" Status "))
                .append((getOver()))
                .append(" Duration")
                .append(getDuration())
                .append(" Deadlines ")
                .append(getDeadlines());
//        getTags().forEach(builder::append);
        return builder.toString();
    }
}