package modulo.logic.predicate;

import java.util.List;
import java.util.function.Predicate;

import modulo.model.displayable.Displayable;

/**
 * Tests that a {@code Person}'s {@code CalendarItemName} matches any of the keywords given.
 */
public class NameContainsKeywordsPredicate implements Predicate<Displayable> {
    private final List<String> keywords;

    /**
     * Constructs a predicate that checks if a displayable contains certain keywords.
     *
     * @param keywords Keywords to find.
     */
    public NameContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Displayable displayable) {
        for (String keyword : keywords) {
            if (!displayable.toString().trim().toLowerCase().contains(keyword)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NameContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((NameContainsKeywordsPredicate) other).keywords)); // state check
    }

    @Override
    public String toString() {
        return keywords.toString().replace('[', '"').replace(']', '"');
    }
}
