package modulo.logic.commands;

import static java.util.Objects.requireNonNull;
import static modulo.commons.core.Messages.MESSAGE_DUPLICATE_MODULE;
import static modulo.testutil.Assert.assertThrows;
import static modulo.testutil.module.TypicalModules.CS2103;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import modulo.commons.core.GuiSettings;
import modulo.logic.commands.exceptions.CommandException;
import modulo.model.Model;
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
                new AcademicYear(2019, 2020, 2)));
        assertThrows(NullPointerException.class, () -> new AddModuleCommand(CS2103.getModuleCode(),
                null));
    }

    @Test
    public void testEquals() {
        Module cs2103 = new ModuleBuilder().withModuleCode("CS2103").build();
        Module cs2105 = new ModuleBuilder().withModuleCode("CS2105").build();
        AddModuleCommand addCS2103Command = new AddModuleCommand(cs2103.getModuleCode(), cs2103.getAcademicYear());
        AddModuleCommand addCS2105Command = new AddModuleCommand(cs2105.getModuleCode(), cs2105.getAcademicYear());

        // same object -> returns true
        assertEquals(addCS2103Command, addCS2103Command);

        // same values -> returns true
        AddModuleCommand addCS2105CommandCopy = new AddModuleCommand(cs2105.getModuleCode(),
                cs2105.getAcademicYear());
        assertEquals(addCS2105Command, addCS2105CommandCopy);

        // different types -> returns false
        assertNotEquals(1, addCS2103Command);

        // null -> returns false
        assertNotEquals(addCS2103Command, null);

        // different module -> returns false
        assertNotEquals(addCS2103Command, addCS2105Command);
    }

    @Test
    public void execute_moduleAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingModuleAdded modelStub = new ModelStubAcceptingModuleAdded();
        Module validModule = new ModuleBuilder().build();

        CommandResult commandResult = new AddModuleCommand(validModule.getModuleCode(),
                validModule.getAcademicYear()).execute(modelStub);
        List<EventType> eventTypeList = ModuleLibrary.getEventTypesOfModule(validModule);
        EventType firstEventType = eventTypeList.get(0);
        assertEquals(String.format(AddModuleCommand.MESSAGE_SUCCESS + "\n\nEnter slot for "
                + validModule.getModuleCode().toString()
                + " " + firstEventType.toString() + ":", validModule), commandResult.getFeedbackToUser());
        assertEquals(Collections.singletonList(validModule), modelStub.modulesAdded);
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
    private static class ModelStub implements Model {

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
     * A Model stub that contains a single module.
     */
    private static class ModelStubWithModule extends ModelStub {

        private final Module module;

        ModelStubWithModule(Module module) {
            requireNonNull(module);
            this.module = module;
        }

        @Override
        public boolean hasModule(ModuleCode moduleCode, AcademicYear academicYear) {
            requireNonNull(moduleCode);
            requireNonNull(academicYear);
            Module m = new ModuleBuilder().withModuleCode(moduleCode.toString())
                    .withAcademicYear(academicYear.getStartYear(), academicYear.getEndYear(),
                            academicYear.getSemester()).build();
            return module.isSameModule(m);
        }
    }

    /**
     * A Model stub that always accept the module being added.
     */
    private static class ModelStubAcceptingModuleAdded extends ModelStub {
        final ArrayList<Module> modulesAdded = new ArrayList<>();

        @Override
        public void addModule(ModuleCode moduleCode, AcademicYear academicYear) {
            requireNonNull(moduleCode);
            requireNonNull(academicYear);
            Module module = new ModuleBuilder().withModuleCode(moduleCode.moduleCode)
                    .withAcademicYear(academicYear.getStartYear(),
                            academicYear.getEndYear(), academicYear.getSemester()).build();
            modulesAdded.add(module);
        }

        @Override
        public Optional<Module> getModule(ModuleCode moduleCode, AcademicYear academicYear) {
            assertEquals(modulesAdded.get(0).getModuleCode(), moduleCode);
            assertEquals(modulesAdded.get(0).getAcademicYear(), academicYear);
            return Optional.of(modulesAdded.get(0));
        }
    }
}
