package modulo.logic.parser;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import modulo.commons.core.index.Index;
import modulo.commons.util.StringUtil;
import modulo.logic.parser.exceptions.ParseException;
import modulo.model.DisplayableType;
import modulo.model.Name;
import modulo.model.event.EventType;
import modulo.model.event.Location;
import modulo.model.module.ModuleCode;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    public static final DateTimeFormatter DATETIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    public static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     *
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        System.out.println("index entered " + oneBasedIndex);
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        System.out.println("index pass " + oneBasedIndex);
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}. Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        System.out.println("Trimmed " + trimmedName);
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses the user input date.
     *
     * @param date Date provided by user.
     * @return LocalDate from the string provided.
     * @throws ParseException If date format is not recognised.
     */
    public static LocalDate parseDate(String date) throws ParseException {
        requireNonNull(date);
        String trimmedDate = date.trim();
        LocalDate result;
        try {
            result = LocalDate.parse(trimmedDate, DATE_FORMAT);
        } catch (DateTimeParseException e) {
            throw new ParseException("Please enter a valid date in the format: yyyy-MM-dd");
        }
        return result;
    }

    /**
     * Parses the user input module code.
     *
     * @param moduleCode Module code provided by user.
     * @return Module code string from the input provided.
     * @throws ParseException If the module code is not valid.
     */
    public static ModuleCode parseModuleCode(String moduleCode) throws ParseException {
        requireNonNull(moduleCode);
        String trimmedModuleCode = moduleCode.trim();
        //TO REPLACE WITH MODULE CHECKING WITHIN IMPORTED NUSMOD SET
        if (!ModuleCode.isValidModuleCode(trimmedModuleCode)) {
            throw new ParseException(ModuleCode.MESSAGE_CONSTRAINTS);
        }
        return new ModuleCode(trimmedModuleCode);
    }

    /**
     * Parses the user input datetime.
     *
     * @param dateTime Date time provided by user.
     * @return LocalDateTime from the string provided.
     * @throws ParseException If datetime format is not recognised.
     */
    public static LocalDateTime parseDateTime(String dateTime) throws ParseException {
        LocalDateTime result;
        try {
            result = LocalDateTime.parse(dateTime, DATETIME_FORMAT);
        } catch (DateTimeParseException e) {
            throw new ParseException("Please enter a valid date time in the format: yyyy-MM-dd HH:mm");
        }
        return result;
    }

    /**
     * Parses the user input repeat.
     *
     * @param repeat Date time provided by user.
     * @return boolean value denoting whether the event should repeat.
     * @throws ParseException If the repeat string is not recognised.
     */
    public static boolean parseRepeat(String repeat) throws ParseException {
        switch (repeat.toLowerCase()) {
        case "yes":
            return true;
        case "no":
            return false;
        default:
            throw new ParseException("Please enter valid input for repeat : YES/NO");
        }
    }


    /**
     * Parses the user input for the event type.
     *
     * @param eventType String containing event type to parse.
     * @return Type of the event.
     * @throws ParseException If the type is not recognised.
     */
    public static EventType parseEventType(String eventType) throws ParseException {
        EventType result = EventType.parseEventType(eventType);
        if (result == null) {
            throw new ParseException("Event type is not recognised!");
        }
        return result;
    }


    /**
     * Parses the user input for the displayable type.
     *
     * @param displayableType String containing the displayable type to parse.
     * @return Type of displayable.
     * @throws ParseException If the type is not recognised.
     */
    public static DisplayableType parseDisplayableType(String displayableType) throws ParseException {
        String cleanedDisplayableType = displayableType.toLowerCase().trim();
        if (cleanedDisplayableType.equals("m") || cleanedDisplayableType.equals("module")) {
            return DisplayableType.MODULE;
        } else if (cleanedDisplayableType.equals("e") || cleanedDisplayableType.equals("event")) {
            return DisplayableType.EVENT;
        }
        throw new ParseException("Displayable type is not recognised!");
    }

    /**
     * Parses the user input directory.
     *
     * @param pathString String provided by user.
     * @return Path from the string provided.
     */
    public static Path parseDirectory(String pathString) {
        requireNonNull(pathString);
        String trimmedPath = pathString.trim();
        return Paths.get(trimmedPath);
    }

    /**
     * Parses a {@code String location} into a {@code Location}. Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code location} is invalid.
     */
    public static Location parseLocation(String location) throws ParseException {
        requireNonNull(location);
        String trimmedLocation = location.trim();
        if (!Location.isValidLocation(location)) {
            throw new ParseException(Location.MESSAGE_CONSTRAINTS);
        }
        return new Location(trimmedLocation);
    }
}
