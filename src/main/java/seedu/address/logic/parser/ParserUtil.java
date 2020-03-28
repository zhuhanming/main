package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.DisplayableType;
import seedu.address.model.Name;
import seedu.address.model.event.EventType;
import seedu.address.model.event.Location;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

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
     * Parses a {@code String phone} into a {@code Phone}. Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String address} into an {@code Address}. Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static Address parseAddress(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        if (!Address.isValidAddress(trimmedAddress)) {
            throw new ParseException(Address.MESSAGE_CONSTRAINTS);
        }
        return new Address(trimmedAddress);
    }

    /**
     * Parses a {@code String email} into an {@code Email}. Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}. Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
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
     * Parses the view index for the displayable type.
     *
     * @param displayableType
     * @return
     * @throws ParseException
     */
    public static DisplayableType parseDisplayableTypeRightPanel(String displayableType) {
        String cleanedDisplayableType = displayableType.toLowerCase().substring(9);
        if (cleanedDisplayableType.contains("lab") || cleanedDisplayableType.contains("tutorial")
                || cleanedDisplayableType.contains("lecture")) {
            return DisplayableType.EVENT;
        } else {
            return DisplayableType.MODULE;
        }
    }

    /**
     * Parses the user input directory.
     *
     * @param pathString String provided by user.
     * @return Path from the string provided.
     * @throws ParseException If path format is not recognised.
     */
    public static Path parseDirectory(String pathString) throws ParseException {
        requireNonNull(pathString);
        String trimmedPath = pathString.trim();

        Path path = Paths.get(pathString);
        if (path == null) {
            throw new ParseException("Please enter a valid date in the format: yyyy-MM-dd");
        }
        return path;
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
