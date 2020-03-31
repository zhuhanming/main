package modulo.storage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import modulo.commons.exceptions.DataConversionException;
import modulo.commons.exceptions.IllegalValueException;
import modulo.model.Modulo;
import modulo.model.ReadOnlyModulo;
import modulo.model.module.Module;

/**
 * An immutable Modulo that is serializable to JSON format.
 */
@JsonRootName(value = "modulo")
class JsonSerializableModulo {

    public static final String MESSAGE_DUPLICATE_MODULE = "Module list contains duplicate module(s).";
    public static final String MESSAGE_DUPLICATE_EVENT = "Event list contains duplicate event(s).";

    private final List<JsonAdaptedModule> modules = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableModulo} with the given persons.
     */
    @JsonCreator
    public JsonSerializableModulo(@JsonProperty("modules") List<JsonAdaptedModule> modules) {
        this.modules.addAll(modules);
    }

    /**
     * Converts a given {@code ReadOnlyModulo} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableModulo}.
     */
    public JsonSerializableModulo(ReadOnlyModulo source) {
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
    public Modulo toModelType() throws IllegalValueException, IOException, DataConversionException {
        Modulo modulo = new Modulo();
        for (JsonAdaptedModule jsonAdaptedModule : modules) {
            Module module = jsonAdaptedModule.toModelType();
            if (modulo.hasModule(module.getModuleCode(), module.getAcademicYear())) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_MODULE);
            }
            modulo.addModuleFromStorage(module);
        }
        return modulo;
    }

}
