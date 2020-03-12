package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.entity.CalendarItem;

/**
 * Unmodifiable view of modules.
 */
public interface ReadOnlyModule {

    ObservableList<CalendarItem> getModuleList();
}
