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
    public Path getCalendarFilePath() {
        return moduloStorage.getCalendarFilePath();
    }

    @Override
    public Optional<ReadOnlyModulo> readCalendar() throws DataConversionException, IOException {
        return readCalendar(moduloStorage.getCalendarFilePath());
    }

    @Override
    public Optional<ReadOnlyModulo> readCalendar(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return moduloStorage.readCalendar(filePath);
    }

    @Override
    public void saveCalendar(ReadOnlyModulo calendar) throws IOException {
        System.out.println("calendar " + calendar);
        saveCalendar(calendar, moduloStorage.getCalendarFilePath());
    }

    @Override
    public void saveCalendar(ReadOnlyModulo calendar, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        moduloStorage.saveCalendar(calendar, filePath);
    }

}
