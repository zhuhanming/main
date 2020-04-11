package modulo.logic.predicate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import modulo.testutil.event.EventBuilder;
import modulo.testutil.module.ModuleBuilder;

/**
 * test case for {@code Module}'s {@code Name} matches any of the keywords given.
 */
public class NameContainsKeywordPredicateTest {

    @Test
    public void equals() {
        String firstPredicateKeywordList = "first";
        String secondPredicateKeywordList = "second";

        NameContainsKeywordsPredicate firstPredicate = new NameContainsKeywordsPredicate(firstPredicateKeywordList);
        NameContainsKeywordsPredicate secondPredicate = new NameContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same keyword -> returns true
        NameContainsKeywordsPredicate firstPredicateCopy = new NameContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different keyword -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {

        // Testing of predicate for modules

        // predicate contains keyword that matches -> return true
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate("cs2103");
        assertTrue(predicate.test(new ModuleBuilder().withModuleCode("CS2103").build()));

        // predicate contains keyword that matches -> return true
        predicate = new NameContainsKeywordsPredicate("cs");
        assertTrue(predicate.test(new ModuleBuilder().withModuleCode("CS2103").build()));

        // predicate contains keyword that matches -> return true
        predicate = new NameContainsKeywordsPredicate("2103");
        assertTrue(predicate.test(new ModuleBuilder().withModuleCode("CS2103").build()));

        // Empty string-matches all items -> returns true
        predicate = new NameContainsKeywordsPredicate("");
        assertTrue(predicate.test(new ModuleBuilder().withModuleCode("CS2103").build()));

        // predicate contains keyword that matches -> return true
        predicate = new NameContainsKeywordsPredicate("2103");
        assertTrue(predicate.test(new ModuleBuilder().withModuleCode("CS2103").build()));

        // predicate contains keyword that matches -> return true
        predicate = new NameContainsKeywordsPredicate("2103");
        assertTrue(predicate.test(new ModuleBuilder().withModuleCode("CS2103").build()));



        // Testing of predicate for events

        // predicate contains keyword that matches -> return true
        predicate = new NameContainsKeywordsPredicate("2103");
        assertTrue(predicate.test(new EventBuilder().withName("Tutorial 1").withParentModule
                (new ModuleBuilder().withModuleCode("CS2103").build()).build()));

        // predicate contains keyword that matches -> return true
        predicate = new NameContainsKeywordsPredicate("tut");
        assertTrue(predicate.test(new EventBuilder().withName("Tutorial 2").withParentModule
                (new ModuleBuilder().withModuleCode("CS2103").build()).build()));

        // predicate contains keyword that matches -> return true
        predicate = new NameContainsKeywordsPredicate("rial 2");
        assertTrue(predicate.test(new EventBuilder().withName("Tutorial 2").withParentModule
                (new ModuleBuilder().withModuleCode("CS2103").build()).build()));

        // Empty string-matches all items -> returns true
        predicate = new NameContainsKeywordsPredicate("");
        assertTrue(predicate.test(new EventBuilder().withName("Tutorial 1").withParentModule
                (new ModuleBuilder().withModuleCode("CS2103").build()).build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {

        // Non-matching keyword -> returns false
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate("eg7788");
        assertFalse(predicate.test(new ModuleBuilder().withModuleCode("CS2103").build()));

        // Only one matching keyword-all keywords should match -> returns false
        predicate = new NameContainsKeywordsPredicate("cs2103 cs2105");
        assertFalse(predicate.test(new ModuleBuilder().withModuleCode("CS2103").build()));

        // Only one matching keyword-all keywords should match -> returns false
        predicate = new NameContainsKeywordsPredicate("tutorial 2");
        assertFalse(predicate.test(new EventBuilder().withName("Tutorial 1").withParentModule
                (new ModuleBuilder().withModuleCode("CS2103").build()).build()));

        // Only one matching keyword-all keywords should match -> returns false
        predicate = new NameContainsKeywordsPredicate("cs2102");
        assertFalse(predicate.test(new EventBuilder().withName("Tutorial 1").withParentModule
                (new ModuleBuilder().withModuleCode("CS2103").build()).build()));
    }
}
