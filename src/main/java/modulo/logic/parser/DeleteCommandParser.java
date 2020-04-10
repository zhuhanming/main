package modulo.logic.parser;

import static modulo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static modulo.logic.parser.CliSyntax.PREFIX_DEADLINE;

import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Stream;

import modulo.commons.core.index.Index;
import modulo.logic.commands.DeleteCommand;
import modulo.logic.parser.exceptions.ParseException;
import modulo.logic.predicate.NameContainsKeywordsPredicate;


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

        String trimmedArgs = args.toLowerCase().trim();

        if (trimmedArgs.isEmpty() || "d/".equals(trimmedArgs)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_DEADLINE);

        if (arePrefixesPresent(argMultimap, PREFIX_DEADLINE)) {
            if (argMultimap.getValue(PREFIX_DEADLINE).get().equals("all")) {
                return new DeleteCommand(new NameContainsKeywordsPredicate(Collections.singletonList("")), true);
            }
            Index deadlineIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_DEADLINE).get());
            return new DeleteCommand(deadlineIndex, true);
        }

        try {
            int indexInteger = Integer.parseInt(trimmedArgs);
            Index index = ParserUtil.parseIndex(Integer.toString(indexInteger));
            return new DeleteCommand(index, false);
        } catch (NumberFormatException e) {
            if (trimmedArgs.equals("all")) {
                return new DeleteCommand(new NameContainsKeywordsPredicate(Collections.singletonList("")), false);
            }
            String[] nameKeywords = {trimmedArgs};
            return new DeleteCommand(new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords)), false);
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given {@code
     * ArgumentMultimap}.
     *
     * @param argumentMultimap Multimap containing the arguments.
     * @param prefixes         Prefixes to check.
     * @return Boolean denoting whether the prefixes are present.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}

