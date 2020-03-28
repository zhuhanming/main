package seedu.address.ui;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.Displayable;
import seedu.address.model.deadline.Deadline;
import seedu.address.model.event.Event;

/**
 * An UI component that displays information of selected {@code Event} and its deadline list
 * update of children node of slideWindowEvent happened here.
 */
public class SlideWindowDeadlineList extends UiPart<Region> {

    private static final String FXML = "SlideWindowDeadlineList.fxml";

    @FXML
    private ListView<Displayable> deadlineListView;
    @FXML
    private VBox slideEventCard;

    private SlideWindowEvent slideWindowEvent;


    @SuppressWarnings({"unchecked", "checkstyle:CommentsIndentation"})
    public SlideWindowDeadlineList(Displayable displayableEvent) {
        super(FXML);
        slideWindowEvent = new SlideWindowEvent(displayableEvent);
        slideEventCard.getChildren().setAll(slideWindowEvent.getRoot());
        Event e = (Event) displayableEvent;
        if (e != null) {
            deadlineListView.setItems(FXCollections.observableArrayList(e.getDeadlines()));
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
                /** Deadline deadline = new Deadline(new Name("Read lecture 8 and do tutorial "),
                 LocalDateTime.now().withNano(0), true);
                 setGraphic(new DeadlineCard(deadline, 0).getRoot());*/
                System.out.println("%%%%%%%%%%%%%%%%%555555555555");
                setGraphic(new DeadlineCard((Deadline) listItem, getIndex() + 1).getRoot());
            }
        }
    }

}
