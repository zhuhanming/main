package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_DATETIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REPEAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_DATETIME;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.stream.Stream;

import seedu.address.logic.commands.AddEventCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.entity.CalendarItemName;
import seedu.address.model.entity.Event;
import seedu.address.model.entity.EventType;
import seedu.address.model.entity.MatchableModule;
import seedu.address.model.entity.Module;


/**
 * Parses input arguments and creates a new AddEventCommand object
 */
public class AddEventCommandParser implements Parser<AddEventCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand and returns an AddCommand object
     * for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    @SuppressWarnings("checkstyle:WhitespaceAround")
    public AddEventCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_MODULE, PREFIX_NAME,
                PREFIX_START_DATETIME, PREFIX_END_DATETIME, PREFIX_REPEAT);


        if (!arePrefixesPresent(argMultimap, PREFIX_MODULE, PREFIX_NAME, PREFIX_START_DATETIME,
                PREFIX_END_DATETIME, PREFIX_REPEAT) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEventCommand.MESSAGE_USAGE));
        }

        // Module module = ParserUtil.parseModule(argMultimap.getValue(PREFIX_MODULE).get());
        CalendarItemName name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        String moduleCode = ParserUtil.parseModuleCode(argMultimap.getValue(PREFIX_MODULE).get());
        LocalDateTime startDateTime = ParserUtil.parseDateTime(argMultimap.getValue(PREFIX_START_DATETIME).get());
        LocalDateTime endDateTime = ParserUtil.parseDateTime(argMultimap.getValue(PREFIX_END_DATETIME).get());
        boolean isRepeated = ParserUtil.parseRepeat(argMultimap.getValue(PREFIX_REPEAT).get());
        LocalDate endRepeatDate = null;
        //if (isRepeated) {
        //    endRepeatDate = ParserUtil.parseDate(argMultimap.getValue(PREFIX_STOP_REPEAT).get());
        //}
        // todo: Need to parse event name for EventType
        // retrieve from module name and check if module already exists
        Module module = new MatchableModule(moduleCode);
        EventType eventType;
        if (name.toString().contains("Tutorial")) {
            eventType = EventType.TUTORIAL;
        } else if (name.toString().contains("Lab")) {
            eventType = EventType.LAB;
        } else {
            eventType = EventType.LECTURE;
        }
        Event event = new Event(name, eventType, startDateTime, endDateTime, module);
        return new AddEventCommand(event, isRepeated, endRepeatDate);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given {@code
     * ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }


}
