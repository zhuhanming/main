package modulo.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import modulo.commons.exceptions.DataConversionException;
import modulo.model.ReadOnlyModulo;
import modulo.model.ReadOnlyUserPrefs;
import modulo.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends ModuloStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getCalendarFilePath();

    @Override
    Optional<ReadOnlyModulo> readCalendar() throws DataConversionException, IOException;

    @Override
    void saveCalendar(ReadOnlyModulo calendar) throws IOException;

}
