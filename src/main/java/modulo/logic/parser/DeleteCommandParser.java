package modulo.logic.parser;

import static modulo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import modulo.commons.core.index.Index;
import modulo.logic.commands.DeleteCommand;
import modulo.logic.parser.exceptions.ParseException;
import modulo.model.predicate.NameContainsKeywordsPredicate;


/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteCommandParser implements Parser<DeleteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand and returns a DeleteCommand
     * object for execution.
     *
     * @param args Arguments passed in by the user.
     * @return {@code DeleteCommand} to execute.
     * @throws ParseException if the user input does not conform the expected format.
     */
    public DeleteCommand parse(String args) throws ParseException {
        if (args.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteCommand(index);
        } catch (ParseException pe) {
            String trimmedArgs = args.toLowerCase().trim();
            if (trimmedArgs.equals("all")) {
                return new DeleteCommand(new NameContainsKeywordsPredicate(Arrays.asList("")));
            }
            String[] nameKeywords = {trimmedArgs};
            return new DeleteCommand(new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
        }
    }
}
