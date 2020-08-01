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
        Title title = new Title(new AcademicYear(2019, 2020, 2));
        assertEquals(new AcademicYear(2019, 2020, 2).toModuleCardFormat(), title.getTitle());
    }

    @Test
    public void constructor_localDate_success() {
        LocalDate firstWeek = new AcademicYear(2019, 2020, 2).getStartDate();
        Title firstWeekTitle = new Title(firstWeek);
        assertEquals("Week 1", firstWeekTitle.getTitle());

        LocalDate recessWeek = new AcademicYear(2019, 2020, 2).getRecessWeekStartDate();
        Title recessWeekTitle = new Title(recessWeek);
        assertEquals("Recess Week", recessWeekTitle.getTitle());

        LocalDate afterRecessWeek = new AcademicYear(2019, 2020, 2).getRecessWeekStartDate().plusWeeks(1);
        Title afterRecessWeekTitle = new Title(afterRecessWeek);
        assertEquals("Week 7", afterRecessWeekTitle.getTitle());
    }
}
