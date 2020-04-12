package modulo.model;

import static modulo.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.nio.file.Path;
import java.util.Objects;

import org.junit.jupiter.api.Test;

import modulo.commons.core.GuiSettings;

public class UserPrefTest {
    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        UserPrefs userPref = new UserPrefs();
        assertThrows(NullPointerException.class, () -> userPref.setGuiSettings(null));
    }

    @Test
    public void setModuloFilePath_nullPath_throwsNullPointerException() {
        UserPrefs userPrefs = new UserPrefs();
        assertThrows(NullPointerException.class, () -> userPrefs.setModuloFilePath(null));
    }

    @Test
    public void testEquals() {
        UserPrefs userPref = new UserPrefs();
        assertEquals(userPref, userPref);
        assertNotEquals(userPref, null);
    }

    @Test
    public void testHashCode() {
        UserPrefs userPref = new UserPrefs();
        GuiSettings guiSettings = userPref.getGuiSettings();
        Path moduloFilePath = userPref.getModuloFilePath();
        assertEquals(userPref.hashCode(), Objects.hash(guiSettings, moduloFilePath));
    }
}
