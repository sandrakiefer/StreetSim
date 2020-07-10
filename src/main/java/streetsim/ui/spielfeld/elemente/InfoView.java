package streetsim.ui.spielfeld.elemente;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

class InfoView extends HBox {

    final Label nachricht;

    InfoView() {
        super();
        this.nachricht = new Label();
        nachricht.setStyle("-fx-text-fill: #FFFFFF; -fx-font-weight: bold;");
        getChildren().add(this.nachricht);
        this.setStyle("-fx-background-color: #bf0000; -fx-padding: 5;");
    }
}
