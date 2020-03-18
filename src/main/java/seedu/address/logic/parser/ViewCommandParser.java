package seedu.address.logic.parser;

import java.util.stream.Stream;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.commands.ViewCommand;
import seedu.address.commons.core.index.Index;


/**
 * Parses input arguments and creates a new ListCommand object
 */
public class ViewCommandParser implements Parser<ViewCommand> {


    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand and returns an AddCommand object
     * for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ViewCommand parse(String args) throws ParseException {
        Index index = ParserUtil.parseIndex(args);
        return new ViewCommand(index);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given {@code
     * ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}


