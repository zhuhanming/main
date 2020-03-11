package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_DATE_RANGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_DATE;

import java.time.LocalDate;
import java.util.stream.Stream;

import seedu.address.logic.commands.AddModuleCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.entity.Module;

/**
 * Parses input arguments and creates a new AddModuleCommand object
 */
public class AddModuleCommandParser implements Parser<AddModuleCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddModuleCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_MODULE, PREFIX_START_DATE, PREFIX_END_DATE);

        if (!arePrefixesPresent(argMultimap, PREFIX_MODULE, PREFIX_START_DATE, PREFIX_END_DATE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddModuleCommand.MESSAGE_USAGE));
        }

        String moduleCode = ParserUtil.parseModuleCode(argMultimap.getValue(PREFIX_MODULE).get()).toUpperCase();
        LocalDate startDate = ParserUtil.parseDate(argMultimap.getValue(PREFIX_START_DATE).get());
        LocalDate endDate = ParserUtil.parseDate(argMultimap.getValue(PREFIX_END_DATE).get());

        if (endDate.isBefore(startDate)) {
            throw new ParseException(String.format(MESSAGE_INVALID_DATE_RANGE, "Start date cannot be after end date!"));
        }

        Module module = new Module(moduleCode, startDate, endDate);

        return new AddModuleCommand(module);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }


}
