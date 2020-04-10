package modulo.logic.parser;

import static modulo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static modulo.logic.parser.CommandParserTestUtil.assertParseFailure;
import static modulo.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static modulo.testutil.TypicalIndexesUtils.INDEX_FIRST_ITEM;

import modulo.model.displayable.DisplayableType;
import org.junit.jupiter.api.Test;

import modulo.logic.commands.ListCommand;

public class ListCommandParserTest {

    private ListCommandParser parser = new ListCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "  ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "  invalidText  ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_listModules_returnsListCommand() {
        // no leading and trailing whitespaces
        ListCommand expectedListCommand =
                new ListCommand(DisplayableType.EVENT, false);
        assertParseSuccess(parser, " e", expectedListCommand);
        // multiple whitespaces between keywords
        assertParseSuccess(parser, " event     ", expectedListCommand);
    }

    @Test
    public void parse_validArgs_listEvents_returnsListCommand() {
        // no leading and trailing whitespaces
        ListCommand expectedListCommand =
                new ListCommand(DisplayableType.MODULE, false);
        assertParseSuccess(parser, " m", expectedListCommand);
        // multiple whitespaces between keywords
        assertParseSuccess(parser, " module     ", expectedListCommand);
    }

    @Test
    public void parse_validArgs_listAllModules_returnsListCommand() {
        // no leading and trailing whitespaces
        ListCommand expectedListCommand =
                new ListCommand(DisplayableType.MODULE, true);
        assertParseSuccess(parser, " all m", expectedListCommand);
        // multiple whitespaces between keywords
        assertParseSuccess(parser, " all module     ", expectedListCommand);
    }

    @Test
    public void parse_validArgs_listAllEvents_returnsListCommand() {
        // no leading and trailing whitespaces
        ListCommand expectedListCommand =
                new ListCommand(DisplayableType.EVENT, true);
        assertParseSuccess(parser, "all e", expectedListCommand);
        // multiple whitespaces between keywords
        assertParseSuccess(parser, "all event     ", expectedListCommand);
    }

}