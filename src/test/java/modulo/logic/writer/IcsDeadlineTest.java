package modulo.logic.writer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import modulo.model.Name;
import modulo.model.deadline.Deadline;
import modulo.model.event.Event;
import modulo.model.event.EventType;
import modulo.model.event.Location;
import modulo.model.module.AcademicYear;
import modulo.model.module.Module;
import modulo.model.module.ModuleCode;

public class IcsDeadlineTest {

    @Test
    public void toIcsString_validDeadline_success() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        String icsString = "BEGIN:VTODO" + System.lineSeparator()
                + "DUE;TZID=Asia/Singapore:"
                + currentDateTime.format(DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss'Z'")) + System.lineSeparator()
                + "SUMMARY:TestDeadline" + System.lineSeparator()
                + "STATUS:NEEDS-ACTION" + System.lineSeparator()
                + "END:VTODO" + System.lineSeparator();
        Module module = new Module(new ModuleCode("CS2103"), new Name("Software Engineering"),
                new AcademicYear(2019, 2020, 2), "Software Engineering",
                new ArrayList<>());
        Event event = new Event(new Name("TestEvent"), EventType.TUTORIAL, currentDateTime, currentDateTime,
                module, new Location("TestLocation"));
        Deadline deadline = new Deadline(new Name("TestDeadline"), event);
        assertEquals(new IcsDeadline(deadline).toIcsString(), icsString);
    }
}
