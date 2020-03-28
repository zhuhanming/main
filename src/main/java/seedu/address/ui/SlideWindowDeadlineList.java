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
import seedu.address.model.module.Module;

/**
 * An UI component that displays information of selected {@code Event} and its deadline list
 * update of children node of slideWindowEvent happened here.
 */
public class SlideWindowDeadlineList extends UiPart<Region> {

    private static final String FXML = "SlideWindowDeadlineList.fxml";
    private ObservableList<Event> eventList;


    @FXML
    private ListView<Displayable> deadlineListView;
    @FXML
    private VBox slideEventCard;

    private SlideWindowEvent slideWindowEvent;

    @SuppressWarnings({"unchecked", "checkstyle:CommentsIndentation"})
    public SlideWindowDeadlineList(Displayable displayableEvent, List<Deadline> deadlines, List<Event> eventList) {
        super(FXML);
        if (eventList == null && deadlines != null) {
            slideWindowEvent = new SlideWindowEvent(displayableEvent);
            slideEventCard.getChildren().setAll(slideWindowEvent.getRoot());
            Event e = (Event) displayableEvent;
            deadlineListView.setItems(FXCollections.observableArrayList(deadlines));
            deadlineListView.setCellFactory(listView -> new ListViewCell());
        } else if (deadlines == null && eventList != null) {
            System.out.println("EventList is " + eventList.get(0).getName().fullName);
            slideWindowEvent = new SlideWindowEvent(displayableEvent);
            slideEventCard.getChildren().setAll(slideWindowEvent.getRoot());
            Module module = (Module) displayableEvent;
            deadlineListView.setItems(FXCollections.observableArrayList(eventList));
            deadlineListView.setCellFactory(listView -> new ListViewCell());
        }
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code event} using a {@code eventCard}.
     */
    static class ListViewCell extends ListCell<Displayable> {
        @Override
        protected void updateItem(Displayable listItem, boolean empty) {
            super.updateItem(listItem, empty);
            if (empty || listItem == null) {
                setGraphic(null);
                setText(null);
            } else if (listItem instanceof Deadline) {
                setGraphic(new DeadlineCard((Deadline) listItem, getIndex() + 1).getRoot());
            } else if (listItem instanceof Event) {
                setGraphic(new EventCard((Event) listItem, getIndex() + 1).getRoot());
            }
        }
    }

}
