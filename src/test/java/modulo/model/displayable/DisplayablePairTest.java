package modulo.model.displayable;

import static modulo.testutil.module.TypicalModules.CS2103;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import modulo.model.module.Module;

/**
 * JUnit test class for {@code DisplayablePair}.
 */
public class DisplayablePairTest {

    @Test
    public void testConstructor_validInputs_successfulConstruction() {
        DisplayablePair<Module, Integer> testDisplayablePair = new DisplayablePair<>(CS2103, 5);
        assertEquals(testDisplayablePair.getFirst(), CS2103);
        assertEquals(testDisplayablePair.getSecond(), 5);
    }
}
