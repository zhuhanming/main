package modulo.storage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import modulo.model.event.Event;
import modulo.model.module.AcademicYear;
import modulo.model.module.Module;
import modulo.commons.exceptions.DataConversionException;
import modulo.commons.exceptions.IllegalValueException;
import modulo.model.module.ModuleCode;
import modulo.model.module.PartialModule;

/**
 * Jackson-friendly version of {@link Module}.
 */
class JsonAdaptedModule {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Module's %s field is missing!";

    private final String moduleCode;
    private final String academicYear;
    private final List<JsonAdaptedEvent> events = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedModule(@JsonProperty("moduleCode") String moduleCode,
                             @JsonProperty("academicYear") String academicYear,
                             @JsonProperty("events") List<JsonAdaptedEvent> events) {
        this.moduleCode = moduleCode;
        this.academicYear = academicYear;
        if (events != null) {
            this.events.addAll(events);
        }
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedModule(Module source) {
        moduleCode = source.getModuleCode().toString();
        academicYear = source.getAcademicYear().toString();
        events.addAll(source.getEvents().stream()
                .map(JsonAdaptedEvent::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Module toModelType() throws IllegalValueException, IOException, DataConversionException {
        List<Event> modelEvents = new ArrayList<>();
        for (JsonAdaptedEvent event : events) {
            modelEvents.add(event.toModelType());
        }

        if (moduleCode == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    ModuleCode.class.getSimpleName()));
        }
        if (!ModuleCode.isValidModuleCode(moduleCode)) {
            throw new IllegalValueException(ModuleCode.MESSAGE_CONSTRAINTS);
        }
        final ModuleCode modelModuleCode = new ModuleCode(moduleCode);

        if (academicYear == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    AcademicYear.class.getSimpleName()));
        }

        final AcademicYear modelAcademicYear = AcademicYear.fromString(academicYear);

        Module result = new PartialModule(modelModuleCode, modelAcademicYear, modelEvents);

        return result;
    }

}

