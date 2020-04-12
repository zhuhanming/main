package modulo.testutil.module;

import static modulo.logic.parser.CliSyntax.PREFIX_ACADEMIC_YEAR;
import static modulo.logic.parser.CliSyntax.PREFIX_MODULE;
import static modulo.logic.parser.CliSyntax.PREFIX_SEMESTER;

import modulo.logic.commands.AddModuleCommand;
import modulo.model.module.Module;

/**
 * Returns the part of command string for the given {@code module}'s details.
 */
public class ModuleUtil {

    /**
     * Returns an add command string for adding the {@code person}.
     */
    public static String getAddCommand(Module module) {
        return AddModuleCommand.COMMAND_WORD + " " + getModuleDetails(module);
    }

    /**
     * Returns the part of command string for the given {@code module}'s details.
     */
    public static String getModuleDetails(Module module) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_MODULE + module.getModuleCode().moduleCode + " ");
        sb.append(PREFIX_ACADEMIC_YEAR + Integer.toString(module.getAcademicYear().getStartYear())
                + "/" + module.getAcademicYear().getEndYear() + " ");
        sb.append(PREFIX_SEMESTER + Integer.toString(module.getAcademicYear().getSemester()) + " ");

        return sb.toString();
    }
}
