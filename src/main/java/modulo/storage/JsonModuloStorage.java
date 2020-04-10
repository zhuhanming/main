package modulo.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import modulo.commons.core.LogsCenter;
import modulo.commons.exceptions.DataConversionException;
import modulo.commons.exceptions.IllegalValueException;
import modulo.commons.util.FileUtil;
import modulo.commons.util.JsonUtil;
import modulo.model.ReadOnlyModulo;

/**
 * A class to access Calendar data stored as a json file on the hard disk.
 */
public class JsonModuloStorage implements ModuloStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonModuloStorage.class);

    private Path filePath;

    public JsonModuloStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getModuloFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyModulo> readModulo() throws DataConversionException {
        return readModulo(filePath);
    }

    /**
     * Similar to {@link #readModulo()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyModulo> readModulo(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableModulo> jsonSerializableCalendar = JsonUtil.readJsonFile(
                filePath, JsonSerializableModulo.class);
        if (jsonSerializableCalendar.isEmpty()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonSerializableCalendar.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveModulo(ReadOnlyModulo modulo) throws IOException {
        saveModulo(modulo, filePath);
    }

    /**
     * Similar to {@link #saveModulo(ReadOnlyModulo)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveModulo(ReadOnlyModulo modulo, Path filePath) throws IOException {
        requireNonNull(modulo);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableModulo(modulo), filePath);
    }

}
