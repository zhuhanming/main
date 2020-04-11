package modulo.logic.parser;

import static modulo.testutil.TypicalIndexes.INDEX_FIRST_MODULE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

import modulo.logic.parser.exceptions.ParseException;
import modulo.model.Name;
import modulo.model.event.Location;
import modulo.model.module.ModuleCode;

/**
 * class contains test coses for different input parse.
 */
public class ParserUtilTest {
    public static final DateTimeFormatter DATETIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    // parse test for module.
    private static final String INVALID_MODULE_CODE = "CS210$";
    private static final String INVALID_MODULE_NAME = "Software Engineer$$$";
    private static final String VALID_MODULE_NAME = "Software Engineering";
    private static final String VALID_MODULE_CODE = "CS2103";
    // parse test for event.
    private static final String INVALID_EVENT_NAME = "Tutorial $";
    private static final String INVALID_EVENT_LOCATION = "";
    private static final String INVALID_EVENT_REPEAT = "LOL";
    private static final String INVALID_EVENT_DATETIME = "03-01-2019 14:00";
    private static final String VALID_EVENT_NAME = "Tutorial 1";
    private static final String VALID_EVENT_LOCATION = "COM1-B103";
    private static final String VALID_EVENT_REPEAT = "YES";
    private static final String VALID_EVENT_DATETIME = "2019-01-15 09:00";
    private static final String WHITESPACE = " \t\r\n";

    @Test
    public void parseIndex_invalidInput_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseIndex("10 a"));
    }

    @Test
    public void parseIndex_validInput_success() throws Exception {
        // No whitespaces
        assertEquals(INDEX_FIRST_MODULE, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST_MODULE, ParserUtil.parseIndex("  1  "));
    }

    @Test
    public void parseModuleName_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseName((String) null));
    }

    @Test
    public void parseModuleName_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseName(INVALID_MODULE_NAME));
    }

    @Test
    public void parseModuleName_validValueWithoutWhitespace_returnsName() throws Exception {
        Name expectedName = new Name(VALID_MODULE_NAME);
        assertEquals(expectedName, ParserUtil.parseName(VALID_MODULE_NAME));
    }

    @Test
    public void parseModuleName_validValueWithWhitespace_returnsTrimmedName() throws Exception {
        String nameWithWhitespace = WHITESPACE + VALID_MODULE_NAME + WHITESPACE;
        Name expectedName = new Name(VALID_MODULE_NAME);
        assertEquals(expectedName, ParserUtil.parseName(nameWithWhitespace));
    }

    @Test
    public void parseModuleCode_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseModuleCode((String) null));
    }

    @Test
    public void parseModuleCode_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseModuleCode(INVALID_MODULE_CODE));
    }

    @Test
    public void parseModuleCode_validValueWithoutWhitespace_returnsPhone() throws Exception {
        ModuleCode expectedModuleCode = new ModuleCode(VALID_MODULE_CODE);
        assertEquals(expectedModuleCode, ParserUtil.parseModuleCode(VALID_MODULE_CODE));
    }

    @Test
    public void parseModuleCode_validValueWithWhitespace_returnsTrimmedPhone() throws Exception {
        String phoneWithWhitespace = WHITESPACE + VALID_MODULE_CODE + WHITESPACE;
        ModuleCode expectedModuleCode = new ModuleCode(VALID_MODULE_CODE);
        assertEquals(expectedModuleCode, ParserUtil.parseModuleCode(phoneWithWhitespace));
    }

    @Test
    public void parseEventName_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseName((String) null));
    }

    @Test
    public void parseEventName_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseDateTime(INVALID_EVENT_NAME));
    }

    @Test
    public void parseEventName_validValueWithoutWhitespace_returnsName() throws Exception {
        Name expectedEventName = new Name(VALID_EVENT_NAME);
        assertEquals(expectedEventName, ParserUtil.parseName(VALID_EVENT_NAME));
    }

    @Test
    public void parseEventDateTime_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseDateTime(INVALID_EVENT_DATETIME));
    }

    @Test
    public void parseEventDateTime_validValueWithoutWhitespace_returnsAddress() throws Exception {
        LocalDateTime expectedAddress = LocalDateTime.parse(VALID_EVENT_DATETIME, DATETIME_FORMAT);
        assertEquals(expectedAddress, ParserUtil.parseDateTime(VALID_EVENT_DATETIME));
    }


    @Test
    public void parseLocation_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseLocation((String) null));
    }

    @Test
    public void parseLocation_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseLocation(INVALID_EVENT_LOCATION));
    }

    @Test
    public void parseLocation_validValueWithoutWhitespace_returnsEmail() throws Exception {
        Location expectedLocation = new Location(VALID_EVENT_LOCATION);
        assertEquals(expectedLocation, ParserUtil.parseLocation(VALID_EVENT_LOCATION));
    }

    @Test
    public void parseLocation_validValueWithWhitespace_returnsTrimmedEmail() throws Exception {
        String locationWithWhitespace = WHITESPACE + VALID_EVENT_LOCATION + WHITESPACE;
        Location expectedEmail = new Location(VALID_EVENT_LOCATION);
        assertEquals(expectedEmail, ParserUtil.parseLocation(locationWithWhitespace));
    }

    @Test
    public void parseEventRepeat_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseRepeat(null));
    }

    @Test
    public void parseEventRepeat_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseRepeat(INVALID_EVENT_REPEAT));
    }

    @Test
    public void parseEventRepeat_validValueWithoutWhitespace_returnsTag() throws Exception {
        Boolean expectedRepeat = true;
        assertEquals(expectedRepeat, ParserUtil.parseRepeat(VALID_EVENT_REPEAT));
    }

}
