package modulo.model.event;

import static modulo.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class EventLocationTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Location(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        assertThrows(IllegalArgumentException.class, () -> new Location(invalidName));
    }

    @Test
    public void isValidName() {
        // null name
        assertThrows(NullPointerException.class, () -> Location.isValidLocation(null));

        // invalid name
        assertFalse(Location.isValidLocation("")); // empty string
        assertFalse(Location.isValidLocation(" ")); // spaces only

        // valid name
        assertTrue(Location.isValidLocation("Utown Tutorial")); // alphabets only
        assertTrue(Location.isValidLocation("12345")); // numbers only
        assertTrue(Location.isValidLocation("COM1-B105")); // alphanumeric characters
    }
}
