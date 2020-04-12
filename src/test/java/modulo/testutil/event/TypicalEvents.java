package modulo.testutil.event;

import static modulo.testutil.module.TypicalModules.CS2103;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import modulo.model.Modulo;
import modulo.model.Name;
import modulo.model.event.Event;
import modulo.model.event.EventType;
import modulo.model.event.Location;
import modulo.model.event.PartialEvent;
import modulo.testutil.module.TypicalModules;


/**
 * A utility class containing a list of {@code Event} objects to be used in tests.
 */
public class TypicalEvents {

    public static final Event TUTORIAL_1 = new EventBuilder().withName("Tutorial 1").withEventStart("2020-01-15 09:00")
            .withEventEnd("2020-01-15 10:00").withEventType("TUTORIAL").withLocation("COM1-B103")
            .withParentModule(CS2103).build();

    public static final Event LECTURE_1 = new EventBuilder().withName("Lecture 1").withEventStart("2019-01-17 14:00")
            .withEventEnd("2019-01-15 16:00").withEventType("LECTURE").withLocation("I3-AUD")
            .withParentModule(CS2103).build();

    public static final Event TUTORIAL_2 = new EventBuilder().withName("Tutorial 2").withEventStart("2019-01-22 09:00")
            .withEventEnd("2019-01-22 10:00").withEventType("TUTORIAL").withLocation("COM1-B103")
            .withParentModule(CS2103).build();

    public static final Event TUTORIAL_3 = new EventBuilder().withName("Tutorial 3").withEventStart("2019-01-22 09:00")
            .withEventEnd("2019-01-22 10:00").withEventType("TUTORIAL").withLocation("COM1-B103")
            .withParentModule(CS2103).build();

    public static final Event TUTORIAL_INVALID_DATE = new EventBuilder().withName("Tutorial 1")
            .withEventStart("2019-05-15 09:00")
            .withEventEnd("2019-05-15 10:00").withEventType("TUTORIAL").withLocation("COM1-B103")
            .withParentModule(CS2103).build();

    public static final Event TUTORIAL_INVALID_TIME = new EventBuilder().withName("Tutorial 1")
            .withEventStart("2019-03-15 11:00")
            .withEventEnd("2019-03-15 10:00").withEventType("TUTORIAL").withLocation("COM1-B103")
            .withParentModule(CS2103).build();

    /**
     * Partial Event for testing
     */
    public static final Event PARTIAL_EVENT_TUTORIAL_1_CS1231S = new PartialEvent(new Name("Tutorial 1"),
            EventType.TUTORIAL, TypicalModules.PARTIAL_MODULE_CS1231S, new Location("Arbitrary Location"));

    public static final Event PARTIAL_EVENT_TUTORIAL_1_CS2103 = new PartialEvent(new Name("Tutorial 1"),
            EventType.TUTORIAL, TypicalModules.PARTIAL_MODULE_CS2103, new Location("Arbitrary Location"));

    public static final Event PARTIAL_EVENT_TUTORIAL_10_CS2103 = new PartialEvent(new Name("Tutorial 10"),
            EventType.TUTORIAL, TypicalModules.PARTIAL_MODULE_CS2103, new Location("Arbitrary Location"));

    private TypicalEvents() {
    } // prevents instantiation

    /**
     * Returns an {@code InventorySystem} with all the typical events.
     */
    public static Modulo getModulo() {
        Modulo ab = new Modulo();
        for (Event event : getTypicalEvent()) {
            ab.addEvent(event);
        }
        return ab;
    }

    public static List<Event> getTypicalEvent() {
        return new ArrayList<>(Arrays.asList(TUTORIAL_1, LECTURE_1, TUTORIAL_2));
    }
}
