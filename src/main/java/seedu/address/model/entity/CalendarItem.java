package seedu.address.model.entity;

/**
 * Represents a item that can be displayed on the calendar. This includes both deadlines and events.
 */
public abstract class CalendarItem {

    private CalendarItemName itemName;
    private CalendarItemType calendarItemType;

    public abstract boolean isSameCalendarItem(CalendarItem otherCalendarItem);

    /**
     * Checks if the other calendar item matches this calendar item.
     *
     * @param otherCalendarItem Other item to check.
     * @return boolean value to denote if the two items match.
     */
    public boolean matchCalendarItem(CalendarItem otherCalendarItem) {
        return otherCalendarItem.getItemName().equals(this.getItemName())
                && otherCalendarItem.getCalendarItemType().equals(otherCalendarItem.getCalendarItemType())
                && otherCalendarItem.getModule().getModuleCode().equals(this.getModule().getModuleCode());
    }

    public abstract String toDebugString();

    public abstract Module getModule();

    public abstract String getItemName();

    public abstract CalendarItemType getCalendarItemType();

}
