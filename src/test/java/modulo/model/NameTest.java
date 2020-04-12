package modulo.model;

import static modulo.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class NameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Name(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        assertThrows(IllegalArgumentException.class, () -> new Name(invalidName));
    }

    @Test
    public void isValidName() {
        // null name
        assertThrows(NullPointerException.class, () -> Name.isValidName(null));

        // invalid name
        assertFalse(Name.isValidName("")); // empty string
        assertFalse(Name.isValidName(" ")); // spaces only
        assertFalse(Name.isValidName("^")); // only non-alphanumeric characters
        assertFalse(Name.isValidName("#Tutorial")); // contains non-alphanumeric characters

        // valid name
        assertTrue(Name.isValidName("prepare lecture")); // alphabets only
        assertTrue(Name.isValidName("12345")); // numbers only
        assertTrue(Name.isValidName("read chapter 2 before lecture 2")); // alphanumeric characters
        assertTrue(Name.isValidName("Prepare for Tutorial")); // with capital letters
        assertTrue(Name.isValidName("prepare for lecture by reading lecture notes")); // long names
    }

    @Test
    public void testCleanName() {
        String originalName = "Business Communication (BBA) Exciting!";
        String cleanedName = "Business Communication BBA Exciting";
        assertEquals(cleanedName, Name.cleanNameString(originalName));
    }

    @Test
    public void testHashCode() {
        String testString = "This is a name";
        Name testName = new Name(testString);
        assertEquals(testString.hashCode(), testName.hashCode());
    }
}
