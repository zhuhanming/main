package seedu.address.ui;

import java.time.format.DateTimeFormatter;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.Displayable;
import seedu.address.model.DisplayableType;
import seedu.address.model.event.Event;
import seedu.address.model.module.Module;

/**
 * An UI component that displays information of select {@code Event} in the right panel
 */
public class SlideWindowEvent extends UiPart<Region> {

    private static final String FXML = "SlideWindowDeadlineList.fxml";
    public final Event eventViewed;
    public final Module moduleViewed;

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


    public SlideWindowEvent(Displayable eventOrModule) {
        super(FXML);

        DisplayableType displayableType = null;

        if (eventOrModule != null) {
            displayableType = ParserUtil.parseDisplayableTypeRightPanel(eventOrModule.toString());
        }

        if (displayableType != null && displayableType.toString().equals("EVENT")) {
            this.eventViewed = (Event) eventOrModule;
            this.moduleViewed = null;
            name.setText((this.eventViewed.getEventType()).toString());
            startDate.setText(this.eventViewed.getEventStart().format(DateTimeFormatter.ofPattern("d MMMM yyyy, h a"))
                    + " - " + this.eventViewed.getEventEnd().format(DateTimeFormatter.ofPattern("h a")));
            module.setText(this.eventViewed.getParentModule().getModuleCode().moduleCode);
            venue.setText(this.eventViewed.getLocation().toString());
            eventTypeDescription.setText(this.eventViewed.getEventType().getDefaultDeadlineDescription());
        } else if (displayableType != null && displayableType.toString().equals("MODULE")) {
            this.moduleViewed = (Module) eventOrModule;
            this.eventViewed = null;
            name.setText((this.moduleViewed.getName().fullName));
            startDate.setText(this.moduleViewed.getAcademicYear().toModuleCardFormat());
            module.setText(this.moduleViewed.getModuleCode().moduleCode);
            venue.setText(null);
            eventTypeDescription.setText(null);
        } else {
            this.eventViewed = null;
            this.moduleViewed = null;
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
