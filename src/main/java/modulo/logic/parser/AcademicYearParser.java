package modulo.logic.parser;

import static modulo.commons.core.Messages.INVALID_ACADEMIC_SEMESTER;
import static modulo.commons.util.CollectionUtil.requireAllNonNull;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

import modulo.logic.parser.exceptions.ParseException;

/**
 * Helper class to process logic with regards to academic years.
 */
public class AcademicYearParser {

    /**
     * Parses given academic year and semester to return an array containing the start and end date of the semester.
     *
     * @param startYear The first year of the academic year, e.g. 2020 for AY20/21.
     * @param semester  The semester of the academic year.
     * @return An array of {@code LocalDate}s, the first being the start date and the second being the end date.
     * @throws ParseException If invalid values were given.
     */
    public static LocalDate[] parseAcademicYear(int startYear, int semester) throws ParseException {
        requireAllNonNull(startYear, semester);
        checkValidSemester(semester);
        LocalDate startDate;
        LocalDate endDate;
        if (semester == 1) {
            startDate = LocalDate.of(startYear, 7, 31).with(TemporalAdjusters.next(DayOfWeek.MONDAY)).plusWeeks(1);
        } else {
            startDate = LocalDate.of(startYear, 7, 31).with(TemporalAdjusters.next(DayOfWeek.MONDAY)).plusWeeks(23);
        }
        endDate = startDate.plusWeeks(16).with(TemporalAdjusters.next(DayOfWeek.SATURDAY));
        return new LocalDate[]{startDate, endDate};
    }

    /**
     * Checks if the given semester is either 1 or 2.
     *
     * @param semester Semester integer to check.
     * @throws ParseException If semester is invalid.
     */
    private static void checkValidSemester(int semester) throws ParseException {
        if (semester == 1 || semester == 2) {
            return;
        }
        throw new ParseException(INVALID_ACADEMIC_SEMESTER);
    }
}
