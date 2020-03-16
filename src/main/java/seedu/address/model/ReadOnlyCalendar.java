package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.event.Event;
import seedu.address.model.module.Module;

/**
 * Unmodifiable view of a calendar.
 */
public interface ReadOnlyCalendar {

    /**
     * Returns an unmodifiable view of the list of events. This list will not contain any duplicate events.
     */
    ObservableList<Event> getEventList();

    ObservableList<Module> getModuleList();

}
