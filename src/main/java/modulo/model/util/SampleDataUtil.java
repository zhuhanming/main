package modulo.model.util;

import modulo.logic.parser.exceptions.ParseException;
import modulo.model.Modulo;
import modulo.model.Name;
import modulo.model.ReadOnlyModulo;
import modulo.model.event.EventType;
import modulo.model.event.Location;
import modulo.model.event.PartialEvent;
import modulo.model.module.AcademicYear;
import modulo.model.module.Module;
import modulo.model.module.ModuleCode;
import modulo.model.module.PartialModule;

/**
 * Contains utility methods for populating {@code Calendar} with sample data.
 */
public class SampleDataUtil {
    public static Module[] getSampleModules() throws ParseException {
        Module cs2103 = new PartialModule(new ModuleCode("CS2103"), AcademicYear.now());
        Module ger1000 = new PartialModule(new ModuleCode("GER1000"), AcademicYear.now());

        cs2103.addEvent(new PartialEvent(new Name("Tutorial 8"), EventType.TUTORIAL, cs2103,
                new Location("COM1-B103")));
        cs2103.addEvent(new PartialEvent(new Name("Lecture"), EventType.LECTURE, cs2103, new Location("I3-AUD")));
        ger1000.addEvent(new PartialEvent(new Name("Tutorial 4"), EventType.TUTORIAL, ger1000,
                new Location("TP-SR7")));

        return new Module[]{cs2103, ger1000};
    }

    public static ReadOnlyModulo getSampleCalendar() {
        Modulo sampleModulo = new Modulo();
        try {
            for (Module sampleModule : getSampleModules()) {
                sampleModulo.addModuleFromStorage(sampleModule);
            }
            return sampleModulo;
        } catch (ParseException e) {
            return sampleModulo;
        }
    }
}
