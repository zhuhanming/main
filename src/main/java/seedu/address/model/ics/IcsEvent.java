package seedu.address.model.ics;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import seedu.address.model.event.Event;

/**
 * todo
 */
public class IcsEvent {

    private static final String ICS_ITEM_TYPE = "VEVENT";
    private Event originalEvent;
    private String uid;
    private String dtstart;
    private String dtend;
    private IcsRRule icsRRule;
    private List<String> exdateList;
    private String summary;
    private String location;
    private String description;
    private List<IcsDeadline> deadlineList;
    private boolean isRecurring;

    public IcsEvent() {

    }

    public IcsEvent(Event event) {
        this.originalEvent = event;

        uid = UUID.randomUUID().toString();
        dtstart = event.getEventStart().format(DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss'Z'"));
        System.out.println(event.getEventStart().toString() + dtstart);
        dtend = event.getEventEnd().format(DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss'Z'"));
        System.out.println(event.getEventEnd().toString() + dtend);
        icsRRule = new IcsRRule("WEEKLY", 1, event.getEventStart().getDayOfWeek().toString()
                .substring(0, 2));
        summary = event.getName().fullName;
        location = event.getLocation().value;
        StringBuilder descriptionString = new StringBuilder();
        deadlineList = event.getDeadlines().stream()
                .map(deadline -> {
                    descriptionString.append(deadline.getDueTime()
                            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + " - "
                            + deadline.getName() + "\n");
                    return new IcsDeadline(deadline);
                }
                ).collect(Collectors.toList());
        this.description = descriptionString.toString();
    }

    /**
     * todo
     * @return
     */
    public String toIcsString() {
        StringBuilder output = new StringBuilder();
        output.append("BEGIN:" + ICS_ITEM_TYPE + System.lineSeparator());
        output.append("UID:" + uid + System.lineSeparator());
        output.append("SEQUENCE:" + 0 + System.lineSeparator());
        output.append("DTSTAMP:" + (LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss'Z'")))
                + System.lineSeparator());
        output.append("DTSTART;TZID=Asia/Singapore:" + dtstart + System.lineSeparator());
        output.append("DTEND;TZID=Asia/Singapore:" + dtend + System.lineSeparator());
        output.append("RRULE:" + icsRRule.toString() + System.lineSeparator());
        output.append("SUMMARY:" + summary + System.lineSeparator());
        output.append("DESCRIPTION:" + description);
        output.append("LOCATION:" + location + System.lineSeparator());
        for (IcsDeadline eachDeadline: deadlineList) {
            output.append(eachDeadline.toIcsString());
        }
        output.append("END:" + ICS_ITEM_TYPE + System.lineSeparator());
        return output.toString();
    }

    public String getIcsItemType() {
        return ICS_ITEM_TYPE;
    }

    public Event getOriginalEvent() {
        return originalEvent;
    }

    public void setOriginalEvent(Event originalEvent) {
        this.originalEvent = originalEvent;
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

    public void setDtstart(String dtstart) {
        this.dtstart = dtstart;
    }

    public String getDtend() {
        return dtend;
    }

    public void setDtend(String dtend) {
        this.dtend = dtend;
    }

    public IcsRRule getIcsRRule() {
        return icsRRule;
    }

    public void setIcsRRule(IcsRRule icsRRule) {
        this.icsRRule = icsRRule;
    }

    public List<String> getExdateList() {
        return exdateList;
    }

    public void setExdateList(List<String> exdateList) {
        this.exdateList = exdateList;
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
