package streetsim.ui.spielfeld.elemente;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class MenueView extends Pane {

    Button aufklappen;

    public MenueView(){
        super();
        VBox menueBox = new VBox();

        HBox auswahlAbschnitte = new HBox();
        HBox auswahlAutos = new HBox();
        HBox auswahlAmpeln = new HBox();

        aufklappen = new Button();

        Label abschnitte = new Label("Straßenabschnitte");
        Label autos = new Label("Autos");
        Label ampeln = new Label("Ampeln");

        auswahlAbschnitte.getChildren().addAll(aufklappen, abschnitte);
        auswahlAutos.getChildren().addAll(aufklappen, autos);
        auswahlAmpeln.getChildren().addAll(aufklappen, ampeln);

        auswahlAbschnitte.setAlignment(Pos.TOP_LEFT);
        auswahlAutos.setAlignment(Pos.TOP_LEFT);
        auswahlAmpeln.setAlignment(Pos.TOP_LEFT);

        menueBox.getChildren().addAll(auswahlAbschnitte, auswahlAutos, auswahlAmpeln);
        menueBox.setAlignment(Pos.CENTER);

        getChildren().addAll(menueBox);
    }
}
