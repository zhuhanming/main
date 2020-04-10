package modulo.model;

import static java.time.temporal.ChronoUnit.DAYS;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import modulo.model.displayable.Displayable;
import modulo.model.module.AcademicYear;

/**
 * Class to section events out by date and modules out by academic year.
 */
public class Title implements Displayable {
    private static final DateTimeFormatter MONTH_FORMAT = DateTimeFormatter.ofPattern("MMM d");
    private static final DateTimeFormatter MONTH_YEAR_FORMAT = DateTimeFormatter.ofPattern("MMM d yyyy");
    private static final DateTimeFormatter WEEKDAY_FORMAT = DateTimeFormatter.ofPattern("EEEE");

    private String title;

    /**
     * Constructs a title using a datetime object.
     *
     * @param localDateTime Datetime object for construction.
     */
    public Title(LocalDateTime localDateTime) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        long differenceInDays = DAYS.between(localDateTime.toLocalDate(), currentDateTime.toLocalDate());
        boolean isToday = differenceInDays == 0;
        boolean isOneDayDifference = Math.abs(differenceInDays) == 1;
        boolean isDifferentYear = localDateTime.getYear() != currentDateTime.getYear();
        if (localDateTime.isBefore(currentDateTime)) {
            if (isToday) {
                this.title = "Today";
            } else if (isOneDayDifference) {
                // isYesterday
                this.title = "Yesterday";
            } else if (isDifferentYear) {
                this.title = localDateTime.format(MONTH_YEAR_FORMAT);
            } else {
                this.title = localDateTime.format(MONTH_FORMAT);
            }
        } else {
            boolean isThisWeek = differenceInDays > -7;
            if (isToday) {
                this.title = "Today";
            } else if (isOneDayDifference) {
                // isTomorrow
                this.title = "Tomorrow";
            } else if (isThisWeek) {
                this.title = localDateTime.format(WEEKDAY_FORMAT);
            } else if (isDifferentYear) {
                this.title = localDateTime.format(MONTH_YEAR_FORMAT);
            } else {
                this.title = localDateTime.format(MONTH_FORMAT);
            }
        }
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
}
