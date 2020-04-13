package modulo.logic.commands;

import static modulo.commons.core.Messages.MESSAGE_MODULO_CLEARED;
import static modulo.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.Test;

import modulo.model.Model;
import modulo.model.ModelManager;
import modulo.model.Modulo;
import modulo.model.UserPrefs;
import modulo.testutil.module.TypicalModules;

public class ClearCommandTest {

    private final Model model = new ModelManager(TypicalModules.getTypicalModulo(), new UserPrefs());

    @Test
    public void execute_clear_success() {
        ClearCommand clearCommand = new ClearCommand();
        Model expectedModel = new ModelManager(new Modulo(), model.getUserPrefs());
        assertCommandSuccess(clearCommand, model, new CommandResult(MESSAGE_MODULO_CLEARED, false, false,
                true, true, null, null), expectedModel);
    }
}
