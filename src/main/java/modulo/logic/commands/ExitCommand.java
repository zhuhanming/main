package modulo.logic.commands;

import static modulo.commons.core.Messages.MESSAGE_EXIT_ACKNOWLEDGEMENT;

import modulo.model.Model;

/**
 * Terminates the program.
 */
public class ExitCommand extends Command {

    public static final String COMMAND_WORD = "exit";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Exits Modulo.\n"
            + "Example: " + COMMAND_WORD;

    /**
     * Returns the result after execution of ClearCommand
     *
     * @param model Model of modulo
     * @return CommandResult containing info regarding execution of the command
     */
    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT, false, true, false, false, null, null);
    }

}
