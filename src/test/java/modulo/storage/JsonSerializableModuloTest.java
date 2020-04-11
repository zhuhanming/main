package modulo.storage;

import static modulo.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import modulo.commons.exceptions.IllegalValueException;
import modulo.commons.util.JsonUtil;
import modulo.model.Modulo;
import modulo.testutil.module.TypicalModules;

public class JsonSerializableModuloTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableModuloTest");
    private static final Path TYPICAL_MODULE_FILE = TEST_DATA_FOLDER.resolve("typicalModuleModulo.json");
    private static final Path INVALID_MODULE_FILE = TEST_DATA_FOLDER.resolve("invalidModuleModulo.json");
    private static final Path DUPLICATE_MODULE_FILE = TEST_DATA_FOLDER.resolve("duplicateModuleModulo.json");

    @Test
    public void toModelType_typicalModuleFile_success() throws Exception {
        if (JsonUtil.readJsonFile(TYPICAL_MODULE_FILE, JsonSerializableModulo.class).isEmpty()) {
            fail();
        }
        JsonSerializableModulo dataFromFile = JsonUtil.readJsonFile(TYPICAL_MODULE_FILE,
                JsonSerializableModulo.class).get();
        Modulo addressBookFromFile = dataFromFile.toModelType();
        Modulo typicalPersonsAddressBook = TypicalModules.getTypicalModuloModulesOnly();
        assertEquals(addressBookFromFile, typicalPersonsAddressBook);
    }

    @Test
    public void toModelType_invalidModuleFile_throwsIllegalValueException() throws Exception {
        if (JsonUtil.readJsonFile(INVALID_MODULE_FILE, JsonSerializableModulo.class).isEmpty()) {
            fail();
        }
        JsonSerializableModulo dataFromFile = JsonUtil.readJsonFile(INVALID_MODULE_FILE,
                JsonSerializableModulo.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateModules_throwsIllegalValueException() throws Exception {
        if (JsonUtil.readJsonFile(DUPLICATE_MODULE_FILE, JsonSerializableModulo.class).isEmpty()) {
            fail();
        }
        JsonSerializableModulo dataFromFile = JsonUtil.readJsonFile(DUPLICATE_MODULE_FILE,
                JsonSerializableModulo.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableModulo.MESSAGE_DUPLICATE_MODULE,
                dataFromFile::toModelType);
    }

}
