package modulo.model.module;

import static modulo.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDate;

import modulo.logic.parser.AcademicYearParser;
import modulo.logic.parser.exceptions.ParseException;
import modulo.model.module.exceptions.AcademicYearException;

/**
 * Class to manage Academic Years.
 */
public class AcademicYear implements Comparable<AcademicYear> {
    public static final String MESSAGE_CONSTRAINTS = "Academic Year should be of the format 2019/2020+1.";

    private int startYear;
    private int endYear;
    private int semester;

    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDate recessWeekStartDate;

    /**
     * Constructs an {@code AcademicYear} using start year, end year and semester integers.
     *
     * @param startYear Start year e.g. 2020.
     * @param endYear   End year e.g. 2021.
     * @param semester  Semester number e.g. 1.
     * @throws AcademicYearException If the inputs are invalid.
     */
    public AcademicYear(int startYear, int endYear, int semester) throws AcademicYearException {
        requireAllNonNull(startYear, endYear, semester);
        checkYears(startYear, endYear);
        checkSemester(semester);
        this.startYear = startYear;
        this.endYear = endYear;
        this.semester = semester;
        LocalDate[] dates;
        try {
            dates = AcademicYearParser.parseAcademicYear(this.startYear, this.semester);
        } catch (ParseException e) {
            throw new AcademicYearException(e.getMessage());
        }
        this.startDate = dates[0];
        this.endDate = dates[1];
        this.recessWeekStartDate = startDate.plusWeeks(6);
    }

    /**
     * Constructs an {@code AcademicYear} with a string {@code years} in the format "2020/2021" and a string {@code
     * semester}.
     *
     * @param years    Years in the format "2020/2021".
     * @param semester Semester number.
     * @throws AcademicYearException If the given inputs are invalid.
     */
    public AcademicYear(String years, String semester) throws AcademicYearException {
        requireAllNonNull(years, semester);
        String[] splitYears = years.split("/", 2);
        int parsedStartYear = Integer.parseInt(splitYears[0]);
        int parsedEndYear = Integer.parseInt(splitYears[1]);
        int parsedSemester = Integer.parseInt(semester);
        checkYears(parsedStartYear, parsedEndYear);
        checkSemester(parsedSemester);
        this.startYear = parsedStartYear;
        this.endYear = parsedEndYear;
        this.semester = parsedSemester;
        LocalDate[] dates;
        try {
            dates = AcademicYearParser.parseAcademicYear(this.startYear, this.semester);
        } catch (ParseException e) {
            throw new AcademicYearException(e.getMessage());
        }
        this.startDate = dates[0];
        this.endDate = dates[1];
        this.recessWeekStartDate = startDate.plusWeeks(6);
    }

    /**
     * Returns the current Academic Year.
     *
     * @return Current academic year.
     * @throws AcademicYearException If some error occurs, which is unlikely.
     */
    public static AcademicYear now() throws AcademicYearException {
        LocalDate curr = LocalDate.now();
        if (curr.isBefore(LocalDate.of(curr.getYear(), 5, 1))
                && curr.isAfter(LocalDate.of(curr.getYear() - 1, 12, 1))) {
            return new AcademicYear(curr.getYear() - 1, curr.getYear(), 2);
        } else {
            return new AcademicYear(curr.getYear(), curr.getYear() + 1, 1);
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

    public LocalDate getRecessWeekStartDate() {
        return recessWeekStartDate;
    }

    /**
     * Parses a stringified academic year back to its original form.
     *
     * @param acadYearInString Stringified academic year.
     * @return Academic Year that the string represents.
     * @throws AcademicYearException Invalid string given.
     */
    public static AcademicYear fromString(String acadYearInString) throws AcademicYearException {
        String[] split = acadYearInString.split("\\+", 2);
        if (split.length < 2) {
            throw new AcademicYearException(MESSAGE_CONSTRAINTS);
        }
        return new AcademicYear(split[0], split[1]);
    }

    public String toModuleCardFormat() {
        return "AY " + getStartYear() + "/" + getEndYear() + " Sem " + getSemester();
    }

    private void checkYears(int startYear, int endYear) throws AcademicYearException {
        if (endYear - startYear != 1) {
            throw new AcademicYearException("Academic end year must be 1 year after the start year!");
        }
    }

    private void checkSemester(int semester) throws AcademicYearException {
        if (semester != 1 && semester != 2) {
            throw new AcademicYearException("Semester must be either 1 or 2!");
        }
    }

    @Override
    public String toString() {
        return getStartYear() + "/" + getEndYear() + "+" + getSemester();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AcademicYear // instanceof handles nulls
                && startYear == (((AcademicYear) other).startYear)
                && endYear == (((AcademicYear) other).endYear)
                && semester == (((AcademicYear) other).semester)
                && startDate.equals(((AcademicYear) other).startDate)
                && endDate.equals(((AcademicYear) other).endDate)); // state check
    }

    @Override
    public int compareTo(AcademicYear o) {
        return startYear - o.startYear == 0 ? semester - o.semester : startYear - o.startYear;
    }
}
