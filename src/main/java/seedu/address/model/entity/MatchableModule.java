package seedu.address.model.entity;

import java.time.LocalDate;

/**
 * A helper class for event creation, where only the module code is required for
 * initialisation. The object returned will only be used for matching with actually
 * created modules subsequently.
 */
public class MatchableModule extends Module {
    public MatchableModule(String moduleCode) {
        super(moduleCode, LocalDate.now(), LocalDate.now());
    }
}
