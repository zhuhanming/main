package modulo.logic.commands;

import java.util.List;

import modulo.model.event.EventType;
import modulo.model.module.Module;

/**
 * A {@code CommandResult} designed to handle the autopopulation logic of module creation.
 */
public class AddModuleCommandResult extends CommandResult {
    private Module module;
    private List<EventType> eventTypes;

    public AddModuleCommandResult(String feedbackToUser, Module module, List<EventType> eventTypes) {
        super(feedbackToUser, false, false, true, true, null);
        this.module = module;
        this.eventTypes = eventTypes;
    }

    public Module getModule() {
        return this.module;
    }

    public List<EventType> getEventTypes() {
        return this.eventTypes;
    }

}
