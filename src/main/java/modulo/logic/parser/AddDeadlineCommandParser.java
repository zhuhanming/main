package modulo.logic.parser;

import static modulo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.stream.Stream;

import modulo.logic.commands.AddDeadlineCommand;
import modulo.logic.parser.exceptions.ParseException;
import modulo.model.deadline.Deadline;
import modulo.model.event.Event;
import modulo.model.module.Module;
import modulo.model.Name;
import modulo.model.event.EventType;
import modulo.model.event.Location;
import modulo.model.event.PartialEvent;
import modulo.model.module.ModuleCode;
import modulo.model.module.PartialModule;


/**
 * Parses input arguments and creates a new AddDeadlineCommand object
 */
public class AddDeadlineCommandParser implements Parser<AddDeadlineCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand and returns an AddCommand object
     * for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddDeadlineCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, CliSyntax.PREFIX_MODULE, CliSyntax.PREFIX_EVENT, CliSyntax.PREFIX_NAME, CliSyntax.PREFIX_REPEAT);

        if (!arePrefixesPresent(argMultimap, CliSyntax.PREFIX_NAME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddDeadlineCommand.MESSAGE_USAGE));
        }

        boolean isRepeated = false;
        if (argMultimap.getValue(CliSyntax.PREFIX_REPEAT).isPresent()) {
            isRepeated = ParserUtil.parseRepeat(argMultimap.getValue(CliSyntax.PREFIX_REPEAT).get());
        }
        Name name = ParserUtil.parseName(argMultimap.getValue(CliSyntax.PREFIX_NAME).get());

        ModuleCode moduleCode = null;
        if (argMultimap.getValue(CliSyntax.PREFIX_MODULE).isPresent()) {
            moduleCode = ParserUtil.parseModuleCode(argMultimap.getValue(CliSyntax.PREFIX_MODULE).get());
        }
        Name eventName = null;
        if (argMultimap.getValue(CliSyntax.PREFIX_MODULE).isPresent()) {
            eventName = ParserUtil.parseName(argMultimap.getValue(CliSyntax.PREFIX_EVENT).get());
        }

        if (moduleCode == null && eventName == null) {
            return new AddDeadlineCommand(name, isRepeated);
        }

        EventType eventType = ParserUtil.parseEventType(name.toString());

        Module module = new PartialModule(moduleCode);
        Event event = new PartialEvent(eventName, eventType, module, new Location("Arbitrary Location"));
        Deadline deadline = new Deadline(name, event);

        return new AddDeadlineCommand(deadline, event, isRepeated);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given {@code
     * ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
