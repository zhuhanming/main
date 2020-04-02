package modulo.testutil.module;

import modulo.logic.parser.exceptions.ParseException;
import modulo.model.Name;
import modulo.model.module.AcademicYear;
import modulo.model.module.Module;
import modulo.model.module.ModuleCode;


/**
 * A utility class to help with building Module objects.
 */
public class ModuleBuilder {

    public static final String DEFAULT_MODULE_CODE = "CS2103";
    public static final String DEFAULT_MODULE_NAME = "Software Engineering";
    public static final int DEFAULT_ACADEMIC_YEAR = 2019 / 2020;
    public static final int DEFAULT_SEMESTER = 2;
    public static final String DEFAULT_DESCRIPTION = "";

    private Name name;
    private ModuleCode moduleCode;
    private AcademicYear academicYear;
    private String description;

    public ModuleBuilder() {
        name = new Name(DEFAULT_MODULE_NAME);
        moduleCode = new ModuleCode(DEFAULT_MODULE_CODE);
        try {
            academicYear = new AcademicYear(DEFAULT_ACADEMIC_YEAR, DEFAULT_SEMESTER);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        description = DEFAULT_DESCRIPTION;
    }

    public ModuleBuilder(Module moduleToCopy) throws ParseException {
        name = moduleToCopy.getName();
        moduleCode = moduleToCopy.getModuleCode();
        academicYear = moduleToCopy.getAcademicYear();
        description = moduleToCopy.getDescription();
    }

    /**
     * Sets the {@code Name} of the {@code Module} that we are building.
     */
    public ModuleBuilder withModuleName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code ModuleCode} into a {@code Module>} that we are building.
     */
    public ModuleBuilder withModuleCode(String moduleCode) {
        this.moduleCode = new ModuleCode(moduleCode);
        return this;
    }


    /**
     * Parses the {@code AcademicYear and semester } into a {@code Set<Tag>} a that we are building.
     */
    public ModuleBuilder withAcademicYear(int academicYear, int semester) {
        try {
            this.academicYear = new AcademicYear(academicYear, semester);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return this;
    }

    /**
     * Parses the {@code description} into a {@code Module} and set it to the {@code Person} that we are building.
     */
    public ModuleBuilder withDescription(String description) {
        this.description = description;
        return this;
    }


    public Module build() {
        return new Module(moduleCode, name, academicYear, description);
    }


}
