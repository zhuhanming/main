package modulo.model;

import java.nio.file.Path;

import modulo.commons.core.GuiSettings;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefs {

    GuiSettings getGuiSettings();

    Path getModuloFilePath();

    Path getIcsFilePath();
}
