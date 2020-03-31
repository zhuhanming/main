package modulo.logic.parser;

import static modulo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.stream.Stream;

import modulo.logic.parser.exceptions.ParseException;
import modulo.model.Name;
import modulo.model.event.EventType;
import modulo.model.event.PartialEvent;
import modulo.model.module.ModuleCode;
import modulo.model.module.PartialModule;
import modulo.commons.core.index.Index;
import modulo.logic.commands.DoneCommand;
import modulo.model.event.Location;

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
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, CliSyntax.PREFIX_MODULE, CliSyntax.PREFIX_EVENT);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
            System.out.println("index " + index.getOneBased());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DoneCommand.MESSAGE_USAGE), pe);
        }

        ModuleCode moduleCode = null;
        Name name = null;

        if (argMultimap.getValue(CliSyntax.PREFIX_MODULE).isPresent()) {
            moduleCode = ParserUtil.parseModuleCode(argMultimap.getValue(CliSyntax.PREFIX_MODULE).get());
        }
        if (argMultimap.getValue(CliSyntax.PREFIX_EVENT).isPresent()) {
            name = ParserUtil.parseName(argMultimap.getValue(CliSyntax.PREFIX_EVENT).get());
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


