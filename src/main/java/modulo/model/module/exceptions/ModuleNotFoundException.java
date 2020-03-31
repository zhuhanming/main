package modulo.model.module.exceptions;

/**
 * Signals that the operation is unable to find the specified module.
 */
public class ModuleNotFoundException extends RuntimeException {
    public ModuleNotFoundException() {
        super("Cannot find the module you specified!");
    }
}
