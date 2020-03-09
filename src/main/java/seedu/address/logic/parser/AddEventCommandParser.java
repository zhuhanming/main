package seedu.address.logic.parser;

import seedu.address.logic.commands.AddEventCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.entity.CalendarItem;
import seedu.address.model.entity.CalendarItemName;
import seedu.address.model.entity.Event;
import seedu.address.model.entity.Module;
import java.util.stream.Stream;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.*;

public class AddEventCommandParser implements Parser<AddEventCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddEventCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_MODULE, PREFIX_NAME, PREFIX_START_DATE, PREFIX_DURATION, PREFIX_REPEAT );

        if (!arePrefixesPresent(argMultimap, PREFIX_MODULE, PREFIX_NAME, PREFIX_START_DATE, PREFIX_DURATION, PREFIX_REPEAT)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEventCommand.MESSAGE_USAGE));
        }

//        Module module = ParserUtil.parseModule(argMultimap.getValue(PREFIX_MODULE).get());
        CalendarItemName name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        CalendarItemName moduleName = ParserUtil.parseName(argMultimap.getValue(PREFIX_MODULE).get());
        String startDate = ParserUtil.parseDate(argMultimap.getValue(PREFIX_START_DATE).get());

        // retrive from module name and check if module alr exists
        Module m = new Module(moduleName);
        Event event = new Event(name,m);
        event.setParentModule(m);

        return new AddEventCommand(event);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }


}
