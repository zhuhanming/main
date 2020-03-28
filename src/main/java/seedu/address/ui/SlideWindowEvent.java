package seedu.address.ui;

import java.time.format.DateTimeFormatter;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.Displayable;
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


    public SlideWindowEvent(Displayable event) {
        super(FXML);
        if (event != null) {
            this.event = (Event) event;
            name.setText((this.event.getEventType()).toString());
            startDate.setText(this.event.getEventStart().format(DateTimeFormatter.ofPattern("d MMMM yyyy, h a")) + " - "
                    + this.event.getEventEnd().format(DateTimeFormatter.ofPattern("h a")));
            module.setText(this.event.getParentModule() != null && this.event.getParentModule().getModuleCode() != null ? this.event.getParentModule().getModuleCode().moduleCode : "DEFAULT123");
            venue.setText(this.event.getLocation().toString());
            eventTypeDescription.setText(this.event.getEventType().getDefaultDeadlineDescription());
            System.out.println("displayable event in event entity is " + ((Event) event).getName().fullName);
        } else {
            this.event = null;
            name.setText("");
            startDate.setText("");
            module.setText("");
            venue.setText("");
            eventTypeDescription.setText("");
        }
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
