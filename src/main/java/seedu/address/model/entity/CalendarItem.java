package seedu.address.model.entity;

public abstract class CalendarItem {

    private CalendarItemName itemName;
    private Module module;
    private CalendarItemType calendarItemType;

    enum CalendarItemType {
        DEADLINE,
        EVENT
    }

    public CalendarItem(CalendarItemName itemName, Module module, CalendarItemType calendarItemType) {
        this.itemName = itemName;
        this.module = module;
        this.calendarItemType = calendarItemType;
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

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
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

        //two calendaritems are the same whe n
        return otherCalendarItem != null &&
                this.itemName.equals(otherCalendarItem.getItemName()) &&
                this.module.getModuleCode() == otherCalendarItem.getModule().getModuleCode(); //todo COMPARE MORE THINGS

    }

    public boolean matchCalendarItem(CalendarItem otherCalendarItem) {
        return otherCalendarItem.getItemName().equals(this.itemName) &&
                otherCalendarItem.getCalendarItemType().equals(otherCalendarItem.getCalendarItemType()) &&
                otherCalendarItem.getModule().getModuleCode().equals(this.module.getModuleCode());
    }

}
