package modulo.logic.parser;

import static modulo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static modulo.logic.parser.CliSyntax.PREFIX_END_DATETIME;
import static modulo.logic.parser.CliSyntax.PREFIX_FREQUENCY;
import static modulo.logic.parser.CliSyntax.PREFIX_MODULE;
import static modulo.logic.parser.CliSyntax.PREFIX_NAME;
import static modulo.logic.parser.CliSyntax.PREFIX_REPEAT;
import static modulo.logic.parser.CliSyntax.PREFIX_START_DATETIME;
import static modulo.logic.parser.CliSyntax.PREFIX_STOP_REPEAT;
import static modulo.logic.parser.CliSyntax.PREFIX_VENUE;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAmount;
import java.util.function.Supplier;
import java.util.stream.Stream;

import modulo.logic.commands.AddEventCommand;
import modulo.logic.parser.exceptions.ParseException;
import modulo.model.Name;
import modulo.model.event.Event;
import modulo.model.event.EventType;
import modulo.model.event.Location;
import modulo.model.event.PartialEvent;
import modulo.model.module.Module;
import modulo.model.module.ModuleCode;
import modulo.model.module.PartialModule;


/**
 * Parses input arguments and creates a new AddEventCommand object
 */
public class AddEventCommandParser implements Parser<AddEventCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddEventCommand and returns an AddEventCommand
     * object for execution.
     *
     * @param args Arguments passed in by the user.
     * @return {@code AddEventCommand} to execute.
     * @throws ParseException if the user input does not conform the expected format.
     */
    public AddEventCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_MODULE, PREFIX_NAME,
                PREFIX_START_DATETIME, PREFIX_END_DATETIME,
                PREFIX_VENUE, PREFIX_REPEAT, PREFIX_FREQUENCY, PREFIX_STOP_REPEAT);

        Supplier<ParseException> parseExceptionSupplier = () -> new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEventCommand.MESSAGE_USAGE));

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_START_DATETIME, PREFIX_END_DATETIME, PREFIX_VENUE)
                || !argMultimap.getPreamble().isEmpty()) {
            parseExceptionSupplier.get();
        }

        // Defensive getting of optionals
        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).orElseThrow(parseExceptionSupplier));
        LocalDateTime startDateTime = ParserUtil.parseDateTime(argMultimap.getValue(PREFIX_START_DATETIME)
                .orElseThrow(parseExceptionSupplier));
        LocalDateTime endDateTime = ParserUtil.parseDateTime(argMultimap.getValue(PREFIX_END_DATETIME)
                .orElseThrow(parseExceptionSupplier));
        Location location = ParserUtil.parseLocation(argMultimap.getValue(PREFIX_VENUE)
                .orElseThrow(parseExceptionSupplier));
        boolean isRepeated = ParserUtil.parseRepeat(argMultimap.getValue(PREFIX_REPEAT).orElse("NO"));
        EventType eventType = ParserUtil.parseEventType(name.toString());

        LocalDate endRepeatDate = null;

        if (isRepeated && argMultimap.getValue(CliSyntax.PREFIX_STOP_REPEAT).isPresent()) {
            endRepeatDate = ParserUtil.parseDate(argMultimap.getValue(CliSyntax.PREFIX_STOP_REPEAT).get());
        }
        TemporalAmount frequency = ParserUtil.parseFrequency(argMultimap.getValue(PREFIX_FREQUENCY).orElse("1"));

        if (argMultimap.getValue(PREFIX_MODULE).isEmpty()) {
            return new AddEventCommand(name, startDateTime, endDateTime, location, isRepeated,
                    endRepeatDate, eventType, frequency);
        }
        ModuleCode moduleCode = ParserUtil.parseModuleCode(argMultimap.getValue(PREFIX_MODULE).get());
        Module module = new PartialModule(moduleCode);
        Event event = new PartialEvent(name, eventType, startDateTime, endDateTime, location, module);
        return new AddEventCommand(event, isRepeated, endRepeatDate, frequency, null);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given {@code
     * ArgumentMultimap}.
     *
     * @param argumentMultimap Multimap containing the arguments.
     * @param prefixes         Prefixes to check.
     * @return Boolean denoting whether the prefixes are present.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
