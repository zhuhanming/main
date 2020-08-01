package modulo.model.module;

import static modulo.logic.commands.CommandTestUtil.INVALID_MODULE_CODE_CS2000;
import static modulo.logic.commands.CommandTestUtil.VALID_CODE_CS2103;
import static modulo.testutil.Assert.assertThrows;
import static modulo.testutil.module.TypicalModules.CS2103;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.DayOfWeek;
import java.time.Period;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

import org.junit.jupiter.api.Test;

import modulo.logic.commands.AddEventCommand;
import modulo.model.Name;
import modulo.model.event.Event;
import modulo.model.event.EventType;
import modulo.model.event.Location;
import modulo.model.event.Slot;
import modulo.model.event.exceptions.EventNotFoundException;
import modulo.model.module.exceptions.ModuleNotFoundException;
import modulo.testutil.module.ModuleBuilder;

/**
 * JUnit test class for {@code ModuleLibrary}.
 */
public class ModuleLibraryTest {

    private static final Module ACC1006 = new ModuleBuilder().withModuleCode("ACC1006").build();
    private static final Module AH3204 = new ModuleBuilder().withModuleCode("AH3204").build();
    private static final Module AR1101 = new ModuleBuilder().withModuleCode("AR1101").build();
    private static final Module AY1130 = new ModuleBuilder().withModuleCode("AY1130").build();
    private static final Module CE3101 = new ModuleBuilder().withModuleCode("CE3101").build();
    private static final Module CS1010S = new ModuleBuilder().withModuleCode("CS1010S").build();
    private static final Module ES1601 = new ModuleBuilder().withModuleCode("ES1601").build();
    private static final Module MLE1010 = new ModuleBuilder().withModuleCode("MLE1010").build();

    @Test
    public void testGetModule_moduleExists_success() {
        Module module = ModuleLibrary.createAndReturnModule(new ModuleCode(VALID_CODE_CS2103), new AcademicYear(2019, 2020, 2));
        assertEquals(module.getModuleCode(), new ModuleCode(VALID_CODE_CS2103));
        assertEquals(module.getAcademicYear(), new AcademicYear(2019, 2020, 2));
    }

    @Test
    public void testGetModule_moduleDoesNotExist_exceptionThrown() {
        assertThrows(ModuleNotFoundException.class, "Cannot find the module you specified!", () -> ModuleLibrary
                .createAndReturnModule(new ModuleCode(INVALID_MODULE_CODE_CS2000), new AcademicYear(2019, 2020, 2)));
    }

    @Test
    public void testGetEventTypesOfModule_moduleExists_success() {
        // Conventional event types
        List<EventType> testListOne = ModuleLibrary.getEventTypesOfModule(CS2103);
        assertTrue(testListOne.contains(EventType.LECTURE));
        assertTrue(testListOne.contains(EventType.TUTORIAL));

        // Rarer event types
        // Sectional
        List<EventType> testListTwo = ModuleLibrary.getEventTypesOfModule(ACC1006);
        assertTrue(testListTwo.contains(EventType.SECTIONAL));

        // Design Lecture
        List<EventType> testListThree = ModuleLibrary.getEventTypesOfModule(AR1101);
        assertTrue(testListThree.contains(EventType.DESIGN_LECTURE));
        assertTrue(testListThree.contains(EventType.TUTORIAL));
        assertTrue(testListThree.contains(EventType.LECTURE));

        // Laboratory
        List<EventType> testListFour = ModuleLibrary.getEventTypesOfModule(AY1130);
        assertTrue(testListFour.contains(EventType.LABORATORY));
        assertTrue(testListFour.contains(EventType.TUTORIAL));
        assertTrue(testListFour.contains(EventType.LECTURE));

        // Packaged Lecture and Packaged Tutorial
        List<EventType> testListFive = ModuleLibrary.getEventTypesOfModule(CE3101);
        assertTrue(testListFive.contains(EventType.PACKAGED_LECTURE));
        assertTrue(testListFive.contains(EventType.PACKAGED_TUTORIAL));

        // Recitation
        List<EventType> testListSix = ModuleLibrary.getEventTypesOfModule(CS1010S);
        assertTrue(testListSix.contains(EventType.RECITATION));
        assertTrue(testListSix.contains(EventType.LECTURE));
        assertTrue(testListSix.contains(EventType.TUTORIAL));

        // Tutorial Type 2
        List<EventType> testListSeven = ModuleLibrary.getEventTypesOfModule(ES1601);
        assertTrue(testListSeven.contains(EventType.TUTORIAL_TYPE_2));
        assertTrue(testListSeven.contains(EventType.TUTORIAL));

        // Workshop
        List<EventType> testListEight = ModuleLibrary.getEventTypesOfModule(MLE1010);
        assertTrue(testListEight.contains(EventType.WORKSHOP));
        assertTrue(testListEight.contains(EventType.LABORATORY));
        assertTrue(testListEight.contains(EventType.TUTORIAL));
        assertTrue(testListEight.contains(EventType.LECTURE));

        // Seminar
        List<EventType> testListNine = ModuleLibrary.getEventTypesOfModule(AH3204);
        assertTrue(testListNine.contains(EventType.SEMINAR));
    }

    @Test
    public void testGetEventTypesOfModule_moduleDoesNotExist_exceptionThrown() {
        assertThrows(ModuleNotFoundException.class, "Cannot find the module you specified!", () -> ModuleLibrary
                .getEventTypesOfModule(new ModuleBuilder().withModuleCode(INVALID_MODULE_CODE_CS2000).build()));
    }

    @Test
    public void testGetEventTypesOfModule_noSemesterData_replacementSemesterDataReturned() {
        // This is a semester-one-only module.
        Module module = new ModuleBuilder().withModuleCode("CS2104").withAcademicYear(2019, 2020, 2).build();
        // Should return me semester one data, even if I asked for semester two.
        List<EventType> eventTypes = ModuleLibrary.getEventTypesOfModule(module);
        assertFalse(eventTypes.isEmpty());
        assertTrue(eventTypes.contains(EventType.LECTURE));
        assertTrue(eventTypes.contains(EventType.TUTORIAL));
    }

    @Test
    public void testGetAddEventCommands_invalidModule_exceptionThrown() {
        assertThrows(ModuleNotFoundException.class, "Cannot find the module you specified!", () -> ModuleLibrary
                .getAddEventCommandToExecute(new ModuleBuilder().withModuleCode(INVALID_MODULE_CODE_CS2000).build(),
                        EventType.TUTORIAL, "Random"));
    }

    @Test
    public void testGetAddEventCommands_invalidSlot_exceptionThrown() {
        assertThrows(EventNotFoundException.class, String.format(ModuleLibrary.SAMPLE_SLOT, "04"), () -> ModuleLibrary
                .getAddEventCommandToExecute(CS2103, EventType.TUTORIAL, "20"));
    }

    @Test
    public void testGetAddEventCommands_invalidEventType_exceptionThrown() {
        assertThrows(EventNotFoundException.class, ModuleLibrary.INVALID_EVENT_TYPE, () -> ModuleLibrary
                .getAddEventCommandToExecute(CS2103, EventType.SEMINAR, "20"));
    }

    @Test
    public void testGetAddEventCommands_validInputsForSingleEvent_singleCommandReturned() {
        List<AddEventCommand> eventCommands = ModuleLibrary.getAddEventCommandToExecute(CS2103, EventType.LECTURE, "1");
        assertEquals(1, eventCommands.size());
        AddEventCommand eventCommand = eventCommands.get(0);
        Event expectedEvent = new Event(new Name(EventType.LECTURE.toString()), EventType.LECTURE,
                new AcademicYear(2019, 2020, 2).getStartDate().with(TemporalAdjusters.next(DayOfWeek.FRIDAY)).atTime(14, 0),
                new AcademicYear(2019, 2020, 2).getStartDate().with(TemporalAdjusters.next(DayOfWeek.FRIDAY)).atTime(16, 0),
                CS2103, new Location("I3-AUD"), new Slot("1"));
        AddEventCommand expectedCommand = new AddEventCommand(expectedEvent, true,
                new AcademicYear(2019, 2020, 2).getStartDate().with(TemporalAdjusters.next(DayOfWeek.FRIDAY)).plusWeeks(12),
                Period.ofWeeks(1), null);
        assertEquals(expectedCommand, eventCommand);
    }

    @Test
    public void testGetAddEventCommands_validInputsForMultipleEvents_multipleCommandsReturned() {
        Module cs2100 = new ModuleBuilder().withModuleCode("CS2100").build();
        List<AddEventCommand> eventCommands = ModuleLibrary.getAddEventCommandToExecute(cs2100, EventType.LECTURE, "2");
        assertEquals(2, eventCommands.size());
        AddEventCommand eventCommand = eventCommands.get(0);
        Event expectedEvent = new Event(new Name(EventType.LECTURE.toString()), EventType.LECTURE,
                new AcademicYear(2019, 2020, 2).getStartDate().atTime(10, 0), new AcademicYear(2019, 2020, 2).getStartDate().atTime(12, 0),
                cs2100, new Location("I3-AUD"), new Slot("2"));
        AddEventCommand expectedCommand = new AddEventCommand(expectedEvent, true,
                new AcademicYear(2019, 2020, 2).getStartDate().plusWeeks(12), Period.ofWeeks(1), 'A');
        assertEquals(expectedCommand, eventCommand);
    }
}
