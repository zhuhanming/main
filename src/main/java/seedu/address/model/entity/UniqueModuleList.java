package seedu.address.model.entity;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.entity.exceptions.CalendarItemNotFoundException;
import seedu.address.model.entity.exceptions.DuplicateCalendarItemException;
import seedu.address.model.entity.exceptions.DuplicateModuleException;
import seedu.address.model.entity.exceptions.ModuleNotFoundException;
import seedu.address.model.person.exceptions.DuplicatePersonException;

import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

public class UniqueModuleList implements Iterable<Module> {

    private final ObservableList<Module> internalList = FXCollections.observableArrayList();
    private final ObservableList<Module> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent person as the given argument.
     */
    public boolean contains(Module toCheck) {
        requireNonNull(toCheck);
        System.out.println("Check in uniqueModuleList     ##########"+toCheck.getModuleCode());
        return internalList.stream().anyMatch(toCheck::isSameModule);
    }

    /**
     * Adds a person to the list.
     * The person must not already exist in the list.
     */
    public void add(Module toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateModuleException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the list.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the list.
     */
    public void setModule(Module target, Module editedModule) {
        requireAllNonNull(target, editedModule);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new ModuleNotFoundException();
        }

        if (!target.isSameModule(editedModule) && contains(editedModule)) {
            throw new DuplicateModuleException();
        }

        internalList.set(index, editedModule);
    }


    /**
     * Removes the equivalent person from the list.
     * The person must exist in the list.
     */
    public void remove(Module toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new ModuleNotFoundException();
        }
    }

    public void setModules(UniqueModuleList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setModules(List<Module> modules) {
        requireAllNonNull(modules);
        if (!modulesAreUnique(modules)) {
            throw new DuplicateModuleException();
        }

        internalList.setAll(modules);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Module> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Module> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueModuleList // instanceof handles nulls
                && internalList.equals(((UniqueModuleList) other).internalList));
    }


    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code persons} contains only unique persons.
     */
    private boolean modulesAreUnique(List<Module> moduleItems) {
        for (int i = 0; i < moduleItems.size() - 1; i++) {
            for (int j = i + 1; j < moduleItems.size(); j++) {
                if (moduleItems.get(i).isSameModule(moduleItems.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
