package modulo.logic.parser;

import static modulo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static modulo.logic.parser.CliSyntax.PREFIX_EVENT;
import static modulo.logic.parser.CliSyntax.PREFIX_MODULE;

import java.util.stream.Stream;

import modulo.commons.core.index.Index;
import modulo.logic.commands.DoneCommand;
import modulo.logic.parser.exceptions.ParseException;
import modulo.model.Name;
import modulo.model.event.EventType;
import modulo.model.event.Location;
import modulo.model.event.PartialEvent;
import modulo.model.module.ModuleCode;
import modulo.model.module.PartialModule;

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


