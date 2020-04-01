package modulo.logic.commands;

import static modulo.commons.core.Messages.MESSAGE_SHOWING_HELP;

import modulo.model.Model;

/**
 * Displays the help window.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows program usage instructions.\n"
            + "Example: " + COMMAND_WORD;

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(MESSAGE_SHOWING_HELP, true, false, false, false, null);
    }
}
