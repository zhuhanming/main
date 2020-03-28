package seedu.address.logic.commands;

import java.io.IOException;
import java.nio.file.Path;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.handler.IcsParser;
import seedu.address.model.Model;

/**
 * Exports current calendar to ics file
 */
public class ExportCommand extends Command {

    public static final String COMMAND_WORD = "export";

    public static final String MESSAGE_SUCCESS = "Calendar successfully exported to .ics file";

    public static final String MESSAGE_FAILED_EXPORT = "Failed to export calendar";

    private Path exportDirectory;

    public ExportCommand() {

    }

    public ExportCommand(Path exportDirectory) {
        this.exportDirectory = exportDirectory;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {

        if (exportDirectory == null) {
            exportDirectory = model.getUserPrefs().getIcsFilePath();
        }

        try {
            IcsParser.generateIcsFile(exportDirectory, model.getCalendar().getEventList());
        } catch (IOException e) {
            e.printStackTrace();
            throw new CommandException(String.format(MESSAGE_FAILED_EXPORT));
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS));
    }
}
