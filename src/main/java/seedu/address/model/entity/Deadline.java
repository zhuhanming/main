package seedu.address.model.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class Deadline extends CalendarItem {
    private CalendarItemName deadlineName;
    private LocalDateTime dueTime;
    private Event parentEvent;
    private boolean isCompleted;

    public Deadline(CalendarItemName deadlineName, Event parentEvent, boolean isCompleted) {
        this.deadlineName = deadlineName;
        this.parentEvent = parentEvent;
        this.isCompleted = isCompleted;
    }

    public Deadline(CalendarItemName deadlineName) {
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
        return getModule().getModuleCode() + " | " + CalendarItemType.DEADLINE + " | " + deadlineName + " || " + parentEvent.toDebugString();
    }

    public Module getModule() {
        return parentEvent.getParentModule();
    }

    @Override
    public boolean isSameCalendarItem(CalendarItem otherCalendarItem) {

        if (otherCalendarItem == this) {
            System.out.println(toDebugString() + " " + otherCalendarItem.toDebugString());
            return true;
        }

        if (otherCalendarItem.getCalendarItemType() != CalendarItemType.DEADLINE) {
            return false;
        }

        Deadline otherDeadline = (Deadline) otherCalendarItem;
        System.out.println(this.deadlineName.equals(otherDeadline.getDeadlineName()));
        System.out.println(this.parentEvent.matchCalendarItem(otherDeadline.getParentEvent()));
        return otherCalendarItem != null &&
                this.deadlineName.equals(otherDeadline.getDeadlineName()) &&
                this.parentEvent.isSameCalendarItem(otherDeadline.getParentEvent());
    }

    @Override
    public String getItemName() {
        return deadlineName.toString();
    }

    @Override
    public CalendarItemType getCalendarItemType() {
        return CalendarItemType.DEADLINE;
    }
}
