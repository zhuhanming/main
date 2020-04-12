package modulo.logic.parser;

import static modulo.logic.commands.CommandTestUtil.CODE_DESC_CS2103;
import static modulo.logic.commands.CommandTestUtil.CODE_DESC_CS2105;
import static modulo.logic.commands.CommandTestUtil.ENDDATE_DESC_TUTORIAL;
import static modulo.logic.commands.CommandTestUtil.FREQUENCY_DESC_TUTORIAL;
import static modulo.logic.commands.CommandTestUtil.NAME_DESC_TUTORIAL;
import static modulo.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static modulo.logic.commands.CommandTestUtil.REPEAT_DESC_TUTORIAL;
import static modulo.logic.commands.CommandTestUtil.STARTDATE_DESC_TUTORIAL;
import static modulo.logic.commands.CommandTestUtil.STOP_REPEAT_DESC_TUTORIAL;
import static modulo.logic.commands.CommandTestUtil.VALID_STOP_REPEAT_TUTORIAL;
import static modulo.logic.commands.CommandTestUtil.VENUE_DESC_TUTORIAL;
import static modulo.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static modulo.testutil.event.TypicalEvents.TUTORIAL_1;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.TemporalAmount;

import org.junit.jupiter.api.Test;

import modulo.logic.commands.AddEventCommand;
import modulo.logic.commands.AddModuleCommand;
import modulo.model.event.Event;
import modulo.testutil.event.EventBuilder;

public class AddEventCommandParserTest {

    private AddEventCommandParser addEventCommandParser = new AddEventCommandParser();
    TemporalAmount frequency = Period.ofDays(7);

    @Test
    public void parse_allFieldsPresent_success() {

        Event expectedEvent = new EventBuilder(TUTORIAL_1).build();
        LocalDate endRepeatDate = null;

        VALID_STOP_REPEAT_TUTORIAL

        // whitespace only preamble
        assertParseSuccess(addEventCommandParser, PREAMBLE_WHITESPACE + CODE_DESC_CS2103
                        + NAME_DESC_TUTORIAL + STARTDATE_DESC_TUTORIAL, ENDDATE_DESC_TUTORIAL,
                VENUE_DESC_TUTORIAL, REPEAT_DESC_TUTORIAL, FREQUENCY_DESC_TUTORIAL, STOP_REPEAT_DESC_TUTORIAL,
                new AddEventCommand(expectedEvent, true, expectedEvent.getEventStart(), frequency);

        // multiple code - last code accepted
        assertParseSuccess(addModuleCommandParser, CODE_DESC_CS2105 + CODE_DESC_CS2103
                        + ACEDEMICYEAR_DESC_CS2103 + SEMESTER_DESC_CS2103,
                new AddModuleCommand(expecteModule.getModuleCode(), expecteModule.getAcademicYear()));

        // multiple academic year - academic year accepted
        assertParseSuccess(addModuleCommandParser, CODE_DESC_CS2103 + ACEDEMICYEAR_DESC_CS2105
                        + ACEDEMICYEAR_DESC_CS2103 + SEMESTER_DESC_CS2103,
                new AddModuleCommand(expecteModule.getModuleCode(), expecteModule.getAcademicYear()));

        // multiple semester - last semester accepted
        assertParseSuccess(addModuleCommandParser, CODE_DESC_CS2103 + ACEDEMICYEAR_DESC_CS2103
                        + SEMESTER_DESC_CS2105 + SEMESTER_DESC_CS2103,
                new AddModuleCommand(expecteModule.getModuleCode(), expecteModule.getAcademicYear()));
    }
}
