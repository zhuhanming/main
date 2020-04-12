package modulo.model.displayable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

/**
 * JUnit test class for {@code DisplayableType}.
 */
public class DisplayableTypeTest {

    private static final int NUM_DISPLAYABLE_TYPE = 2;

    /**
     * Tests the integrity of the {@code DisplayableType} enum.
     */
    @Test
    public void testDisplayableType() {
        assertEquals(NUM_DISPLAYABLE_TYPE, DisplayableType.values().length);
        try {
            DisplayableType.valueOf("MODULE");
            DisplayableType.valueOf("EVENT");
        } catch (IllegalArgumentException e) {
            fail();
        }
    }
}
