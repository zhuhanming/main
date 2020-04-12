package modulo.testutil.module;

import static modulo.logic.parser.CliSyntax.PREFIX_MODULE;

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
        return PREFIX_MODULE + module.getModuleCode().moduleCode + " ";
    }
}
