package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DIRECTORY;

import java.util.stream.Stream;

import seedu.address.logic.commands.ExportCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * todo
 */
public class ExportCommandParser implements Parser<ExportCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ExportCommandParser
     * and returns a ExportCommandParser object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */

    public ExportCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_DIRECTORY);
        if (!arePrefixesPresent(argMultimap, PREFIX_DIRECTORY) || !argMultimap.getPreamble().isEmpty()) {
            return new ExportCommand();
        } else {
            return new ExportCommand(ParserUtil.parseDirectory(argMultimap.getValue(PREFIX_DIRECTORY).get()));
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given {@code
     * ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
