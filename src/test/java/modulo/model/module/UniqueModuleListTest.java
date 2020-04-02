package modulo.model.module;

import static modulo.logic.commands.CommandTestUtil.VALID_ACADEMICYEAR_CS2105;
import static modulo.logic.commands.CommandTestUtil.VALID_DESCRIPTION_CS2105;
import static modulo.logic.commands.CommandTestUtil.VALID_SEMESTER_CS2105;
import static modulo.testutil.Assert.assertThrows;
import static modulo.testutil.module.TypicalModule.CS2103;
import static modulo.testutil.module.TypicalModule.CS2105;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import modulo.logic.parser.exceptions.ParseException;
import modulo.model.module.exceptions.DuplicateModuleException;
import modulo.model.module.exceptions.ModuleNotFoundException;
import modulo.testutil.module.ModuleBuilder;

public class UniqueModuleListTest {

    private final UniqueModuleList uniqueModuleList = new UniqueModuleList();

    @Test
    public void contains_nullModule_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueModuleList.contains(null));
    }

    @Test
    public void contains_moduleNotInList_returnsFalse() {
        assertFalse(uniqueModuleList.contains(CS2103));
    }

    @Test
    public void contains_moduleInList_returnsTrue() {
        uniqueModuleList.add(CS2103);
        assertTrue(uniqueModuleList.contains(CS2103));
    }

    // not sure what to compare here
    @Test
    public void contains_personWithSameIdentityFieldsInList_returnsTrue() throws ParseException {
        uniqueModuleList.add(CS2103);
        Module editedCS2103 = new ModuleBuilder(CS2103).withDescription(VALID_DESCRIPTION_CS2105).build();
        assertTrue(uniqueModuleList.contains(editedCS2103));
    }

    @Test
    public void add_nullModule_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueModuleList.add(null));
    }

    @Test
    public void add_duplicateModule_throwsDuplicatePersonException() {
        uniqueModuleList.add(CS2103);
        assertThrows(DuplicateModuleException.class, () -> uniqueModuleList.add(CS2103));
    }

    @Test
    public void setModule_nullTargetModule_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueModuleList.setModule(null, CS2103));
    }

    @Test
    public void setModule_nullEditedModule_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueModuleList.setModule(CS2103, null));
    }

    @Test
    public void setModule_targetModuleNotInList_throwsModuleNotFoundException() {
        assertThrows(ModuleNotFoundException.class, () -> uniqueModuleList.setModule(CS2103, CS2103));
    }

    @Test
    public void setModule_editedModuleIsSameModule_success() {
        uniqueModuleList.add(CS2103);
        uniqueModuleList.setModule(CS2103, CS2103);
        UniqueModuleList expectedUniqueModuleList = new UniqueModuleList();
        expectedUniqueModuleList.add(CS2103);
        assertEquals(expectedUniqueModuleList, uniqueModuleList);
    }

    @Test
    public void setModule_editedModuleHasSameIdentity_success() throws ParseException {
        uniqueModuleList.add(CS2103);
        Module editedCS2103 = new ModuleBuilder(CS2103).withAcademicYear(VALID_ACADEMICYEAR_CS2105, VALID_SEMESTER_CS2105).withDescription(VALID_DESCRIPTION_CS2105).build();
        uniqueModuleList.setModule(CS2103, editedCS2103);
        UniqueModuleList expectedUniqueModuleList = new UniqueModuleList();
        expectedUniqueModuleList.add(editedCS2103);
        assertEquals(expectedUniqueModuleList, uniqueModuleList);
    }

    @Test
    public void setModule_editedModuleHasDifferentIdentity_success() {
        uniqueModuleList.add(CS2103);
        uniqueModuleList.setModule(CS2103, CS2105);
        UniqueModuleList expectedUniquePersonList = new UniqueModuleList();
        expectedUniquePersonList.add(CS2105);
        assertEquals(expectedUniquePersonList, uniqueModuleList);
    }

    @Test
    public void setModule_editedModuleHasNonUniqueIdentity_throwsDuplicateModuleException() {
        uniqueModuleList.add(CS2103);
        uniqueModuleList.add(CS2105);
        assertThrows(DuplicateModuleException.class, () -> uniqueModuleList.setModule(CS2103, CS2105));
    }

    @Test
    public void remove_nullModule_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueModuleList.remove(null));
    }

    @Test
    public void remove_moduleDoesNotExist_throwsModuleNotFoundException() {
        assertThrows(ModuleNotFoundException.class, () -> uniqueModuleList.remove(CS2103));
    }

    @Test
    public void remove_existingModule_removesModule() {
        uniqueModuleList.add(CS2103);
        uniqueModuleList.remove(CS2103);
        UniqueModuleList expectedUniqueModuleList = new UniqueModuleList();
        assertEquals(expectedUniqueModuleList, uniqueModuleList);
    }

    @Test
    public void setModule_nullUniqueModuleList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueModuleList.setModules((UniqueModuleList) null));
    }

    @Test
    public void setModule_uniqueModuleList_replacesOwnListWithProvidedUniqueModuleList() {
        uniqueModuleList.add(CS2103);
        UniqueModuleList expectedUniquePersonList = new UniqueModuleList();
        expectedUniquePersonList.add(CS2105);
        uniqueModuleList.setModules(expectedUniquePersonList);
        assertEquals(expectedUniquePersonList, uniqueModuleList);
    }

    @Test
    public void setModule_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueModuleList.setModules((List<Module>) null));
    }

    @Test
    public void setModules_list_replacesOwnListWithProvidedList() {
        uniqueModuleList.add(CS2103);
        List<Module> moduleList = Collections.singletonList(CS2105);
        uniqueModuleList.setModules(moduleList);
        UniqueModuleList expectedUniquePersonList = new UniqueModuleList();
        expectedUniquePersonList.add(CS2105);
        assertEquals(expectedUniquePersonList, uniqueModuleList);
    }

    @Test
    public void setModules_listWithDuplicateModules_throwsDuplicatePersonException() {
        List<Module> listWithDuplicatePersons = Arrays.asList(CS2103, CS2103);
        assertThrows(DuplicateModuleException.class, () -> uniqueModuleList.setModules(listWithDuplicatePersons));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
                -> uniqueModuleList.asUnmodifiableObservableList().remove(0));
    }

}
