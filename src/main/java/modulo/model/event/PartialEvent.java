package modulo.model.event;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import java.util.List;

import modulo.model.Name;
import modulo.model.deadline.Deadline;
import modulo.model.module.Module;


/**
 * A helper class for deadline creation, where only the event name and {@code MatchableModule} required for
 * initialisation. The object returned will only be used for matching with actually created events subsequently.
 */
public class PartialEvent extends Event {
    public PartialEvent(Name eventName, EventType eventType, Module parentModule, Location location) {
        super(eventName, eventType, LocalDateTime.of(LocalDate.of(2020, 1, 20), LocalTime.NOON),
                LocalDateTime.of(LocalDate.of(2020, 1, 20), LocalTime.NOON.plusHours(1)), parentModule, location);
    }

    public PartialEvent(Name eventName, EventType eventType, LocalDateTime eventStart, LocalDateTime eventEnd,
                        Module parentModule, Location location, List<Deadline> deadlines, Slot slot) {
        super(eventName, eventType, eventStart, eventEnd, parentModule, location, deadlines, slot);
    }

    public PartialEvent(Name eventName, EventType eventType, LocalDateTime eventStart,
                        LocalDateTime eventEnd, Location location, Module parentModule) {
        super(eventName, eventType, eventStart, eventEnd, parentModule, location);
    }
}
