package seedu.address.model.entity.exceptions;

public class DuplicateModuleException extends RuntimeException{
    public DuplicateModuleException() {
        super("Operation would result in duplicate module");
    }
}
