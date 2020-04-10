package modulo.model.module.exceptions;

/**
 * Exception thrown during operations with {@link modulo.model.module.AcademicYear}.
 */
public class AcademicYearException extends RuntimeException {
    public AcademicYearException(String error) {
        super(error);
    }
}
