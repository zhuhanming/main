package seedu.address.model.util;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Calendar;
import seedu.address.model.Name;
import seedu.address.model.ReadOnlyCalendar;
import seedu.address.model.event.EventType;
import seedu.address.model.event.PartialEvent;
import seedu.address.model.module.AcademicYear;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.PartialModule;

/**
 * Contains utility methods for populating {@code Calendar} with sample data.
 */
public class SampleDataUtil {
    public static Module[] getSampleModules() throws ParseException {
        Module cs2103 = new PartialModule(new ModuleCode("CS2103"), AcademicYear.now());
        Module ger1000 = new PartialModule(new ModuleCode("GER1000"), AcademicYear.now());

        cs2103.addEvent(new PartialEvent(new Name("Tutorial 8"), EventType.TUTORIAL, cs2103));
        cs2103.addEvent(new PartialEvent(new Name("Lecture"), EventType.LECTURE, cs2103));
        ger1000.addEvent(new PartialEvent(new Name("Tutorial 4"), EventType.TUTORIAL, ger1000));

        return new Module[]{cs2103, ger1000};
    }

    public static ReadOnlyCalendar getSampleCalendar() {
        Calendar sampleCalendar = new Calendar();
        try {
            for (Module sampleModule : getSampleModules()) {
                sampleCalendar.addModuleFromStorage(sampleModule);
            }
            return sampleCalendar;
        } catch (ParseException e) {
            return sampleCalendar;
        }
    }
}
