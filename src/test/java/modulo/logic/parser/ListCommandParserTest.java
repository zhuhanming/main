package modulo.logic.parser;

import static modulo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static modulo.logic.parser.CommandParserTestUtil.assertParseFailure;
import static modulo.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import modulo.logic.commands.ListCommand;
import modulo.model.displayable.DisplayableType;

public class ListCommandParserTest {

    private final ListCommandParser parser = new ListCommandParser();

    @Test
    public void parseEmptyArg_throwsParseException() {
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "    ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "invalidText",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "  invalidText  ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "  module invalidText  ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "  event invalidText  ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
    }

    @Test
    public void parseValidArgs_listModules_returnsListCommand() {
        // no leading and trailing whitespaces
        ListCommand expectedListCommand =
                new ListCommand(DisplayableType.EVENT);
        assertParseSuccess(parser, "e", expectedListCommand);
        // multiple whitespaces between keywords
        assertParseSuccess(parser, "  e  ", expectedListCommand);

        assertParseSuccess(parser, "event", expectedListCommand);
        // multiple whitespaces between keywords
        assertParseSuccess(parser, " event     ", expectedListCommand);

        assertParseSuccess(parser, "events", expectedListCommand);
        // multiple whitespaces between keywords
        assertParseSuccess(parser, " events     ", expectedListCommand);
    }

    @Test
    public void parseValidArgs_listEvents_returnsListCommand() {
        // no leading and trailing whitespaces
        ListCommand expectedListCommand =
                new ListCommand(DisplayableType.MODULE);
        assertParseSuccess(parser, "m", expectedListCommand);
        // multiple whitespaces between keywords
        assertParseSuccess(parser, "  m     ", expectedListCommand);

        assertParseSuccess(parser, "module", expectedListCommand);
        // multiple whitespaces between keywords
        assertParseSuccess(parser, " module     ", expectedListCommand);

        assertParseSuccess(parser, "modules", expectedListCommand);
        // multiple whitespaces between keywords
        assertParseSuccess(parser, " modules     ", expectedListCommand);
    }
}
