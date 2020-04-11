package modulo.logic.writer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import modulo.model.event.Event;

/**
 * Class that helps to convert a {@link Event} into .ics format.
 */
public class IcsEvent {

    private static final String ICS_ITEM_TYPE = "VEVENT";
    private String uid;
    private String dtstart;
    private String dtend;
    private IcsRRule icsRRule;
    private String summary;
    private String location;
    private String description;
    private List<IcsDeadline> deadlineList;
    private boolean isRecurring;

    /**
     * Creates an {@code IcsEvent} from a given {@code Event}.
     *
     * @param event Event to create the object from.
     */
    public IcsEvent(Event event) {
        uid = UUID.randomUUID().toString();
        dtstart = event.getEventStart().format(DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss'Z'"));
        dtend = event.getEventEnd().format(DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss'Z'"));
        icsRRule = new IcsRRule("WEEKLY", 1, event.getEventStart().getDayOfWeek().toString().substring(0, 2));
        summary = event.getName().fullName;
        location = event.getLocation().value;
        StringBuilder descriptionString = new StringBuilder();
        deadlineList = event.getDeadlines().stream().map(deadline -> {
            descriptionString.append(deadline.getDueTime()
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).append(" - ")
                    .append(deadline.getName()).append(System.lineSeparator());
            return new IcsDeadline(deadline);
        }).collect(Collectors.toList());
        this.description = descriptionString.toString();
    }

    /**
     * Converts the event into an .ics string.
     *
     * @return .ics String.
     */
    public String toIcsString() {
        StringBuilder output = new StringBuilder();
        output.append("BEGIN:" + ICS_ITEM_TYPE).append(System.lineSeparator());
        output.append("UID:").append(uid).append(System.lineSeparator());
        output.append("SEQUENCE:" + 0).append(System.lineSeparator());
        output.append("DTSTAMP:")
                .append(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss'Z'")))
                .append(System.lineSeparator());
        output.append("DTSTART;TZID=Asia/Singapore:").append(dtstart).append(System.lineSeparator());
        output.append("DTEND;TZID=Asia/Singapore:").append(dtend).append(System.lineSeparator());
        output.append("RRULE:").append(icsRRule.toString()).append(System.lineSeparator());
        output.append("SUMMARY:").append(summary).append(System.lineSeparator());
        output.append("DESCRIPTION:").append(description);
        output.append("LOCATION:").append(location).append(System.lineSeparator());
        for (IcsDeadline eachDeadline : deadlineList) {
            output.append(eachDeadline.toIcsString());
        }
        output.append("END:" + ICS_ITEM_TYPE).append(System.lineSeparator());
        return output.toString();
    }

    public static String getIcsItemType() {
        return ICS_ITEM_TYPE;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getDtstart() {
        return dtstart;
    }

    public String getDtend() {
        return dtend;
    }

    public IcsRRule getIcsRRule() {
        return icsRRule;
    }

    public List<IcsDeadline> getDeadlineList() {
        return deadlineList;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isRecurring() {
        return isRecurring;
    }

    public void setRecurring(boolean recurring) {
        isRecurring = recurring;
    }
}
