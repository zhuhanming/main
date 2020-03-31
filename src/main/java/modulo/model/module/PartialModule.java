package modulo.model.module;

import java.util.List;

import modulo.logic.parser.exceptions.ParseException;
import modulo.model.event.Event;
import modulo.model.Name;

/**
 * A helper class for event creation, with only the {@code ModuleName} required for initialisation. The object returned
 * will only be used for matching with actually created modules subsequently.
 */
public class PartialModule extends Module {
    public PartialModule(ModuleCode moduleCode) throws ParseException {
        super(moduleCode, new Name("MatchableModule"), AcademicYear.now(), "Empty desc.");
    }

    public PartialModule(ModuleCode moduleCode, AcademicYear academicYear) {
        super(moduleCode, new Name("MatchableModule"), academicYear, "Empty desc.");
    }

    public PartialModule(ModuleCode moduleCode, AcademicYear academicYear, List<Event> events) {
        super(moduleCode, new Name("MatchableModule"), academicYear, "Empty desc.", events);
    }
}
