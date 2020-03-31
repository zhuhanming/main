package modulo.logic.commands;

import static java.util.Objects.requireNonNull;
import static modulo.commons.util.CollectionUtil.requireAllNonNull;
import static modulo.logic.parser.CliSyntax.PREFIX_ACADEMIC_YEAR;
import static modulo.logic.parser.CliSyntax.PREFIX_MODULE;
import static modulo.logic.parser.CliSyntax.PREFIX_SEMESTER;

import java.util.List;

import modulo.logic.commands.exceptions.CommandException;
import modulo.model.Model;
import modulo.model.event.EventType;
import modulo.model.module.AcademicYear;
import modulo.model.module.Module;
import modulo.model.module.ModuleCode;
import modulo.model.module.ModuleLibrary;

/**
 * Adds a module to Modulo.
 */
public class AddModuleCommand extends Command {
    public static final String COMMAND_WORD = "module";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a module to Modulo. "
            + "Parameters: "
            + PREFIX_MODULE + "MODULE "
            + "[" + PREFIX_ACADEMIC_YEAR + "ACADEMIC YEAR] "
            + "[" + PREFIX_SEMESTER + "SEMESTER] "
            + "\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_MODULE + "CS2103 "
            + PREFIX_ACADEMIC_YEAR + "2019/2020 "
            + PREFIX_SEMESTER + "2 ";

    public static final String MESSAGE_SUCCESS = "New module added: %1$s";
    public static final String MESSAGE_DUPLICATE_MODULE = "This module already exists in Modulo";

    private final ModuleCode moduleCode;
    private final AcademicYear academicYear;

    /**
     * Creates a AddModuleCommand that adds a module with the given module code and academic year to Modulo.
     *
     * @param moduleCode   Module code of the module.
     * @param academicYear Academic year of the module.
     */
    public AddModuleCommand(ModuleCode moduleCode, AcademicYear academicYear) {
        requireAllNonNull(moduleCode, academicYear);
        this.moduleCode = moduleCode;
        this.academicYear = academicYear;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasModule(moduleCode, academicYear)) {
            throw new CommandException(MESSAGE_DUPLICATE_MODULE);
        }

        model.addModule(moduleCode, academicYear);
        Module addedModule = model.getModule(moduleCode, academicYear).get();
        System.out.println(model.checkCurrentCalendar());
        List<EventType> eventTypeList = ModuleLibrary.getEventTypesOfModule(addedModule);
        String feedbackToUser = String.format(MESSAGE_SUCCESS, addedModule);
        if (eventTypeList.size() > 0) {
            EventType firstEventType = eventTypeList.get(0);
            feedbackToUser += "\nEnter slot for " + addedModule.getModuleCode().toString()
                    + " " + firstEventType.toString() + ".";
        }
        return new AddModuleCommandResult(feedbackToUser, addedModule, eventTypeList);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddModuleCommand // instanceof handles nulls
                && moduleCode.equals(((AddModuleCommand) other).moduleCode)
                && academicYear.toString().equals(((AddModuleCommand) other).academicYear.toString()));
    }
}
