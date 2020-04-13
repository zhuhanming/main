package modulo.logic.parser;

import static modulo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static modulo.logic.commands.CommandTestUtil.CODE_DESC_CS2103;
import static modulo.logic.commands.CommandTestUtil.CODE_DESC_CS2105;
import static modulo.logic.commands.CommandTestUtil.ENDDATE_DESC_TUTORIAL;
import static modulo.logic.commands.CommandTestUtil.FREQUENCY_DESC_TUTORIAL;
import static modulo.logic.commands.CommandTestUtil.INVALID_DESC_EVENT_NAME;
import static modulo.logic.commands.CommandTestUtil.NAME_DESC_TUTORIAL;
import static modulo.logic.commands.CommandTestUtil.NAME_DESC_TUTORIAL_2;
import static modulo.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static modulo.logic.commands.CommandTestUtil.REPEAT_DESC_TUTORIAL;
import static modulo.logic.commands.CommandTestUtil.STARTDATE_DESC_TUTORIAL;
import static modulo.logic.commands.CommandTestUtil.STOP_REPEAT_DESC_TUTORIAL;
import static modulo.logic.commands.CommandTestUtil.VALID_FREQUENCY_TUTORIAL;
import static modulo.logic.commands.CommandTestUtil.VALID_STOP_REPEAT_TUTORIAL;
import static modulo.logic.commands.CommandTestUtil.VENUE_DESC_TUTORIAL;
import static modulo.logic.parser.CommandParserTestUtil.assertParseFailure;
import static modulo.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static modulo.testutil.event.TypicalEvents.TUTORIAL_1;

import java.time.LocalDate;
import java.time.temporal.TemporalAmount;

import org.junit.jupiter.api.Test;

import modulo.logic.commands.AddEventCommand;
import modulo.logic.parser.exceptions.ParseException;
import modulo.model.Name;
import modulo.model.event.Event;
import modulo.testutil.event.EventBuilder;

/**
 * Test class to test the functionality of the AddEventCommandParser.
 */
public class AddEventCommandParserTest {
    private final AddEventCommandParser addEventCommandParser = new AddEventCommandParser();
    private final String expectedMessage = String.format(
            MESSAGE_INVALID_COMMAND_FORMAT, AddEventCommand.MESSAGE_USAGE);

    @Test
    public void parse_emptyInput_throwsParseException() {
        // Empty string
        assertParseFailure(addEventCommandParser, "", expectedMessage);
        // Empty string with whitespaces
        assertParseFailure(addEventCommandParser, "     ", expectedMessage);
    }

    @Test
    public void parse_invalidInput_throwsParseException() {

        // Input without frequency and repeat
        assertParseFailure(addEventCommandParser, CODE_DESC_CS2105 + CODE_DESC_CS2103
                + STARTDATE_DESC_TUTORIAL + ENDDATE_DESC_TUTORIAL + VENUE_DESC_TUTORIAL
                + REPEAT_DESC_TUTORIAL, expectedMessage);

        // Input without event name
        assertParseFailure(addEventCommandParser, CODE_DESC_CS2105 + STARTDATE_DESC_TUTORIAL
                + ENDDATE_DESC_TUTORIAL + VENUE_DESC_TUTORIAL + REPEAT_DESC_TUTORIAL + FREQUENCY_DESC_TUTORIAL
                + STOP_REPEAT_DESC_TUTORIAL, expectedMessage);

        // Input without event start time
        assertParseFailure(addEventCommandParser, CODE_DESC_CS2105 + NAME_DESC_TUTORIAL
                + ENDDATE_DESC_TUTORIAL + VENUE_DESC_TUTORIAL + REPEAT_DESC_TUTORIAL + FREQUENCY_DESC_TUTORIAL
                + STOP_REPEAT_DESC_TUTORIAL, expectedMessage);

        // Input without event end time
        assertParseFailure(addEventCommandParser, CODE_DESC_CS2105 + NAME_DESC_TUTORIAL
                + STARTDATE_DESC_TUTORIAL + VENUE_DESC_TUTORIAL + REPEAT_DESC_TUTORIAL
                + FREQUENCY_DESC_TUTORIAL + STOP_REPEAT_DESC_TUTORIAL, expectedMessage);

        // Input without venue time
        assertParseFailure(addEventCommandParser, CODE_DESC_CS2105 + NAME_DESC_TUTORIAL
                + STARTDATE_DESC_TUTORIAL + ENDDATE_DESC_TUTORIAL + REPEAT_DESC_TUTORIAL
                + FREQUENCY_DESC_TUTORIAL + STOP_REPEAT_DESC_TUTORIAL, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid event name ;
        assertParseFailure(addEventCommandParser, CODE_DESC_CS2103
                + INVALID_DESC_EVENT_NAME + STARTDATE_DESC_TUTORIAL + ENDDATE_DESC_TUTORIAL
                + VENUE_DESC_TUTORIAL + REPEAT_DESC_TUTORIAL + FREQUENCY_DESC_TUTORIAL
                + STOP_REPEAT_DESC_TUTORIAL, Name.MESSAGE_CONSTRAINTS);

        // invalid event start date


    }

    @Test
    public void parse_allFieldsPresent_success() {

        TemporalAmount frequency = null;
        try {
            frequency = ParserUtil.parseFrequency(VALID_FREQUENCY_TUTORIAL);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Event expectedEvent = new EventBuilder(TUTORIAL_1).build();
        LocalDate endRepeatDate = null;
        try {
            endRepeatDate = ParserUtil.parseDate(VALID_STOP_REPEAT_TUTORIAL);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        // whitespace only preamble
        assertParseSuccess(addEventCommandParser, PREAMBLE_WHITESPACE + CODE_DESC_CS2103
                        + NAME_DESC_TUTORIAL + STARTDATE_DESC_TUTORIAL + ENDDATE_DESC_TUTORIAL
                        + VENUE_DESC_TUTORIAL + REPEAT_DESC_TUTORIAL + FREQUENCY_DESC_TUTORIAL
                        + STOP_REPEAT_DESC_TUTORIAL,
                new AddEventCommand(expectedEvent, true, endRepeatDate, frequency, null));

        // multi module code, last one accepted
        assertParseSuccess(addEventCommandParser, PREAMBLE_WHITESPACE + CODE_DESC_CS2103
                        + CODE_DESC_CS2103 + NAME_DESC_TUTORIAL + STARTDATE_DESC_TUTORIAL + ENDDATE_DESC_TUTORIAL
                        + VENUE_DESC_TUTORIAL + REPEAT_DESC_TUTORIAL + FREQUENCY_DESC_TUTORIAL
                        + STOP_REPEAT_DESC_TUTORIAL,
                new AddEventCommand(expectedEvent, true, endRepeatDate, frequency, null));

        // multi event name, last name accepted
        assertParseSuccess(addEventCommandParser, PREAMBLE_WHITESPACE + CODE_DESC_CS2103
                        + NAME_DESC_TUTORIAL_2 + NAME_DESC_TUTORIAL + STARTDATE_DESC_TUTORIAL + ENDDATE_DESC_TUTORIAL
                        + VENUE_DESC_TUTORIAL + REPEAT_DESC_TUTORIAL + FREQUENCY_DESC_TUTORIAL
                        + STOP_REPEAT_DESC_TUTORIAL,
                new AddEventCommand(expectedEvent, true, endRepeatDate, frequency, null));

    }
}
