package modulo.storage;

import static modulo.testutil.Assert.assertThrows;
import static modulo.testutil.module.TypicalModules.CS2103;
import static modulo.testutil.module.TypicalModules.GER1000;
import static modulo.testutil.module.TypicalModules.IS1103;
import static modulo.testutil.module.TypicalModules.getTypicalModulo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import modulo.commons.exceptions.DataConversionException;
import modulo.model.Modulo;
import modulo.model.ReadOnlyModulo;

public class JsonModuloStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonModuloStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readModulo_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readModulo(null));
    }

    private java.util.Optional<ReadOnlyModulo> readModulo(String filePath) throws Exception {
        return new JsonModuloStorage(Paths.get(filePath)).readModulo(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readModulo("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readModulo("notJsonFormatModulo.json"));
    }

    @Test
    public void readModulo_invalidModuleModulo_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readModulo("invalidModuleModulo.json"));
    }

    @Test
    public void readModulo_invalidAndValidModuleModulo_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readModulo("invalidAndValidModuleModulo.json"));
    }

    @Test
    public void readAndSaveModulo_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempModulo.json");
        Modulo original = getTypicalModulo();
        JsonModuloStorage jsonModuloStorage = new JsonModuloStorage(filePath);

        // Save in new file and read back
        jsonModuloStorage.saveModulo(original, filePath);
        if (jsonModuloStorage.readModulo(filePath).isEmpty()) {
            fail();
        }
        ReadOnlyModulo readBack = jsonModuloStorage.readModulo(filePath).get();
        assertEquals(original, new Modulo(readBack));

        // Modify data, overwrite existing file, and read back
        original.addModule(GER1000.getModuleCode(), GER1000.getAcademicYear());
        original.removeModule(CS2103);
        jsonModuloStorage.saveModulo(original, filePath);
        readBack = jsonModuloStorage.readModulo(filePath).get();
        assertEquals(original, new Modulo(readBack));

        // Save and read without specifying file path
        original.addModule(IS1103.getModuleCode(), IS1103.getAcademicYear());
        jsonModuloStorage.saveModulo(original); // file path not specified
        if (jsonModuloStorage.readModulo().isEmpty()) {
            fail();
        }
        readBack = jsonModuloStorage.readModulo().get(); // file path not specified
        assertEquals(original, new Modulo(readBack));
    }

    @Test
    public void saveModulo_nullModulo_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveModulo(null, "SomeFile.json"));
    }

    /**
     * Saves {@code modulo} at the specified {@code filePath}.
     */
    private void saveModulo(ReadOnlyModulo modulo, String filePath) {
        try {
            new JsonModuloStorage(Paths.get(filePath))
                    .saveModulo(modulo, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveModulo_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveModulo(new Modulo(), null));
    }
}
