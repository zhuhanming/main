package modulo.ui;

import java.time.format.DateTimeFormatter;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import modulo.model.deadline.Deadline;
import modulo.model.event.Event;

/**
 * An UI component that displays information of a {@code Event}.
 */
public class EventCard extends UiPart<Region> {

    private static final String FXML = "EventListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX. As a consequence, UI
     * elements' variable names cannot be set to such keywords or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Event event;

    @FXML
    private HBox eventCardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label startDate;
    @FXML
    private Label endDate;
    @FXML
    private Label moduleCode;
    @FXML
    private Label isCompleted;

    public EventCard(Event event, int displayedIndex, String tagColorClass) {
        super(FXML);
        this.event = event;
        id.setText(displayedIndex + ". ");
        name.setText(event.getName().fullName);
        startDate.setText(event.getEventStart().format(DateTimeFormatter.ofPattern("h a")) + " - "
                + event.getEventEnd().format(DateTimeFormatter.ofPattern("h a")));
        endDate.setText(event.getLocation().toString());
        moduleCode.setText(event.getParentModule().getModuleCode().toString());
        moduleCode.getStyleClass().clear();
        moduleCode.getStyleClass().addAll(tagColorClass, "moduleCode");
        boolean areAllDeadlinesCompleted = event.getDeadlines().stream().allMatch(Deadline::isCompleted);
        isCompleted.setText(areAllDeadlinesCompleted ? "\u2713" : "\u2718"); // tick or cross depending on status
        if (!isCompleted.getStyleClass().contains("completed") && areAllDeadlinesCompleted) {
            isCompleted.getStyleClass().add("completed");
        } else if (!areAllDeadlinesCompleted) {
            isCompleted.getStyleClass().remove("completed");
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EventCard)) {
            return false;
        }

        // state check
        EventCard card = (EventCard) other;
        return id.getText().equals(card.id.getText())
                && event.equals(card.event);
    }
}
