package modulo.logic.parser;

import static modulo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static modulo.logic.commands.CommandTestUtil.ACEDEMICYEAR_DESC_CS2103;
import static modulo.logic.commands.CommandTestUtil.ACEDEMICYEAR_DESC_CS2105;
import static modulo.logic.commands.CommandTestUtil.CODE_DESC_CS2103;
import static modulo.logic.commands.CommandTestUtil.CODE_DESC_CS2105;
import static modulo.logic.commands.CommandTestUtil.INVALID_ACEDEMICYEAR_DESC;
import static modulo.logic.commands.CommandTestUtil.INVALID_CODE_DESC;
import static modulo.logic.commands.CommandTestUtil.INVALID_SEMESTER_DESC;
import static modulo.logic.commands.CommandTestUtil.NAME_DESC_CS2103;
import static modulo.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static modulo.logic.commands.CommandTestUtil.SEMESTER_DESC_CS2103;
import static modulo.logic.commands.CommandTestUtil.SEMESTER_DESC_CS2105;
import static modulo.logic.commands.CommandTestUtil.VALID_ACADEMIC_END_YEAR_CS2105;
import static modulo.logic.commands.CommandTestUtil.VALID_ACADEMIC_START_YEAR_CS2105;
import static modulo.logic.commands.CommandTestUtil.VALID_CODE_CS2105;
import static modulo.logic.commands.CommandTestUtil.VALID_SEMESTER_CS2105;
import static modulo.logic.parser.CommandParserTestUtil.assertParseFailure;
import static modulo.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static modulo.testutil.module.TypicalModules.CS2103;

import org.junit.jupiter.api.Test;

import modulo.logic.commands.AddModuleCommand;
import modulo.model.module.Module;
import modulo.model.module.ModuleCode;
import modulo.testutil.module.ModuleBuilder;

public class AddModuleCommandParserTest {
    private AddModuleCommandParser addModuleCommandParser = new AddModuleCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {

        Module expecteModule = new ModuleBuilder(CS2103).build();

        // whitespace only preamble
        assertParseSuccess(addModuleCommandParser, PREAMBLE_WHITESPACE + CODE_DESC_CS2103 + ACEDEMICYEAR_DESC_CS2103
                + SEMESTER_DESC_CS2103, new AddModuleCommand(expecteModule.getModuleCode(),
                expecteModule.getAcademicYear()));

        // multiple code - last code accepted
        assertParseSuccess(addModuleCommandParser, CODE_DESC_CS2105 + CODE_DESC_CS2103
                        + ACEDEMICYEAR_DESC_CS2103 + SEMESTER_DESC_CS2103,
                new AddModuleCommand(expecteModule.getModuleCode(), expecteModule.getAcademicYear()));

        // multiple academic year - academic year accepted
        assertParseSuccess(addModuleCommandParser, CODE_DESC_CS2103 + ACEDEMICYEAR_DESC_CS2105
                        + ACEDEMICYEAR_DESC_CS2103 + SEMESTER_DESC_CS2103,
                new AddModuleCommand(expecteModule.getModuleCode(), expecteModule.getAcademicYear()));

        // multiple semester - last semester accepted
        assertParseSuccess(addModuleCommandParser, CODE_DESC_CS2103 + ACEDEMICYEAR_DESC_CS2103
                        + SEMESTER_DESC_CS2105 + SEMESTER_DESC_CS2103,
                new AddModuleCommand(expecteModule.getModuleCode(), expecteModule.getAcademicYear()));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {

        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddModuleCommand.MESSAGE_USAGE);

        // missing module code prefix
        assertParseFailure(addModuleCommandParser, ACEDEMICYEAR_DESC_CS2103 + SEMESTER_DESC_CS2103,
                expectedMessage);

        // missing academic year prefix
        assertParseFailure(addModuleCommandParser, NAME_DESC_CS2103 + SEMESTER_DESC_CS2103,
                expectedMessage);

        // missing semester prefix
        assertParseFailure(addModuleCommandParser, NAME_DESC_CS2103 + ACEDEMICYEAR_DESC_CS2103,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(addModuleCommandParser, VALID_CODE_CS2105 + VALID_ACADEMIC_START_YEAR_CS2105
                        + VALID_ACADEMIC_END_YEAR_CS2105 + VALID_SEMESTER_CS2105,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid module code
        assertParseFailure(addModuleCommandParser, INVALID_CODE_DESC + ACEDEMICYEAR_DESC_CS2105 + SEMESTER_DESC_CS2103,
                ModuleCode.MESSAGE_CONSTRAINTS);

        // TODO: continue when academicyeartest finish
        /** invalid AcademicYear
         assertParseFailure(addModuleCommandParser, CODE_DESC_CS2103 + INVALID_ACEDEMICYEAR_DESC + SEMESTER_DESC_CS2103,
         AcademicYear.MESSAGE_CONSTRAINTS);
         */

        // invalid semester
        assertParseFailure(addModuleCommandParser, CODE_DESC_CS2103 + ACEDEMICYEAR_DESC_CS2103 + INVALID_SEMESTER_DESC,
                "Semester must be either 1 or 2!");

        // two invalid values, only first invalid value reported
        assertParseFailure(addModuleCommandParser, INVALID_CODE_DESC
                        + INVALID_ACEDEMICYEAR_DESC + VALID_SEMESTER_CS2105,
                ModuleCode.MESSAGE_CONSTRAINTS);
    }
}
