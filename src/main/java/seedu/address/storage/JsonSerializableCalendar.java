package seedu.address.storage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.Calendar;
import seedu.address.model.ReadOnlyCalendar;
import seedu.address.model.module.Module;

/**
 * An Immutable AddressBook that is serializable to JSON format.
 */
@JsonRootName(value = "calendar")
class JsonSerializableCalendar {

    public static final String MESSAGE_DUPLICATE_MODULE = "Module list contains duplicate module(s).";
    public static final String MESSAGE_DUPLICATE_EVENT = "Event list contains duplicate event(s).";

    private final List<JsonAdaptedModule> modules = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableCalendar} with the given persons.
     */
    @JsonCreator
    public JsonSerializableCalendar(@JsonProperty("modules") List<JsonAdaptedModule> modules) {
        this.modules.addAll(modules);
    }

    /**
     * Converts a given {@code ReadOnlyCalendar} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableCalendar}.
     */
    public JsonSerializableCalendar(ReadOnlyCalendar source) {
        modules.addAll(source.getModuleList()
                .stream()
                .map(JsonAdaptedModule::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public Calendar toModelType() throws IllegalValueException, IOException, DataConversionException {
        Calendar calendar = new Calendar();
        for (JsonAdaptedModule jsonAdaptedModule : modules) {
            Module module = jsonAdaptedModule.toModelType();
            if (calendar.hasModule(module.getModuleCode(), module.getAcademicYear())) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_MODULE);
            }
            calendar.addModuleFromStorage(module);
        }
        return calendar;
    }

}
