package modulo.storage;

import static modulo.storage.JsonAdaptedDeadline.MISSING_FIELD_MESSAGE_FORMAT;
import static modulo.testutil.Assert.assertThrows;
import static modulo.testutil.deadline.TypicalDeadlines.DO_TUTORIAL;
import static modulo.testutil.deadline.TypicalDeadlines.PREPARE_FOR_LECTURE;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import modulo.commons.exceptions.IllegalValueException;
import modulo.model.Name;
import modulo.model.deadline.Deadline;

public class JsonAdaptedDeadlineTest {
    private static final String INVALID_NAME = "d3@d|!n3";
    private static final String INVALID_DUE_TIME = "blahblahdatetime";

    private static final String VALID_NAME = DO_TUTORIAL.getName().toString();
    private static final String VALID_DUE_TIME = DO_TUTORIAL.getDueTime().toString();
    private static final String VALID_IS_COMPLETED = String.valueOf(DO_TUTORIAL.isCompleted());

    @Test
    public void toModelType_validDeadlineDetails_returnsDeadline() throws Exception {
        JsonAdaptedDeadline deadline = new JsonAdaptedDeadline(PREPARE_FOR_LECTURE);
        Deadline expectedResult = new Deadline(PREPARE_FOR_LECTURE.getName(), PREPARE_FOR_LECTURE.getDueTime(),
                PREPARE_FOR_LECTURE.isCompleted());
        assertEquals(expectedResult, deadline.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedDeadline deadline = new JsonAdaptedDeadline(INVALID_NAME, VALID_DUE_TIME, VALID_IS_COMPLETED);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, deadline::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedDeadline deadline = new JsonAdaptedDeadline(null, VALID_DUE_TIME, VALID_IS_COMPLETED);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, deadline::toModelType);
    }

    @Test
    public void toModelType_invalidDueTime_throwsIllegalValueException() {
        JsonAdaptedDeadline deadline = new JsonAdaptedDeadline(VALID_NAME, INVALID_DUE_TIME, VALID_IS_COMPLETED);
        String expectedMessage = JsonAdaptedDeadline.INVALID_DUE_TIME_FORMAT;
        assertThrows(IllegalValueException.class, expectedMessage, deadline::toModelType);
    }

    @Test
    public void toModelType_nullDueTime_throwsIllegalValueException() {
        JsonAdaptedDeadline deadline = new JsonAdaptedDeadline(VALID_NAME, null, VALID_IS_COMPLETED);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "Deadline Due Time");
        assertThrows(IllegalValueException.class, expectedMessage, deadline::toModelType);
    }


    @Test
    public void toModelType_nullIsCompleted_throwsIllegalValueException() {
        JsonAdaptedDeadline deadline = new JsonAdaptedDeadline(VALID_NAME, VALID_DUE_TIME, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "Deadline Completion Status");
        assertThrows(IllegalValueException.class, expectedMessage, deadline::toModelType);
    }
}
