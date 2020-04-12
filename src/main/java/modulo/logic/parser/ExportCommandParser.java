package modulo.logic.parser;

import static modulo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static modulo.logic.parser.CliSyntax.PREFIX_DIRECTORY;

import modulo.logic.commands.ExportCommand;
import modulo.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ExportCommand object
 */
public class ExportCommandParser implements Parser<ExportCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ExportCommandParser and returns a
     * ExportCommandParser object for execution.
     *
     * @param args Arguments passed in by the user.
     * @return {@code ExportCommand} to execute.
     * @throws ParseException if the user input does not conform the expected format.
     */

    public ExportCommand parse(String args) throws ParseException {

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_DIRECTORY);
        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportCommand.MESSAGE_USAGE));
        }
        if (argMultimap.getValue(PREFIX_DIRECTORY).isEmpty()) {
            return new ExportCommand();
        } else {
            return new ExportCommand(ParserUtil.parseDirectory(argMultimap.getValue(PREFIX_DIRECTORY).get()));
        }
    }
}
