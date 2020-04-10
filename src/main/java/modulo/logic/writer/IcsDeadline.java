package modulo.logic.writer;

import java.time.format.DateTimeFormatter;

import modulo.model.deadline.Deadline;

/**
 * Class that helps to convert a {@link Deadline} into .ics format.
 */
public class IcsDeadline {

    private static final String ICS_ITEM_TYPE = "VTODO";
    private String due;
    private String summary;
    private String status;

    /**
     * Creates an {@code IcsDeadline} from a given {@code Deadline}.
     *
     * @param deadline Deadline to create the object from.
     */
    public IcsDeadline(Deadline deadline) {
        this.due = deadline.getDueTime().format(DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss'Z'"));
        this.summary = deadline.getName().fullName;
        this.status = deadline.isCompleted() ? "COMPLETED" : "NEEDS-ACTION";
    }

    /**
     * Converts the deadline into an .ics string.
     *
     * @return .ics String.
     */
    public String toIcsString() {
        return "BEGIN:" + ICS_ITEM_TYPE + System.lineSeparator()
                + "DUE;TZID=Asia/Singapore:" + due + System.lineSeparator()
                + "SUMMARY:" + summary + System.lineSeparator()
                + "STATUS:" + status + System.lineSeparator()
                + "END:" + ICS_ITEM_TYPE + System.lineSeparator();
    }

    /**
     * Returns the String {@code due} of the Ics deadline.
     *
     * @return String representing the due datetime of the deadline.
     */
    public String getDue() {
        return due;
    }

    /**
     * Sets the String {@code due} of the Ics deadline.
     *
     * @param due String representing the due datetime of the deadline.
     */
    public void setDue(String due) {
        this.due = due;
    }

    /**
     * Returns the String {@code summary} of the Ics deadline.
     *
     * @return String representing the summary of the deadline.
     */
    public String getSummary() {
        return summary;
    }

    /**
     * Sets the String {@code summary} of the Ics deadline.
     *
     * @param summary String representing the summary of the deadline.
     */
    public void setSummary(String summary) {
        this.summary = summary;
    }

    /**
     * Returns the String {@code status} of the Ics deadline.
     *
     * @return String representing the status of the deadline.
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the String {@code status} of the Ics deadline.
     *
     * @param status String representing the status of the deadline.
     */
    public void setStatus(String status) {
        this.status = status;
    }
}
