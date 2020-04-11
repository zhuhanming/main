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
        super(eventName, eventType, LocalDateTime.of(LocalDate.now(), LocalTime.NOON),
                LocalDateTime.of(LocalDate.now(), LocalTime.NOON.plusHours(1)), parentModule, location);
    }

    public PartialEvent(Name eventName, EventType eventType, LocalDateTime eventStart, LocalDateTime eventEnd,
                        Module parentModule, Location location, List<Deadline> deadlines) {
        super(eventName, eventType, eventStart, eventEnd, parentModule, location, deadlines);
    }

    public PartialEvent(Name eventName, EventType eventType, LocalDateTime eventStart,
                        LocalDateTime eventEnd, Location location, Module parentModule) {
        super(eventName, eventType, eventStart, eventEnd, parentModule, location);
    }
}
