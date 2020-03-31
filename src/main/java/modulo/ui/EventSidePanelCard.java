package modulo.ui;

import java.time.format.DateTimeFormatter;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import modulo.model.event.Event;

/**
 * An UI component that displays information of a {@code Event}.
 */
public class EventSidePanelCard extends UiPart<Region> {

    private static final String FXML = "EventListSidePanelCard.fxml";

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

    public EventSidePanelCard(Event event, int displayedIndex) {
        super(FXML);
        this.event = event;
        id.setText(displayedIndex + ". ");
        name.setText(event.getName().fullName);
        startDate.setText(event.getEventStart().format(DateTimeFormatter.ofPattern("EEE h a")) + " - "
                + event.getEventEnd().format(DateTimeFormatter.ofPattern("h a")));
        endDate.setText(event.getLocation().toString());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EventSidePanelCard)) {
            return false;
        }

        // state check
        EventSidePanelCard card = (EventSidePanelCard) other;
        return id.getText().equals(card.id.getText())
                && event.equals(card.event);
    }
}
