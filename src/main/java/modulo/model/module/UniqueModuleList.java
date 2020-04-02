package modulo.model.module;

import static java.util.Objects.requireNonNull;
import static modulo.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import modulo.commons.util.CollectionUtil;
import modulo.model.module.exceptions.DuplicateModuleException;
import modulo.model.module.exceptions.ModuleNotFoundException;

/**
 * A list of modules that enforces uniqueness between its elements and does not allow nulls. A module is considered
 * unique by comparing using {@code Module#isSameModule(Module)}. As such, adding and updating of modules uses
 * Module#isSameModule(Module) for equality so as to ensure that the module being added or updated is unique in terms of
 * identity in the UniqueModuleList. However, the removal of a module uses Module#equals(Object) so as to ensure that
 * the module with exactly the same fields will be removed.
 * <p>
 * Supports a minimal set of list operations.
 *
 * @see Module#isSameModule(Module)
 */
public class UniqueModuleList implements Iterable<Module> {

    private final ObservableList<Module> internalList = FXCollections.observableArrayList();
    private final ObservableList<Module> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent person as the given argument.
     */
    public boolean contains(Module toCheck) {
        requireNonNull(toCheck);
        System.out.println("Check in uniqueModuleList     ##########" + toCheck.getModuleCode());
        return internalList.stream().anyMatch(toCheck::isSameModule);
    }

    /**
     * Returns true if the list contains a module with the given module code and academic year.
     *
     * @param moduleCode   Code of the module.
     * @param academicYear Academic year.
     * @return Boolean denoting whether the list contains this module.
     */
    public boolean contains(ModuleCode moduleCode, AcademicYear academicYear) {
        requireAllNonNull(moduleCode, academicYear);
        return internalList.stream().anyMatch(x -> x.getModuleCode().equals(moduleCode)
                && x.getAcademicYear().toString().equals(academicYear.toString()));
    }

    /**
     * Gets the module from the list. If it does not exist, an empty Optional is returned.
     *
     * @param moduleCode   Code of the module required.
     * @param academicYear Academic year of the module.
     * @return Module required.
     */
    public Optional<Module> getModule(ModuleCode moduleCode, AcademicYear academicYear) {
        if (contains(moduleCode, academicYear)) {
            return Optional.of(internalList.stream().filter(x -> x.getModuleCode().equals(moduleCode)
                    && x.getAcademicYear().toString().equals(academicYear.toString()))
                    .collect(CollectionUtil.toSingleton()));
        }
        return Optional.empty();
    }

    /**
     * Creates a module from the given code and academic year and adds it.
     *
     * @param moduleCode   Module code of module.
     * @param academicYear Academic year of module.
     */
    public void addModule(ModuleCode moduleCode, AcademicYear academicYear) {
        if (!contains(moduleCode, academicYear)) {
            Module module = ModuleLibrary.createAndReturnModule(moduleCode, academicYear);
            add(module);
        }
    }

    /**
     * Adds a module to the list. The module must not already exist in the list.
     */
    public void add(Module toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd.getModuleCode(), toAdd.getAcademicYear())) {
            throw new DuplicateModuleException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the person {@code target} in the list with {@code editedPerson}. {@code target} must exist in the list.
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
     * Removes the equivalent module from the list. The module must exist in the list.
     */
    public void remove(Module toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new ModuleNotFoundException();
        }
    }

    /**
     * Replaces the contents of this list with {@code modules}.
     */
    public void setModules(UniqueModuleList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code modules}. {@code modules} must not contain duplicate persons.
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
