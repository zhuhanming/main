package seedu.address.model.event;

import java.time.LocalDateTime;

import seedu.address.model.Name;
import seedu.address.model.module.Module;


/**
 * A helper class for deadline creation, where only the event name and {@code MatchableModule} required for
 * initialisation. The object returned will only be used for matching with actually created events subsequently.
 */
public class MatchableEvent extends Event {
    public MatchableEvent(Name eventName, EventType eventType, Module parentModule) {
        super(eventName, eventType, LocalDateTime.now(), LocalDateTime.now(), parentModule);
    }
}
