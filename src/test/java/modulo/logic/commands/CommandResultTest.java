package modulo.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import modulo.commons.core.index.Index;

public class CommandResultTest {
    @Test
    public void equals() {

        CommandResult commandResult = new CommandResult("feedback");

        // same values -> returns true
        assertEquals(commandResult, (new CommandResult("feedback")));
        assertEquals(commandResult, (new CommandResult("feedback", false, false, false, false, null, null)));

        // same object -> returns true
        assertEquals(commandResult, (commandResult));

        // null -> returns false
        assertNotEquals(commandResult, (null));

        // different types -> returns false
        assertNotEquals(commandResult, (0.5f));

        // different feedbackToUser value -> returns false
        assertNotEquals(commandResult, (new CommandResult("different")));

        // different showHelp value -> returns false
        assertNotEquals(commandResult, (new CommandResult("feedback", true, false, false, false, null, null)));

        // different exit value -> returns false
        assertNotEquals(commandResult, (new CommandResult("feedback", false, true, false, false, null, null)));

        // different updateLeftPanel value -> return false ;
        assertNotEquals(commandResult, (new CommandResult("feedback", false, false, true, false, null, null)));

        // different updateLeftPanel value -> return false ;
        assertNotEquals(commandResult, new CommandResult("feedback", false, false, true, false, null, null));

        // different updateRightPanel value -> return false ;
        assertNotEquals(commandResult, (new CommandResult("feedback", false, false, false, true, null, null)));

        // different index value -> return false ;
        assertNotEquals(commandResult, (new CommandResult("feedback", false, false, false, false,
                Index.fromOneBased(1), null)));

    }

    @Test
    public void hashcode() {
        CommandResult commandResult = new CommandResult("feedback");

        // same values -> returns same hashcode
        assertEquals(commandResult.hashCode(), new CommandResult("feedback").hashCode());

        // different feedbackToUser value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("different").hashCode());

        // different showHelp value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("feedback", true,
                false, false, false, null, null).hashCode());

        // different exit value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("feedback", false,
                true, false, false, null, null).hashCode());

        // different updateLeftPanel value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("feedback", false,
                false, true, false, null, null).hashCode());

        // different updateRightPanel value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("feedback", false,
                false, false, true, null, null).hashCode());

        // different indexToShow value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("feedback", false,
                false, false, true, Index.fromOneBased(1), null).hashCode());


    }

}
