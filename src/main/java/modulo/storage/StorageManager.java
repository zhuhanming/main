package modulo.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import modulo.commons.core.LogsCenter;
import modulo.commons.exceptions.DataConversionException;
import modulo.model.ReadOnlyModulo;
import modulo.model.ReadOnlyUserPrefs;
import modulo.model.UserPrefs;

/**
 * Manages storage of AddressBook data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private ModuloStorage moduloStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(ModuloStorage moduloStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.moduloStorage = moduloStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }


    // ================ AddressBook methods ==============================

    @Override
    public Path getModuloFilePath() {
        return moduloStorage.getModuloFilePath();
    }

    @Override
    public Optional<ReadOnlyModulo> readModulo() throws DataConversionException, IOException {
        return readModulo(moduloStorage.getModuloFilePath());
    }

    @Override
    public Optional<ReadOnlyModulo> readModulo(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return moduloStorage.readModulo(filePath);
    }

    @Override
    public void saveModulo(ReadOnlyModulo modulo) throws IOException {
        System.out.println("calendar " + modulo);
        saveModulo(modulo, moduloStorage.getModuloFilePath());
    }

    @Override
    public void saveModulo(ReadOnlyModulo modulo, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        moduloStorage.saveModulo(modulo, filePath);
    }

}
