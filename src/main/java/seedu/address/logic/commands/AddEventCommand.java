package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.entity.Deadline;
import seedu.address.model.entity.Event;
import seedu.address.model.entity.Module;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.*;

public class AddEventCommand extends Command {
    public static final String COMMAND_WORD = "event";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a event to the calendar. "
            + "Parameters: "
            + PREFIX_MODULE + "MODULE "
            + PREFIX_NAME + "EVENT_NAME "
            + PREFIX_START_DATETIME + "EVENT_START_DATETIME "
            + PREFIX_END_DATETIME + "EVENT_END_DATETIME "
            + PREFIX_REPEAT + "YES/NO \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_MODULE + "CS2103 "
            + PREFIX_NAME + "Tutorial  "
            + PREFIX_START_DATETIME + "2020-01-01 15:00"
            + PREFIX_END_DATETIME + "2020-01-01 16:00"
            + PREFIX_REPEAT + "YES ";

    public static final String MESSAGE_SUCCESS = "New event added: %1$s";
    public static final String MESSAGE_DUPLICATE_EVENT = "This event in this module is already exists in the calendar";
    public static final String MESSAGE_INVALID_DATE_RANGE = "The specified date range is invalid!";
    public static final String MESSAGE_MODULE_DOESNT_EXIST = "The specified module does not exist in the calendar";

    private final Event toAdd;
    private final Module parentModule;
    private final Deadline deadline;
    private final boolean isRepeated;
    private LocalDate endRepeatDate;


    public AddEventCommand(Event event, boolean isRepeated, LocalDate endRepeatDate) {
        requireNonNull(event);
        toAdd = event;
        parentModule = null;
        deadline = null;
        endRepeatDate = endRepeatDate;
        this.isRepeated = isRepeated;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        System.out.println("module +++++    "+ toAdd.getParentModule().getModuleCode());
        Module module = model.findModule(toAdd.getParentModule());
        System.out.println("module retrieve "+module);
        toAdd.setParentModule(module);

        if (module == null) {
            throw new CommandException(MESSAGE_MODULE_DOESNT_EXIST);
        }

        if (toAdd.getEventStart().toLocalDate().isBefore(module.getStartDate()) || toAdd.getEventStart().toLocalDate().isAfter(module.getEndDate())
        || toAdd.getEventEnd().toLocalDate().isBefore(module.getStartDate()) || toAdd.getEventEnd().toLocalDate().isAfter(module.getEndDate())) {
            throw new CommandException(MESSAGE_INVALID_DATE_RANGE);
        }

        if (model.hasCalendarItem(toAdd) && !isRepeated) {
            throw new CommandException(MESSAGE_DUPLICATE_EVENT);
        }

        if (endRepeatDate == null || endRepeatDate.isAfter(module.getEndDate())) {
            endRepeatDate = module.getEndDate();
        }
        if (isRepeated) {
            for (LocalDateTime start = toAdd.getEventStart(), end = toAdd.getEventEnd(); !start.toLocalDate().isAfter(endRepeatDate) && !end.toLocalDate().isAfter(endRepeatDate); start = start.plusDays(7), end = end.plusDays(7)) {
                Event nextEvent = new Event(toAdd.getEventName(), toAdd.getEventType(), start, end);
                nextEvent.setParentModule(module);
                if (!model.hasCalendarItem(nextEvent)) {
                    module.addEvents(nextEvent);
                    model.addCalendarItem(nextEvent);
                }

            }

        }
        System.out.println(model.checkCurrentCalendar());
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddEventCommand // instanceof handles nulls
                && toAdd.equals(((AddEventCommand) other).toAdd));
    }

}
