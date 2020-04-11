package modulo.logic.commands;

import static modulo.commons.core.Messages.MESSAGE_EXIT_ACKNOWLEDGEMENT;
import static modulo.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.Test;

import modulo.model.Model;
import modulo.model.ModelManager;

/**
 * class to test on exit commands
 */
public class ExitCommandTestUtil {

    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void execute_exit_success() {
        CommandResult expectedCommandResult = new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT, false,
                true, false, false, null);
        assertCommandSuccess(new ExitCommand(), model, expectedCommandResult, expectedModel);
    }
}
