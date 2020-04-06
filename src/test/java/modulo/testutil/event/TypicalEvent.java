package modulo.testutil.event;

import static modulo.testutil.module.TypicalModule.CS2103;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import modulo.model.Modulo;
import modulo.model.event.Event;


/**
 * A utility class containing a list of {@code Module} objects to be used in tests..
 */
public class TypicalEvent {

    public static final Event TUTORIAL_1 = new EventBuilder().withName("Tutorial 1").withEventStart("2020-01-15 09:00")
            .withEventEnd("2020-01-15 10:00").withEventType("TUTORIAL").withLocation("COM1-B103")
            .withParentModlule(CS2103).build();

    public static final Event LECTURE_1 = new EventBuilder().withName("Lecture 1").withEventStart("2019-01-17 14:00")
            .withEventEnd("2019-01-15 16:00").withEventType("LECTURE").withLocation("I3-AUD")
            .withParentModlule(CS2103).build();

    public static final Event TUTORIAL_2 = new EventBuilder().withName("Tutorial 2").withEventStart("2019-01-22 09:00")
            .withEventEnd("2019-01-22 10:00").withEventType("TUTORIAL").withLocation("COM1-B103")
            .withParentModlule(CS2103).build();

    private TypicalEvent() {
    } // prevents instantiation

    /**
     * Returns an {@code InventorySystem} with all the typical persons.
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
