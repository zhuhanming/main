package seedu.address.logic.parser;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.model.Displayable;

/**
 * Tests that a {@code Person}'s {@code CalendarItemName} matches any of the keywords given.
 */
public class SameItemPredicate implements Predicate<Displayable> {
    private final List<String> keywords;

    public SameItemPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Displayable event) {
        //return keywords.stream().anyMatch(keyword -> StringUtil.containsWordIgnoreCase(event.toString().trim(), keyword));

        if((event.toString()).trim().equals(keywords.get(0))) {
            return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SameItemPredicate // instanceof handles nulls
                && keywords.equals(((SameItemPredicate) other).keywords)); // state check
    }

}
