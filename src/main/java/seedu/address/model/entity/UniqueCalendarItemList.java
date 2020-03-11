package seedu.address.model.entity;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.entity.exceptions.CalendarItemNotFoundException;
import seedu.address.model.entity.exceptions.DuplicateCalendarItemException;
import seedu.address.model.person.exceptions.DuplicatePersonException;

/**
 * A list of CalendarItems that enforces uniqueness between its elements and does not allow nulls.
 * A module is considered unique by comparing using {@code CalendarItem#isSameCalendarItem(CalendarItem)}.
 * As such, adding and updating of CalendarItems uses CalendarItem#isSameCalendarItem(CalendarItem) for
 * equality so as to ensure that the calendar item being added or updated is unique in terms of identity in the
 * UniqueCalendarItemList. However, the removal of a calendar item uses CalendarItem#equals(Object) so as to
 * ensure that the calendar item with exactly the same fields will be removed.
 * <p>
 * Supports a minimal set of list operations.
 *
 * @see CalendarItem#isSameCalendarItem(CalendarItem)
 */
public class UniqueCalendarItemList implements Iterable<CalendarItem> {

    private final ObservableList<CalendarItem> internalList = FXCollections.observableArrayList();
    private final ObservableList<CalendarItem> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent person as the given argument.
     */
    public boolean contains(CalendarItem toCheck) {
        requireNonNull(toCheck);
        // System.out.println("toCheck ins uniqueCalendarList "+ toCheck.);
        return internalList.stream().anyMatch(toCheck::isSameCalendarItem);
    }

    /**
     * Adds a person to the list.
     * The person must not already exist in the list.
     */
    public void add(CalendarItem toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            System.out.println("ALready contain: " + toAdd.toDebugString());
            throw new DuplicateCalendarItemException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the list.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the list.
     */
    public void setCalendarItem(CalendarItem target, CalendarItem editedCalendarItem) {
        requireAllNonNull(target, editedCalendarItem);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new CalendarItemNotFoundException();
        }

        if (!target.isSameCalendarItem(editedCalendarItem) && contains(editedCalendarItem)) {
            throw new DuplicateCalendarItemException();
        }

        internalList.set(index, editedCalendarItem);
    }

    /**
     * Removes the equivalent person from the list.
     * The person must exist in the list.
     */
    public void remove(CalendarItem toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new CalendarItemNotFoundException();
        }
    }

    public void setCalendarItems(UniqueCalendarItemList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setCalendarItems(List<CalendarItem> calendarItems) {
        requireAllNonNull(calendarItems);
        if (!calendarItemsAreUnique(calendarItems)) {
            throw new DuplicatePersonException();
        }

        internalList.setAll(calendarItems);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<CalendarItem> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<CalendarItem> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueCalendarItemList // instanceof handles nulls
                && internalList.equals(((UniqueCalendarItemList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code persons} contains only unique persons.
     */
    private boolean calendarItemsAreUnique(List<CalendarItem> calendarItems) {
        for (int i = 0; i < calendarItems.size() - 1; i++) {
            for (int j = i + 1; j < calendarItems.size(); j++) {
                if (calendarItems.get(i).isSameCalendarItem(calendarItems.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
