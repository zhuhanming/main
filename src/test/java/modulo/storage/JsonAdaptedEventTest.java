package modulo.storage;

import static modulo.storage.JsonAdaptedEvent.MISSING_FIELD_MESSAGE_FORMAT;
import static modulo.testutil.Assert.assertThrows;
import static modulo.testutil.event.TypicalEvents.TUTORIAL_1;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import modulo.commons.exceptions.IllegalValueException;
import modulo.model.Name;
import modulo.model.event.Event;
import modulo.model.event.EventType;
import modulo.model.event.Location;
import modulo.model.event.PartialEvent;
import modulo.model.module.AcademicYear;
import modulo.model.module.ModuleCode;

public class JsonAdaptedEventTest {
    private static final String INVALID_NAME = "@n 3v3|/|+";
    private static final String INVALID_DATETIME = "blahblahdatetime";
    private static final String INVALID_PARENT_MODULE_CODE = " HELLO";
    private static final String INVALID_ACADEMIC_YEAR = "123456123";
    private static final String INVALID_LOCATION = " ";

    private static final String VALID_NAME = TUTORIAL_1.getName().toString();
    private static final String VALID_EVENT_TYPE = TUTORIAL_1.getEventType().toString();
    private static final String VALID_EVENT_START = TUTORIAL_1.getEventStart().toString();
    private static final String VALID_EVENT_END = TUTORIAL_1.getEventEnd().toString();
    private static final String VALID_PARENT_MODULE_CODE = TUTORIAL_1.getParentModule().getModuleCode().toString();
    private static final String VALID_ACADEMIC_YEAR = TUTORIAL_1.getParentModule().getAcademicYear().toString();
    private static final String VALID_LOCATION = TUTORIAL_1.getLocation().toString();
    private static final List<JsonAdaptedDeadline> VALID_DEADLINES = TUTORIAL_1.getDeadlines().stream()
            .map(JsonAdaptedDeadline::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validEventDetails_returnsPartialEvent() throws Exception {
        JsonAdaptedEvent event = new JsonAdaptedEvent(TUTORIAL_1);
        Event expectedResult = new PartialEvent(TUTORIAL_1.getName(), TUTORIAL_1.getEventType(),
                TUTORIAL_1.getEventStart(), TUTORIAL_1.getEventEnd(), TUTORIAL_1.getParentModule(),
                TUTORIAL_1.getLocation(), TUTORIAL_1.getDeadlines());
        assertEquals(expectedResult, event.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedEvent event = new JsonAdaptedEvent(INVALID_NAME, VALID_EVENT_TYPE, VALID_EVENT_START,
                VALID_EVENT_END, VALID_PARENT_MODULE_CODE, VALID_ACADEMIC_YEAR, VALID_LOCATION, VALID_DEADLINES);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedEvent event = new JsonAdaptedEvent(null, VALID_EVENT_TYPE, VALID_EVENT_START,
                VALID_EVENT_END, VALID_PARENT_MODULE_CODE, VALID_ACADEMIC_YEAR, VALID_LOCATION, VALID_DEADLINES);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_nullEventType_throwsIllegalValueException() {
        JsonAdaptedEvent event = new JsonAdaptedEvent(VALID_NAME, null, VALID_EVENT_START,
                VALID_EVENT_END, VALID_PARENT_MODULE_CODE, VALID_ACADEMIC_YEAR, VALID_LOCATION, VALID_DEADLINES);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, EventType.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_invalidEventStart_throwsIllegalValueException() {
        JsonAdaptedEvent event = new JsonAdaptedEvent(VALID_NAME, VALID_EVENT_TYPE, INVALID_DATETIME,
                VALID_EVENT_END, VALID_PARENT_MODULE_CODE, VALID_ACADEMIC_YEAR, VALID_LOCATION, VALID_DEADLINES);
        String expectedMessage = JsonAdaptedEvent.INVALID_START_DATE_TIME_FORMAT;
        assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_nullEventStart_throwsIllegalValueException() {
        JsonAdaptedEvent event = new JsonAdaptedEvent(VALID_NAME, VALID_EVENT_TYPE, null,
                VALID_EVENT_END, VALID_PARENT_MODULE_CODE, VALID_ACADEMIC_YEAR, VALID_LOCATION, VALID_DEADLINES);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "Event Start Date");
        assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_invalidEventEnd_throwsIllegalValueException() {
        JsonAdaptedEvent event = new JsonAdaptedEvent(VALID_NAME, VALID_EVENT_TYPE, VALID_EVENT_START,
                INVALID_DATETIME, VALID_PARENT_MODULE_CODE, VALID_ACADEMIC_YEAR, VALID_LOCATION, VALID_DEADLINES);
        String expectedMessage = JsonAdaptedEvent.INVALID_END_DATE_TIME_FORMAT;
        assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_nullEventEnd_throwsIllegalValueException() {
        JsonAdaptedEvent event = new JsonAdaptedEvent(VALID_NAME, VALID_EVENT_TYPE, VALID_EVENT_START,
                null, VALID_PARENT_MODULE_CODE, VALID_ACADEMIC_YEAR, VALID_LOCATION, VALID_DEADLINES);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "Event End Date");
        assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_invalidParentModuleCode_throwsIllegalValueException() {
        JsonAdaptedEvent event = new JsonAdaptedEvent(VALID_NAME, VALID_EVENT_TYPE, VALID_EVENT_START,
                VALID_EVENT_END, INVALID_PARENT_MODULE_CODE, VALID_ACADEMIC_YEAR, VALID_LOCATION, VALID_DEADLINES);
        String expectedMessage = ModuleCode.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_nullParentModuleCode_throwsIllegalValueException() {
        JsonAdaptedEvent event = new JsonAdaptedEvent(VALID_NAME, VALID_EVENT_TYPE, VALID_EVENT_START,
                VALID_EVENT_END, null, VALID_ACADEMIC_YEAR, VALID_LOCATION, VALID_DEADLINES);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, ModuleCode.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_invalidAcademicYear_throwsIllegalValueException() {
        JsonAdaptedEvent event = new JsonAdaptedEvent(VALID_NAME, VALID_EVENT_TYPE, VALID_EVENT_START,
                VALID_EVENT_END, VALID_PARENT_MODULE_CODE, INVALID_ACADEMIC_YEAR, VALID_LOCATION, VALID_DEADLINES);
        String expectedMessage = AcademicYear.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_nullAcademicYear_throwsIllegalValueException() {
        JsonAdaptedEvent event = new JsonAdaptedEvent(VALID_NAME, VALID_EVENT_TYPE, VALID_EVENT_START,
                VALID_EVENT_END, VALID_PARENT_MODULE_CODE, null, VALID_LOCATION, VALID_DEADLINES);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, AcademicYear.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_invalidLocation_throwsIllegalValueException() {
        JsonAdaptedEvent event = new JsonAdaptedEvent(VALID_NAME, VALID_EVENT_TYPE, VALID_EVENT_START,
                VALID_EVENT_END, VALID_PARENT_MODULE_CODE, VALID_ACADEMIC_YEAR, INVALID_LOCATION, VALID_DEADLINES);
        String expectedMessage = Location.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_nullLocation_throwsIllegalValueException() {
        JsonAdaptedEvent event = new JsonAdaptedEvent(VALID_NAME, VALID_EVENT_TYPE, VALID_EVENT_START,
                VALID_EVENT_END, VALID_PARENT_MODULE_CODE, VALID_ACADEMIC_YEAR, null, VALID_DEADLINES);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Location.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }
}
