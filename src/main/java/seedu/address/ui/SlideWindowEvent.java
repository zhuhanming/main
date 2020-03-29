package seedu.address.ui;

import java.time.format.DateTimeFormatter;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.Displayable;
import seedu.address.model.deadline.Deadline;
import seedu.address.model.event.Event;
import seedu.address.model.module.Module;

/**
 * An UI component that displays information of select {@code Event} in the right panel
 */
public class SlideWindowEvent extends UiPart<Region> {

    private static final String FXML = "DetailsWindow.fxml";
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
    private Label completionStatus;
    @FXML
    private Label venue;
    @FXML
    private Label eventTypeDescription;


    public SlideWindowEvent(Displayable eventOrModule) {
        super(FXML);

        if (eventOrModule instanceof Event) {
            this.eventViewed = (Event) eventOrModule;
            this.moduleViewed = null;
            name.setText(this.eventViewed.getName().toString());
            startDate.setText(this.eventViewed.getEventStart().format(DateTimeFormatter.ofPattern("d MMMM yyyy, h a"))
                    + " - " + this.eventViewed.getEventEnd().format(DateTimeFormatter.ofPattern("h a")));
            module.setText(this.eventViewed.getParentModule().getModuleCode().moduleCode);
            module.getStyleClass().remove("hidden");
            boolean areAllDeadlinesCompleted = this.eventViewed.getDeadlines().stream().allMatch(Deadline::isCompleted);
            completionStatus.setText(areAllDeadlinesCompleted ? "Deadlines Complete" : "Deadlines Incomplete");
            completionStatus.getStyleClass().remove("hidden");
            if (areAllDeadlinesCompleted && !completionStatus.getStyleClass().contains("allComplete")) {
                completionStatus.getStyleClass().add("allComplete");
            }
            venue.setText(this.eventViewed.getLocation().toString());
            eventTypeDescription.setText("Prepare for " + this.eventViewed.getName().toString() + " by doing:");
        } else if (eventOrModule instanceof Module) {
            this.moduleViewed = (Module) eventOrModule;
            this.eventViewed = null;
            name.setText((this.moduleViewed.getName().toString()));
            startDate.setText(this.moduleViewed.getAcademicYear().toModuleCardFormat());
            module.setText(this.moduleViewed.getModuleCode().moduleCode);
            module.getStyleClass().remove("hidden");
            completionStatus.setText("");
            if (!completionStatus.getStyleClass().contains("hidden")) {
                completionStatus.getStyleClass().add("hidden");
            }
            completionStatus.getStyleClass().remove("allComplete");
            venue.setText(null);
            eventTypeDescription.setText("Events under this module:");
        } else {
            this.eventViewed = null;
            this.moduleViewed = null;
            name.setText("");
            startDate.setText("");
            module.setText("");
            if (!module.getStyleClass().contains("hidden")) {
                module.getStyleClass().add("hidden");
            }
            completionStatus.setText("");
            if (!completionStatus.getStyleClass().contains("hidden")) {
                completionStatus.getStyleClass().add("hidden");
            }
            completionStatus.getStyleClass().remove("allComplete");
            venue.setText("");
            eventTypeDescription.setText("");
        }
    }
}
