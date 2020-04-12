package modulo.logic.commands;

import static modulo.commons.core.Messages.MESSAGE_DUPLICATE_EVENT;
import static modulo.commons.core.Messages.MESSAGE_ENDDATE_MUST_AFTER_STARTDATE;
import static modulo.commons.core.Messages.MESSAGE_EVENT_ADDED;
import static modulo.commons.core.Messages.MESSAGE_INVALID_DATE_RANGE;
import static modulo.commons.core.Messages.MESSAGE_MODULE_DOES_NOT_EXIST;
import static modulo.logic.commands.CommandTestUtil.VALID_NAME_EVENT_TUTORIAL_1;
import static modulo.logic.commands.CommandTestUtil.VALID_NAME_EVENT_TUTORIAL_10;
import static modulo.logic.commands.CommandTestUtil.assertCommandFailure;
import static modulo.logic.commands.CommandTestUtil.assertCommandSuccess;
import static modulo.testutil.event.TypicalEvents.PARTIAL_EVENT_TUTORIAL_10_CS2103;
import static modulo.testutil.event.TypicalEvents.PARTIAL_EVENT_TUTORIAL_1_CS1231S;
import static modulo.testutil.event.TypicalEvents.PARTIAL_EVENT_TUTORIAL_1_CS2103;
import static modulo.testutil.event.TypicalEvents.TUTORIAL_1;
import static modulo.testutil.event.TypicalEvents.TUTORIAL_2;
import static modulo.testutil.event.TypicalEvents.TUTORIAL_INVALID_DATE;
import static modulo.testutil.event.TypicalEvents.TUTORIAL_INVALID_TIME;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.temporal.TemporalAmount;

import org.junit.jupiter.api.Test;

import modulo.commons.core.Messages;
import modulo.model.Model;
import modulo.model.ModelManager;
import modulo.model.Name;
import modulo.model.UserPrefs;
import modulo.model.event.EventType;
import modulo.model.event.Location;
import modulo.testutil.module.TypicalModules;


/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code DeleteCommand}.
 */
public class AddEventCommandTest {

    private Model model = new ModelManager(TypicalModules.getTypicalModulo(), new UserPrefs());

    @Test
    public void executeNothingToAdd_focusedDisplayableEvent_throwsCommandException() {
        AddEventCommand addEventCommand = new AddEventCommand(new Name(VALID_NAME_EVENT_TUTORIAL_1),
                LocalDateTime.of(LocalDate.now(), LocalTime.NOON),
                LocalDateTime.of(LocalDate.now(), LocalTime.NOON.plusHours(1)), new Location("Arbitrary location"),
                false, null, null, null);

        Model expectedModel = new ModelManager(model.getModulo(), model.getUserPrefs());
        expectedModel.setFocusedDisplayable(expectedModel.getFilteredEventList().get(0));

        assertCommandFailure(addEventCommand, expectedModel, Messages.MESSAGE_CANNOT_ADD_EVENT_TO_EVENT);
    }

    @Test
    public void executeNothingToAdd_noFocusedDisplayable_throwsCommandException() {
        AddEventCommand addEventCommand = new AddEventCommand(new Name(VALID_NAME_EVENT_TUTORIAL_1),
                LocalDateTime.of(LocalDate.now(), LocalTime.NOON),
                LocalDateTime.of(LocalDate.now(), LocalTime.NOON.plusHours(1)), new Location("Arbitrary location"),
                false, null, null, null);

        Model expectedModel = new ModelManager(model.getModulo(), model.getUserPrefs());
        expectedModel.unsetFocusedDisplayable();

        assertCommandFailure(addEventCommand, expectedModel, Messages.MESSAGE_MODULE_DOES_NOT_EXIST);
    }

    @Test
    public void executeNothingToAdd_startTimeBeforeEndTime_throwsCommandException() {
        LocalDateTime start = LocalDateTime.of(LocalDate.now(), LocalTime.NOON.plusHours(1));
        LocalDateTime end = LocalDateTime.of(LocalDate.now(), LocalTime.NOON);

        AddEventCommand addEventCommand = new AddEventCommand(new Name(VALID_NAME_EVENT_TUTORIAL_10),
                start, end, new Location("Arbitrary location"),
                false, null, null, null);

        Model expectedModel = new ModelManager(model.getModulo(), model.getUserPrefs());
        expectedModel.setFocusedDisplayable(expectedModel.getFilteredModuleList().get(0));

        assertCommandFailure(addEventCommand, expectedModel,
                String.format(MESSAGE_ENDDATE_MUST_AFTER_STARTDATE, start.toString().replace('T', ' '),
                        end.toString().replace('T', ' ')));
    }

    @Test
    public void executeEventIsSpecified_startTimeBeforeEndTime_throwsCommandException() {
        LocalDate endDate = TUTORIAL_INVALID_TIME.getEventEnd().toLocalDate();
        LocalDateTime endTime = TUTORIAL_INVALID_TIME.getEventEnd();
        TemporalAmount frequency = Period.ofWeeks(1);
        AddEventCommand addEventCommand = new AddEventCommand(TUTORIAL_INVALID_TIME,
                true, endDate, frequency, null);
        LocalDateTime start = TUTORIAL_INVALID_TIME.getEventStart();
        Model expectedModel = new ModelManager(model.getModulo(), model.getUserPrefs());
        expectedModel.setFocusedDisplayable(expectedModel.getFilteredModuleList().get(1));

        assertCommandFailure(addEventCommand, expectedModel,
                String.format(MESSAGE_ENDDATE_MUST_AFTER_STARTDATE, start.toString().replace('T', ' '),
                        endTime.toString().replace('T', ' ')));
    }

    @Test
    public void executeEventIsSpecified_modelDoesNotContainModuleOfEvent_throwsCommandException() {
        LocalDate end = LocalDate.now();
        TemporalAmount frequency = Period.ofWeeks(1);
        AddEventCommand addEventCommand = new AddEventCommand(PARTIAL_EVENT_TUTORIAL_1_CS1231S,
                true, end, frequency, null);
        LocalDateTime start = PARTIAL_EVENT_TUTORIAL_1_CS1231S.getEventStart();
        Model expectedModel = new ModelManager(model.getModulo(), model.getUserPrefs());
        expectedModel.setFocusedDisplayable(expectedModel.getFilteredModuleList().get(0));

        assertCommandFailure(addEventCommand, expectedModel,
                MESSAGE_MODULE_DOES_NOT_EXIST);
    }

    @Test
    public void executeValidInput_eventAlreadyExistsAndWillNotBeRepeated_throwsCommandException() {
        LocalDate end = LocalDate.now();
        TemporalAmount frequency = Period.ofWeeks(1);
        AddEventCommand addEventCommand = new AddEventCommand(PARTIAL_EVENT_TUTORIAL_1_CS2103,
                false, end, frequency, null);
        LocalDateTime start = PARTIAL_EVENT_TUTORIAL_1_CS2103.getEventStart();
        Model expectedModel = new ModelManager(model.getModulo(), model.getUserPrefs());
        expectedModel.setFocusedDisplayable(expectedModel.getFilteredModuleList().get(0));

        assertCommandFailure(addEventCommand, expectedModel,
                MESSAGE_DUPLICATE_EVENT);
    }

    @Test
    public void executeValidInput_hasInvalidDateRange_throwsCommandException() {
        LocalDate end = LocalDate.now();
        TemporalAmount frequency = Period.ofWeeks(1);
        AddEventCommand addEventCommand = new AddEventCommand(TUTORIAL_INVALID_DATE,
                false, end, frequency, null);
        LocalDateTime start = TUTORIAL_INVALID_DATE.getEventStart();
        Model expectedModel = new ModelManager(model.getModulo(), model.getUserPrefs());
        expectedModel.setFocusedDisplayable(expectedModel.getFilteredModuleList().get(0));

        assertCommandFailure(addEventCommand, expectedModel,
                MESSAGE_INVALID_DATE_RANGE);
    }

    @Test
    public void execute_validInputs_success() {
        TemporalAmount frequency = Period.ofWeeks(1);
        AddEventCommand addEventCommand = new AddEventCommand(new Name(VALID_NAME_EVENT_TUTORIAL_10),
                LocalDateTime.of(LocalDate.now(), LocalTime.NOON),
                LocalDateTime.of(LocalDate.now(), LocalTime.NOON.plusHours(1)), new Location("Arbitrary location"),
                false, null, EventType.TUTORIAL, frequency);

        CommandResult expectedResult = new CommandResult(String.format(MESSAGE_EVENT_ADDED,
                PARTIAL_EVENT_TUTORIAL_10_CS2103), false, false, true, true, null, null);
        Model expectedModel = new ModelManager(model.getModulo(), model.getUserPrefs());
        expectedModel.setFocusedDisplayable(expectedModel.getFilteredModuleList().get(1));

        assertCommandSuccess(addEventCommand, expectedModel, expectedResult, expectedModel);
    }


    @Test
    public void sameCommandWithToAddEvent() {
        TemporalAmount frequency = Period.ofWeeks(1);
        AddEventCommand addEventCommandFirst = new AddEventCommand(new Name(VALID_NAME_EVENT_TUTORIAL_10),
                LocalDateTime.of(LocalDate.now(), LocalTime.NOON),
                LocalDateTime.of(LocalDate.now(), LocalTime.NOON.plusHours(1)), new Location("Arbitrary location"),
                false, null, EventType.TUTORIAL, frequency);
        AddEventCommand addEventCommandSecond = new AddEventCommand(new Name(VALID_NAME_EVENT_TUTORIAL_10),
                LocalDateTime.of(LocalDate.now(), LocalTime.NOON),
                LocalDateTime.of(LocalDate.now(), LocalTime.NOON.plusHours(1)),
                new Location("Second Arbitrary location"),
                false, null, EventType.TUTORIAL, frequency);

        assertEquals(addEventCommandFirst, addEventCommandFirst);

        assertNotEquals(addEventCommandFirst, addEventCommandSecond);


        addEventCommandFirst = new AddEventCommand(new Name(VALID_NAME_EVENT_TUTORIAL_10),
                LocalDateTime.of(LocalDate.now(), LocalTime.NOON),
                LocalDateTime.of(LocalDate.now(), LocalTime.NOON.plusHours(1)), new Location("Arbitrary location"),
                false, null, EventType.TUTORIAL, frequency);
        addEventCommandSecond = new AddEventCommand(new Name(VALID_NAME_EVENT_TUTORIAL_10),
                LocalDateTime.of(LocalDate.now(), LocalTime.NOON),
                LocalDateTime.of(LocalDate.now(), LocalTime.NOON.plusHours(2)), new Location("Arbitrary location"),
                false, null, EventType.TUTORIAL, frequency);


        assertNotEquals(addEventCommandFirst, addEventCommandSecond);


        addEventCommandFirst = new AddEventCommand(new Name(VALID_NAME_EVENT_TUTORIAL_10),
                LocalDateTime.of(LocalDate.now(), LocalTime.NOON),
                LocalDateTime.of(LocalDate.now(), LocalTime.NOON.plusHours(1)), new Location("Arbitrary location"),
                false, null, EventType.TUTORIAL, frequency);
        frequency = Period.ofWeeks(2);
        addEventCommandSecond = new AddEventCommand(new Name(VALID_NAME_EVENT_TUTORIAL_10),
                LocalDateTime.of(LocalDate.now(), LocalTime.NOON),
                LocalDateTime.of(LocalDate.now(), LocalTime.NOON.plusHours(1)), new Location("Arbitrary location"),
                false, null, EventType.TUTORIAL, frequency);

        assertNotEquals(addEventCommandFirst, addEventCommandSecond);



        frequency = Period.ofWeeks(1);
        addEventCommandFirst = new AddEventCommand(new Name(VALID_NAME_EVENT_TUTORIAL_1),
                LocalDateTime.of(LocalDate.now(), LocalTime.NOON),
                LocalDateTime.of(LocalDate.now(), LocalTime.NOON.plusHours(1)), new Location("Arbitrary location"),
                false, null, EventType.TUTORIAL, frequency);
        addEventCommandSecond = new AddEventCommand(new Name(VALID_NAME_EVENT_TUTORIAL_10),
                LocalDateTime.of(LocalDate.now(), LocalTime.NOON),
                LocalDateTime.of(LocalDate.now(), LocalTime.NOON.plusHours(1)), new Location("Arbitrary location"),
                false, null, EventType.TUTORIAL, frequency);

        assertNotEquals(addEventCommandFirst, addEventCommandSecond);

        addEventCommandFirst = new AddEventCommand(new Name(VALID_NAME_EVENT_TUTORIAL_10),
                LocalDateTime.of(LocalDate.now(), LocalTime.NOON),
                LocalDateTime.of(LocalDate.now(), LocalTime.NOON.plusHours(1)), new Location("Arbitrary location"),
                false, null, EventType.TUTORIAL, frequency);
        addEventCommandSecond = new AddEventCommand(new Name(VALID_NAME_EVENT_TUTORIAL_10),
                LocalDateTime.of(LocalDate.now(), LocalTime.NOON),
                LocalDateTime.of(LocalDate.now(), LocalTime.NOON.plusHours(1)), new Location("Arbitrary location"),
                false, null, EventType.LECTURE, frequency);

        assertNotEquals(addEventCommandFirst, addEventCommandSecond);


    }

    @Test
    public void sameCommandWithoutToAddEvent() {
        LocalDate end = LocalDate.now();
        TemporalAmount frequency = Period.ofWeeks(1);
        AddEventCommand addEventCommandFirst = new AddEventCommand(TUTORIAL_1,
                true, end, frequency, null);
        AddEventCommand addEventCommandSecond = new AddEventCommand(TUTORIAL_1,
                false, end, frequency, null);

        assertEquals(addEventCommandFirst, addEventCommandFirst);

        assertNotEquals(addEventCommandFirst, addEventCommandSecond);


        addEventCommandFirst = new AddEventCommand(TUTORIAL_1,
                false, end, frequency, '1');
        addEventCommandSecond = new AddEventCommand(TUTORIAL_1,
                false, end, frequency, null);

        assertNotEquals(addEventCommandFirst, addEventCommandSecond);



        addEventCommandFirst = new AddEventCommand(TUTORIAL_1,
                false, end, frequency, null);

        frequency = Period.ofWeeks(2);
        addEventCommandSecond = new AddEventCommand(TUTORIAL_1,
                false, end, frequency, null);

        assertNotEquals(addEventCommandFirst, addEventCommandSecond);



        addEventCommandFirst = new AddEventCommand(TUTORIAL_1,
                false, end, frequency, null);

        addEventCommandSecond = new AddEventCommand(TUTORIAL_2,
                false, end, frequency, null);

        assertNotEquals(addEventCommandFirst, addEventCommandSecond);



        addEventCommandFirst = new AddEventCommand(TUTORIAL_1,
                true, end, frequency, null);
        end = LocalDate.now().minusDays(1);
        addEventCommandSecond = new AddEventCommand(TUTORIAL_1,
                false, end, frequency, null);

        assertNotEquals(addEventCommandFirst, addEventCommandSecond);
    }


}
