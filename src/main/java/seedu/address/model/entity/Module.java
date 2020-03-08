package seedu.address.model.entity;

import java.util.List;

public class Module extends CalendarItem {

    private String moduleCode;
    private CalendarItemName moduleName;
    List<Event> events;


    public Module(CalendarItemName moduleName){
        this.moduleName = moduleName;
    }

    public String getModuleCode() {
        return moduleCode;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    public CalendarItemName getModuleName() {
        return moduleName;
    }
    public void setModuleName(CalendarItemName moduleName) {
        this.moduleName = moduleName;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public void addEvents(Event event){
        events.add(event);
    }
}
