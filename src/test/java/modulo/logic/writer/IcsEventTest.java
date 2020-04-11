package modulo.logic.writer;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import modulo.model.Name;
import modulo.model.deadline.Deadline;
import modulo.model.event.Event;
import modulo.model.event.EventType;
import modulo.model.event.Location;
import modulo.model.module.AcademicYear;
import modulo.model.module.Module;
import modulo.model.module.ModuleCode;

public class IcsEventTest {

    @Test
    public void toIcsString_validEvent_success() {
        LocalDateTime currentDateTime = LocalDateTime.now();

        String uuid = UUID.randomUUID().toString();
        String icsString = "BEGIN:VEVENT" + System.lineSeparator()
                + "UID:" + uuid + System.lineSeparator()
                + "SEQUENCE:0" + System.lineSeparator()
                + "DTSTAMP:" + currentDateTime.format(DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss'Z'"))
                + System.lineSeparator()
                + "DTSTART;TZID=Asia/Singapore:"
                + currentDateTime.format(DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss'Z'")) + System.lineSeparator()
                + "DTEND;TZID=Asia/Singapore:"
                + currentDateTime.format(DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss'Z'")) + System.lineSeparator()
                + "RRULE:FREQ=WEEKLY;COUNT=1;BYDAY=SA" + System.lineSeparator()
                + "SUMMARY:TestEvent" + System.lineSeparator()
                + "DESCRIPTION:"
                + currentDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + " - TestDeadline0"
                + System.lineSeparator()
                + currentDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + " - TestDeadline1"
                + System.lineSeparator()
                + currentDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + " - TestDeadline2"
                + System.lineSeparator()
                + "LOCATION:TestLocation" + System.lineSeparator()
                + "BEGIN:VTODO" + System.lineSeparator()
                + "DUE;TZID=Asia/Singapore:"
                + currentDateTime.format(DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss'Z'")) + System.lineSeparator()
                + "SUMMARY:TestDeadline0" + System.lineSeparator()
                + "STATUS:NEEDS-ACTION" + System.lineSeparator()
                + "END:VTODO" + System.lineSeparator()
                + "BEGIN:VTODO" + System.lineSeparator()
                + "DUE;TZID=Asia/Singapore:"
                + currentDateTime.format(DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss'Z'")) + System.lineSeparator()
                + "SUMMARY:TestDeadline1" + System.lineSeparator()
                + "STATUS:NEEDS-ACTION" + System.lineSeparator()
                + "END:VTODO" + System.lineSeparator()
                + "BEGIN:VTODO" + System.lineSeparator()
                + "DUE;TZID=Asia/Singapore:"
                + currentDateTime.format(DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss'Z'")) + System.lineSeparator()
                + "SUMMARY:TestDeadline2" + System.lineSeparator()
                + "STATUS:NEEDS-ACTION" + System.lineSeparator()
                + "END:VTODO" + System.lineSeparator()
                + "END:VEVENT" + System.lineSeparator();

        Module module = new Module(new ModuleCode("CS2103"), new Name("Software Engineering"),
                new AcademicYear(2019, 2020, 2), "Software Engineering",
                new ArrayList<Event>());
        Event event = new Event(new Name("TestEvent"), EventType.TUTORIAL, currentDateTime, currentDateTime,
                module, new Location("TestLocation"));
        event.removeAllDeadlines();
        for (int i = 0; i < 3; i++) {
            event.addDeadline(new Deadline(new Name("TestDeadline" + i), event));
        }
        IcsEvent icsEvent = new IcsEvent(event);
        icsEvent.setUid(uuid);

        assertTrue(icsEvent.toIcsString().equals(icsString));
    }
}
