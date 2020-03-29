package seedu.address.ui;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.Displayable;
import seedu.address.model.deadline.Deadline;
import seedu.address.model.event.Event;

/**
 * An UI component that displays information of selected {@code Event} and its deadline list update of children node of
 * slideWindowEvent happened here.
 */
public class DetailsWindow extends UiPart<Region> {

    private static final String FXML = "DetailsWindow.fxml";
    private ObservableList<Event> eventList;
    private MainWindow mainWindow;

    private Displayable displayable;

    @FXML
    private ListView<Displayable> deadlineListView;
    @FXML
    private VBox slideEventCard;

    private SlideWindowEvent slideWindowEvent;

    public DetailsWindow(Displayable displayable, List<Deadline> deadlines, List<Event> events,
                         MainWindow mainWindow) {
        super(FXML);
        this.mainWindow = mainWindow;
        this.displayable = displayable;
        if (events == null && deadlines != null) {
            slideWindowEvent = new SlideWindowEvent(displayable);
            slideEventCard.getChildren().setAll(slideWindowEvent.getRoot());
            deadlineListView.setItems(FXCollections.observableArrayList(deadlines));
            deadlineListView.setCellFactory(listView -> new ListViewCell(mainWindow));
        } else if (deadlines == null && events != null) {
            slideWindowEvent = new SlideWindowEvent(displayable);
            slideEventCard.getChildren().setAll(slideWindowEvent.getRoot());
            deadlineListView.setItems(FXCollections.observableArrayList(events));
            deadlineListView.setCellFactory(listView -> new ListViewCell(mainWindow));
        }
    }

    /**
     * Updates this component.
     */
    public void updateStatus() {
        deadlineListView.setItems(deadlineListView.getItems());
        deadlineListView.setCellFactory(listView -> new ListViewCell(mainWindow));
        slideWindowEvent = new SlideWindowEvent(displayable);
        slideEventCard.getChildren().setAll(slideWindowEvent.getRoot());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code event} using a {@code eventCard}.
     */
    static class ListViewCell extends ListCell<Displayable> {
        private MainWindow mainWindow;

        public ListViewCell(MainWindow mainWindow) {
            this.mainWindow = mainWindow;
        }

        @Override
        protected void updateItem(Displayable listItem, boolean empty) {
            super.updateItem(listItem, empty);
            if (empty || listItem == null) {
                setGraphic(null);
                setText(null);
            } else if (listItem instanceof Deadline) {
                setGraphic(new DeadlineCard((Deadline) listItem, getIndex() + 1, mainWindow).getRoot());
            } else if (listItem instanceof Event) {
                setGraphic(new EventCard((Event) listItem, getIndex() + 1).getRoot());
            }
        }
    }
}
