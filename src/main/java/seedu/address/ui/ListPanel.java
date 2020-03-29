package seedu.address.ui;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.model.Displayable;
import seedu.address.model.event.Event;
import seedu.address.model.module.Module;

/**
 * Panel containing the list of focused items.
 */
public class ListPanel extends UiPart<Region> {
    private static final String FXML = "ListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(ListPanel.class);

    @FXML
    private ListView<Displayable> listView;

    @SuppressWarnings("unchecked")
    public ListPanel(ObservableList<? extends Displayable> displayableList, MainWindow mainWindow) {
        super(FXML);
        System.out.println("List Panel running here is " + displayableList.toString());
        ObservableList<Displayable> listViewList;
        if (!displayableList.isEmpty() && displayableList.get(0) instanceof Event) {
            listViewList = processDisplayableList((ObservableList<Displayable>) displayableList);
        } else {
            listViewList = (ObservableList<Displayable>) displayableList;
        }
        listView.setItems(listViewList);
        listView.setCellFactory(listView -> new ListViewCell(mainWindow));
    }

    /**
     * Returns a processed Event list with Titles inserted.
     *
     * @param displaybleList List of {@code Event} to process.
     * @return List with Titles inserted.
     */
    private ObservableList<Displayable> processDisplayableList(ObservableList<Displayable> displaybleList) {
        final ObservableList<Displayable> result = FXCollections.observableArrayList();
        if (displaybleList.size() == 0) {
            return result;
        }
        LocalDateTime currentDateTime = ((Event) displaybleList.get(0)).getEventStart();
        LocalDate currentDate = currentDateTime.toLocalDate();
        result.add(0, new Title(currentDateTime));
        for (int i = 0; i < displaybleList.size(); i++) {
            Event event = (Event) displaybleList.get(i);
            LocalDateTime localDateTime = event.getEventStart();
            LocalDate localDate = localDateTime.toLocalDate();
            if (!localDate.isEqual(currentDate)) {
                result.add(new Title(localDateTime));
                currentDate = localDate;
            }
            result.add(new DisplayablePair<>(event, i));
        }
        return result;
    }

    /**
     * Selects displayable at given index.
     *
     * @param index Index of displayable.
     */
    public void selectDisplayable(Index index) {
        int actualSelectionIndex = index.getZeroBased();
        ObservableList<Displayable> listViewList = listView.getItems();
        for (int i = 0; i < actualSelectionIndex + 1; i++) {
            if (listViewList.get(i) instanceof Title) {
                actualSelectionIndex++;
            }
        }
        this.listView.getSelectionModel().select(actualSelectionIndex);
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
            } else if (listItem instanceof DisplayablePair) {
                @SuppressWarnings("unchecked")
                DisplayablePair<Event, Integer> item = (DisplayablePair<Event, Integer>) listItem;
                setGraphic(new EventCard(item.getFirst(), item.getSecond() + 1).getRoot());
                setOnMouseClicked(event -> mainWindow.handleListClick(item.getSecond()));
                setDisable(false);
            } else if (listItem instanceof Module) {
                setGraphic(new ModuleCard((Module) listItem, getIndex() + 1).getRoot());
                setOnMouseClicked(event -> mainWindow.handleListClick(getIndex()));
                setDisable(false);
            } else if (listItem instanceof Title) {
                setGraphic(new TitleCard((Title) listItem).getRoot());
                setDisable(true);
            }
        }
    }
}
