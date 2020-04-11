package modulo.logic.commands.module;

import static java.util.Objects.requireNonNull;
import static modulo.commons.core.Messages.MESSAGE_DUPLICATE_MODULE;
import static modulo.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import modulo.commons.core.GuiSettings;
import modulo.logic.commands.AddModuleCommand;
import modulo.logic.commands.CommandResult;
import modulo.logic.commands.exceptions.CommandException;
import modulo.model.Model;
import modulo.model.Modulo;
import modulo.model.ReadOnlyModulo;
import modulo.model.ReadOnlyUserPrefs;
import modulo.model.displayable.Displayable;
import modulo.model.displayable.DisplayableType;
import modulo.model.event.Event;
import modulo.model.event.EventType;
import modulo.model.module.AcademicYear;
import modulo.model.module.Module;
import modulo.model.module.ModuleCode;
import modulo.model.module.ModuleLibrary;
import modulo.testutil.module.ModuleBuilder;

/**
 * class to test on adding module command
 */
public class AddModuleCommandTest {

    @Test
    public void constructor_nullModule_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddModuleCommand(null,
                null));
    }

    @Test
    public void equals() {
        Module cs2103 = new ModuleBuilder().withModuleCode("CS2103").build();
        Module cs2105 = new ModuleBuilder().withModuleCode("CS2105").build();
        AddModuleCommand addCS2103Command = new AddModuleCommand(cs2103.getModuleCode(), cs2103.getAcademicYear());
        AddModuleCommand addCS2105Command = new AddModuleCommand(cs2105.getModuleCode(), cs2105.getAcademicYear());

        // same object -> returns true
        assertTrue(addCS2103Command.equals(addCS2103Command));

        // same values -> returns true
        AddModuleCommand addCS2105CommandCopy = new AddModuleCommand(cs2105.getModuleCode(),
                cs2105.getAcademicYear());
        assertTrue(addCS2105Command.equals(addCS2105CommandCopy));

        // different types -> returns false
        assertFalse(addCS2103Command.equals(1));

        // null -> returns false
        assertFalse(addCS2103Command.equals(null));

        // different person -> returns false
        assertFalse(addCS2103Command.equals(addCS2105Command));
    }

    @Test
    public void execute_personAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingModuleAdded modelStub = new ModelStubAcceptingModuleAdded();
        Module validModule = new ModuleBuilder().build();

        CommandResult commandResult = new AddModuleCommand(validModule.getModuleCode(),
                validModule.getAcademicYear()).execute(modelStub);
        List<EventType> eventTypeList = ModuleLibrary.getEventTypesOfModule(validModule);
        EventType firstEventType = eventTypeList.get(0);
        assertEquals(String.format(AddModuleCommand.MESSAGE_SUCCESS + "\n\nEnter slot for "
                + validModule.getModuleCode().toString()
                + " " + firstEventType.toString() + ":", validModule), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validModule), modelStub.modulesAdded);
    }

    @Test
    public void execute_duplicateModule_throwsCommandException() {
        Module validModule = new ModuleBuilder().build();
        AddModuleCommand addModuleCommand = new AddModuleCommand(validModule.getModuleCode(),
                validModule.getAcademicYear());
        ModelStub modelStub = new ModelStubWithModule(validModule);
        assertThrows(CommandException.class, MESSAGE_DUPLICATE_MODULE, () -> addModuleCommand.execute(modelStub));
    }


    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {

        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getModuloFilePath() {
            return null;
        }

        @Override
        public void setModuloFilePath(Path moduloFilePath) {

        }

        @Override
        public Event findEvent(Event event) {
            return null;
        }

        @Override
        public Module findModule(Module module) {
            return null;
        }

        @Override
        public void addModule(ModuleCode moduleCode, AcademicYear academicYear) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Optional<Module> getModule(ModuleCode moduleCode, AcademicYear academicYear) {
            return Optional.empty();
        }

        @Override
        public void setEvent(Event target, Event editedEvent) {

        }

        @Override
        public void setModulo(ReadOnlyModulo newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyModulo getModulo() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasEvent(Event event) {
            return false;
        }

        @Override
        public boolean hasModule(ModuleCode moduleCode, AcademicYear academicYear) {
            return false;
        }

        @Override
        public void deleteEvent(Event event) {

        }

        @Override
        public void deleteModule(Module module) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addEvent(Event event) {

        }

        @Override
        public void setModule(Module target, Module editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Event> getFilteredEventList() {
            return null;
        }

        @Override
        public ObservableList<Module> getFilteredModuleList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredEventList(Predicate<Event> predicate) {

        }

        @Override
        public void updateFilteredModuleList(Predicate<Module> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<? extends Displayable> getFilteredFocusedList() {
            return null;
        }

        @Override
        public Displayable getFocusedDisplayable() {
            return null;
        }

        @Override
        public void setFilteredFocusedList(DisplayableType displayableType) {

        }

        @Override
        public void setFocusedDisplayable(Displayable displayable) {

        }

        @Override
        public DisplayableType getCurrentDisplayableType() {
            return null;
        }

        @Override
        public void setFilteredFocusedListShowAll(DisplayableType displayableType) {

        }

        @Override
        public boolean isSameFocusedDisplayable(Displayable displayable) {
            return false;
        }

        @Override
        public void updateFilteredDisplayableList(Predicate<Displayable> predicate) {

        }

        @Override
        public Object[] getFilteredDisplayableList(Predicate<Displayable> predicate) {
            return new Object[0];
        }

        @Override
        public void unsetFocusedDisplayable() {

        }

        @Override
        public String checkCurrentCalendar() {
            return null;
        }

        @Override
        public List<Event> findAllEvents(Event toFind) {
            return null;
        }
    }

    /**
     * A Model stub that contains a single person.
     */
    private class ModelStubWithModule extends ModelStub {

        private final Module module;

        ModelStubWithModule(Module module) {
            requireNonNull(module);
            this.module = module;
        }

        @Override
        public boolean hasModule(ModuleCode moduleCode, AcademicYear academicYear) {
            requireNonNull(moduleCode);
            requireNonNull(academicYear);
            //TODO: check if this correct, ,manually create a module the do compare cuz
            // hasModule does not take in module object as input param,
            String ac = (academicYear.getStartYear()) + "/" + (academicYear.getEndYear());
            Module m = new ModuleBuilder().withModuleCode(moduleCode.moduleCode)
                    .withAcademicYear(academicYear.getStartYear(), academicYear.getEndYear(),
                            academicYear.getSemester()).build();
            return module.isSameModule(m);
        }
    }

    /**
     * A Model stub that always accept the person being added.
     */
    private class ModelStubAcceptingModuleAdded extends ModelStub {
        final ArrayList<Module> modulesAdded = new ArrayList<>();
        // TODO: Need Review on this part, have to use Modulo to add module otherwise
        //  it will throw module does not exists.
        private final Modulo m = new Modulo();

        //TODO: Review on this part - addModule in Model does not take a Module object
        // Have to create or retrieve from modulo.
        @Override
        public void addModule(ModuleCode moduleCode, AcademicYear academicYear) {
            requireNonNull(moduleCode);
            requireNonNull(academicYear);
            String ac = (academicYear.getStartYear()) + "/" + (academicYear.getEndYear());
            Module module = new ModuleBuilder().withModuleCode(moduleCode.moduleCode)
                    .withAcademicYear(academicYear.getStartYear(),
                            academicYear.getEndYear(), academicYear.getSemester()).build();
            modulesAdded.add(module);
            m.addModule(moduleCode, academicYear);
        }

        @Override
        public Optional<Module> getModule(ModuleCode moduleCode, AcademicYear academicYear) {
            return m.getModule(moduleCode, academicYear);
        }

        @Override
        public ReadOnlyModulo getModulo() {
            return new Modulo();
        }
    }
}
