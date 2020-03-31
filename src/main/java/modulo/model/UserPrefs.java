package modulo.model;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import modulo.commons.core.GuiSettings;

/**
 * Represents User's preferences.
 */

public class UserPrefs implements ReadOnlyUserPrefs {

    private GuiSettings guiSettings = new GuiSettings();
    private Path moduloFilePath = Paths.get("data", "modulo.json");
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
        setModuloFilePath(newUserPrefs.getModuloFilePath());
        setIcsFilePath(newUserPrefs.getIcsFilePath());
    }

    public GuiSettings getGuiSettings() {
        return guiSettings;
    }

    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        this.guiSettings = guiSettings;
    }

    public Path getModuloFilePath() {
        return moduloFilePath;
    }

    public void setModuloFilePath(Path moduloFilePath) {
        requireNonNull(moduloFilePath);
        this.moduloFilePath = moduloFilePath;
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
                && moduloFilePath.equals(o.moduloFilePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings, moduloFilePath);
    }

    @Override
    public String toString() {
        return "Gui Settings : " + guiSettings + "\nLocal data file location : " + moduloFilePath;
    }

}
