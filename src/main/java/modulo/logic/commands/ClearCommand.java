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

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setModulo(new Modulo());
        return new CommandResult(MESSAGE_MODULO_CLEARED);
    }
}
