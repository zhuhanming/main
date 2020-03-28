package seedu.address.model.ics;

import java.time.format.DateTimeFormatter;
import java.util.UUID;

import seedu.address.model.deadline.Deadline;

/**
 * todo
 */
public class IcsDeadline {

    private static final String ICS_ITEM_TYPE = "VTODO";
    private Deadline originalDeadline;
    private String uid;
    private String due;
    private String summary;
    private String status;

    public IcsDeadline() {

    }

    public IcsDeadline(Deadline deadline) {
        this.originalDeadline = deadline;
        this.uid = UUID.randomUUID().toString();
        this.due = deadline.getDueTime().format(DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss'Z'"));
        this.summary = deadline.getName().fullName;
        this.status = deadline.isCompleted() ? "COMPLETED" : "NEEDS-ACTION";

    }

    /**
     * todo
     * @return
     */
    public String toIcsString() {
        StringBuilder output = new StringBuilder();
        output.append("BEGIN:" + ICS_ITEM_TYPE + System.lineSeparator());
        output.append("DUE;TZID=Asia/Singapore:" + due + System.lineSeparator());
        output.append("SUMMARY:" + summary + System.lineSeparator());
        output.append("STATUS:" + status + System.lineSeparator());
        output.append("END:" + ICS_ITEM_TYPE + System.lineSeparator());
        return output.toString();
    }

    public String getIcsItemType() {
        return ICS_ITEM_TYPE;
    }

    public Deadline getOriginalDeadline() {
        return originalDeadline;
    }

    public void setOriginalDeadline(Deadline originalDeadline) {
        this.originalDeadline = originalDeadline;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
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
