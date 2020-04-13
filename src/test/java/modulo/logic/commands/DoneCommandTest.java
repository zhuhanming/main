package modulo.logic.commands;

import static modulo.testutil.Assert.assertThrows;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import modulo.commons.core.index.Index;
import modulo.logic.commands.exceptions.CommandException;
import modulo.model.Model;
import modulo.model.ModelManager;
import modulo.model.Name;
import modulo.model.UserPrefs;
import modulo.model.event.Event;
import modulo.model.event.EventType;
import modulo.model.event.Location;
import modulo.model.module.AcademicYear;
import modulo.model.module.Module;
import modulo.model.module.ModuleCode;
import modulo.testutil.module.TypicalModules;

public class DoneCommandTest {

    private final Model model = new ModelManager(TypicalModules.getTypicalModulo(), new UserPrefs());

    @Test
    public void execute_invalidModule_throwsCommandException() {
        DoneCommand doneCommand = new DoneCommand(new Module(new ModuleCode("AB1234"), new Name("InvalidModule"),
                new AcademicYear(2019, 2020, 2), "InvalidModule",
                new ArrayList<>()), null, Index.fromOneBased(1));
        assertThrows(CommandException.class, () -> doneCommand.execute(model));
    }

    @Test
    public void execute_invalidEvent_throwsCommandException() {
        Module module = model.getModule(new ModuleCode("CS2103"), AcademicYear.now()).orElseThrow();
        Event event = new Event(new Name("TestEvent"), EventType.TUTORIAL, LocalDateTime.now(), LocalDateTime.now(),
                module, new Location("TestLocation"));
        DoneCommand doneCommand = new DoneCommand(module, event, Index.fromOneBased(1));
        assertThrows(CommandException.class, () -> doneCommand.execute(model));
    }
}
