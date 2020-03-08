package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.entity.CalendarItem;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final Calendar calendar;
    private final UserPrefs userPrefs;
    private final FilteredList<CalendarItem> filteredCalendarItems;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyCalendar calendar, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(calendar, userPrefs);

        logger.fine("Initializing with calendar: " + calendar + " and user prefs " + userPrefs);

        this.calendar = new Calendar(calendar);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredCalendarItems = new FilteredList<>(this.calendar.getCalendarItemList());
    }

    public ModelManager() {
        this(new Calendar(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getCalendarFilePath() {
        return userPrefs.getCalendarFilePath();
    }

    @Override
    public void setCalendarFilePath(Path calendarFilePath) {
        requireNonNull(calendarFilePath);
        userPrefs.setCalendarFilePath(calendarFilePath);
    }

    //=========== AddressBook ================================================================================

    @Override
    public void setCalendar(ReadOnlyCalendar calendar) {
        this.calendar.resetData(calendar);
    }

    @Override
    public ReadOnlyCalendar getCalendar() {
        return calendar;
    }

    @Override
    public boolean hasCalendarItem(CalendarItem calendarItem) {
        requireNonNull(calendarItem);
        return calendar.hasCalendarItem(calendarItem);
    }

    @Override
    public void deleteCalendarItem(CalendarItem target) {
        calendar.removeCalendarItem(target);
    }

    @Override
    public void addCalendarItem(CalendarItem calendarItem) {
        calendar.addCalendarItem(calendarItem);
        updateFilteredCalendarItemList(PREDICATE_SHOW_ALL_CALENDAR_ITEMS);
    }

    @Override
    public void setCalendarItem(CalendarItem target, CalendarItem editedCalendarItem) {
        requireAllNonNull(target, editedCalendarItem);

        calendar.setCalendarItems(target, editedCalendarItem);
    }

    @Override
    public CalendarItem findCalendarItem(CalendarItem toFind) {
        requireNonNull(toFind);
        List<CalendarItem> calendarItemList =  calendar.getCalendarItemList();
        for (int i = 0; i < calendarItemList.size(); i++) {
            if (calendarItemList.get(i).matchCalendarItem(toFind)) {
                return calendarItemList.get(i);
            }
        }
        return null;
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<CalendarItem> getFilteredCalendarItemList() {
        return filteredCalendarItems;
    }

    @Override
    public void updateFilteredCalendarItemList(Predicate<CalendarItem> predicate) {
        requireNonNull(predicate);
        filteredCalendarItems.setPredicate(predicate);
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return calendar.equals(other.calendar)
                && userPrefs.equals(other.userPrefs)
                && filteredCalendarItems.equals(other.filteredCalendarItems);
    }

}
