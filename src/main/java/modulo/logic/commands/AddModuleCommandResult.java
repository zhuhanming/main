package modulo.logic.commands;

import java.util.List;
import java.util.Objects;

import modulo.model.event.EventType;
import modulo.model.module.Module;

/**
 * A {@code CommandResult} designed to handle the autopopulation logic of module creation.
 */
public class AddModuleCommandResult extends CommandResult {
    private final Module module;
    private final List<EventType> eventTypes;

    /**
     * Creates a {@code AddModuleCommandResult} that holds the state to load into the stateful logic manager.
     *
     * @param feedbackToUser     Feedback to display to user.
     * @param module             Module that was added.
     * @param eventTypes         Types of classes that the module has.
     * @param newPlaceholderText The new placeholder text to display.
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

    @Override
    public boolean equals(Object other) {
        return super.equals(other) // Takes care of null
                && ((AddModuleCommandResult) other).module.isSameModule(module)
                && ((AddModuleCommandResult) other).eventTypes.equals(eventTypes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(module, eventTypes, super.hashCode());
    }
}
