package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.entity.Event;
import seedu.address.model.entity.Module;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.*;

public class AddModuleCommand extends Command {
    public static final String COMMAND_WORD = "module";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a module to the calendar. "
            + "Parameters: "
            + PREFIX_MODULE + "MODULE "
            + "\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_MODULE + "CS2103 ";

    public static final String MESSAGE_SUCCESS = "New module added: %1$s";
    public static final String MESSAGE_DUPLICATE_MODULE = "This module already exists in the calendar";
    public static final String MESSAGE_MODULE_DOESNT_EXIST = "The specified module does not exist in the calendar";

    private final Module toAdd;

    public AddModuleCommand(Module module) {
        requireNonNull(module);
        toAdd = module;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // change it module entity
        if (model.hasModule(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_MODULE);
        }

        Module module = new Module(toAdd.getModuleCode());
        module.setEvents(null);
        model.addModule(toAdd);
        System.out.println(model.checkCurrentCalendar());
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddModuleCommand // instanceof handles nulls
                && toAdd.equals(((AddModuleCommand) other).toAdd));
    }
}
