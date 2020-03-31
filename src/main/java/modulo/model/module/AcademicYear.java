package modulo.model.module;

import static modulo.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDate;

import modulo.logic.parser.AcademicYearParser;
import modulo.logic.parser.exceptions.ParseException;

/**
 * Class to manage Academic Years.
 */
public class AcademicYear {
    private int startYear;
    private int endYear;
    private int semester;

    private LocalDate startDate;
    private LocalDate endDate;

    public AcademicYear(int startYear, int semester) throws ParseException {
        requireAllNonNull(startYear, semester);
        this.startYear = startYear;
        this.endYear = startYear + 1;
        this.semester = semester;
        LocalDate[] dates = AcademicYearParser.parseAcademicYear(startYear, semester);
        this.startDate = dates[0];
        this.endDate = dates[1];
    }

    public AcademicYear(String years, String semester) throws ParseException {
        requireAllNonNull(years, semester);
        String[] splitYears = years.split("/", 2);
        this.startYear = Integer.parseInt(splitYears[0]);
        this.endYear = Integer.parseInt(splitYears[1]);
        this.semester = Integer.parseInt(semester);
        LocalDate[] dates = AcademicYearParser.parseAcademicYear(this.startYear, this.semester);
        this.startDate = dates[0];
        this.endDate = dates[1];
    }

    /**
     * Returns the current Academic Year.
     *
     * @return Current academic year.
     * @throws ParseException If some error occurs, which is unlikely.
     */
    public static AcademicYear now() throws ParseException {
        LocalDate curr = LocalDate.now();
        if (curr.isBefore(LocalDate.of(curr.getYear(), 5, 1))
                && curr.isAfter(LocalDate.of(curr.getYear() - 1, 12, 1))) {
            return new AcademicYear(curr.getYear() - 1, 2);
        } else {
            return new AcademicYear(curr.getYear(), 1);
        }
    }

    public int getStartYear() {
        return startYear;
    }

    public int getEndYear() {
        return endYear;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public int getSemester() {
        return semester;
    }

    /**
     * Parses a stringified academic year back to its original form.
     *
     * @param acadYearInString Stringified academic year.
     * @return Academic Year that the string represents.
     * @throws ParseException Invalid string given.
     */
    public static AcademicYear fromString(String acadYearInString) throws ParseException {
        String[] split = acadYearInString.split("\\+", 2);
        if (split.length < 2) {
            throw new ParseException("Invalid Academic Year String!");
        }
        return new AcademicYear(split[0], split[1]);
    }

    public String toModuleCardFormat() {
        return "AY " + getStartYear() + "/" + getEndYear() + " Sem " + getSemester();
    }

    @Override
    public String toString() {
        return getStartYear() + "/" + getEndYear() + "+" + getSemester();
    }
}
