package modulo.logic.parser;

import static modulo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static modulo.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import modulo.commons.core.LogsCenter;
import modulo.logic.commands.AddDeadlineCommand;
import modulo.logic.commands.AddEventCommand;
import modulo.logic.commands.AddModuleCommand;
import modulo.logic.commands.ClearCommand;
import modulo.logic.commands.Command;
import modulo.logic.commands.DeleteCommand;
import modulo.logic.commands.DoneCommand;
import modulo.logic.commands.ExitCommand;
import modulo.logic.commands.ExportCommand;
import modulo.logic.commands.FindCommand;
import modulo.logic.commands.HelpCommand;
import modulo.logic.commands.ListCommand;
import modulo.logic.commands.ViewCommand;
import modulo.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class ModuloParser {
    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    private final Logger logger = LogsCenter.getLogger(ModuloParser.class);

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        logger.info("----------------[COMMAND WORD][" + commandWord + "]");
        logger.info("----------------[ARGUMENTS][" + arguments + "]");
        switch (commandWord) {

        case AddDeadlineCommand.COMMAND_WORD:
            return new AddDeadlineCommandParser().parse(arguments);

        case AddEventCommand.COMMAND_WORD:
            return new AddEventCommandParser().parse(arguments);

        case AddModuleCommand.COMMAND_WORD:
            return new AddModuleCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case DoneCommand.COMMAND_WORD:
            return new DoneCommandParser().parse(arguments);

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case ExportCommand.COMMAND_WORD:
            return new ExportCommandParser().parse(arguments);

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case ListCommand.COMMAND_WORD:
            return new ListCommandParser().parse(arguments);

        case ViewCommand.COMMAND_WORD:
            return new ViewCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }
}
