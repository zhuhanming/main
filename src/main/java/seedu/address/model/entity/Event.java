package seedu.address.model.entity;

import seedu.address.model.person.Person;

import java.time.LocalDateTime;
import java.time.temporal.TemporalAmount;
import java.util.Date;
import java.util.List;

public class Event extends CalendarItem {

    private CalendarItemName eventName;
    // List of Enum class
    private Enum eventType;
    private List<Deadline> deadlines;
    private Boolean isOver;
    private Date eventStart;
    private Date eventEnd;
    private TemporalAmount duration;
    private Module parentModule;


    public Event() {
    }

    public Event(CalendarItemName eventName, Enum eventType, Date eventStart, Date eventEnd) {
        super(eventName, CalendarItemType.EVENT);
        this.eventName = eventName;
        this.eventType = eventType;
        this.eventStart = eventStart;
        this.eventEnd = eventEnd;
        // create deadline here also ??
    }


    public Event(CalendarItemName eventName,Module module) {
        super(eventName,CalendarItemType.EVENT);
        this.eventName = eventName;
        this.parentModule = module;
    }

    public Event(CalendarItemName eventName) {
        super(eventName,CalendarItemType.EVENT);
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
        super.setModule(module);
    }

    public Date getEventStart() {
        return eventStart;
    }

    public void setEventStart(Date eventStart) {
        this.eventStart = eventStart;
    }

    public Date getEventEnd() {
        return eventEnd;
    }

    public void setEventEnd(Date eventEnd) {
        this.eventEnd = eventEnd;
    }

    public boolean isSameEvent(Event otherEvent) {
        if (otherEvent == this) {
            return true;
        }

        return otherEvent != null
                && otherEvent.getEventName().equals(getEventName())
                && (otherEvent.getParentModule().equals(getParentModule()));
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
                && otherEvent.getParentModule().equals(getParentModule())
                && otherEvent.getDeadlines().equals(getDeadlines())
                && otherEvent.getDuration().equals(getDuration())
                && otherEvent.getEventEnd().equals(getEventEnd())
                && otherEvent.getEventStart().equals(getEventStart())
                && otherEvent.getOver().equals(getOver())
                && otherEvent.getEventType().equals(getEventType());
    }
@Override
public Module getModule() {
        return parentModule;
}
    public String toDebugString() {
        return (getModule() == null ? "null" : super.getModule().getModuleCode()) + " | " + eventType + " | " + eventName + " | " + eventStart + " | " + eventEnd;
//        getTags().forEach(builder::append);
    }
}