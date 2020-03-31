package modulo.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

/**
 * An UI component that displays information of a {@code Title}.
 */
public class TitleCard extends UiPart<Region> {

    private static final String FXML = "TitleListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX. As a consequence, UI
     * elements' variable names cannot be set to such keywords or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Title title;

    @FXML
    private HBox titleCardPane;
    @FXML
    private Label name;

    public TitleCard(Title title) {
        super(FXML);
        this.title = title;
        name.setText(title.getTitle());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TitleCard)) {
            return false;
        }

        // state check
        TitleCard card = (TitleCard) other;
        return title.getTitle().equals(card.title.getTitle());
    }
}
