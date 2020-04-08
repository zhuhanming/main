package modulo.logic.parser.module;

import static modulo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static modulo.logic.commands.CommandTestUtil.ACEDEMICYEAR_DESC_CS2103;
import static modulo.logic.commands.CommandTestUtil.ACEDEMICYEAR_DESC_CS2105;
import static modulo.logic.commands.CommandTestUtil.CODE_DESC_CS2103;
import static modulo.logic.commands.CommandTestUtil.INVALID_ACEDEMICYEAR_DESC;
import static modulo.logic.commands.CommandTestUtil.INVALID_CODE_DESC;
import static modulo.logic.commands.CommandTestUtil.NAME_DESC_CS2105;
import static modulo.logic.commands.CommandTestUtil.SEMESTER_DESC_CS2103;
import static modulo.logic.commands.CommandTestUtil.SEMESTER_DESC_CS2105;
import static modulo.logic.commands.CommandTestUtil.VALID_ACADEMICYEAR_CS2103;
import static modulo.logic.commands.CommandTestUtil.VALID_ACADEMICYEAR_CS2105;
import static modulo.logic.commands.CommandTestUtil.VALID_CODE_CS2103;
import static modulo.logic.commands.CommandTestUtil.VALID_CODE_CS2105;
import static modulo.logic.commands.CommandTestUtil.VALID_SEMESTER_CS2103;
import static modulo.logic.commands.CommandTestUtil.VALID_SEMESTER_CS2105;
import static modulo.logic.parser.CommandParserTestUtil.assertParseFailure;
import static modulo.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static modulo.testutil.module.TypicalModule.CS2103;

import org.junit.jupiter.api.Test;

import modulo.logic.commands.AddModuleCommand;
import modulo.logic.parser.AddModuleCommandParser;
import modulo.logic.parser.exceptions.ParseException;
import modulo.model.module.AcademicYear;
import modulo.model.module.Module;
import modulo.model.module.ModuleCode;
import modulo.testutil.module.ModuleBuilder;

/**
 * class to test on parser of module command.
 */
public class AddModuleCommandParserTest {
    private AddModuleCommandParser parser = new AddModuleCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Module expectedModule = null;
        try {
            expectedModule = new ModuleBuilder(CS2103).build();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // whitespace only preamble
        try {
            assertParseSuccess(parser, CODE_DESC_CS2103
                            + ACEDEMICYEAR_DESC_CS2103
                            + SEMESTER_DESC_CS2103,
                    new AddModuleCommand(new ModuleCode(VALID_CODE_CS2103), new AcademicYear(VALID_ACADEMICYEAR_CS2103,
                            VALID_SEMESTER_CS2103)));
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {

        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddModuleCommand.MESSAGE_USAGE);

        // missing module code prefix
        assertParseFailure(parser, ACEDEMICYEAR_DESC_CS2105 + SEMESTER_DESC_CS2105,
                expectedMessage);

        // missing academic year prefix
        assertParseFailure(parser, NAME_DESC_CS2105 + SEMESTER_DESC_CS2105,
                expectedMessage);

        // missing semester prefix
        assertParseFailure(parser, NAME_DESC_CS2105 + ACEDEMICYEAR_DESC_CS2105,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_CODE_CS2105 + VALID_ACADEMICYEAR_CS2105 + VALID_SEMESTER_CS2105,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid module code
        assertParseFailure(parser, INVALID_CODE_DESC + ACEDEMICYEAR_DESC_CS2105 + SEMESTER_DESC_CS2103,
                ModuleCode.MESSAGE_CONSTRAINTS);

        /** invalid semester
         assertParseFailure(parser, CODE_DESC_CS2105 + INVALID_ACEDEMICYEAR_DESC + VALID_SEMESTER_CS2105,
         AcademicYear.MESSAGE_CONSTRAINTS);

         assertParseFailure(parser, CODE_DESC_CS2105 + ACEDEMICYEAR_DESC_CS2105 + INVALID_SEMESTER_DESC,
         AcademicYear.MESSAGE_CONSTRAINTS);
         */

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_CODE_DESC + INVALID_ACEDEMICYEAR_DESC + VALID_SEMESTER_CS2105,
                ModuleCode.MESSAGE_CONSTRAINTS);
    }
}
