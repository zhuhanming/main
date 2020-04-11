package modulo.testutil.module;

import static modulo.logic.commands.CommandTestUtil.VALID_ACADEMIC_END_YEAR_CS2103;
import static modulo.logic.commands.CommandTestUtil.VALID_ACADEMIC_END_YEAR_CS2105;
import static modulo.logic.commands.CommandTestUtil.VALID_ACADEMIC_START_YEAR_CS2103;
import static modulo.logic.commands.CommandTestUtil.VALID_ACADEMIC_START_YEAR_CS2105;
import static modulo.logic.commands.CommandTestUtil.VALID_CODE_CS2103;
import static modulo.logic.commands.CommandTestUtil.VALID_CODE_CS2105;
import static modulo.logic.commands.CommandTestUtil.VALID_DESCRIPTION_CS2103;
import static modulo.logic.commands.CommandTestUtil.VALID_DESCRIPTION_CS2105;
import static modulo.logic.commands.CommandTestUtil.VALID_NAME_CS2103;
import static modulo.logic.commands.CommandTestUtil.VALID_NAME_CS2105;
import static modulo.logic.commands.CommandTestUtil.VALID_SEMESTER_CS2103;
import static modulo.logic.commands.CommandTestUtil.VALID_SEMESTER_CS2105;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import modulo.model.Modulo;
import modulo.model.module.AcademicYear;
import modulo.model.module.Module;
import modulo.model.module.ModuleCode;
import modulo.model.module.PartialModule;

/**
 * A utility class containing a list of {@code Module} objects to be used in tests..
 */
public class TypicalModules {

    public static final Module CS1010E = new ModuleBuilder().withModuleCode("CS1010E")
            .withModuleName("Programming Methodology")
            .withAcademicYear(2019, 2020, 2)
            .withDescription("This module introduces the fundamental concepts of problem solving by "
                    + "computing and programming using an imperative programming language.").build();

    public static final Module CS1231S = new ModuleBuilder().withModuleCode("CS1231S")
            .withAcademicYear(2019, 2020, 2)
            .build();

    /**
     * Manually added
     */
    public static final Module GER1000 = new ModuleBuilder().withModuleCode("GER1000")
            .withAcademicYear(2019, 2020, 2).build();
    public static final Module IS1103 = new ModuleBuilder().withModuleCode("IS1103")
            .withAcademicYear(2019, 2020, 2).build();

    /**
     * Manually added - Module details found in {@code CommandTestUtil}
     */
    public static final Module CS2103 = new ModuleBuilder().withModuleCode(VALID_CODE_CS2103)
            .withModuleName(VALID_NAME_CS2103)
            .withAcademicYear(VALID_ACADEMIC_START_YEAR_CS2103, VALID_ACADEMIC_END_YEAR_CS2103, VALID_SEMESTER_CS2103)
            .withDescription(VALID_DESCRIPTION_CS2103).build();

    public static final Module CS2105 = new ModuleBuilder()
            .withModuleCode(VALID_CODE_CS2105)
            .withModuleName(VALID_NAME_CS2105)
            .withAcademicYear(VALID_ACADEMIC_START_YEAR_CS2105, VALID_ACADEMIC_END_YEAR_CS2105, VALID_SEMESTER_CS2105)
            .withDescription(VALID_DESCRIPTION_CS2105).build();

    /**
     * Partial Modules for testing
     */
    public static final Module PARTIAL_MODULE_CS1231S = new PartialModule(new ModuleCode("CS1231S"),
            new AcademicYear(2019, 2020, 2));


    private TypicalModules() {
    } // prevents instantiation

    /**
     * Returns an {@code InventorySystem} with all the typical modules.
     */
    public static Modulo getTypicalModulo() {
        Modulo ab = new Modulo();
        for (Module module : getTypicalModule()) {
            ab.addModule(module.getModuleCode(), module.getAcademicYear());
        }
        return ab;
    }

    public static List<Module> getTypicalModule() {
        return new ArrayList<>(Arrays.asList(CS1010E, CS2103, CS2105));
    }

}
