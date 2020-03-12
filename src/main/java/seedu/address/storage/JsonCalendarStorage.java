package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.ReadOnlyCalendar;

/**
 * A class to access Calendar data stored as a json file on the hard disk.
 */
public class JsonCalendarStorage implements CalendarStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonCalendarStorage.class);

    private Path filePath;

    public JsonCalendarStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getCalendarFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyCalendar> readCalendar() throws DataConversionException {
        return readCalendar(filePath);
    }

    /**
     * Similar to {@link #readCalendar()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyCalendar> readCalendar(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableCalendar> jsonSerializableCalendar = JsonUtil.readJsonFile(
                filePath, JsonSerializableCalendar.class);
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
    public void saveCalendar(ReadOnlyCalendar calendar) throws IOException {
        saveCalendar(calendar, filePath);
    }

    /**
     * Similar to {@link #saveCalendar(ReadOnlyCalendar)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveCalendar(ReadOnlyCalendar calendar, Path filePath) throws IOException {
        requireNonNull(calendar);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableCalendar(calendar), filePath);
    }

}
