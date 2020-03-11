package seedu.address.model.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.util.Objects.requireNonNull;

public class Module {

    private String moduleCode;
    private LocalDate startDate;
    private LocalDate endDate;

    List<Event> events;

    public Module(String moduleCode, LocalDate startDate, LocalDate endDate) {
        this.moduleCode = moduleCode;
        this.startDate = startDate;
        this.endDate = endDate;
        this.events = new ArrayList<>();
    }

    public Module(String moduleCode) {
        this.moduleCode = moduleCode;
        this.events = new ArrayList<>();
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

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
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
        return moduleCode + " | " + (startDate != null ? startDate.toString() : "null") + " | " + (endDate != null ? endDate.toString() : "null");
    }
}
