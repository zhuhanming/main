package seedu.address.model.module;

import java.net.URL;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.NoSuchElementException;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.logic.commands.AddEventCommand;
import seedu.address.model.Name;
import seedu.address.model.event.EventType;
import seedu.address.model.module.exceptions.ModuleNotFoundException;

/**
 * Interfacing class between the app and the module JSON files.
 */
public class ModuleLibrary {
    /**
     * Creates a module based on the code and academic year given, with data from the module json files.
     *
     * @param moduleCode   Code of the module.
     * @param academicYear AY for the module.
     * @return New module created from save file.
     * @throws ModuleNotFoundException Fail to read data from the module.
     */
    @SuppressWarnings("unchecked")
    public static Module createAndReturnModule(ModuleCode moduleCode, AcademicYear academicYear)
            throws ModuleNotFoundException {
        char firstCharacter = moduleCode.toString().charAt(0);
        try {
            URL jsonFile = ModuleLibrary.class.getResource("/modules/" + firstCharacter + "Modules.json");
            HashMap<String, LinkedHashMap<String, String>> map =
                    JsonUtil.readJsonFile(Path.of(jsonFile.getPath()), HashMap.class).orElseThrow();
            LinkedHashMap<String, String> moduleNeeded = map.get(moduleCode.toString());
            Name name = new Name(moduleNeeded.get("title"));
            return new Module(moduleCode, name, academicYear, moduleNeeded.get("description"));
        } catch (DataConversionException | NoSuchElementException e) {
            throw new ModuleNotFoundException();
        }
    }

    public static AddEventCommand getAddEventCommandToExecute(Module module, EventType eventType, int eventSlot) {

    }
}
