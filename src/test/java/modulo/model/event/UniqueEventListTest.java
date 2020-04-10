package modulo.model.event;

import static modulo.testutil.Assert.assertThrows;
import static modulo.testutil.event.TypicalEvents.TUTORIAL_1;
import static modulo.testutil.event.TypicalEvents.TUTORIAL_2;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import modulo.model.event.exceptions.DuplicateEventException;
import modulo.model.event.exceptions.EventNotFoundException;

public class UniqueEventListTest {

    private final UniqueEventList uniqueEventList = new UniqueEventList();

    @Test
    public void contains_nullEvent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueEventList.contains(null));
    }

    @Test
    public void contains_moduleNotInList_returnsFalse() {
        assertFalse(uniqueEventList.contains(TUTORIAL_1));
    }

    @Test
    public void contains_moduleInList_returnsTrue() {
        uniqueEventList.add(TUTORIAL_1);
        assertTrue(uniqueEventList.contains(TUTORIAL_1));
    }

    @Test
    public void add_nullModule_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueEventList.add(null));
    }

    @Test
    public void add_duplicateModule_throwsDuplicatePersonException() {
        uniqueEventList.add(TUTORIAL_1);
        assertThrows(DuplicateEventException.class, () -> uniqueEventList.add(TUTORIAL_1));
    }

    @Test
    public void setModule_nullTargetModule_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueEventList.setEvent(null, TUTORIAL_1));
    }

    @Test
    public void setModule_nullEditedModule_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueEventList.setEvent(TUTORIAL_1, null));
    }

    @Test
    public void setModule_targetModuleNotInList_throwsModuleNotFoundException() {
        assertThrows(EventNotFoundException.class, () -> uniqueEventList.setEvent(TUTORIAL_1, TUTORIAL_1));
    }

    @Test
    public void setModule_editedModuleHasDifferentIdentity_success() {
        uniqueEventList.add(TUTORIAL_1);
        uniqueEventList.setEvent(TUTORIAL_1, TUTORIAL_2);
        UniqueEventList expectedUniquePersonList = new UniqueEventList();
        expectedUniquePersonList.add(TUTORIAL_2);
        assertEquals(expectedUniquePersonList, uniqueEventList);
    }

    @Test
    public void setModule_editedModuleHasNonUniqueIdentity_throwsDuplicateModuleException() {
        uniqueEventList.add(TUTORIAL_1);
        uniqueEventList.add(TUTORIAL_2);
        assertThrows(DuplicateEventException.class, () -> uniqueEventList.setEvent(TUTORIAL_1, TUTORIAL_2));
    }

    @Test
    public void remove_nullModule_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueEventList.remove(null));
    }

    @Test
    public void remove_moduleDoesNotExist_throwsModuleNotFoundException() {
        assertThrows(EventNotFoundException.class, () -> uniqueEventList.remove(TUTORIAL_1));
    }

    @Test
    public void remove_existingModule_removesModule() {
        uniqueEventList.add(TUTORIAL_1);
        uniqueEventList.remove(TUTORIAL_1);
        UniqueEventList expectedUniqueModuleList = new UniqueEventList();
        assertEquals(expectedUniqueModuleList, uniqueEventList);
    }

    @Test
    public void setModule_nullUniqueModuleList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueEventList.setEvents((UniqueEventList) null));
    }

    @Test
    public void setModule_uniqueModuleList_replacesOwnListWithProvidedUniqueModuleList() {
        uniqueEventList.add(TUTORIAL_1);
        UniqueEventList expectedUniquePersonList = new UniqueEventList();
        expectedUniquePersonList.add(TUTORIAL_2);
        uniqueEventList.setEvents(expectedUniquePersonList);
        assertEquals(expectedUniquePersonList, uniqueEventList);
    }

    @Test
    public void setModule_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueEventList.setEvents((List<Event>) null));
    }

    @Test
    public void setModules_list_replacesOwnListWithProvidedList() {
        uniqueEventList.add(TUTORIAL_1);
        List<Event> eventList = Collections.singletonList(TUTORIAL_2);
        uniqueEventList.setEvents(eventList);
        UniqueEventList expectedUniquePersonList = new UniqueEventList();
        expectedUniquePersonList.add(TUTORIAL_2);
        assertEquals(expectedUniquePersonList, uniqueEventList);
    }

    @Test
    public void setModules_listWithDuplicateModules_throwsDuplicatePersonException() {
        List<Event> listWithDuplicatePersons = Arrays.asList(TUTORIAL_1, TUTORIAL_1);
        assertThrows(DuplicateEventException.class, () -> uniqueEventList.setEvents(listWithDuplicatePersons));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () ->
                uniqueEventList.asUnmodifiableObservableList().remove(0));
    }
}
