package modulo.ui;

import java.time.format.DateTimeFormatter;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import modulo.model.deadline.Deadline;
import modulo.model.displayable.Displayable;
import modulo.model.event.Event;
import modulo.model.module.Module;

/**
 * An UI component that displays information of select {@code Event} in the right panel
 */
public class RightPanelDescription extends UiPart<Region> {

    private static final String FXML = "RightPanel.fxml";
    private final Event eventViewed;
    private final Module moduleViewed;
    private final MainWindow mainWindow;

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


    public RightPanelDescription(Displayable eventOrModule, MainWindow mainWindow) {
        super(FXML);
        this.mainWindow = mainWindow;
        if (eventOrModule instanceof Event) {
            this.eventViewed = (Event) eventOrModule;
            this.moduleViewed = null;
            name.setText(this.eventViewed.getName().toString());
            name.setWrapText(true);
            startDate.setText(this.eventViewed.getEventStart().format(DateTimeFormatter.ofPattern("d MMMM yyyy, h a"))
                    + " - " + this.eventViewed.getEventEnd().format(DateTimeFormatter.ofPattern("h a")));
            module.setText(this.eventViewed.getParentModule().getModuleCode().toString());
            module.getStyleClass().clear();
            module.getStyleClass().addAll(mainWindow.getTagColourClass(
                    eventViewed.getParentModule().getModuleCode()), "moduleCode");
            boolean areAllDeadlinesCompleted = this.eventViewed.getDeadlines().stream().allMatch(Deadline::isCompleted);
            completionStatus.setText(areAllDeadlinesCompleted ? "Deadlines Complete" : "Deadlines Incomplete");
            completionStatus.getStyleClass().remove("hidden");
            if (areAllDeadlinesCompleted && !completionStatus.getStyleClass().contains("allComplete")) {
                completionStatus.getStyleClass().add("allComplete");
            }
            venue.setText(this.eventViewed.getLocation().toString());
            venue.getStyleClass().remove("moduleDescription");
            eventTypeDescription.setText("Prepare for " + this.eventViewed.getName().toString() + " by doing:");
        } else if (eventOrModule instanceof Module) {
            this.moduleViewed = (Module) eventOrModule;
            this.eventViewed = null;
            name.setText((this.moduleViewed.getName().toString()));
            name.setWrapText(true);
            startDate.setText(this.moduleViewed.getAcademicYear().toModuleCardFormat());
            module.setText(this.moduleViewed.getModuleCode().moduleCode);
            module.getStyleClass().clear();
            module.getStyleClass().addAll(mainWindow.getTagColourClass(moduleViewed.getModuleCode()), "moduleCode");
            completionStatus.setText("");
            if (!completionStatus.getStyleClass().contains("hidden")) {
                completionStatus.getStyleClass().add("hidden");
            }
            completionStatus.getStyleClass().remove("allComplete");
            venue.setText("Click to view description");
            if (!venue.getStyleClass().contains("moduleDescription")) {
                venue.getStyleClass().add("moduleDescription");
            }
            venue.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> openDescription());
            eventTypeDescription.setText("Official events under this module:");
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

    @FXML
    private void openDescription() {
        if (moduleViewed != null && eventViewed == null) {
            mainWindow.handleDescription(moduleViewed.getDescription(), moduleViewed.getName().toString());
        }
    }
}
