package modulo.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import modulo.logic.commands.exceptions.CommandException;
import modulo.model.ModelManager;

public class ExportCommandTest {

    @Test
    public void execute_nullDirectory_success() throws CommandException {
        ExportCommand exportCommand = new ExportCommand(null);
        exportCommand.execute(new ModelManager());
        assertEquals(exportCommand.getExportDirectory(), Paths.get("data"));
    }
}
