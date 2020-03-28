package seedu.address.storage;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.Name;
import seedu.address.model.deadline.Deadline;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventType;
import seedu.address.model.event.Location;
import seedu.address.model.event.PartialEvent;
import seedu.address.model.module.AcademicYear;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.PartialModule;

/**
 * Jackson-friendly version of {@link Event}.
 */
class JsonAdaptedEvent {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Event's %s field is missing!";

    private final String name;
    private final String eventType;
    private final String eventStart;
    private final String eventEnd;
    private final String parentModuleCode;
    private final String academicYear;
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
                            @JsonProperty("deadlines") List<JsonAdaptedDeadline> deadlines) {
        this.name = name;
        this.eventType = eventType;
        this.eventStart = eventStart;
        this.eventEnd = eventEnd;
        this.parentModuleCode = parentModuleCode;
        this.academicYear = academicYear;
        this.location = location;
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
        System.out.println("Event Type is " + eventType);
        eventStart = source.getEventStart().toString();
        eventEnd = source.getEventEnd().toString();
        parentModuleCode = source.getParentModule().getModuleCode().toString();
        academicYear = source.getParentModule().getAcademicYear().toString();
        location = source.getLocation().toString();
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

        EventType parsedEventType;
//        System.out.println("evevnt Type is " + EventType.valueOf(eventType));
        try {
            parsedEventType = EventType.parseEventType(eventType);
        } catch (IllegalArgumentException e) {
            throw new IllegalValueException(EventType.getMessageConstraints());
        }
        final EventType modelEventType = parsedEventType;

        if (eventStart == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Event Start Date"));
        }
        final LocalDateTime modelEventStart = LocalDateTime.parse(eventStart);

        if (eventEnd == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Event End Date"));
        }
        final LocalDateTime modelEventEnd = LocalDateTime.parse(eventEnd);


        if (parentModuleCode == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Module.class.getSimpleName()));
        }
        final ModuleCode modelModuleCode = new ModuleCode(parentModuleCode);

        if (location == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Location.class.getSimpleName()));
        }
        final Location modelLocation = new Location(location);

        if (academicYear == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    AcademicYear.class.getSimpleName()));
        }
        final AcademicYear modelAcademicYear = AcademicYear.fromString(academicYear);

        Module modelParentModule = new PartialModule(modelModuleCode, modelAcademicYear);

        return new PartialEvent(modelName, modelEventType, modelEventStart, modelEventEnd, modelParentModule,
                modelLocation, eventDeadlines);
    }

}

