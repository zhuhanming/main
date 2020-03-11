package seedu.address.model.entity;

public abstract class CalendarItem {

    private CalendarItemName itemName;
    private CalendarItemType calendarItemType;

    public enum CalendarItemType {
        DEADLINE,
        EVENT
    }

    public CalendarItem() {

    }


    public abstract boolean isSameCalendarItem(CalendarItem otherCalendarItem);

    public boolean matchCalendarItem(CalendarItem otherCalendarItem) {
        return otherCalendarItem.getItemName().equals(this.getItemName()) &&
                otherCalendarItem.getCalendarItemType().equals(otherCalendarItem.getCalendarItemType()) &&
                otherCalendarItem.getModule().getModuleCode().equals(this.getModule().getModuleCode());
    }

    public abstract String toDebugString();

    public abstract Module getModule();

    public abstract String getItemName();

    public abstract CalendarItemType getCalendarItemType();

}
