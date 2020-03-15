package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DoneCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.entity.CalendarItemName;
import seedu.address.model.entity.EventType;
import seedu.address.model.entity.MatchableEvent;
import seedu.address.model.entity.MatchableModule;

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
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_MODULE, PREFIX_NAME);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
            System.out.println("index " + index.getOneBased());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DoneCommand.MESSAGE_USAGE), pe);
        }

        if (!arePrefixesPresent(argMultimap, PREFIX_MODULE, PREFIX_NAME)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DoneCommand.MESSAGE_USAGE));
        }

        String moduleCode = ParserUtil.parseModuleCode(argMultimap.getValue(PREFIX_MODULE).get());
        CalendarItemName name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());

        MatchableModule module = new MatchableModule(moduleCode);
        EventType eventType;
        if (name.toString().contains("Tutorial")) {
            eventType = EventType.TUTORIAL;
        } else if (name.toString().contains("Lab")) {
            eventType = EventType.LAB;
        } else {
            eventType = EventType.LECTURE;
        }
        MatchableEvent event = new MatchableEvent(name, eventType, module);
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


