package modulo.logic.commands;

import static java.util.Objects.requireNonNull;
import static modulo.commons.core.Messages.MESSAGE_CANNOT_ADD_EVENT_TO_EVENT;
import static modulo.commons.core.Messages.MESSAGE_DUPLICATE_EVENT;
import static modulo.commons.core.Messages.MESSAGE_ENDDATE_MUST_AFTER_STARTDATE;
import static modulo.commons.core.Messages.MESSAGE_EVENT_ADDED;
import static modulo.commons.core.Messages.MESSAGE_INVALID_DATE_RANGE;
import static modulo.commons.core.Messages.MESSAGE_MODULE_DOES_NOT_EXIST;
import static modulo.commons.util.CollectionUtil.requireAllNonNull;
import static modulo.logic.parser.CliSyntax.PREFIX_END_DATETIME;
import static modulo.logic.parser.CliSyntax.PREFIX_FREQUENCY;
import static modulo.logic.parser.CliSyntax.PREFIX_MODULE;
import static modulo.logic.parser.CliSyntax.PREFIX_NAME;
import static modulo.logic.parser.CliSyntax.PREFIX_REPEAT;
import static modulo.logic.parser.CliSyntax.PREFIX_START_DATETIME;
import static modulo.logic.parser.CliSyntax.PREFIX_STOP_REPEAT;
import static modulo.logic.parser.CliSyntax.PREFIX_VENUE;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAmount;

import modulo.logic.commands.exceptions.CommandException;
import modulo.model.Model;
import modulo.model.Name;
import modulo.model.displayable.Displayable;
import modulo.model.event.Event;
import modulo.model.event.EventType;
import modulo.model.event.Location;
import modulo.model.module.AcademicYear;
import modulo.model.module.Module;


/**
 * Adds an {@code Event} to Modulo.
 */
public class AddEventCommand extends Command {
    public static final String COMMAND_WORD = "event";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a event to Modulo. "
            + "Parameters:\n"
            + "1. (if viewing module) "
            + PREFIX_NAME + "EVENT_NAME "
            + PREFIX_START_DATETIME + "START DATETIME "
            + PREFIX_END_DATETIME + "END DATETIME "
            + PREFIX_VENUE + "VENUE "
            + "[" + PREFIX_REPEAT + "TO REPEAT] "
            + "[" + PREFIX_FREQUENCY + "FREQ (WEEKS)] "
            + "[" + PREFIX_STOP_REPEAT + "EVENT_REPEAT_END]\n"
            + "2. (otherwise) "
            + PREFIX_MODULE + "MODULE CODE "
            + PREFIX_NAME + "EVENT_NAME "
            + PREFIX_START_DATETIME + "EVENT_START_DATETIME "
            + PREFIX_END_DATETIME + "EVENT_END_DATETIME "
            + PREFIX_VENUE + "VENUE "
            + "[" + PREFIX_REPEAT + "YES/NO] "
            + "[" + PREFIX_STOP_REPEAT + "EVENT_REPEAT_END] \n\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_MODULE + "CS2103 "
            + PREFIX_NAME + "Tutorial "
            + PREFIX_START_DATETIME + "2020-01-30 09:00 "
            + PREFIX_END_DATETIME + "2020-01-30 10:00 "
            + PREFIX_VENUE + "COM1-B103 "
            + PREFIX_REPEAT + "YES "
            + PREFIX_FREQUENCY + "2 "
            + PREFIX_STOP_REPEAT + "2020-05-08"
            + "\nRefer to the UserGuide for more details!";

    private final Event toAdd;
    private final Name name;
    private final LocalDateTime startDateTime;
    private final LocalDateTime endDateTime;
    private final boolean isRepeated;
    private LocalDate endRepeatDate;
    private final TemporalAmount frequency;
    private final Location location;
    private final EventType eventType;
    private final Character suffix;

    /**
     * Creates an AddEventCommand to add an Event to a specified Module. The module data is stored within the event
     * itself.
     *
     * @param event         Helper event that contains the information.
     * @param isRepeated    Whether the event should repeat.
     * @param endRepeatDate End {@code LocalDateTime} of the event.
     * @param frequency     Frequency of repeat.
     */
    public AddEventCommand(Event event, boolean isRepeated, LocalDate endRepeatDate, TemporalAmount frequency,
                           Character suffix) {
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
        this.suffix = suffix;
    }

    /**
     * Creates an AddEventCommand to add an Event to the focused module.
     *
     * @param name          Name of Event.
     * @param startDateTime Start {@code LocalDateTime} of the event.
     * @param endDateTime   End {@code LocalDateTime} of the event.
     * @param location      Location of the event.
     * @param isRepeated    Whether the event repeats.
     * @param endRepeatDate When the event stops repeating.
     * @param eventType     Type of the event.
     * @param frequency     Frequency of the event.
     */
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
        this.suffix = null;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Module actualModule;
        Event actualEvent;
        // If there's no event specified, we create one.
        if (toAdd == null) {
            Displayable displayable = model.getFocusedDisplayable();
            if (displayable instanceof Event) {
                throw new CommandException(MESSAGE_CANNOT_ADD_EVENT_TO_EVENT);
            }
            if (displayable == null) {
                throw new CommandException(MESSAGE_MODULE_DOES_NOT_EXIST);
            }
            if (endDateTime.isBefore(startDateTime)) {
                throw new CommandException(String.format(MESSAGE_ENDDATE_MUST_AFTER_STARTDATE,
                        startDateTime.toString().replace('T', ' '), endDateTime).replace('T', ' '));
            }

            actualModule = (Module) displayable;
            actualEvent = new Event(name, eventType, startDateTime, endDateTime, actualModule, location);
        } else {
            // If an event is specified, we recreate the event with the correct module reference.
            Module partialModule = toAdd.getParentModule();
            actualModule = model.getModule(partialModule.getModuleCode(), partialModule.getAcademicYear())
                    .orElseThrow(() -> new CommandException(MESSAGE_MODULE_DOES_NOT_EXIST));
            if (toAdd.getEventEnd().isBefore(toAdd.getEventStart())) {
                throw new CommandException(String.format(MESSAGE_ENDDATE_MUST_AFTER_STARTDATE,
                        toAdd.getEventStart().toString().replace('T', ' '),
                        toAdd.getEventEnd().toString().replace('T', ' ')));
            }
            actualEvent = new Event(toAdd.getName(), toAdd.getEventType(), toAdd.getEventStart(),
                    toAdd.getEventEnd(), actualModule, toAdd.getLocation(), toAdd.getSlot());

        }

        if (model.hasEvent(actualEvent)) {
            throw new CommandException(MESSAGE_DUPLICATE_EVENT);
        }

        if (hasInvalidDateRange(actualEvent, actualModule)) {
            throw new CommandException(MESSAGE_INVALID_DATE_RANGE);
        }

        // We allow the creation of "existing" events if it's repeated.
        if (model.hasEvent(actualEvent) && !isRepeated) {
            throw new CommandException(MESSAGE_DUPLICATE_EVENT);
        }

        if (endRepeatDate == null || endRepeatDate.isAfter(actualModule.getAcademicYear().getEndDate())) {
            endRepeatDate = actualModule.getAcademicYear().getEndDate();
        }

        if (isRepeated) {
            int eventNumber = 1;
            AcademicYear academicYear = actualEvent.getParentModule().getAcademicYear();
            for (LocalDateTime start = actualEvent.getEventStart(), end = actualEvent.getEventEnd();
                 !start.toLocalDate().isAfter(endRepeatDate) && !end.toLocalDate().isAfter(endRepeatDate);
                 start = start.plus(frequency), end = end.plus(frequency)) {
                if (actualEvent.getSlot() != null
                        && start.toLocalDate().isAfter(academicYear.getRecessWeekStartDate().minusDays(1))
                        && start.toLocalDate().isBefore(academicYear.getRecessWeekStartDate().plusDays(7))) {
                    start = start.plusWeeks(1);
                    endRepeatDate = endRepeatDate.plusWeeks(1);
                }
                Event nextEvent = new Event(new Name(actualEvent.getName().toString() + " " + eventNumber
                        + (suffix == null ? "" : suffix.toString())), actualEvent.getEventType(), start, end,
                        actualModule, actualEvent.getLocation(), actualEvent.getSlot());
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
        return new CommandResult(String.format(MESSAGE_EVENT_ADDED, actualEvent), false, false, true, true, null, null);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddEventCommand // instanceof handles nulls
                && toAdd.equals(((AddEventCommand) other).toAdd)
                && name.equals(((AddEventCommand) other).name));
    }

    /**
     * Checks if an event has a valid date range specified.
     *
     * @param event  Event to check.
     * @param module Module of the event.
     * @return Boolean indicating whether the date range is valid.
     */
    private boolean hasInvalidDateRange(Event event, Module module) {
        return event.getEventStart().toLocalDate().isBefore(module.getAcademicYear().getStartDate())
                || event.getEventStart().toLocalDate().isAfter(module.getAcademicYear().getEndDate())
                || event.getEventEnd().toLocalDate().isBefore(module.getAcademicYear().getStartDate())
                || event.getEventEnd().toLocalDate().isAfter(module.getAcademicYear().getEndDate());
    }
}
