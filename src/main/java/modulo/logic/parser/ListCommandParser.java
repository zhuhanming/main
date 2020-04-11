package modulo.logic.parser;

import static modulo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import modulo.logic.commands.ListCommand;
import modulo.logic.parser.exceptions.ParseException;
import modulo.model.displayable.DisplayableType;


/**
 * Parses input arguments and creates a new ListCommand object
 */
public class ListCommandParser implements Parser<ListCommand> {


    /**
     * Parses the given {@code String} of arguments in the context of the ListCommand and returns a ListCommand object
     * for execution.
     *
     * @param args Arguments passed in by the user.
     * @return {@code ListCommand} to execute.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ListCommand parse(String args) throws ParseException {
        String trimmedArgs = args.toLowerCase().trim();
        DisplayableType displayableType;

        try {
            displayableType = ParserUtil.parseDisplayableType(trimmedArgs);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE), pe);
        }

        return new ListCommand(displayableType);
    }
}


