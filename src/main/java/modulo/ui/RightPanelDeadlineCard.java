package modulo.ui;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import modulo.model.deadline.Deadline;

/**
 * An UI component that displays information of a {@code Deadline}.
 */
public class RightPanelDeadlineCard extends UiPart<Region> {

    private static final String FXML = "RightPanelDeadlineCard.fxml";

    public final Deadline deadline;

    @FXML
    private HBox deadlineCardPane;

    @FXML
    private CheckBox isCompleted;

    @FXML
    private Label name;


    public RightPanelDeadlineCard(Deadline deadline, int displayedIndex, MainWindow mainWindow) {
        super(FXML);
        this.deadline = deadline;
        name.setText(displayedIndex + ". " + deadline.getName().fullName);
        isCompleted.setSelected(deadline.isCompleted());
        isCompleted.setOnAction(e -> {
            mainWindow.handleDeadlineClick(displayedIndex);
            isCompleted.setSelected(deadline.isCompleted());
        });
    }

    public void updateStatus() {
        isCompleted.setSelected(deadline.isCompleted());
    }
}

