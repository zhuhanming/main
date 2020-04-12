package modulo.logic.parser;

import static modulo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static modulo.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static modulo.testutil.Assert.assertThrows;
import static modulo.testutil.TypicalIndexesUtils.INDEX_FIRST_ITEM;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import modulo.logic.commands.AddModuleCommand;
import modulo.logic.commands.DeleteCommand;
import modulo.logic.commands.ExitCommand;
import modulo.logic.commands.FindCommand;
import modulo.logic.commands.HelpCommand;
import modulo.logic.commands.ListCommand;
import modulo.logic.parser.exceptions.ParseException;
import modulo.logic.predicate.NameContainsKeywordsPredicate;
import modulo.model.module.Module;
import modulo.testutil.module.ModuleBuilder;
import modulo.testutil.module.ModuleUtil;

public class ModuloParserTest {

    private final ModuloParser parser = new ModuloParser();

    @Test
    public void parseCommand_add() throws Exception {
        Module module = new ModuleBuilder().build();
        AddModuleCommand command = (AddModuleCommand) parser.parseCommand(ModuleUtil.getAddCommand(module));
        assertEquals(new AddModuleCommand(module.getModuleCode(), module.getAcademicYear()), command);
    }
    //TODO: Wait for clear command to finish

    /**
     * @Test public void parseCommand_clear() throws Exception {
     * assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
     * assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
     * }
     */

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_ITEM.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_ITEM, false), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + keywords.get(0));
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords.get(0))), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " modules") instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " events") instanceof ListCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                HelpCommand.MESSAGE_USAGE), () -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }
}
