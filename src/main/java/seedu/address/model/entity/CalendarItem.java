package seedu.address.model.entity;

public abstract class CalendarItem {

    private CalendarItemName itemName;
    private CalendarItemType calendarItemType;

    public enum CalendarItemType {
        DEADLINE,
        EVENT
    }


    public CalendarItem(CalendarItemName itemName, CalendarItemType calendarItemType) {
        this.itemName = itemName;
        this.calendarItemType = calendarItemType;
    }

    public CalendarItem() {

    }

    public CalendarItemName getItemName() {
        return itemName;
    }

    public void setItemName(CalendarItemName itemName) {
        this.itemName = itemName;
    }


    public CalendarItemType getCalendarItemType() {
        return calendarItemType;
    }

    public void setCalendarItemType(CalendarItemType calendarItemType) {
        this.calendarItemType = calendarItemType;
    }

    public boolean isSameCalendarItem(CalendarItem otherCalendarItem) {
        if (otherCalendarItem == this) {
            return true;
        }
        System.out.println("itemName is "+itemName);
        System.out.println("Module name is "+this.getModule());
        //two calendaritems are the same when
        return otherCalendarItem != null &&
                this.itemName.equals(otherCalendarItem.getItemName()) &&
                this.getModule().getModuleCode() == otherCalendarItem.getModule().getModuleCode(); //todo COMPARE MORE THINGS

    }

    public boolean matchCalendarItem(CalendarItem otherCalendarItem) {
        return otherCalendarItem.getItemName().equals(this.itemName) &&
                otherCalendarItem.getCalendarItemType().equals(otherCalendarItem.getCalendarItemType()) &&
                otherCalendarItem.getModule().getModuleCode().equals(this.getModule().getModuleCode());
    }

    public abstract String toDebugString();

    public abstract Module getModule();

}
