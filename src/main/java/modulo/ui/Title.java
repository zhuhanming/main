package modulo.ui;

import static java.time.temporal.ChronoUnit.DAYS;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import modulo.model.Displayable;

/**
 * Class to section events out by date.
 */
public class Title implements Displayable {
    private static final DateTimeFormatter MONTH_FORMAT = DateTimeFormatter.ofPattern("MMM d");
    private static final DateTimeFormatter MONTH_YEAR_FORMAT = DateTimeFormatter.ofPattern("MMM d yyyy");
    private static final DateTimeFormatter WEEKDAY_FORMAT = DateTimeFormatter.ofPattern("EEEE");

    private String title;

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

    public String getTitle() {
        return title;
    }
}
