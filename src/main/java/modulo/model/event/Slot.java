package modulo.model.event;

import static java.util.Objects.requireNonNull;
import static modulo.commons.util.AppUtil.checkArgument;

/**
 * Slot for official events in module timetables.
 */
public class Slot {
    public static final String MESSAGE_CONSTRAINTS =
            "Slots should only contain alphanumeric characters, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}]*";

    public final String slot;

    public Slot(String slot) {
        requireNonNull(slot);
        checkArgument(isValidSlot(slot), MESSAGE_CONSTRAINTS);
        this.slot = slot;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidSlot(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return slot;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Slot // instanceof handles nulls
                && slot.toLowerCase().equals(((Slot) other).slot.toLowerCase())); // state check
    }

    @Override
    public int hashCode() {
        return slot.hashCode();
    }
}
