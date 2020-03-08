package seedu.address.model.entity;

import java.time.LocalDateTime;

public class Deadline extends CalendarItem {
    private CalendarItemName deadlineName;
    private LocalDateTime dueTime;
    private Event parentEvent;
    private boolean isCompleted;

    public Deadline(CalendarItemName deadlineName, LocalDateTime dueTime, Event parentEvent, boolean isCompleted) {
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

    public LocalDateTime getDueTime() {
        return dueTime;
    }

    public void setDueTime(LocalDateTime dueTime) {
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
}
