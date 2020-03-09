package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.entity.CalendarItem;

public interface ReadOnlyModule {

    ObservableList<CalendarItem> getModuleList();
}
