package modulo.model.deadline;

import static modulo.logic.commands.CommandTestUtil.VALID_NAME_DEADLINE_LECTURE;
import static modulo.logic.commands.CommandTestUtil.VALID_NAME_DEADLINE_TUTORIAL;
import static modulo.testutil.event.TypicalEvents.TUTORIAL_1;
import static modulo.testutil.event.TypicalEvents.TUTORIAL_2;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import modulo.model.Name;
import modulo.testutil.deadline.DeadlineBuilder;

/**
 * JUnit test case for {@code Deadline}.
 */
public class DeadlineTest {

    @Test
    public void testConstructor_usingEvent_success() {
        Deadline testDeadline = new Deadline(new Name(VALID_NAME_DEADLINE_TUTORIAL), TUTORIAL_1);
        assertEquals(testDeadline.getDueTime(), TUTORIAL_1.getEventStart());
        assertFalse(testDeadline.isCompleted());
        assertEquals(testDeadline.getName(), new Name(VALID_NAME_DEADLINE_TUTORIAL));
    }

    @Test
    public void testConstructor_usingRawFields_success() {
        LocalDateTime testDueTime = LocalDate.of(2020, 1, 20).atTime(10, 0);
        Deadline testDeadline = new Deadline(new Name(VALID_NAME_DEADLINE_TUTORIAL), testDueTime, true);
        assertEquals(testDeadline.getName(), new Name(VALID_NAME_DEADLINE_TUTORIAL));
        assertEquals(testDeadline.getDueTime(), testDueTime);
        assertTrue(testDeadline.isCompleted());
    }

    @Test
    public void testIsCompletedToggle() {
        Deadline testDeadline = new Deadline(new Name(VALID_NAME_DEADLINE_TUTORIAL), TUTORIAL_1);
        assertFalse(testDeadline.isCompleted());
        testDeadline.setCompleted(true);
        assertTrue(testDeadline.isCompleted());
        testDeadline.setCompleted(false);
        assertFalse(testDeadline.isCompleted());
    }

    @Test
    public void testIsSameDeadline() {
        Deadline testDeadlineOne = new Deadline(new Name(VALID_NAME_DEADLINE_TUTORIAL), TUTORIAL_1);
        // same object -> returns true
        assertTrue(testDeadlineOne.isSameDeadline(testDeadlineOne));

        // null -> returns false
        assertFalse(testDeadlineOne.isSameDeadline(null));

        // different name -> returns false
        Deadline testDeadlineTwo = new Deadline(new Name(VALID_NAME_DEADLINE_LECTURE), TUTORIAL_1);
        assertFalse(testDeadlineOne.isSameDeadline(testDeadlineTwo));

        // same name different event -> returns true
        Deadline testDeadlineThree = new Deadline(new Name(VALID_NAME_DEADLINE_TUTORIAL), TUTORIAL_2);
        assertTrue(testDeadlineOne.isSameDeadline(testDeadlineThree));
    }

    @Test
    public void testEquals() {
        Deadline testDeadlineOne = new Deadline(new Name(VALID_NAME_DEADLINE_TUTORIAL), TUTORIAL_1);

        // same values -> returns true
        Deadline testDeadlineTwo = new DeadlineBuilder(testDeadlineOne).build();
        assertEquals(testDeadlineOne, testDeadlineTwo);

        // same object -> returns true
        assertEquals(testDeadlineOne, testDeadlineOne);

        // null -> returns false
        assertNotEquals(testDeadlineOne, null);

        // different type -> returns false
        assertNotEquals(5, testDeadlineOne);

        Deadline testDeadlineThree = new Deadline(new Name(VALID_NAME_DEADLINE_TUTORIAL), TUTORIAL_2);
        // different due time -> returns false
        assertNotEquals(testDeadlineOne, testDeadlineThree);

        Deadline testDeadlineFour = new DeadlineBuilder(testDeadlineOne).build();
        testDeadlineFour.setCompleted(true);
        // different completion status -> returns false
        assertNotEquals(testDeadlineOne, testDeadlineFour);
    }
}
