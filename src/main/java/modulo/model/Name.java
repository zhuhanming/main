package modulo.model;

import static java.util.Objects.requireNonNull;
import static modulo.commons.util.AppUtil.checkArgument;

/**
 * Represents a name in the address book. Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class Name {

    public static final String MESSAGE_CONSTRAINTS =
            "Names should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String fullName;

    /**
     * Constructs a {@code Name}.
     *
     * @param name A valid name.
     */
    public Name(String name) {
        requireNonNull(name);
        checkArgument(isValidName(name), MESSAGE_CONSTRAINTS);
        fullName = name;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Cleans an input string to be suitable for module name usage.
     *
     * @param text Module name.
     * @return Cleaned module name.
     */
    public static String cleanNameString(String text) {
        String result = "";
        for (int i = 0, length = text.length(); i < length; i++) {
            if (!String.valueOf(text.charAt(i)).matches(VALIDATION_REGEX)) {
                if (result.charAt(result.length() - 1) != ' ') {
                    result += " ";
                }
            } else {
                result += text.charAt(i);
            }
        }
        return result.trim();
    }


    @Override
    public String toString() {
        return fullName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Name // instanceof handles nulls
                && fullName.toLowerCase().equals(((Name) other).fullName.toLowerCase())); // state check
    }

    @Override
    public int hashCode() {
        return fullName.hashCode();
    }

}
