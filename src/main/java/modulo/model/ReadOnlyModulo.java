package modulo.model;

import javafx.collections.ObservableList;
import modulo.model.event.Event;
import modulo.model.module.Module;

/**
 * Unmodifiable view of Modulo.
 */
public interface ReadOnlyModulo {
    /**
     * Returns an unmodifiable view of the list of events. This list will not contain any duplicate events.
     *
     * @return Unmodifiable view of the list of events.
     */
    ObservableList<Event> getEventList();

    /**
     * Returns an unmodifiable view of the list of modules. This list will not contain any duplicate modules.
     *
     * @return Unmodifiable view of the list of modules.
     */
    ObservableList<Module> getModuleList();
}
