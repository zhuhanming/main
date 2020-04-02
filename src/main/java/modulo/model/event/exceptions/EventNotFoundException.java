package modulo.model.event.exceptions;

/**
 * Signals that the operation is unable to find the specified event.
 */
public class EventNotFoundException extends RuntimeException {
    public EventNotFoundException() {
        super("Cannot find the event you specified!");
    }

    public EventNotFoundException(String e) {
        super(e);
    }
}
