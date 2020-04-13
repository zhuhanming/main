package modulo.logic;

import static modulo.testutil.Assert.assertThrows;
import static modulo.testutil.module.TypicalModules.CS2103;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import modulo.commons.core.index.Index;
import modulo.logic.commands.AddModuleCommandResult;
import modulo.logic.commands.CommandResult;
import modulo.logic.commands.exceptions.CommandException;
import modulo.logic.parser.exceptions.ParseException;
import modulo.model.Model;
import modulo.model.ModelManager;
import modulo.model.UserPrefs;
import modulo.model.event.EventType;
import modulo.model.module.Module;
import modulo.model.module.ModuleLibrary;
import modulo.testutil.module.TypicalModules;

/**
 * JUnit test class for {@code AddModuleStatefulLogicManagerTest}.
 */
public class AddModuleStatefulLogicManagerTest {
    private final Model model = new ModelManager(TypicalModules.getTypicalModulo(), new UserPrefs());

    @Test
    public void constructor_isStateless_success() {
        assertFalse(new AddModuleStatefulLogicManager(model).hasState());
    }

    @Test
    public void setState_noEventTypes_isStateless() {
        AddModuleStatefulLogicManager manager = new AddModuleStatefulLogicManager(model);
        manager.setStateWithCommandResult(new EventlessAddModuleCommandResultStub());
        assertFalse(manager.hasState());
    }

    @Test
    public void setState_hasEventTypes_hasState() {
        AddModuleStatefulLogicManager manager = new AddModuleStatefulLogicManager(model);
        manager.setStateWithCommandResult(new EventfulAddModuleCommandResultStub());
        assertTrue(manager.hasState());
    }

    @Test
    public void execute_correctInputs_success() {
        AddModuleStatefulLogicManager manager = new AddModuleStatefulLogicManager(model);
        manager.setStateWithCommandResult(new ValidAddModuleCommandResultStub());
        try {
            CommandResult result = manager.execute("1");
            assertEquals(result.getFeedbackToUser(), AddModuleStatefulLogicManager.MESSAGE_ALL_EVENTS_ADDED);
        } catch (CommandException | ParseException e) {
            fail();
        }
    }

    @Test
    public void execute_invalidSlot_parseExceptionThrown() {
        AddModuleStatefulLogicManager manager = new AddModuleStatefulLogicManager(model);
        manager.setStateWithCommandResult(new ValidAddModuleCommandResultStub());
        String expectedMessage = String.format(AddModuleStatefulLogicManager.MESSAGE_INVALID_SLOT_NUMBER,
                EventType.LECTURE.toString()) + "\n" + String.format(ModuleLibrary.SAMPLE_SLOT, "1");
        assertThrows(ParseException.class, expectedMessage, () -> manager.execute("ha"));
    }

    /**
     * Default {@code AddModuleCommandResult} stub that has all its methods failing.
     */
    private static class AddModuleCommandResultStub extends AddModuleCommandResult {

        public AddModuleCommandResultStub() {
            super("", null, null, "");
        }

        @Override
        public Module getModule() {
            return null;
        }

        @Override
        public List<EventType> getEventTypes() {
            return null;
        }

        @Override
        public boolean equals(Object other) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public int hashCode() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public String getFeedbackToUser() {
            return null;
        }

        @Override
        public boolean isToShowHelp() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean isToExit() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean isToUpdateLeftPanel() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean isToUpdateRightPanel() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Index getIndexToShow() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public String getNewPlaceholderText() {
            return null;
        }
    }

    /**
     * Eventless AddModuleCommandResultStub.
     */
    private static class EventlessAddModuleCommandResultStub extends AddModuleCommandResultStub {
        @Override
        public List<EventType> getEventTypes() {
            return new ArrayList<>();
        }
    }

    /**
     * Eventful AddModuleCommandResultStub.
     */
    private static class EventfulAddModuleCommandResultStub extends AddModuleCommandResultStub {
        @Override
        public List<EventType> getEventTypes() {
            return List.of(EventType.LECTURE);
        }
    }

    /**
     * Valid AddModuleCommandResultStub.
     */
    private static class ValidAddModuleCommandResultStub extends AddModuleCommandResultStub {
        @Override
        public List<EventType> getEventTypes() {
            return List.of(EventType.LECTURE);
        }

        @Override
        public Module getModule() {
            return CS2103;
        }
    }
}
