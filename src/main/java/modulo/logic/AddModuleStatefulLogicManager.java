package modulo.logic;

import java.util.ArrayList;
import java.util.List;

import modulo.logic.commands.AddEventCommand;
import modulo.logic.commands.AddModuleCommandResult;
import modulo.logic.commands.CommandResult;
import modulo.logic.commands.exceptions.CommandException;
import modulo.logic.parser.exceptions.ParseException;
import modulo.model.Model;
import modulo.model.displayable.DisplayableType;
import modulo.model.event.EventType;
import modulo.model.event.exceptions.EventNotFoundException;
import modulo.model.module.Module;
import modulo.model.module.ModuleLibrary;

/**
 * The {@code AddModuleStatefulLogic} handles the processing of commands related to module creation.
 */
public class AddModuleStatefulLogicManager implements StatefulLogic {
    public static final String MESSAGE_INVALID_SLOT_NUMBER = "Please enter a proper %1$s slot!";
    public static final String MESSAGE_ALL_EVENTS_ADDED = "All events added!";
    private final Model model;

    // State
    private final List<EventType> eventTypes;
    private Module module;

    public AddModuleStatefulLogicManager(Model model) {
        this.model = model;
        this.eventTypes = new ArrayList<>();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        assert hasState();
        String eventSlot;

        eventSlot = commandText.trim().toLowerCase();

        EventType eventType = this.eventTypes.remove(0);
        AddEventCommand addEventCommand;
        try {
            addEventCommand = ModuleLibrary.getAddEventCommandToExecute(this.module, eventType, eventSlot);
        } catch (EventNotFoundException e) {
            this.eventTypes.add(0, eventType);
            throw new ParseException(String.format(MESSAGE_INVALID_SLOT_NUMBER, this.eventTypes.get(0).toString())
                    + "\n" + e.getMessage());
        }
        addEventCommand.execute(model);
        if (this.eventTypes.size() != 0) {
            return new CommandResult("Enter slot for " + module.getModuleCode().toString()
                    + " " + this.eventTypes.get(0).toString() + ":");
        }
        clearState();
        model.setFilteredFocusedList(DisplayableType.MODULE);
        return new CommandResult(MESSAGE_ALL_EVENTS_ADDED, false, false, true, true, null);
    }

    /**
     * Returns true if this stateful logic manager has state.
     *
     * @return A boolean value.
     */
    @Override
    public boolean hasState() {
        return this.eventTypes.size() > 0;
    }

    /**
     * Sets the state of the stateful logic manager.
     *
     * @param commandResult CommandResult to load state from.
     */
    @Override
    public void setStateWithCommandResult(CommandResult commandResult) {
        assert commandResult instanceof AddModuleCommandResult;
        AddModuleCommandResult castedCommandResult = (AddModuleCommandResult) commandResult;
        List<EventType> eventTypes = castedCommandResult.getEventTypes();
        Module module = castedCommandResult.getModule();
        if (eventTypes.size() > 0) {
            this.module = module;
            this.eventTypes.addAll(eventTypes);
        }
    }

    private void clearState() {
        this.module = null;
        this.eventTypes.clear();
    }
}
