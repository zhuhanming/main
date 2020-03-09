package seedu.address.model.entity;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.requireNonNull;

public class Module {

    private String moduleCode;
    List<Event> events;

    public Module(String moduleCode) {
        this.moduleCode = moduleCode;
        this.events = new ArrayList<Event>();
    }

    public String getModuleCode() {
        return moduleCode;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }


    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public void addEvents(Event event) {
        events.add(event);
    }

    public boolean isSameModule(Module otherModule) {
        if (otherModule == this) {
            return true;
        } else {
            return otherModule.getModuleCode().equals(this.getModuleCode());
        }
    }

    public boolean matchModule(Module otherModule) {
        return this.moduleCode.equals(otherModule.getModuleCode());
    }

    public String toDebugString() {
        return moduleCode;
    }
}
