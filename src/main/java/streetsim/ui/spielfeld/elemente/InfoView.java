package streetsim.ui.spielfeld.elemente;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

class InfoView extends HBox {

    Label nachricht;

    public InfoView() {
        super();
        this.nachricht = new Label();
        getChildren().add(this.nachricht);
    }
}
