package modulo.model;

import static modulo.testutil.Assert.assertThrows;
import static modulo.testutil.module.TypicalModules.CS2103;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import modulo.commons.core.GuiSettings;
import modulo.model.module.AcademicYear;
import modulo.model.module.ModuleCode;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new Modulo(), new Modulo(modelManager.getModulo()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setModuloFilePath(Paths.get("address/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setModuloFilePath(Paths.get("new/address/book/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        modelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    @Test
    public void setCalendarFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setModuloFilePath(null));
    }

    @Test
    public void setCalendarFilePath_validPath_setsCalendarFilePath() {
        Path path = Paths.get("address/book/file/path");
        modelManager.setModuloFilePath(path);
        assertEquals(path, modelManager.getModuloFilePath());
    }

    @Test
    public void hasModule_nullModule_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasModule(null, null));
    }

    @Test
    public void hasModule_moduleNotInModulo_returnsFalse() {
        assertFalse(modelManager.hasModule(new ModuleCode("CS2222"), new AcademicYear(2021, 2022, 2)));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        modelManager.addModule(CS2103.getModuleCode(), CS2103.getAcademicYear());
        assertTrue(modelManager.hasModule(CS2103.getModuleCode(), CS2103.getAcademicYear()));
    }

    @Test
    public void getFilteredModuleList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredModuleList().remove(0));
    }

}
