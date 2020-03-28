package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REPEAT;

import java.util.stream.Stream;

import seedu.address.logic.commands.AddDeadlineCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Name;
import seedu.address.model.deadline.Deadline;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventType;
import seedu.address.model.event.Location;
import seedu.address.model.event.PartialEvent;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.PartialModule;


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
                ArgumentTokenizer.tokenize(args, PREFIX_MODULE, PREFIX_EVENT, PREFIX_NAME, PREFIX_REPEAT);

        if (!arePrefixesPresent(argMultimap, PREFIX_MODULE, PREFIX_EVENT, PREFIX_NAME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddDeadlineCommand.MESSAGE_USAGE));
        }

        ModuleCode moduleCode = ParserUtil.parseModuleCode(argMultimap.getValue(PREFIX_MODULE).get());
        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Name eventName = ParserUtil.parseName(argMultimap.getValue(PREFIX_EVENT).get());
        boolean isRepeated = false;
        if (argMultimap.getValue(PREFIX_REPEAT).isPresent()) {
            isRepeated = ParserUtil.parseRepeat(argMultimap.getValue(PREFIX_REPEAT).get());
        }

        // Todo: need to parse eventName to get eventType
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
