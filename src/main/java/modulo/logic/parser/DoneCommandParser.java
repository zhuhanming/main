package modulo.logic.parser;

import static modulo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static modulo.logic.parser.CliSyntax.PREFIX_EVENT;
import static modulo.logic.parser.CliSyntax.PREFIX_MODULE;

import java.util.function.Supplier;

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
 * Parser user input arguments to create a {@code DoneCommand} that completes a certain deadline.
 */
public class DoneCommandParser implements Parser<DoneCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the DoneCommand and returns a DoneCommand object
     * for execution.
     *
     * @param args Arguments passed in by the user.
     * @return {@code DoneCommand} to execute.
     * @throws ParseException if the user input does not conform the expected format.
     */
    public DoneCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_MODULE, PREFIX_EVENT);

        Supplier<ParseException> parseExceptionSupplier = () -> new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DoneCommand.MESSAGE_USAGE));

        Index index;
        try {
            int indexInteger = Integer.parseInt(argMultimap.getPreamble());
            index = ParserUtil.parseIndex(Integer.toString(indexInteger));
        } catch (NumberFormatException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DoneCommand.MESSAGE_USAGE), pe);
        }

        ModuleCode moduleCode = null;
        Name eventName = null;

        if (argMultimap.getValue(PREFIX_MODULE).isPresent()) {
            moduleCode = ParserUtil.parseModuleCode(argMultimap.getValue(PREFIX_MODULE).get());
        }
        if (argMultimap.getValue(PREFIX_EVENT).isPresent()) {
            eventName = ParserUtil.parseName(argMultimap.getValue(PREFIX_EVENT).get());
        }
        if (moduleCode == null && eventName == null) {
            return new DoneCommand(null, null, index);
        } else if (moduleCode == null || eventName == null) {
            parseExceptionSupplier.get();
        }
        PartialModule module = new PartialModule(moduleCode);
        assert eventName != null;
        EventType eventType = ParserUtil.parseEventType(eventName.toString());
        PartialEvent event = new PartialEvent(eventName, eventType, module, new Location("Arbitrary Location"));
        return new DoneCommand(module, event, index);
    }
}


