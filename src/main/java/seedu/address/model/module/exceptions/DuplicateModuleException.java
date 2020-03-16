package seedu.address.model.module.exceptions;

/**
 * Exception thrown when an operation would result in duplicate modules.
 */
public class DuplicateModuleException extends RuntimeException {
    public DuplicateModuleException() {
        super("Operation would result in duplicate modules!");
    }
}
