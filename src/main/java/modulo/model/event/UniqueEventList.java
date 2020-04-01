package modulo.model.event;

import static java.util.Objects.requireNonNull;
import static modulo.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import modulo.commons.util.CollectionUtil;
import modulo.model.event.exceptions.DuplicateEventException;
import modulo.model.event.exceptions.EventNotFoundException;
import modulo.model.module.Module;

/**
 * A list of Events that enforces uniqueness between its elements and does not allow nulls. An event is considered
 * unique by comparing using {@link Event#isSameEvent(Event)}. As such, adding and updating of Events uses {@link
 * Event#isSameEvent(Event)} for equality so as to ensure that the event being added or updated is unique in terms of
 * identity in the UniqueEventList. However, the removal of an event uses Event#equals(Object) so as to ensure that the
 * event with exactly the same fields will be removed.
 * <p>
 * Supports a minimal set of list operations.
 *
 * @see Event#isSameEvent(Event)
 */
public class UniqueEventList implements Iterable<Event> {


    private static final Comparator<Event> DEFAULT_START_DATE_COMPARATOR =
            Comparator.comparing(Event::getEventStart).thenComparing(e -> e.getName().toString());

    private final ObservableList<Event> internalList = FXCollections.observableArrayList();
    private final ObservableList<Event> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);
    private Comparator<Event> comparatorToUse = DEFAULT_START_DATE_COMPARATOR;


    /**
     * Returns true if the list contains an equivalent person as the given argument.
     */
    public boolean contains(Event toCheck) {
        requireNonNull(toCheck);
        System.out.println("toCheck ins uniqueCalendarList " + toCheck);
        return internalList.stream().anyMatch(toCheck::isSameEvent);
    }

    /**
     * Adds a person to the list. The person must not already exist in the list.
     */
    public void add(Event toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            System.out.println("Already contain: " + toAdd.toDebugString());
            throw new DuplicateEventException();
        }
        internalList.add(toAdd);
        internalList.sort(comparatorToUse);
    }

    /**
     * Replaces the person {@code target} in the list with {@code editedPerson}. {@code target} must exist in the list.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the list.
     */
    public void setEvent(Event target, Event editedCalendarItem) {
        requireAllNonNull(target, editedCalendarItem);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new EventNotFoundException();
        }

        if (!target.isSameEvent(editedCalendarItem) && contains(editedCalendarItem)) {
            throw new DuplicateEventException();
        }

        internalList.set(index, editedCalendarItem);
        internalList.sort(comparatorToUse);
    }

    /**
     * Returns an event that matches the given event.
     *
     * @param eventToCompare Event to compare against.
     * @return Event that matches.
     */
    public Event getEvent(Event eventToCompare) {
        return internalList.stream().filter(x -> x.isSameEvent(eventToCompare))
                .collect(CollectionUtil.toSingleton());
    }

    /**
     * Removes the equivalent person from the list. The person must exist in the list.
     */
    public void remove(Event toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new EventNotFoundException();
        }
        Module module = toRemove.getParentModule();
        module.deleteEvent(toRemove);
    }

    public void setEvents(UniqueEventList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
        internalList.sort(comparatorToUse);
    }

    /**
     * Replaces the contents of this list with {@code persons}. {@code persons} must not contain duplicate persons.
     */
    public void setEvents(List<Event> events) {
        requireAllNonNull(events);
        if (!eventsAreUnique(events)) {
            throw new DuplicateEventException();
        }

        internalList.setAll(events);
        internalList.sort(comparatorToUse);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Event> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Event> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueEventList // instanceof handles nulls
                && internalList.equals(((UniqueEventList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code persons} contains only unique persons.
     */
    private boolean eventsAreUnique(List<Event> events) {
        for (int i = 0; i < events.size() - 1; i++) {
            for (int j = i + 1; j < events.size(); j++) {
                if (events.get(i).isSameEvent(events.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
