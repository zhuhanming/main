package seedu.address.model.event;

import java.time.LocalDateTime;
import java.util.List;

import seedu.address.model.Name;
import seedu.address.model.deadline.Deadline;
import seedu.address.model.module.Module;


/**
 * A helper class for deadline creation, where only the event name and {@code MatchableModule} required for
 * initialisation. The object returned will only be used for matching with actually created events subsequently.
 */
public class PartialEvent extends Event {
    public PartialEvent(Name eventName, EventType eventType, Module parentModule) {
        super(eventName, eventType, LocalDateTime.now(), LocalDateTime.now(), parentModule);
    }

    public PartialEvent(Name eventName, EventType eventType, LocalDateTime eventStart,
                        LocalDateTime eventEnd, Module parentModule, List<Deadline> deadlines) {
        super(eventName, eventType, eventStart, eventEnd, parentModule, deadlines);
    }

    public PartialEvent(Name eventName, EventType eventType, LocalDateTime eventStart,
                        LocalDateTime eventEnd, Module parentModule) {
        super(eventName, eventType, eventStart, eventEnd, parentModule);
    }
}
