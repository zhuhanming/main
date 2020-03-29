package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DoneCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Name;
import seedu.address.model.event.EventType;
import seedu.address.model.event.Location;
import seedu.address.model.event.PartialEvent;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.PartialModule;

/**
 * Parser done arguments to set a deadline to be done.
 */
public class DoneCommandParser implements Parser<DoneCommand> {
    /**
     * @param args
     * @return a DoneCommand class
     * @throws ParseException
     */
    public DoneCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_MODULE, PREFIX_EVENT);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
            System.out.println("index " + index.getOneBased());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DoneCommand.MESSAGE_USAGE), pe);
        }

        ModuleCode moduleCode = null;
        Name name = null;

        if (argMultimap.getValue(PREFIX_MODULE).isPresent()) {
            moduleCode = ParserUtil.parseModuleCode(argMultimap.getValue(PREFIX_MODULE).get());
        }
        if (argMultimap.getValue(PREFIX_EVENT).isPresent()) {
            name = ParserUtil.parseName(argMultimap.getValue(PREFIX_EVENT).get());
        }
        if (moduleCode == null && name == null) {
            return new DoneCommand(null, null, index);
        } else if (moduleCode == null || name == null) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DoneCommand.MESSAGE_USAGE));
        }
        PartialModule module = new PartialModule(moduleCode);
        EventType eventType = ParserUtil.parseEventType(name.toString());
        PartialEvent event = new PartialEvent(name, eventType, module, new Location("Arbitrary Location"));
        return new DoneCommand(module, event, index);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given {@code
     * ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}


