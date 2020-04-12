package modulo.logic.parser;

import static modulo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static modulo.logic.commands.CommandTestUtil.ACEDEMICYEAR_DESC_CS2103;
import static modulo.logic.commands.CommandTestUtil.ACEDEMICYEAR_DESC_CS2105;
import static modulo.logic.commands.CommandTestUtil.CODE_DESC_CS2103;
import static modulo.logic.commands.CommandTestUtil.CODE_DESC_CS2105;
import static modulo.logic.commands.CommandTestUtil.INVALID_CODE_DESC;
import static modulo.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static modulo.logic.commands.CommandTestUtil.SEMESTER_DESC_CS2103;
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
        assertParseSuccess(addModuleCommandParser, PREAMBLE_WHITESPACE + CODE_DESC_CS2103,
                new AddModuleCommand(expecteModule.getModuleCode(),
                        expecteModule.getAcademicYear()));

        // multiple code - last code accepted
        assertParseSuccess(addModuleCommandParser, CODE_DESC_CS2105 + CODE_DESC_CS2103,
                new AddModuleCommand(expecteModule.getModuleCode(), expecteModule.getAcademicYear()));

    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddModuleCommand.MESSAGE_USAGE);
        // missing module code prefix
        assertParseFailure(addModuleCommandParser, ACEDEMICYEAR_DESC_CS2103 + SEMESTER_DESC_CS2103,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid module code
        assertParseFailure(addModuleCommandParser, INVALID_CODE_DESC + ACEDEMICYEAR_DESC_CS2105 + SEMESTER_DESC_CS2103,
                ModuleCode.MESSAGE_CONSTRAINTS);
    }
}
