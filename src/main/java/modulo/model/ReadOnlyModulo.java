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
     */
    ObservableList<Event> getEventList();

    ObservableList<Module> getModuleList();

}
