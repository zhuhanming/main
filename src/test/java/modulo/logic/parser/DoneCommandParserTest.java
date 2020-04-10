package modulo.logic.parser;

import static modulo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static modulo.logic.parser.CommandParserTestUtil.assertParseFailure;
import static modulo.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static modulo.testutil.TypicalIndexesUtils.INDEX_FIRST_ITEM;

import modulo.testutil.event.TypicalEvents;
import modulo.testutil.module.TypicalModules;
import org.junit.jupiter.api.Test;

import modulo.logic.commands.DoneCommand;

public class DoneCommandParserTest {

    private DoneCommandParser parser = new DoneCommandParser();

    @Test
    public void parse_emptyArgs_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, DoneCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, " invalidArgs ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, DoneCommand.MESSAGE_USAGE));
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
    public void parse_validIndexArgs_validModuleArgs_validEventArgs_returnsDoneCommand() {
        // no leading and trailing whitespaces
        DoneCommand expectedDoneCommand =
                new DoneCommand(TypicalModules.partialModule_CS1231S, TypicalEvents.partialEvent_TUTORIAL_1, INDEX_FIRST_ITEM);
        assertParseSuccess(parser, "1 m/CS1231S e/Tutorial 1", expectedDoneCommand);
    }
}
