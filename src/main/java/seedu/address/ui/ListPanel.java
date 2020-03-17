package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.Displayable;
import seedu.address.model.event.Event;

/**
 * Panel containing the list of focused items.
 */
public class ListPanel extends UiPart<Region> {
    private static final String FXML = "ListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(ListPanel.class);

    @FXML
    private ListView<Displayable> listView;

    @SuppressWarnings("unchecked")
    public ListPanel(ObservableList<? extends Displayable> eventList) {
        super(FXML);
        listView.setItems((ObservableList<Displayable>) eventList);
        listView.setCellFactory(listView -> new ListViewCell());
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
            } else if (listItem instanceof Event) {
                setGraphic(new EventCard((Event) listItem, getIndex() + 1).getRoot());
            }
        }
    }

}
