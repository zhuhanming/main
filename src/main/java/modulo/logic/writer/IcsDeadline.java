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

    public String getDue() {
        return due;
    }

    public void setDue(String due) {
        this.due = due;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
