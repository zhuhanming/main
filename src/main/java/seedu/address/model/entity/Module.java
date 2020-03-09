package seedu.address.model.entity;

import java.util.List;

import static java.util.Objects.requireNonNull;

public class Module {

    private String moduleCode;
    private CalendarItemName moduleName;
    List<Event> events;
    private final UniqueModuleList moduleList;

    {
        moduleList = new UniqueModuleList();
    }

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

    public void addModule(Module module ){
        moduleList.add(module);
    }

    public boolean hasModule(Module module ){
        requireNonNull(module);
//        System.out.println("Calendar item !!!!!!!!!!!!!!!"+ calendarItem);
        return moduleList.contains(module);
    }

    public boolean isSameModule(Module otherModule ){
        if(otherModule == this ){
            return true;
        }else{
            return otherModule.getModuleName().equals(this.getModuleName());
        }
    }
}
