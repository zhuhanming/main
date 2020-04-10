package modulo.storage;

import static modulo.storage.JsonAdaptedModule.MISSING_FIELD_MESSAGE_FORMAT;
import static modulo.testutil.Assert.assertThrows;
import static modulo.testutil.module.TypicalModules.CS1231S;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import modulo.commons.exceptions.IllegalValueException;
import modulo.model.module.AcademicYear;
import modulo.model.module.Module;
import modulo.model.module.ModuleCode;
import modulo.model.module.PartialModule;

public class JsonAdaptedModuleTest {
    private static final String INVALID_MODULE_CODE = " HELLO";
    private static final String INVALID_ACADEMIC_YEAR = "123456123";

    private static final String VALID_MODULE_CODE = CS1231S.getModuleCode().toString();
    private static final String VALID_ACADEMIC_YEAR = CS1231S.getAcademicYear().toString();
    private static final List<JsonAdaptedEvent> VALID_EVENTS = CS1231S.getEvents().stream()
            .map(JsonAdaptedEvent::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validModuleDetails_returnsPartialModule() throws Exception {
        JsonAdaptedModule module = new JsonAdaptedModule(CS1231S);
        Module expectedResult = new PartialModule(CS1231S.getModuleCode(), CS1231S.getAcademicYear(),
                CS1231S.getEvents());
        assertEquals(expectedResult, module.toModelType());
    }

    @Test
    public void toModelType_invalidModuleCode_throwsIllegalValueException() {
        JsonAdaptedModule module = new JsonAdaptedModule(INVALID_MODULE_CODE, VALID_ACADEMIC_YEAR, VALID_EVENTS);
        String expectedMessage = ModuleCode.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, module::toModelType);
    }

    @Test
    public void toModelType_nullModuleCode_throwsIllegalValueException() {
        JsonAdaptedModule module = new JsonAdaptedModule(null, VALID_ACADEMIC_YEAR, VALID_EVENTS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, ModuleCode.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, module::toModelType);
    }

    @Test
    public void toModelType_invalidAcademicYear_throwsIllegalValueException() {
        JsonAdaptedModule module = new JsonAdaptedModule(VALID_MODULE_CODE, INVALID_ACADEMIC_YEAR, VALID_EVENTS);
        String expectedMessage = AcademicYear.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, module::toModelType);
    }

    @Test
    public void toModelType_nullAcademicYear_throwsIllegalValueException() {
        JsonAdaptedModule module = new JsonAdaptedModule(VALID_MODULE_CODE, null, VALID_EVENTS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, AcademicYear.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, module::toModelType);
    }
}
