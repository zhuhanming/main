package seedu.address.logic.parser;//package seedu.address.logic.parser;
//
//import seedu.address.logic.commands.AddEventCommand;
//import seedu.address.logic.commands.DoneCommand;
//import seedu.address.logic.parser.exceptions.ParseException;
//import seedu.address.model.entity.MatchableModule;
//
//import java.util.stream.Stream;
//
//import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.DoneCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.entity.CalendarItemName;
import seedu.address.model.entity.EventType;
import seedu.address.model.entity.MatchableEvent;
import seedu.address.model.entity.MatchableModule;

import java.util.stream.Stream;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.*;

public class DoneCommandParser implements Parser<DoneCommand> {
    public DoneCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_INDEX, PREFIX_MODULE, PREFIX_NAME);

        if (!arePrefixesPresent(argMultimap, PREFIX_INDEX, PREFIX_MODULE, PREFIX_NAME) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DoneCommand.MESSAGE_USAGE));
        }
        String moduleCode = ParserUtil.parseModuleCode(argMultimap.getValue(PREFIX_MODULE).get());
        CalendarItemName name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        String index = argMultimap.getValue(PREFIX_INDEX).get();
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


