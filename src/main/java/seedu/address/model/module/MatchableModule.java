package seedu.address.model.module;

import java.time.LocalDate;

import seedu.address.model.Name;

/**
 * A helper class for event creation, with only the {@code ModuleName} required for initialisation. The object returned
 * will only be used for matching with actually created modules subsequently.
 */
public class MatchableModule extends Module {
    public MatchableModule(ModuleCode moduleCode) {
        super(moduleCode, new Name("MatchableModule"), LocalDate.now(), LocalDate.now(), "Empty desc.");
    }
}
