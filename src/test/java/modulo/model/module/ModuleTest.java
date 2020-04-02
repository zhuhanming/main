package modulo.model.module;

import static modulo.logic.commands.CommandTestUtil.VALID_ACADEMICYEAR_CS2105;
import static modulo.logic.commands.CommandTestUtil.VALID_DESCRIPTION_CS2105;
import static modulo.logic.commands.CommandTestUtil.VALID_NAME_CS2105;
import static modulo.logic.commands.CommandTestUtil.VALID_SEMESTER_CS2105;
import static modulo.testutil.module.TypicalModule.CS2103;
import static modulo.testutil.module.TypicalModule.CS2105;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import modulo.logic.parser.exceptions.ParseException;
import modulo.testutil.module.ModuleBuilder;


public class ModuleTest {

    /**
     * @Test public void asObservableList_modifyList_throwsUnsupportedOperationException() throws ParseException {
     * Module module = new ModuleBuilder().build();
     * assertThrows(UnsupportedOperationException.class, () -> module.getEvents().remove(0));
     * }
     */

    @Test
    public void isSamePerson() throws ParseException {
        // same object -> returns true
        assertTrue(CS2103.isSameModule(CS2103));

        // null -> returns false
        assertFalse(CS2103.isSameModule(null));

        // different academic year and semester  -> returns false
        Module editedCS2103 = new ModuleBuilder(CS2103)
                .withAcademicYear(VALID_ACADEMICYEAR_CS2105, VALID_SEMESTER_CS2105)
                .build();
        assertFalse(CS2103.isSameModule(editedCS2103));

        // different module name -> returns false
        editedCS2103 = new ModuleBuilder(CS2103).withModuleName(VALID_NAME_CS2105).build();
        assertFalse(CS2103.isSameModule(editedCS2103));

        // same module name, same module code , different attributes(description) -> returns true
        editedCS2103 = new ModuleBuilder(CS2103).withDescription(VALID_DESCRIPTION_CS2105).build();
        assertTrue(CS2103.isSameModule(editedCS2103));
    }

    @Test
    public void equals() throws ParseException {
        // same values -> returns true
        Module cs2103Copy = new ModuleBuilder(CS2103).build();
        assertTrue(CS2103.equals(cs2103Copy));

        // same object -> returns true
        assertTrue(CS2103.equals(CS2103));

        // null -> returns false
        assertFalse(CS2103.equals(null));

        // different type -> returns false
        assertFalse(CS2103.equals(5));

        // different person -> returns false
        assertFalse(CS2103.equals(CS2105));
    }

}
