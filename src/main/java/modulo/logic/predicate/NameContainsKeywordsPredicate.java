package modulo.logic.predicate;

import java.util.function.Predicate;

import modulo.model.displayable.Displayable;

/**
 * Tests that a {@code Person}'s {@code CalendarItemName} matches any of the keywords given.
 */
public class NameContainsKeywordsPredicate implements Predicate<Displayable> {
    private final String keyword;

    /**
     * Constructs a predicate that checks if a displayable contains certain keywords.
     *
     * @param keyword Keyword to find.
     */
    public NameContainsKeywordsPredicate(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public boolean test(Displayable displayable) {
        return displayable.toString().trim().toLowerCase().contains(keyword);
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NameContainsKeywordsPredicate // instanceof handles nulls
                && keyword.equals(((NameContainsKeywordsPredicate) other).keyword)); // state check
    }

    @Override
    public String toString() {
        return "\"" + keyword + "\"";

    }
}
