package modulo.logic.parser;

import static modulo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static modulo.logic.commands.CommandTestUtil.CODE_DESC_CS1231S;
import static modulo.logic.commands.CommandTestUtil.CODE_DESC_CS2103;
import static modulo.logic.commands.CommandTestUtil.DEADLINE_DESC_ONE;
import static modulo.logic.commands.CommandTestUtil.EVENT_DESC_TUTORIAL_1;
import static modulo.logic.commands.CommandTestUtil.REPEAT;
import static modulo.logic.commands.CommandTestUtil.VALID_DEADLINE_ONE;
import static modulo.logic.commands.CommandTestUtil.VALID_REPEAT_BOOL;

import static modulo.logic.parser.CommandParserTestUtil.assertParseFailure;
import static modulo.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static modulo.testutil.event.TypicalEvents.PARTIAL_EVENT_TUTORIAL_1;

import org.junit.jupiter.api.Test;

import modulo.logic.commands.AddDeadlineCommand;
import modulo.model.Name;

public class AddDeadlineCommandParserTest {

    private AddDeadlineCommandParser parser = new AddDeadlineCommandParser();

    @Test
    public void parse_emptyInput_throwsParseException() {
        String expectedMessage = String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, AddDeadlineCommand.MESSAGE_USAGE);

        // Empty string
        assertParseFailure(parser, "", expectedMessage);
        // Empty string with whitespaces
        assertParseFailure(parser, "     ", expectedMessage);
    }


    @Test
    public void parse_invalidInput_throwsParseException() {
        String expectedMessage = String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, AddDeadlineCommand.MESSAGE_USAGE);

        // Input with only module and event
        assertParseFailure(parser, CODE_DESC_CS2103 + EVENT_DESC_TUTORIAL_1, expectedMessage);
        // Input with only module
        assertParseFailure(parser, CODE_DESC_CS2103, expectedMessage);
        // Input with only event
        assertParseFailure(parser, EVENT_DESC_TUTORIAL_1, expectedMessage);
    }

    @Test
    public void parse_validArgumentsOnlyNameAndRepeatPresent_success() {
        // Only deadline name and repeat entered
        assertParseSuccess(parser, DEADLINE_DESC_ONE + REPEAT,
                new AddDeadlineCommand(new Name(VALID_DEADLINE_ONE), VALID_REPEAT_BOOL));
        // Only deadline name and repeat entered with additional whitespaces
        assertParseSuccess(parser, "   " + DEADLINE_DESC_ONE + "   " + REPEAT,
                new AddDeadlineCommand(new Name(VALID_DEADLINE_ONE), VALID_REPEAT_BOOL));
    }

    @Test
    public void parse_validArgumentsAllFieldsPresent_success() {
        // All fields entered
        assertParseSuccess(parser, CODE_DESC_CS1231S + EVENT_DESC_TUTORIAL_1 + DEADLINE_DESC_ONE + REPEAT,
                new AddDeadlineCommand(new Name(VALID_DEADLINE_ONE), PARTIAL_EVENT_TUTORIAL_1, VALID_REPEAT_BOOL));
        // All fields entered with additional whitespaces
        assertParseSuccess(parser, "   " + CODE_DESC_CS1231S + "   "
                        + EVENT_DESC_TUTORIAL_1 + "   " + DEADLINE_DESC_ONE + "   " + REPEAT,
                new AddDeadlineCommand(new Name(VALID_DEADLINE_ONE), PARTIAL_EVENT_TUTORIAL_1, VALID_REPEAT_BOOL));
    }
}
