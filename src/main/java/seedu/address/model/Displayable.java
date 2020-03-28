package seedu.address.model;

import java.util.function.Predicate;

/**
 * An interface for classes that can be listed in Modulo.
 */
public interface Displayable extends Predicate<Displayable> {
}
