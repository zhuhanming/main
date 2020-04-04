package modulo.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import modulo.commons.core.LogsCenter;

/**
 * Controller for a description page
 */
public class DescriptionWindow extends UiPart<Stage> {

    private static final Logger logger = LogsCenter.getLogger(DescriptionWindow.class);
    private static final String FXML = "DescriptionWindow.fxml";

    @FXML
    private Label description;

    @FXML
    private Label moduleName;

    /**
     * Creates a new DescriptionWindow.
     *
     * @param root        Stage to use as the root of the DescriptionWindow.
     * @param description Description to display.
     */
    public DescriptionWindow(Stage root, String description, String moduleName) {
        super(FXML, root);
        this.moduleName.setText(moduleName);
        this.moduleName.setWrapText(true);
        this.moduleName.setMaxWidth(600.0);
        this.description.setText(description);
        this.description.setWrapText(true);
        this.description.setMaxWidth(600.0);
    }

    /**
     * Creates a new empty DescriptionWindow.
     */
    public DescriptionWindow() {
        this(new Stage(), "", "");
    }

    /**
     * Creates a new DescriptionWindow with a description.
     */
    public DescriptionWindow(String description, String moduleName) {
        this(new Stage(), description, moduleName);
    }

    /**
     * Shows the help window.
     *
     * @throws IllegalStateException <ul>
     *                               <li>
     *                               if this method is called on a thread other than the JavaFX
     *                               Application Thread.
     *                               </li>
     *                               <li>
     *                               if this method is called during animation or layout processing.
     *                               </li>
     *                               <li>
     *                               if this method is called on the primary stage.
     *                               </li>
     *                               <li>
     *                               if {@code dialogStage} is already showing.
     *                               </li>
     *                               </ul>
     */
    public void show() {
        logger.fine("Showing help page about the application.");
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Returns true if the help window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the help window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the help window.
     */
    public void focus() {
        getRoot().requestFocus();
    }
}
