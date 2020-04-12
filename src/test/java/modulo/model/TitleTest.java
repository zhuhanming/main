package modulo.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import modulo.model.module.AcademicYear;

/**
 * JUnit test class for {@code Title}.
 */
public class TitleTest {
    @Test
    public void constructor_academicYear_success() {
        Title title = new Title(AcademicYear.now());
        assertEquals(AcademicYear.now().toModuleCardFormat(), title.getTitle());
    }

    @Test
    public void constructor_localDate_success() {
        LocalDate firstWeek = AcademicYear.now().getStartDate();
        Title firstWeekTitle = new Title(firstWeek);
        assertEquals("Week 1", firstWeekTitle.getTitle());

        LocalDate recessWeek = AcademicYear.now().getRecessWeekStartDate();
        Title recessWeekTitle = new Title(recessWeek);
        assertEquals("Recess Week", recessWeekTitle.getTitle());

        LocalDate afterRecessWeek = AcademicYear.now().getRecessWeekStartDate().plusWeeks(1);
        Title afterRecessWeekTitle = new Title(afterRecessWeek);
        assertEquals("Week 7", afterRecessWeekTitle.getTitle());
    }
}
