package modulo.logic.parser;

import static modulo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import modulo.commons.core.index.Index;
import modulo.logic.commands.ViewCommand;
import modulo.logic.parser.exceptions.ParseException;


/**
 * Parses input arguments and creates a new ListCommand object
 */
public class ViewCommandParser implements Parser<ViewCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the ViewCommand and returns a ViewCommand object
     * for execution.
     *
     * @param args Arguments passed in by the user.
     * @return {@code ViewCommand} to execute.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ViewCommand parse(String args) throws ParseException {
        Index index;
        try {
            index = ParserUtil.parseIndex(args);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE), pe);
        }
        return new ViewCommand(index);
    }
}


