package modulo.logic.commands;

import static modulo.commons.core.Messages.MESSAGE_EXPORT_FAILED;
import static modulo.commons.core.Messages.MESSAGE_EXPORT_SUCCESS;
import static modulo.logic.parser.CliSyntax.PREFIX_DIRECTORY;

import java.io.IOException;
import java.nio.file.Path;

import modulo.logic.commands.exceptions.CommandException;
import modulo.logic.writer.IcsWriter;
import modulo.model.Model;

/**
 * Exports current Modulo to a .ics file.
 */
public class ExportCommand extends Command {

    public static final String COMMAND_WORD = "export";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Exports Modulo as a .ics calendar file.\n"
            + "Parameters: "
            + "[" + PREFIX_DIRECTORY + "DIRECTORY]"
            + "\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_DIRECTORY + "data/ ";

    private Path exportDirectory;

    /**
     * Creates an {@code ExportCommand} without an explicit export directory. The file will be exported to the default
     * directory.
     */
    public ExportCommand() {
    }

    /**
     * Creates an {@code ExportCommand} with a specified export directory.
     *
     * @param exportDirectory Directory to export to.
     */
    public ExportCommand(Path exportDirectory) {
        this.exportDirectory = exportDirectory;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {

        if (exportDirectory == null) {
            exportDirectory = model.getUserPrefs().getIcsFilePath();
        }
        try {
            IcsWriter.writeIcsFile(exportDirectory, model.getModulo().getEventList());
        } catch (IOException e) {
            e.printStackTrace();
            throw new CommandException(MESSAGE_EXPORT_FAILED);
        }
        return new CommandResult(MESSAGE_EXPORT_SUCCESS);
    }

    public Path getExportDirectory() {
        return exportDirectory;
    }

    @Override
    public boolean equals(Object other) {
        ExportCommand otherCommand = (ExportCommand) other;

        if (this.exportDirectory == null && otherCommand.exportDirectory == null) {
            return true;
        }
        return other == this // short circuit if same object
                || (other instanceof ExportCommand // instanceof handles nulls
                && (exportDirectory.equals(otherCommand.exportDirectory))); // state check
    }
}
