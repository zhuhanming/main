package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_DATETIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REPEAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_DATETIME;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAmount;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;
import seedu.address.model.module.Module;


/**
 * Adds an event to the address book.
 */
public class AddEventCommand extends Command {
    public static final String COMMAND_WORD = "event";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a event to the calendar. "
            + "Parameters: "
            + PREFIX_MODULE + "MODULE CODE "
            + PREFIX_NAME + "EVENT_NAME "
            + PREFIX_START_DATETIME + "EVENT_START_DATETIME "
            + PREFIX_END_DATETIME + "EVENT_END_DATETIME "
            + "[" + PREFIX_REPEAT + "YES/NO] \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_MODULE + "CS2103 "
            + PREFIX_NAME + "Tutorial "
            + PREFIX_START_DATETIME + "2020-01-01 15:00 "
            + PREFIX_END_DATETIME + "2020-01-01 16:00 "
            + PREFIX_REPEAT + "YES ";

    public static final String MESSAGE_SUCCESS = "New event added: %1$s";
    public static final String MESSAGE_DUPLICATE_EVENT = "This event already exists for this module!";
    public static final String MESSAGE_INVALID_DATE_RANGE = "The specified date range is invalid!";
    public static final String MESSAGE_MODULE_DOESNT_EXIST = "The specified module does not exist in the calendar";

    private final Event toAdd;
    private final boolean isRepeated;
    private LocalDate endRepeatDate;
    private final TemporalAmount frequency;


    public AddEventCommand(Event event, boolean isRepeated, LocalDate endRepeatDate, TemporalAmount frequency) {
        requireAllNonNull(event, frequency);
        this.toAdd = event;
        this.endRepeatDate = endRepeatDate;
        this.isRepeated = isRepeated;
        this.frequency = frequency;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Module partialModule = toAdd.getParentModule();

        if (!model.hasModule(partialModule.getModuleCode(), partialModule.getAcademicYear())) {
            throw new CommandException(MESSAGE_MODULE_DOESNT_EXIST);
        }

        Module actualModule = model.getModule(partialModule.getModuleCode(), partialModule.getAcademicYear());
        Event actualEvent = new Event(toAdd.getName(), toAdd.getEventType(), toAdd.getEventStart(),
                toAdd.getEventEnd(), actualModule);

        if (model.hasEvent(actualEvent)) {
            throw new CommandException(MESSAGE_DUPLICATE_EVENT);
        }

        if (actualEvent.getEventStart().toLocalDate().isBefore(actualModule.getAcademicYear().getStartDate())
                || actualEvent.getEventStart().toLocalDate().isAfter(actualModule.getAcademicYear().getEndDate())
                || actualEvent.getEventEnd().toLocalDate().isBefore(actualModule.getAcademicYear().getStartDate())
                || actualEvent.getEventEnd().toLocalDate().isAfter(actualModule.getAcademicYear().getEndDate())) {
            System.out.println(actualModule.getAcademicYear().getStartDate());
            System.out.println(actualModule.getAcademicYear().getEndDate());
            throw new CommandException(MESSAGE_INVALID_DATE_RANGE);
        }

        if (model.hasEvent(actualEvent) && !isRepeated) {
            throw new CommandException(MESSAGE_DUPLICATE_EVENT);
        }

        if (endRepeatDate == null || endRepeatDate.isAfter(actualModule.getAcademicYear().getEndDate())) {
            endRepeatDate = actualModule.getAcademicYear().getEndDate();
        }
        if (isRepeated) {
            for (LocalDateTime start = actualEvent.getEventStart(), end = actualEvent.getEventEnd();
                 !start.toLocalDate().isAfter(endRepeatDate) && !end.toLocalDate().isAfter(endRepeatDate);
                 start = start.plus(frequency), end = end.plus(frequency)) {
                Event nextEvent = new Event(actualEvent.getName(), actualEvent.getEventType(),
                        start, end, actualModule);
                if (!model.hasEvent(nextEvent)) {
                    actualModule.addEvent(nextEvent);
                    model.addEvent(nextEvent);
                }
            }
        } else {
            actualModule.addEvent(actualEvent);
            model.addEvent(actualEvent);
        }
        System.out.println(model.checkCurrentCalendar());
        return new CommandResult(String.format(MESSAGE_SUCCESS, actualEvent));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddEventCommand // instanceof handles nulls
                && toAdd.equals(((AddEventCommand) other).toAdd));
    }

}
