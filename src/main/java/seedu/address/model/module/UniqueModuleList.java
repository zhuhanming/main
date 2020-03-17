package seedu.address.model.module;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.net.URL;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.NoSuchElementException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.Name;
import seedu.address.model.module.exceptions.DuplicateModuleException;
import seedu.address.model.module.exceptions.ModuleNotFoundException;

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
     * Gets the module from the list. If it does not exist, it is created.
     *
     * @param moduleCode   Code of the module required.
     * @param academicYear Academic year of the module.
     * @return Module required.
     */
    public Module getModule(ModuleCode moduleCode, AcademicYear academicYear) {
        if (contains(moduleCode, academicYear)) {
            return internalList.stream().filter(x -> x.getModuleCode().equals(moduleCode)
                    && x.getAcademicYear().toString().equals(academicYear.toString()))
                    .collect(CollectionUtil.toSingleton());
        }
        Module module = createModule(moduleCode, academicYear);
        add(module);
        return module;
    }

    /**
     * Adds a module to the list. The module must not already exist in the list.
     */
    private void add(Module toAdd) {
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

    /**
     * Creates a module based on the code and academic year given, with data from the module json files.
     *
     * @param moduleCode   Code of the module.
     * @param academicYear AY for the module.
     * @return New module created from save file.
     * @throws ModuleNotFoundException Fail to read data from the module.
     */
    @SuppressWarnings("unchecked")
    private static Module createModule(ModuleCode moduleCode, AcademicYear academicYear)
            throws ModuleNotFoundException {
        char firstCharacter = moduleCode.toString().charAt(0);
        try {
            URL jsonFile = UniqueModuleList.class.getResource("/modules/" + firstCharacter + "Modules.json");
            HashMap<String, LinkedHashMap<String, String>> map =
                    JsonUtil.readJsonFile(Path.of(jsonFile.getPath()), HashMap.class).orElseThrow();
            LinkedHashMap<String, String> moduleNeeded = map.get(moduleCode.toString());
            Name name = new Name(moduleNeeded.get("title"));
            return new Module(moduleCode, name, academicYear, moduleNeeded.get("description"));
        } catch (DataConversionException | NoSuchElementException e) {
            throw new ModuleNotFoundException();
        }
    }
}
