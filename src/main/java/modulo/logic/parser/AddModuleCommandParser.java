package modulo.logic.parser;

import static modulo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static modulo.logic.parser.CliSyntax.PREFIX_ACADEMIC_YEAR;
import static modulo.logic.parser.CliSyntax.PREFIX_MODULE;
import static modulo.logic.parser.CliSyntax.PREFIX_SEMESTER;

import java.util.function.Supplier;
import java.util.stream.Stream;

import modulo.logic.commands.AddModuleCommand;
import modulo.logic.parser.exceptions.ParseException;
import modulo.model.module.AcademicYear;
import modulo.model.module.ModuleCode;
import modulo.model.module.exceptions.AcademicYearException;

/**
 * Parses input arguments and creates a new AddModuleCommand object
 */
public class AddModuleCommandParser implements Parser<AddModuleCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddModuleCommand and returns an
     * AddModuleCommand object for execution.
     *
     * @param args Arguments passed in by the user.
     * @return {@code AddModuleCommand} to execute.
     * @throws ParseException if the user input does not conform the expected format.
     */
    public AddModuleCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_MODULE);

        Supplier<ParseException> parseExceptionSupplier = () -> new ParseException(String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, AddModuleCommand.MESSAGE_USAGE));

        if (!arePrefixesPresent(argMultimap, PREFIX_MODULE) || !argMultimap.getPreamble().isEmpty()) {
            parseExceptionSupplier.get();
        }

        ModuleCode moduleCode = ParserUtil.parseModuleCode(argMultimap.getValue(PREFIX_MODULE)
                .orElseThrow(parseExceptionSupplier));

        AcademicYear academicYear = new AcademicYear(2019, 2020, 2);

        return new AddModuleCommand(moduleCode, academicYear);
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

    /**
     * Returns the academic year based on user input multimap.
     *
     * @param argumentMultimap User input multimap.
     * @return Academic year from the user input.
     * @throws AcademicYearException If the user input is invalid.
     */
    private static AcademicYear getAcademicYear(ArgumentMultimap argumentMultimap) throws AcademicYearException {
        AcademicYear academicYear = new AcademicYear(2019, 2020, 2);

        if (argumentMultimap.getValue(PREFIX_ACADEMIC_YEAR).isPresent()
                && argumentMultimap.getValue(PREFIX_SEMESTER).isPresent()) {
            academicYear = new AcademicYear(argumentMultimap.getValue(PREFIX_ACADEMIC_YEAR).get(),
                    argumentMultimap.getValue(PREFIX_SEMESTER).get());
        } else if (argumentMultimap.getValue(PREFIX_SEMESTER).isPresent()) {
            academicYear = new AcademicYear(new AcademicYear(2019, 2020, 2).getStartYear(), new AcademicYear(2019, 2020, 2).getEndYear(),
                    Integer.parseInt(argumentMultimap.getValue(PREFIX_SEMESTER).get()));
        } else if (argumentMultimap.getValue(PREFIX_ACADEMIC_YEAR).isPresent()) {
            academicYear = new AcademicYear(argumentMultimap.getValue(PREFIX_ACADEMIC_YEAR).get(),
                    String.valueOf(new AcademicYear(2019, 2020, 2).getSemester()));
        }
        return academicYear;
    }

}
