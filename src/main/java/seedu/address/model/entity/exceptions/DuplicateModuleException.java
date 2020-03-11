package seedu.address.model.entity.exceptions;

/**
 * Exception thrown when an operation would result in duplicate module.
 */
public class DuplicateModuleException extends RuntimeException {
    public DuplicateModuleException() {
        super("Operation would result in duplicate module");
    }
}
