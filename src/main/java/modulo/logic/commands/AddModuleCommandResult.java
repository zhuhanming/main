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

    /**
     * Creates a {@code AddModuleCommandResult} that holds the state to load into the stateful logic manager.
     *
     * @param feedbackToUser Feedback to display to user.
     * @param module         Module that was added.
     * @param eventTypes     Types of classes that the module has.
     */
    public AddModuleCommandResult(String feedbackToUser, Module module, List<EventType> eventTypes,
                                  String newPlaceholderText) {
        super(feedbackToUser, false, false, true, true, null, newPlaceholderText);
        this.module = module;
        this.eventTypes = eventTypes;
    }

    /**
     * Returns the module that was created.
     *
     * @return Module that was created.
     */
    public Module getModule() {
        return this.module;
    }

    /**
     * Returns a list of the types of classes that the created module has.
     *
     * @return List of event types.
     */
    public List<EventType> getEventTypes() {
        return this.eventTypes;
    }

}
