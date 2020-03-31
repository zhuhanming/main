package modulo.logic.parser;

import static modulo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.stream.Stream;

import modulo.logic.commands.AddModuleCommand;
import modulo.logic.parser.exceptions.ParseException;
import modulo.model.module.AcademicYear;
import modulo.model.module.ModuleCode;

/**
 * Parses input arguments and creates a new AddModuleCommand object
 */
public class AddModuleCommandParser implements Parser<AddModuleCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand and returns an AddCommand object
     * for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddModuleCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, CliSyntax.PREFIX_MODULE, CliSyntax.PREFIX_ACADEMIC_YEAR, CliSyntax.PREFIX_SEMESTER);

        if (!arePrefixesPresent(argMultimap, CliSyntax.PREFIX_MODULE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddModuleCommand.MESSAGE_USAGE));
        }

        ModuleCode moduleCode = ParserUtil.parseModuleCode(argMultimap.getValue(CliSyntax.PREFIX_MODULE).get());
        AcademicYear academicYear = AcademicYear.now();

        if (argMultimap.getValue(CliSyntax.PREFIX_ACADEMIC_YEAR).isPresent()
                && argMultimap.getValue(CliSyntax.PREFIX_SEMESTER).isPresent()) {
            academicYear = new AcademicYear(argMultimap.getValue(CliSyntax.PREFIX_ACADEMIC_YEAR).get(),
                    argMultimap.getValue(CliSyntax.PREFIX_SEMESTER).get());
        } else if (argMultimap.getValue(CliSyntax.PREFIX_SEMESTER).isPresent()) {
            academicYear = new AcademicYear(AcademicYear.now().getStartYear(),
                    Integer.parseInt(argMultimap.getValue(CliSyntax.PREFIX_SEMESTER).get()));
        } else if (argMultimap.getValue(CliSyntax.PREFIX_ACADEMIC_YEAR).isPresent()) {
            academicYear = new AcademicYear(argMultimap.getValue(CliSyntax.PREFIX_ACADEMIC_YEAR).get(),
                    String.valueOf(AcademicYear.now().getSemester()));
        }

        return new AddModuleCommand(moduleCode, academicYear);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given {@code
     * ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }


}
