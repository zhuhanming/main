package seedu.address.ui;

import java.time.format.DateTimeFormatter;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.event.Event;

/**
 * An UI component that displays information of select {@code Event} in the right panel
 */
public class SlideWindowEvent extends UiPart<Region> {

    private static final String FXML = "SlideWindowDeadlineList.fxml";
    public final Event event;

    @FXML
    private VBox slideEventCard;
    @FXML
    private Label name;
    @FXML
    private Label startDate;
    @FXML
    private Label module;
    @FXML
    private Label venue;
    @FXML
    private Label eventTypeDescription;


    public SlideWindowEvent(Event event, int displayedIndex) {
        super(FXML);
        this.event = event;
        name.setText((event.getName().fullName));
        startDate.setText(event.getEventStart().format(DateTimeFormatter.ofPattern("d MMMM yyyy, h a")) + " - "
                + event.getEventEnd().format(DateTimeFormatter.ofPattern("h a")));
        module.setText(event.getParentModule().getModuleCode().moduleCode);
        venue.setText(event.getLocation().toString());
        eventTypeDescription.setText(event.getEventType().getDefaultDeadlineDescription());
    }

    Label getStartDate() {
        return startDate;
    }

    Label getName() {
        return name;
    }

    Label getModule() {
        return module;
    }

    Label getVenue() {
        return venue;
    }

    Label getDoWork() {
        return eventTypeDescription;
    }

}
