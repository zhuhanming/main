package modulo.logic.parser;

import static modulo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static modulo.commons.core.Messages.MESSAGE_INVALID_INDEX;
import static modulo.logic.parser.CommandParserTestUtil.assertParseFailure;
import static modulo.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import modulo.logic.commands.DoneCommand;
import modulo.logic.parser.exceptions.ParseException;

public class DoneCommandParserTest {

    private DoneCommandParser doneCommandParser = new DoneCommandParser();

    @Test
    public void parse_emptyInput_throwsParseException() {
        assertThrows(ParseException.class, () -> doneCommandParser.parse(""));
        assertParseFailure(doneCommandParser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DoneCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_inputIsZeroInteger_throwsParseException() {
        assertThrows(ParseException.class, () -> doneCommandParser.parse("0"));
        assertParseFailure(doneCommandParser, "0",
                MESSAGE_INVALID_INDEX);
    }

    @Test
    public void parse_inputIsNegativeInteger_throwsParseException() {
        assertThrows(ParseException.class, () -> doneCommandParser.parse("-1"));
        assertParseFailure(doneCommandParser, "-1",
                MESSAGE_INVALID_INDEX);
    }
}
