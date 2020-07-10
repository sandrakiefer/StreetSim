package streetsim.ui.spielfeld.elemente;

import javafx.geometry.Pos;
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
        speichern.setStyle("-fx-background-color: #397542; -fx-text-fill: #FFFFFF; -fx-font-weight: bold; -fx-border-radius: 5;");
        verwerfen = new Button("Verwerfen");
        verwerfen.setStyle("-fx-background-color: #bf0000; -fx-text-fill: #FFFFFF; -fx-font-weight: bold; -fx-border-radius: 5;");
        abbrechen = new Button("Abbrechen");
        abbrechen.setStyle("-fx-background-color: #2f6cb9; -fx-text-fill: #FFFFFF; -fx-font-weight: bold; -fx-border-radius: 5;");

        btnBox.getChildren().addAll(speichern, verwerfen, abbrechen);
        btnBox.setAlignment(Pos.CENTER);
        btnBox.setStyle("-fx-padding: 15; -fx-spacing: 10;");

        getChildren().addAll(info, btnBox);
        this.setStyle("-fx-padding: 30; -fx-background-color: #c6ccc7;");

    }

}
