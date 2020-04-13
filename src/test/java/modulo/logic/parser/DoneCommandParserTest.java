package modulo.logic.parser;

import static modulo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static modulo.commons.core.Messages.MESSAGE_INVALID_INDEX;
import static modulo.logic.parser.CommandParserTestUtil.assertParseFailure;
import static modulo.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static modulo.testutil.Assert.assertThrows;
import static modulo.testutil.TypicalIndexesUtils.INDEX_FIRST_ITEM;

import org.junit.jupiter.api.Test;

import modulo.logic.commands.DoneCommand;
import modulo.logic.parser.exceptions.ParseException;
import modulo.testutil.event.TypicalEvents;
import modulo.testutil.module.TypicalModules;

public class DoneCommandParserTest {

    private final DoneCommandParser parser = new DoneCommandParser();

    @Test
    public void parse_invalidInputZeroInteger_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse("0"));
        assertParseFailure(parser, "0",
                MESSAGE_INVALID_INDEX);
    }

    @Test
    public void parse_invalidInputNegativeInteger_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse("-1"));
        assertParseFailure(parser, "-1",
                MESSAGE_INVALID_INDEX);
    }

    @Test
    public void parse_emptyArgs_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse(""));
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DoneCommand.MESSAGE_USAGE));
        assertThrows(ParseException.class, () -> parser.parse("     "));
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DoneCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse("invalidArgs"));
        assertParseFailure(parser, "invalidArgs", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DoneCommand.MESSAGE_USAGE));

        assertThrows(ParseException.class, () -> parser.parse(" invalidArgs "));
        assertParseFailure(parser, " invalidArgs ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DoneCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validIndexArgs_returnsDoneCommand() {
        // no leading and trailing whitespaces
        DoneCommand expectedDoneCommand =
                new DoneCommand(null, null, INDEX_FIRST_ITEM);
        assertParseSuccess(parser, "1", expectedDoneCommand);
        // multiple whitespaces between keywords
        assertParseSuccess(parser, " 1     ", expectedDoneCommand);
    }

    @Test
    public void parse_validIndexArgsValidModuleArgsValidEventArgs_returnsDoneCommand() {
        // no leading and trailing whitespaces
        DoneCommand expectedDoneCommand =
                new DoneCommand(TypicalModules.PARTIAL_MODULE_CS1231S, TypicalEvents.PARTIAL_EVENT_TUTORIAL_1_CS1231S,
                        INDEX_FIRST_ITEM);
        assertParseSuccess(parser, "1 m/CS1231S e/Tutorial 1", expectedDoneCommand);
    }

}
