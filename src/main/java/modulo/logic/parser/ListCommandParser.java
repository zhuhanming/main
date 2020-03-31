package modulo.logic.parser;

import modulo.logic.commands.ListCommand;
import modulo.logic.parser.exceptions.ParseException;
import modulo.model.DisplayableType;


/**
 * Parses input arguments and creates a new ListCommand object
 */
public class ListCommandParser implements Parser<ListCommand> {


    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand and returns an AddCommand object
     * for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ListCommand parse(String args) throws ParseException {
        String[] arrOfArgs = args.toLowerCase().trim().split(" ");
        DisplayableType displayableType = ParserUtil.parseDisplayableType(arrOfArgs[0]);
        if (arrOfArgs.length > 1 && arrOfArgs[1].equals("all")) {
            return new ListCommand(displayableType, true);
        }
        return new ListCommand(displayableType, false);
    }
}


