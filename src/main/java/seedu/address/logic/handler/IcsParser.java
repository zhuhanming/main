package seedu.address.logic.handler;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import seedu.address.model.event.Event;
import seedu.address.model.ics.IcsEvent;

/**
 * todo
 */
public class IcsParser {

    /**
     * todo
     * @param exportDirectory
     * @param eventList
     * @throws IOException
     */
    public static void generateIcsFile(Path exportDirectory, List<Event> eventList) throws IOException {
        System.out.println("ICS PARSER START");
        FileWriter fw = new FileWriter(exportDirectory.toString() + "\\calendar.ics");
        System.out.println("ICS PARSER START2");
        fw.write("BEGIN:VCALENDAR" + System.lineSeparator());
        fw.write("VERSION:2.0" + System.lineSeparator());
        fw.write("PRODID:-//CS2103//CS2103//EN" + System.lineSeparator());
        for (int i = 0; i < eventList.size(); i++) {
            fw.write(new IcsEvent(eventList.get(i)).toIcsString());
        }
        fw.write("END:VCALENDAR");
        fw.close();
    }
}
