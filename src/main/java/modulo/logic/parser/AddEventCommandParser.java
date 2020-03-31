package modulo.logic.parser;

import static modulo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.TemporalAmount;
import java.util.stream.Stream;

import modulo.logic.commands.AddEventCommand;
import modulo.logic.parser.exceptions.ParseException;
import modulo.model.event.Event;
import modulo.model.module.Module;
import modulo.model.module.PartialModule;
import modulo.model.Name;
import modulo.model.event.EventType;
import modulo.model.event.Location;
import modulo.model.event.PartialEvent;
import modulo.model.module.ModuleCode;


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
    public AddEventCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, CliSyntax.PREFIX_MODULE, CliSyntax.PREFIX_NAME,
                CliSyntax.PREFIX_START_DATETIME, CliSyntax.PREFIX_END_DATETIME, CliSyntax.PREFIX_VENUE, CliSyntax.PREFIX_REPEAT);

        if (!arePrefixesPresent(argMultimap, CliSyntax.PREFIX_NAME, CliSyntax.PREFIX_START_DATETIME,
                CliSyntax.PREFIX_END_DATETIME, CliSyntax.PREFIX_VENUE) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEventCommand.MESSAGE_USAGE));
        }

        // Module module = ParserUtil.parseModule(argMultimap.getValue(PREFIX_MODULE).get());
        Name name = ParserUtil.parseName(argMultimap.getValue(CliSyntax.PREFIX_NAME).get());
        LocalDateTime startDateTime = ParserUtil.parseDateTime(argMultimap.getValue(CliSyntax.PREFIX_START_DATETIME).get());
        LocalDateTime endDateTime = ParserUtil.parseDateTime(argMultimap.getValue(CliSyntax.PREFIX_END_DATETIME).get());
        Location location = ParserUtil.parseLocation(argMultimap.getValue(CliSyntax.PREFIX_VENUE).get());
        boolean isRepeated = false;
        if (argMultimap.getValue(CliSyntax.PREFIX_REPEAT).isPresent()) {
            isRepeated = ParserUtil.parseRepeat(argMultimap.getValue(CliSyntax.PREFIX_REPEAT).get());
        }
        LocalDate endRepeatDate = null;
        EventType eventType = ParserUtil.parseEventType(name.toString());
        if (isRepeated && argMultimap.getValue(CliSyntax.PREFIX_STOP_REPEAT).isPresent()) {
            endRepeatDate = ParserUtil.parseDate(argMultimap.getValue(CliSyntax.PREFIX_STOP_REPEAT).get());
        }
        TemporalAmount frequency = Period.ofDays(7);
        if (!argMultimap.getValue(CliSyntax.PREFIX_MODULE).isPresent()) {
            return new AddEventCommand(name, startDateTime, endDateTime, location, isRepeated,
                    endRepeatDate, eventType, frequency);
        }
        ModuleCode moduleCode = ParserUtil.parseModuleCode(argMultimap.getValue(CliSyntax.PREFIX_MODULE).get());
        Module module = new PartialModule(moduleCode);
        Event event = new PartialEvent(name, eventType, startDateTime, endDateTime, location, module);
        return new AddEventCommand(event, isRepeated, endRepeatDate, frequency);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given {@code
     * ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }


}
