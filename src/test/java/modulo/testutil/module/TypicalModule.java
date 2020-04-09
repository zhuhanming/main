package modulo.testutil.module;

import static modulo.logic.commands.CommandTestUtil.VALID_ACADEMICYEAR_CS2103;
import static modulo.logic.commands.CommandTestUtil.VALID_ACADEMICYEAR_CS2105;
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
import modulo.model.module.Module;

/**
 * A utility class containing a list of {@code Module} objects to be used in tests..
 */
public class TypicalModule {

    public static final Module CS1010E = new ModuleBuilder().withModuleCode("CS1010E")
            .withModuleName("Programming Methodology")
            .withAcademicYear("2019/2020", "2")
            .withDescription("This module introduces the fundamental concepts of problem solving by "
                    + "computing and programming using an imperative programming language.").build();


    /**
     * Manually added - Module's details found int in {@code CommandTestUtil}
     */
    public static final Module CS2103 = new ModuleBuilder().withModuleCode(VALID_CODE_CS2103)
            .withModuleName(VALID_NAME_CS2103)
            .withAcademicYear(VALID_ACADEMICYEAR_CS2103, VALID_SEMESTER_CS2103)
            .withDescription(VALID_DESCRIPTION_CS2103).build();

    public static final Module CS2105 = new ModuleBuilder()
            .withModuleCode(VALID_CODE_CS2105)
            .withModuleName(VALID_NAME_CS2105)
            .withAcademicYear(VALID_ACADEMICYEAR_CS2105, VALID_SEMESTER_CS2105)
            .withDescription(VALID_DESCRIPTION_CS2105).build();


    private TypicalModule() {
    } // prevents instantiation

    /**
     * Returns an {@code Modulo} with all the typical modulo.
     */
    public static Modulo getModulo() {
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
