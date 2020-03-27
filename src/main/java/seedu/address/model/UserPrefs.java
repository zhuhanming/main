package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import seedu.address.commons.core.GuiSettings;

/**
 * Represents User's preferences.
 */

public class UserPrefs implements ReadOnlyUserPrefs {

    private GuiSettings guiSettings = new GuiSettings();
    private Path calendarFilePath = Paths.get("data", "calendar.json");
    private Path icsFilePath = Paths.get("data");

    /**
     * Creates a {@code UserPrefs} with default values.
     */
    public UserPrefs() {
    }

    /**
     * Creates a {@code UserPrefs} with the prefs in {@code userPrefs}.
     */
    public UserPrefs(ReadOnlyUserPrefs userPrefs) {
        this();
        resetData(userPrefs);
    }

    /**
     * Resets the existing data of this {@code UserPrefs} with {@code newUserPrefs}.
     */
    public void resetData(ReadOnlyUserPrefs newUserPrefs) {
        requireNonNull(newUserPrefs);
        setGuiSettings(newUserPrefs.getGuiSettings());
        setCalendarFilePath(newUserPrefs.getCalendarFilePath());
        setIcsFilePath(newUserPrefs.getIcsFilePath());
    }

    public GuiSettings getGuiSettings() {
        return guiSettings;
    }

    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        this.guiSettings = guiSettings;
    }

    public Path getCalendarFilePath() {
        return calendarFilePath;
    }

    public void setCalendarFilePath(Path calendarFilePath) {
        requireNonNull(calendarFilePath);
        this.calendarFilePath = calendarFilePath;
    }

    public Path getIcsFilePath() {
        return icsFilePath;
    }

    public void setIcsFilePath(Path icsFilePath) {
        requireNonNull(icsFilePath);
        this.icsFilePath = icsFilePath;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof UserPrefs)) { //this handles null as well.
            return false;
        }

        UserPrefs o = (UserPrefs) other;

        return guiSettings.equals(o.guiSettings)
                && calendarFilePath.equals(o.calendarFilePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings, calendarFilePath);
    }

    @Override
    public String toString() {
        return "Gui Settings : " + guiSettings + "\nLocal data file location : " + calendarFilePath;
    }

}
