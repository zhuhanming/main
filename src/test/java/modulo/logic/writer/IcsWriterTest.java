package modulo.logic.writer;

import static modulo.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Stream;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

public class IcsWriterTest {

    @Test
    public void writeIcsFile_nullDirectory_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> IcsWriter.writeIcsFile(null,
                new ArrayList<>()));
    }

    @Test
    public void writeIcsFile_invalidDirectory_createsDirectory() {
        try {
            IcsWriter.writeIcsFile(Paths.get("ABCD:\\data"), new ArrayList<>());
            File file = new File("ABCD:\\data/modulo.ics");
            assertTrue(file.exists());
        } catch (IOException e) {
            fail();
        }
    }

    @AfterAll
    public static void cleanUpTestFiles() {
        try {
            deleteDirectory("ABCD:\\data");
        } catch (IOException e) {
            // Nothing
        }
    }

    /**
     * Deletes directories thoroughly.
     *
     * @param path Path to delete.
     * @throws IOException If the file cannot be found.
     */
    private static void deleteDirectory(String path) throws IOException {
        Path rootPath = Paths.get(path);
        try (Stream<Path> walk = Files.walk(rootPath)) {
            walk.sorted(Comparator.reverseOrder()).map(Path::toFile).forEach(File::delete);
        } catch (IOException e) {
            throw e;
        }
    }
}
