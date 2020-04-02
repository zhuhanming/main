package modulo.logic.writer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import modulo.model.event.Event;

/**
 * Writes the current Modulo as a .ics file.
 */
public class IcsWriter {

    /**
     * @param exportDirectory Directory to export to.
     * @param eventList       List of events to export.
     * @throws IOException Failed to write.
     */
    public static void writeIcsFile(Path exportDirectory, List<Event> eventList) throws IOException {
        File file = new File(String.valueOf(exportDirectory));
        if (!file.exists()) {
            file.mkdirs();
        }
        FileWriter fw = new FileWriter(String.valueOf(exportDirectory.resolve("modulo.ics")));
        fw.write("BEGIN:VCALENDAR" + System.lineSeparator());
        fw.write("VERSION:2.0" + System.lineSeparator());
        fw.write("PRODID:-//CS2103//CS2103//EN" + System.lineSeparator());
        for (Event event : eventList) {
            fw.write(new IcsEvent(event).toIcsString());
        }
        fw.write("END:VCALENDAR");
        fw.close();
    }
}
