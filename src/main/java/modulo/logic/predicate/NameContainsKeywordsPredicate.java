package modulo.logic.predicate;

import java.util.List;
import java.util.function.Predicate;

import modulo.commons.util.StringUtil;
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
        System.out.println("Displayable is " + displayable.toString());
        /**
         for (String keyword : keywords) {
         if (!displayable.toString().trim().toLowerCase().contains(keyword.toLowerCase())) {
         return false;
         }
         }*/
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(displayable.toString().trim(), keyword));
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
