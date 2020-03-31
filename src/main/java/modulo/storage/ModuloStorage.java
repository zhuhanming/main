package modulo.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import modulo.commons.exceptions.DataConversionException;
import modulo.model.Modulo;
import modulo.model.ReadOnlyModulo;

/**
 * Represents a storage for {@link Modulo}.
 */
public interface ModuloStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getCalendarFilePath();

    /**
     * Returns AddressBook data as a {@link ReadOnlyModulo}. Returns {@code Optional.empty()} if storage file is not
     * found.
     *
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException             if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyModulo> readCalendar() throws DataConversionException, IOException;

    /**
     * @see #getCalendarFilePath()
     */
    Optional<ReadOnlyModulo> readCalendar(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyModulo} to the storage.
     *
     * @param calendar cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveCalendar(ReadOnlyModulo calendar) throws IOException;

    /**
     * @see #saveCalendar(ReadOnlyModulo)
     */
    void saveCalendar(ReadOnlyModulo calendar, Path filePath) throws IOException;
}
