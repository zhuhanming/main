package seedu.address.model.module;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.TemporalAmount;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import seedu.address.logic.commands.AddEventCommand;
import seedu.address.model.Name;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventType;
import seedu.address.model.event.Location;
import seedu.address.model.event.exceptions.EventNotFoundException;
import seedu.address.model.module.exceptions.ModuleNotFoundException;

/**
 * Interfacing class between the app and the module JSON files.
 */
public class ModuleLibrary {
    private static final Gson GSON = new Gson();

    /**
     * Creates a module based on the code and academic year given, with data from the module json files.
     *
     * @param moduleCode   Code of the module.
     * @param academicYear AY for the module.
     * @return New module created from save file.
     * @throws ModuleNotFoundException Fail to read data from the module.
     */
    public static Module createAndReturnModule(ModuleCode moduleCode, AcademicYear academicYear)
            throws ModuleNotFoundException {
        try {
            JsonObject moduleNeeded = getModule(moduleCode);
            Name name = new Name(GSON.fromJson(moduleNeeded.get("title"), String.class));
            String description = GSON.fromJson(moduleNeeded.get("description"), String.class);
            return new Module(moduleCode, name, academicYear, description);
        } catch (IOException e) {
            throw new ModuleNotFoundException();
        }
    }

    /**
     * Returns a list of event types for the given module.
     *
     * @param module Module to search for.
     * @return List of event types the module possesses.
     * @throws ModuleNotFoundException If the module cannot be found, which is likely impossible.
     */
    public static List<EventType> getEventTypesOfModule(Module module) throws ModuleNotFoundException {
        try {
            JsonArray timetable = getTimetable(module);
            List<EventType> eventTypes = new ArrayList<>();
            for (int i = 0; i < timetable.size(); i++) {
                JsonObject lesson = timetable.get(i).getAsJsonObject();
                String lessonType = lesson.get("lessonType").getAsString();
                EventType eventType = EventType.parseEventType(lessonType);
                if (!eventTypes.contains(eventType)) {
                    eventTypes.add(eventType);
                }
            }
            return eventTypes;
        } catch (IOException e) {
            throw new ModuleNotFoundException();
        }
    }

    public static AddEventCommand getAddEventCommandToExecute(Module module, EventType eventType,
                                                              int eventSlot) throws EventNotFoundException {
        try {
            JsonArray timetable = getTimetable(module);
            JsonObject lesson = null;
            for (int i = 0; i < timetable.size(); i++) {
                JsonObject selectLesson = timetable.get(i).getAsJsonObject();
                String lessonType = selectLesson.get("lessonType").getAsString();
                String classNumber = selectLesson.get("classNo").getAsString();
                if (EventType.parseEventType(lessonType) == eventType && getClassNumber(classNumber) == eventSlot) {
                    lesson = selectLesson;
                    break;
                }
            }
            if (lesson == null) {
                throw new EventNotFoundException();
            }
            JsonArray weeks = lesson.getAsJsonArray("weeks");
            String day = lesson.get("day").getAsString().toUpperCase();
            String startTime = lesson.get("startTime").getAsString();
            String endTime = lesson.get("endTime").getAsString();
            String location = lesson.get("venue").getAsString();
            AcademicYear academicYear = module.getAcademicYear();
            LocalDate eventDay = academicYear.getStartDate()
                    .plusWeeks(weeks.get(0).getAsInt() - 1)
                    .with(TemporalAdjusters.nextOrSame(DayOfWeek.valueOf(day)));
            LocalDateTime eventStart = eventDay.atTime(Integer.parseInt(startTime.substring(0, 2)),
                    Integer.parseInt(startTime.substring(2, 4)));
            LocalDateTime eventEnd = eventDay.atTime(Integer.parseInt(endTime.substring(0, 2)),
                    Integer.parseInt(endTime.substring(2, 4)));
            boolean isRepeated = false;
            TemporalAmount frequency = null;
            LocalDate endRepeatDate = eventDay;
            if (weeks.size() > 1) {
                frequency = Period.ofWeeks(weeks.get(1).getAsInt() - weeks.get(0).getAsInt());
                isRepeated = true;
                endRepeatDate = endRepeatDate.plusWeeks(weeks.get(weeks.size() - 1).getAsInt()
                        - weeks.get(0).getAsInt());
            }
            Event eventToAdd = new Event(new Name(eventType.toString()), eventType,
                    eventStart, eventEnd, module, new Location(location));
            return new AddEventCommand(eventToAdd, isRepeated, endRepeatDate, frequency);
        } catch (IOException e) {
            throw new EventNotFoundException();
        }
    }

    private static int getClassNumber(String classNumber) {
        while (!Character.isDigit(classNumber.charAt(0))) {
            classNumber = classNumber.substring(1);
        }
        return Integer.parseInt(classNumber);
    }

    private static JsonObject getModule(ModuleCode moduleCode) throws IOException {
        char firstCharacter = moduleCode.toString().charAt(0);
        URL jsonFile = ModuleLibrary.class.getResource("/modules/" + firstCharacter + "Modules.json");
        JsonObject jsonObject = JsonParser.parseString(Files.readString(Path.of(jsonFile.getPath())))
                .getAsJsonObject();
        return jsonObject.get(moduleCode.toString()).getAsJsonObject();
    }

    private static JsonArray getTimetable(Module module) throws IOException {
        AcademicYear academicYear = module.getAcademicYear();
        JsonObject moduleNeeded = getModule(module.getModuleCode());
        JsonArray semesterData = moduleNeeded.getAsJsonArray("semesterData");
        JsonArray timetable = null;
        for (int i = 0; i < semesterData.size(); i++) {
            JsonObject semester = semesterData.get(i).getAsJsonObject();
            int semesterNumber = semester.get("semester").getAsInt();
            if (semesterNumber == academicYear.getSemester()) {
                timetable = semester.get("timetable").getAsJsonArray();
            }
        }
        if (timetable == null) {
            timetable = ((JsonObject) semesterData.get(0)).get("timetable").getAsJsonArray();
        }
        return timetable;
    }
}
