package modulo.model;

import static modulo.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

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
}
