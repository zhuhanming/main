package seedu.address.logic.parser;

import seedu.address.logic.commands.AddEventCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.entity.CalendarItem;
import seedu.address.model.entity.CalendarItemName;
import seedu.address.model.entity.Event;
import seedu.address.model.entity.Module;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.stream.Stream;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.*;

public class AddEventCommandParser implements Parser<AddEventCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddEventCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_MODULE, PREFIX_NAME, PREFIX_START_DATETIME, PREFIX_END_DATETIME);


        if (!arePrefixesPresent(argMultimap, PREFIX_MODULE, PREFIX_NAME, PREFIX_START_DATETIME, PREFIX_END_DATETIME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEventCommand.MESSAGE_USAGE));
        }

//        Module module = ParserUtil.parseModule(argMultimap.getValue(PREFIX_MODULE).get());
        CalendarItemName name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        String moduleCode = ParserUtil.parseModuleCode(argMultimap.getValue(PREFIX_MODULE).get());
        Date startDateTime = ParserUtil.parseDateTime(argMultimap.getValue(PREFIX_START_DATETIME).get());
        Date endDateTime = ParserUtil.parseDateTime(argMultimap.getValue(PREFIX_END_DATETIME).get());

        // retrive from module name and check if module alr exists
        Module module = new Module(moduleCode);
        Event event = new Event(name, CalendarItem.CalendarItemType.EVENT, startDateTime, endDateTime);
        event.setParentModule(module);

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
