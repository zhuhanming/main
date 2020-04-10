package modulo.storage;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import modulo.commons.exceptions.IllegalValueException;
import modulo.model.Name;
import modulo.model.deadline.Deadline;

/**
 * Jackson-friendly version of {@link Deadline}.
 */
public class JsonAdaptedDeadline {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Deadline's %s field is missing!";
    public static final String INVALID_DUE_TIME_FORMAT = "Invalid datetime format for the deadline due time!";

    private final String name;
    private final String dueTime;
    private final String isCompleted;

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedDeadline(@JsonProperty("name") String name,
                               @JsonProperty("dueTime") String dueTime,
                               @JsonProperty("isCompleted") String isCompleted) {
        this.name = name;
        this.dueTime = dueTime;
        this.isCompleted = isCompleted;
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedDeadline(Deadline source) {
        name = source.getName().toString();
        dueTime = source.getDueTime().toString();
        isCompleted = String.valueOf(source.isCompleted());
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Deadline toModelType() throws IllegalValueException {
        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (dueTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Deadline Due Time"));
        }
        final LocalDateTime modelDueTime;
        try {
            modelDueTime = LocalDateTime.parse(dueTime);
        } catch (DateTimeParseException e) {
            throw new IllegalValueException(INVALID_DUE_TIME_FORMAT);
        }

        if (isCompleted == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Deadline Completion Status"));
        }
        final boolean modelIsCompleted = Boolean.parseBoolean(isCompleted);

        return new Deadline(modelName, modelDueTime, modelIsCompleted);
    }
}
