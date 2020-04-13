package modulo.logic.commands;

import static java.util.Objects.requireNonNull;
import static modulo.commons.core.Messages.MESSAGE_MODULO_CLEARED;

import modulo.model.Model;
import modulo.model.Modulo;

/**
 * Clears Modulo.
 */
public class ClearCommand extends Command {
    public static final String COMMAND_WORD = "clear";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Clears all data from Modulo.\n"
            + "Example: " + COMMAND_WORD;

    /**
     * Returns the result after execution of ClearCommand
     *
     * @param model Model of modulo
     * @return CommandResult containing info regarding execution of the command
     */
    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setModulo(new Modulo());
        return new CommandResult(MESSAGE_MODULO_CLEARED, false, false, true, true, null, null);
    }
}
