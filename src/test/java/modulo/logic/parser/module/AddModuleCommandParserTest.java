package modulo.logic.parser.module;

import static modulo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static modulo.logic.commands.CommandTestUtil.CODE_DESC_CS2103;
import static modulo.logic.commands.CommandTestUtil.INVALID_CODE_DESC;
import static modulo.logic.commands.CommandTestUtil.VALID_ACADEMIC_END_YEAR_CS2103;
import static modulo.logic.commands.CommandTestUtil.VALID_ACADEMIC_START_YEAR_CS2103;
import static modulo.logic.commands.CommandTestUtil.VALID_CODE_CS2103;
import static modulo.logic.commands.CommandTestUtil.VALID_CODE_CS2105;
import static modulo.logic.commands.CommandTestUtil.VALID_SEMESTER_CS2103;
import static modulo.logic.parser.CommandParserTestUtil.assertParseFailure;
import static modulo.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import modulo.logic.commands.AddModuleCommand;
import modulo.logic.parser.AddModuleCommandParser;
import modulo.model.module.AcademicYear;
import modulo.model.module.ModuleCode;

/**
 * class to test on parser of module command.
 */
public class AddModuleCommandParserTest {
    private AddModuleCommandParser parser = new AddModuleCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        // whitespace only preamble
        assertParseSuccess(parser, CODE_DESC_CS2103,
                new AddModuleCommand(new ModuleCode(VALID_CODE_CS2103),
                        new AcademicYear(VALID_ACADEMIC_START_YEAR_CS2103,
                                VALID_ACADEMIC_END_YEAR_CS2103, VALID_SEMESTER_CS2103)));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {

        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddModuleCommand.MESSAGE_USAGE);

        // missing module code prefix
        assertParseFailure(parser, VALID_CODE_CS2105, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid module code
        assertParseFailure(parser, INVALID_CODE_DESC, ModuleCode.MESSAGE_CONSTRAINTS);
    }
}
