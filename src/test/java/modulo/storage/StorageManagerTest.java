package modulo.storage;

import static modulo.testutil.module.TypicalModules.getTypicalModuloModulesOnly;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import modulo.commons.core.GuiSettings;
import modulo.model.Modulo;
import modulo.model.ReadOnlyModulo;
import modulo.model.UserPrefs;

public class StorageManagerTest {

    @TempDir
    public Path testFolder;

    private StorageManager storageManager;

    @BeforeEach
    public void setUp() {
        JsonModuloStorage moduloStorage = new JsonModuloStorage(getTempFilePath("ab"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        storageManager = new StorageManager(moduloStorage, userPrefsStorage);
    }

    private Path getTempFilePath(String fileName) {
        return testFolder.resolve(fileName);
    }

    @Test
    public void prefsReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonUserPrefsStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonUserPrefsStorageTest} class.
         */
        UserPrefs original = new UserPrefs();
        original.setGuiSettings(new GuiSettings(300, 600, 4, 6));
        storageManager.saveUserPrefs(original);
        if (storageManager.readUserPrefs().isEmpty()) {
            fail();
        }
        UserPrefs retrieved = storageManager.readUserPrefs().get();
        assertEquals(original, retrieved);
    }

    @Test
    public void moduloReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonModuloStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonModuloStorageTest} class.
         */
        Modulo original = getTypicalModuloModulesOnly();
        storageManager.saveModulo(original);
        if (storageManager.readModulo().isEmpty()) {
            fail();
        }
        ReadOnlyModulo retrieved = storageManager.readModulo().get();
        assertEquals(original, new Modulo(retrieved));
    }

    @Test
    public void getModuloFilePath() {
        assertNotNull(storageManager.getModuloFilePath());
    }

    @Test
    public void getUserPrefsFilePath() {
        assertNotNull(storageManager.getUserPrefsFilePath());
    }
}
