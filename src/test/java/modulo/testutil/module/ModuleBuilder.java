package modulo.testutil.module;

import java.util.List;

import modulo.model.Name;
import modulo.model.event.Event;
import modulo.model.module.AcademicYear;
import modulo.model.module.Module;
import modulo.model.module.ModuleCode;
import modulo.model.module.exceptions.AcademicYearException;


/**
 * A utility class to help with building Module objects.
 */
public class ModuleBuilder {

    public static final String DEFAULT_MODULE_CODE = "CS2103";
    public static final String DEFAULT_MODULE_NAME = "Software Engineering";
    public static final String DEFAULT_ACADEMIC_YEAR = "2019/2020";
    public static final String DEFAULT_SEMESTER = "2";
    public static final String DEFAULT_DESCRIPTION = "";

    private Name name;
    private ModuleCode moduleCode;
    private AcademicYear academicYear;
    private String description;
    private List<Event> events;

    public ModuleBuilder() {
        name = new Name(DEFAULT_MODULE_NAME);
        moduleCode = new ModuleCode(DEFAULT_MODULE_CODE);
        try {
            academicYear = new AcademicYear(DEFAULT_ACADEMIC_YEAR, DEFAULT_SEMESTER);
        } catch (AcademicYearException e) {
            e.printStackTrace();
        }
        description = DEFAULT_DESCRIPTION;
        events = null;
    }

    public ModuleBuilder(Module moduleToCopy) {
        name = moduleToCopy.getName();
        moduleCode = moduleToCopy.getModuleCode();
        academicYear = moduleToCopy.getAcademicYear();
        description = moduleToCopy.getDescription();
    }

    /**
     * Sets the {@code Name} of the module that we are building.
     */
    public ModuleBuilder withModuleName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code ModuleCode} for the module that we are building.
     */
    public ModuleBuilder withModuleCode(String moduleCode) {
        this.moduleCode = new ModuleCode(moduleCode);
        return this;
    }


    /**
     * Parses the {@code startYear}, {@code endYear} and {@code semester} into an {@code AcademicYear} for the module we
     * are building.
     */
    public ModuleBuilder withAcademicYear(int startYear, int endYear, int semester) {
        try {
            this.academicYear = new AcademicYear(startYear, endYear, semester);
        } catch (AcademicYearException e) {
            e.printStackTrace();
        }
        return this;
    }

    /**
     * Sets the {@code description} for the module that we are building.
     */
    public ModuleBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    /**
     * Sets the {@code events} for the module that we are building.
     */
    public ModuleBuilder withEvents(List<Event> events) {
        this.events = events;
        return this;
    }

    /**
     * Builds the module with the given inputs.
     */
    public Module build() {
        if (this.events == null) {
            return new Module(moduleCode, name, academicYear, description);
        }
        return new Module(moduleCode, name, academicYear, description, events);
    }
}
