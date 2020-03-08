package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.entity.CalendarItem;

/**
 * Unmodifiable view of a calendar
 */
public interface ReadOnlyCalendar {

    /**
     * Returns an unmodifiable view of the calendar.
     * This list will not contain any duplicate calendar items.
     */
    ObservableList<CalendarItem> getCalendarItemList();

}
