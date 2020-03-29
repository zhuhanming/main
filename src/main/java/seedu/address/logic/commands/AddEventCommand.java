package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_DATETIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REPEAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_DATETIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STOP_REPEAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENUE;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAmount;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Displayable;
import seedu.address.model.Model;
import seedu.address.model.Name;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventType;
import seedu.address.model.event.Location;
import seedu.address.model.module.Module;


/**
 * Adds an event to the address book.
 */
public class AddEventCommand extends Command {
    public static final String COMMAND_WORD = "event";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a event to the calendar. "
            + "Parameters: "
            + "(if viewing Module) "
            + PREFIX_NAME + "EVENT_NAME "
            + PREFIX_START_DATETIME + "EVENT_START_DATETIME "
            + PREFIX_END_DATETIME + "EVENT_END_DATETIME "
            + PREFIX_VENUE + "VENUE "
            + "[" + PREFIX_REPEAT + "YES/NO] "
            + "[" + PREFIX_STOP_REPEAT + "EVENT_REPEAT_END] "
            + "Parameters: (else) "
            + PREFIX_MODULE + "MODULE CODE "
            + PREFIX_NAME + "EVENT_NAME "
            + PREFIX_START_DATETIME + "EVENT_START_DATETIME "
            + PREFIX_END_DATETIME + "EVENT_END_DATETIME "
            + PREFIX_VENUE + "VENUE "
            + "[" + PREFIX_REPEAT + "YES/NO] "
            + "[" + PREFIX_STOP_REPEAT + "EVENT_REPEAT_END] \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_MODULE + "CS2103 "
            + PREFIX_NAME + "Tutorial "
            + PREFIX_START_DATETIME + "2020-01-30 09:00 "
            + PREFIX_END_DATETIME + "2020-01-30 10:00 "
            + PREFIX_VENUE + "COM1-B103 "
            + PREFIX_REPEAT + "YES "
            + PREFIX_STOP_REPEAT + "2020-05-08";

    public static final String MESSAGE_SUCCESS = "New event added: %1$s";
    public static final String MESSAGE_DUPLICATE_EVENT = "This event already exists for this module!";
    public static final String MESSAGE_INVALID_DATE_RANGE = "The specified date range is invalid!";
    public static final String MESSAGE_MODULE_DOESNT_EXIST = "The specified module does not exist in the calendar!";
    public static final String MESSAGE_CANNOT_ADD_TO_EVENT = "You cannot add events to events!";


    private final Event toAdd;
    private final Name name;
    private final LocalDateTime startDateTime;
    private final LocalDateTime endDateTime;
    private final boolean isRepeated;
    private LocalDate endRepeatDate;
    private final TemporalAmount frequency;
    private final Location location;
    private final EventType eventType;


    public AddEventCommand(Event event, boolean isRepeated, LocalDate endRepeatDate, TemporalAmount frequency) {
        requireAllNonNull(event, frequency);
        this.toAdd = event;
        this.endRepeatDate = endRepeatDate;
        this.isRepeated = isRepeated;
        this.frequency = frequency;
        this.name = null;
        this.startDateTime = null;
        this.endDateTime = null;
        this.location = null;
        this.eventType = null;
    }

    public AddEventCommand(Name name, LocalDateTime startDateTime, LocalDateTime endDateTime, Location location,
                           boolean isRepeated, LocalDate endRepeatDate, EventType eventType, TemporalAmount frequency) {
        this.toAdd = null;
        this.name = name;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.location = location;
        this.isRepeated = isRepeated;
        this.endRepeatDate = endRepeatDate;
        this.eventType = eventType;
        this.frequency = frequency;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Module actualModule;
        Event actualEvent;
        if (toAdd == null) {
            Displayable displayable = model.getFocusedDisplayable();
            if (displayable instanceof Event) {
                throw new CommandException(MESSAGE_CANNOT_ADD_TO_EVENT);
            }
            if (displayable == null) {
                throw new CommandException(MESSAGE_MODULE_DOESNT_EXIST);
            }
            actualModule = (Module) displayable;
            actualEvent = new Event(name, eventType, startDateTime, endDateTime, actualModule, location);
        } else {
            Module partialModule = toAdd.getParentModule();
            actualModule = model.getModule(partialModule.getModuleCode(), partialModule.getAcademicYear())
                    .orElseThrow(() -> new CommandException(MESSAGE_MODULE_DOESNT_EXIST));

            actualEvent = new Event(toAdd.getName(), toAdd.getEventType(), toAdd.getEventStart(),
                    toAdd.getEventEnd(), actualModule, toAdd.getLocation());
        }

        if (model.hasEvent(actualEvent)) {
            throw new CommandException(MESSAGE_DUPLICATE_EVENT);
        }

        if (actualEvent.getEventStart().toLocalDate().isBefore(actualModule.getAcademicYear().getStartDate())
                || actualEvent.getEventStart().toLocalDate().isAfter(actualModule.getAcademicYear().getEndDate())
                || actualEvent.getEventEnd().toLocalDate().isBefore(actualModule.getAcademicYear().getStartDate())
                || actualEvent.getEventEnd().toLocalDate().isAfter(actualModule.getAcademicYear().getEndDate())) {
            throw new CommandException(MESSAGE_INVALID_DATE_RANGE);
        }

        if (model.hasEvent(actualEvent) && !isRepeated) {
            throw new CommandException(MESSAGE_DUPLICATE_EVENT);
        }

        if (endRepeatDate == null || endRepeatDate.isAfter(actualModule.getAcademicYear().getEndDate())) {
            endRepeatDate = actualModule.getAcademicYear().getEndDate();
        }
        if (isRepeated) {
            int eventNumber = 1;
            for (LocalDateTime start = actualEvent.getEventStart(), end = actualEvent.getEventEnd();
                 !start.toLocalDate().isAfter(endRepeatDate) && !end.toLocalDate().isAfter(endRepeatDate);
                 start = start.plus(frequency), end = end.plus(frequency)) {
                Event nextEvent = new Event(new Name(actualEvent.getName().toString() + " " + eventNumber),
                        actualEvent.getEventType(), start, end, actualModule, actualEvent.getLocation());
                if (!model.hasEvent(nextEvent)) {
                    actualModule.addEvent(nextEvent);
                    model.addEvent(nextEvent);
                }
                eventNumber += 1;
            }
        } else {
            actualModule.addEvent(actualEvent);
            model.addEvent(actualEvent);
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, actualEvent), false, false, true, true, null);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddEventCommand // instanceof handles nulls
                && toAdd.equals(((AddEventCommand) other).toAdd));
    }

}
