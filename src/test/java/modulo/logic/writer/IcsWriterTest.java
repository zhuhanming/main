package modulo.logic.writer;

import static modulo.testutil.Assert.assertThrows;

import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import modulo.model.event.Event;

public class IcsWriterTest {

    @Test
    public void writeIcsFile_nullDirectory_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> IcsWriter.writeIcsFile(null,
                new ArrayList<Event>()));
    }

    @Test
    public void writeIcsFile_invalidDirectory_throwsFileNotFoundException() {
        assertThrows(InvalidPathException.class, () -> IcsWriter.writeIcsFile(Paths.get("ABCD:\\data"),
                new ArrayList<Event>()));
    }
}
