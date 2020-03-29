package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.deadline.Deadline;

/**
 * An UI component that displays information of a {@code Deadline}.
 */
public class DeadlineCard extends UiPart<Region> {

    private static final String FXML = "DeadlineListCard.fxml";

    public final Deadline deadline;

    @FXML
    private HBox deadlineCardPane;

    @FXML
    private CheckBox isCompleted;

    @FXML
    private Label name;


    public DeadlineCard(Deadline deadline, int displayedIndex, MainWindow mainWindow) {
        super(FXML);
        this.deadline = deadline;
        name.setText(deadline.getName().fullName);
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

