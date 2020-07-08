package streetsim.ui.spielfeld.elemente;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class QuitPopUpView extends VBox {

    Button speichern, verwerfen, abbrechen;

    QuitPopUpView(){
        super();

        Label info = new Label("Du hast ungespeicherte Änderungen. Alle ungespeicherten Änderungen gehen verloren.");

        HBox btnBox = new HBox();
        speichern = new Button("Speichern");

        verwerfen = new Button("Verwerfen");

        abbrechen = new Button("Abbrechen");

        btnBox.getChildren().addAll(speichern, verwerfen, abbrechen);

        getChildren().addAll(info, btnBox);
    }

}
