package modulo.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import modulo.model.displayable.Displayable;
import modulo.model.module.AcademicYear;

/**
 * Class to section events out by date and modules out by academic year.
 */
public class Title implements Displayable {
    private String title;

    /**
     * Constructs a title using a datetime object.
     *
     * @param localDate Date object for construction.
     */
    public Title(LocalDate localDate) {
        LocalDate startDate = AcademicYear.now().getStartDate();
        int weekNumber = (int) ChronoUnit.WEEKS.between(startDate, localDate);
        this.title = processWeekNumber(weekNumber);
    }

    /**
     * Constructs a title from an {@code AcademicYear} object.
     *
     * @param academicYear Academic year to construct the title from.
     */
    public Title(AcademicYear academicYear) {
        this.title = academicYear.toModuleCardFormat();
    }

    /**
     * Returns the title.
     *
     * @return The title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Returns a Title String for the given week number.
     *
     * @param weekNumber Week number to process.
     * @return String for the title.
     */
    private String processWeekNumber(int weekNumber) {
        if (weekNumber == 6) {
            return "Recess Week";
        } else if (weekNumber > 6) {
            return "Week " + weekNumber;
        }
        return "Week " + (weekNumber + 1);
    }
}
