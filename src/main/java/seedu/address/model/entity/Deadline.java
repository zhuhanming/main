package seedu.address.model.entity;

import java.time.LocalDateTime;
import java.util.Date;

public class Deadline extends CalendarItem {
    private CalendarItemName deadlineName;
    private Date dueTime;
    private Event parentEvent;
    private boolean isCompleted;

    public Deadline(CalendarItemName deadlineName, Date dueTime, Event parentEvent, boolean isCompleted) {
        this.deadlineName = deadlineName;
        this.dueTime = dueTime;
        this.parentEvent = parentEvent;
        this.isCompleted = isCompleted;
    }

    public Deadline(CalendarItemName deadlineName) {
        super(deadlineName, CalendarItemType.DEADLINE);
        this.deadlineName = deadlineName;
        this.isCompleted = false;
    }

    public Deadline() {
    }

    public CalendarItemName getDeadlineName() {
        return deadlineName;
    }

    public void setDeadlineName(CalendarItemName deadlineName) {
        this.deadlineName = deadlineName;
    }

    public Date getDueTime() {
        return dueTime;
    }

    public void setDueTime(Date dueTime) {
        this.dueTime = dueTime;
    }

    public void setDueTime() {
        this.dueTime = parentEvent.getEventStart();
    }

    public Event getParentEvent() {
        return parentEvent;
    }

    public void setParentEvent(Event parentEvent) {
        this.parentEvent = parentEvent;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public String toDebugString() {
        return super.getCalendarItemType() + " " + deadlineName;
    }

    public Module getModule() {
        return parentEvent.getParentModule();
    }
}
