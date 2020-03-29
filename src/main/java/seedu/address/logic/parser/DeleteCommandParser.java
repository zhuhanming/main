package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.predicate.NameContainsKeywordsPredicate;

import java.util.Arrays;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteCommandParser implements Parser<DeleteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     */
    public DeleteCommand parse(String args) throws ParseException{
        if (args.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
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
