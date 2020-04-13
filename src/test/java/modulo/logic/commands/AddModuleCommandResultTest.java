package modulo.logic.commands;

import static modulo.testutil.module.TypicalModules.CS2103;
import static modulo.testutil.module.TypicalModules.CS2105;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import modulo.model.event.EventType;

/**
 * JUnit test class for {@code AddModuleCommandResult}.
 */
public class AddModuleCommandResultTest {
    @Test
    public void equals() {

        AddModuleCommandResult commandResult = new AddModuleCommandResult("feedback", CS2103, new ArrayList<>(),
                "placeholder");

        // same values -> returns true
        assertEquals(commandResult, new AddModuleCommandResult("feedback", CS2103, new ArrayList<>(),
                "placeholder"));

        // same object -> returns true
        assertEquals(commandResult, commandResult);

        // null -> returns false
        assertNotEquals(commandResult, null);

        // different types -> returns false
        assertNotEquals(commandResult, 0.5f);

        // different feedbackToUser value -> returns false
        assertNotEquals(commandResult, new AddModuleCommandResult("different", CS2103, new ArrayList<>(),
                "placeholder"));

        // different module value -> returns false
        assertNotEquals(commandResult, new AddModuleCommandResult("feedback", CS2105, new ArrayList<>(),
                "placeholder"));

        // different eventList value -> returns false
        assertNotEquals(commandResult, new AddModuleCommandResult("feedback", CS2103, List.of(EventType.LECTURE),
                "placeholder"));

        // different placeholder value -> return false ;
        assertNotEquals(commandResult, new AddModuleCommandResult("feedback", CS2103, new ArrayList<>(),
                "different"));
    }

    @Test
    public void hashcode() {
        AddModuleCommandResult commandResult = new AddModuleCommandResult("feedback", CS2103, new ArrayList<>(),
                "placeholder");

        // same values -> returns same hashcode
        assertEquals(commandResult.hashCode(), new AddModuleCommandResult("feedback", CS2103, new ArrayList<>(),
                "placeholder").hashCode());

        // different feedbackToUser value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new AddModuleCommandResult("different", CS2103, new ArrayList<>(),
                "placeholder").hashCode());

        // different module value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new AddModuleCommandResult("feedback", CS2105, new ArrayList<>(),
                "placeholder").hashCode());

        // different eventList value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new AddModuleCommandResult("feedback", CS2103,
                List.of(EventType.LECTURE), "placeholder").hashCode());

        // different placeholder value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new AddModuleCommandResult("feedback", CS2103, new ArrayList<>(),
                "different").hashCode());
    }

    @Test
    public void testGetModule() {
        AddModuleCommandResult commandResult = new AddModuleCommandResult("feedback", CS2103, new ArrayList<>(),
                "placeholder");
        assertEquals(commandResult.getModule(), CS2103);
    }

    @Test
    public void testGetEventList() {
        AddModuleCommandResult commandResult = new AddModuleCommandResult("feedback", CS2103, new ArrayList<>(),
                "placeholder");
        assertEquals(commandResult.getEventTypes(), new ArrayList<EventType>());
    }

}
