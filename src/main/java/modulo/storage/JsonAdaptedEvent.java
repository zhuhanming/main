package modulo.storage;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import modulo.commons.exceptions.IllegalValueException;
import modulo.model.Name;
import modulo.model.deadline.Deadline;
import modulo.model.event.Event;
import modulo.model.event.EventType;
import modulo.model.event.Location;
import modulo.model.event.PartialEvent;
import modulo.model.event.Slot;
import modulo.model.module.AcademicYear;
import modulo.model.module.Module;
import modulo.model.module.ModuleCode;
import modulo.model.module.PartialModule;
import modulo.model.module.exceptions.AcademicYearException;

/**
 * Jackson-friendly version of {@link Event}.
 */
class JsonAdaptedEvent {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Event's %s field is missing!";
    public static final String INVALID_START_DATE_TIME_FORMAT = "Invalid datetime format for the event start time!";
    public static final String INVALID_END_DATE_TIME_FORMAT = "Invalid datetime format for the event end time!";

    private final String name;
    private final String eventType;
    private final String eventStart;
    private final String eventEnd;
    private final String parentModuleCode;
    private final String academicYear;
    private final String slot;
    private final String location;
    private final List<JsonAdaptedDeadline> deadlines = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedEvent(@JsonProperty("name") String name, @JsonProperty("eventType") String eventType,
                            @JsonProperty("eventStart") String eventStart,
                            @JsonProperty("eventEnd") String eventEnd,
                            @JsonProperty("parentModuleCode") String parentModuleCode,
                            @JsonProperty("academicYear") String academicYear,
                            @JsonProperty("location") String location,
                            @JsonProperty("slot") String slot,
                            @JsonProperty("deadlines") List<JsonAdaptedDeadline> deadlines) {
        this.name = name;
        this.eventType = eventType;
        this.eventStart = eventStart;
        this.eventEnd = eventEnd;
        this.parentModuleCode = parentModuleCode;
        this.academicYear = academicYear;
        this.location = location;
        this.slot = slot;
        if (deadlines != null) {
            this.deadlines.addAll(deadlines);
        }
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedEvent(Event source) {
        name = source.getName().toString();
        eventType = source.getEventType().toString();
        //System.out.println("Event Type is " + eventType);
        eventStart = source.getEventStart().toString();
        eventEnd = source.getEventEnd().toString();
        parentModuleCode = source.getParentModule().getModuleCode().toString();
        academicYear = source.getParentModule().getAcademicYear().toString();
        location = source.getLocation().toString();
        slot = source.getSlot() == null ? "null" : source.getSlot().toString();
        deadlines.addAll(source.getDeadlines().stream()
                .map(JsonAdaptedDeadline::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Event toModelType() throws IllegalValueException {
        final List<Deadline> eventDeadlines = new ArrayList<>();
        for (JsonAdaptedDeadline deadline : deadlines) {
            eventDeadlines.add(deadline.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (eventType == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    EventType.class.getSimpleName()));
        }

        final EventType modelEventType = EventType.parseEventType(eventType);

        if (eventStart == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Event Start Date"));
        }
        final LocalDateTime modelEventStart;
        try {
            modelEventStart = LocalDateTime.parse(eventStart);
        } catch (DateTimeParseException e) {
            throw new IllegalValueException(INVALID_START_DATE_TIME_FORMAT);
        }

        if (eventEnd == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Event End Date"));
        }
        final LocalDateTime modelEventEnd;
        try {
            modelEventEnd = LocalDateTime.parse(eventEnd);
        } catch (DateTimeParseException e) {
            throw new IllegalValueException(INVALID_END_DATE_TIME_FORMAT);
        }

        if (parentModuleCode == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    ModuleCode.class.getSimpleName()));
        }
        if (!ModuleCode.isValidModuleCode(parentModuleCode)) {
            throw new IllegalValueException(ModuleCode.MESSAGE_CONSTRAINTS);
        }
        final ModuleCode modelModuleCode = new ModuleCode(parentModuleCode);

        if (location == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Location.class.getSimpleName()));
        }
        if (!Location.isValidLocation(location)) {
            throw new IllegalValueException(Location.MESSAGE_CONSTRAINTS);
        }
        final Location modelLocation = new Location(location);

        if (academicYear == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    AcademicYear.class.getSimpleName()));
        }
        final AcademicYear modelAcademicYear;

        try {
            modelAcademicYear = AcademicYear.fromString(academicYear);
        } catch (AcademicYearException e) {
            throw new IllegalValueException(e.getMessage());
        }

        if (slot == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Slot.class.getSimpleName()));
        }
        if (!Slot.isValidSlot(slot)) {
            throw new IllegalValueException(Slot.MESSAGE_CONSTRAINTS);
        }
        final Slot modelSlot = slot.equals("null") ? null : new Slot(slot);

        Module modelParentModule = new PartialModule(modelModuleCode, modelAcademicYear);

        return new PartialEvent(modelName, modelEventType, modelEventStart, modelEventEnd, modelParentModule,
                modelLocation, eventDeadlines, modelSlot);
    }

}

