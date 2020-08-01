package modulo.ui;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import modulo.commons.core.LogsCenter;
import modulo.commons.core.index.Index;
import modulo.model.Title;
import modulo.model.displayable.Displayable;
import modulo.model.displayable.DisplayablePair;
import modulo.model.event.Event;
import modulo.model.module.AcademicYear;
import modulo.model.module.Module;

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
        ObservableList<Displayable> listViewList;
        if (!displayableList.isEmpty() && displayableList.get(0) instanceof Event) {
            listViewList = processEventDisplayableList((ObservableList<Displayable>) displayableList);
        } else {
            listViewList = processModuleDisplayableList((ObservableList<Displayable>) displayableList);
        }
        listView.setItems(listViewList);
        listView.setCellFactory(listView -> new ListViewCell(mainWindow));
    }

    /**
     * Returns a processed Event list with Titles inserted.
     *
     * @param displayableList List of {@code Event} to process.
     * @return List with Titles inserted.
     */
    private ObservableList<Displayable> processEventDisplayableList(ObservableList<Displayable> displayableList) {
        final ObservableList<Displayable> result = FXCollections.observableArrayList();
        if (displayableList.size() == 0) {
            return result;
        }
        LocalDate currentDate = ((Event) displayableList.get(0)).getEventStart().toLocalDate();
        LocalDate startDate = new AcademicYear(2019, 2020, 2).getStartDate();
        while (ChronoUnit.WEEKS.between(startDate, currentDate) > 0) {
            startDate = startDate.plusWeeks(1);
        }
        result.add(0, new Title(startDate));
        for (int i = 0; i < displayableList.size(); i++) {
            Event event = (Event) displayableList.get(i);
            LocalDate localDate = event.getEventStart().toLocalDate();
            if (ChronoUnit.WEEKS.between(startDate, localDate) > 0) {
                while (ChronoUnit.WEEKS.between(startDate, localDate) > 0) {
                    startDate = startDate.plusWeeks(1);
                }
                result.add(new Title(startDate));
            }
            result.add(new DisplayablePair<>(event, i));
        }
        return result;
    }

    /**
     * Returns a processed Module list with Titles inserted.
     *
     * @param displayableList List of {@code Module} to process.
     * @return List with Titles inserted.
     */
    private ObservableList<Displayable> processModuleDisplayableList(ObservableList<Displayable> displayableList) {
        final ObservableList<Displayable> result = FXCollections.observableArrayList();
        if (displayableList.size() == 0) {
            return result;
        }
        AcademicYear currentAcademicYear = ((Module) displayableList.get(0)).getAcademicYear();
        result.add(0, new Title(currentAcademicYear));
        for (int i = 0; i < displayableList.size(); i++) {
            Module module = (Module) displayableList.get(i);
            AcademicYear academicYear = module.getAcademicYear();
            if (!academicYear.equals(currentAcademicYear)) {
                result.add(new Title(academicYear));
                currentAcademicYear = academicYear;
            }
            result.add(new DisplayablePair<>(module, i));
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
     * Returns the zero-based index of an event in the event list.
     *
     * @param event Event to find.
     * @return Index of the event.
     */
    public Index getEventIndex(Event event) {
        ObservableList<Displayable> listViewList = listView.getItems();
        int x = 0;
        for (Displayable displayable : listViewList) {
            if (displayable instanceof Title) {
                continue;
            }
            @SuppressWarnings("unchecked")
            DisplayablePair<Event, Integer> pair = (DisplayablePair<Event, Integer>) displayable;
            Event eventTwo = pair.getFirst();
            if (event.equals(eventTwo)) {
                return Index.fromZeroBased(x);
            }
            x++;
        }
        return null;
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
                DisplayablePair item = (DisplayablePair) listItem;
                Integer index = ((Integer) item.getSecond());
                if (item.getFirst() instanceof Event) {
                    Event event = (Event) item.getFirst();
                    setGraphic(new EventCard(event, index + 1,
                            mainWindow.getTagColourClass(event.getParentModule().getModuleCode())).getRoot());
                } else {
                    assert item.getFirst() instanceof Module;
                    Module module = (Module) item.getFirst();
                    setGraphic(new ModuleCard(module, index + 1,
                            mainWindow.getTagColourClass(module.getModuleCode())).getRoot());
                }
                setOnMouseClicked(event -> mainWindow.handleListClick(index));
                setDisable(false);
            } else if (listItem instanceof Title) {
                setGraphic(new TitleCard((Title) listItem).getRoot());
                setDisable(true);
            }
        }
    }
}
