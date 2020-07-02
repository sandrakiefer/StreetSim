package streetsim.ui.startseite;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import streetsim.ui.utils.ResourceAssist;

public class StartseiteView extends BorderPane {

    final Button fortfahren, starten, laden;
    final StackPane kontrollPane;
    final VBox fortfahrPane, startPane;

    StartseiteView(){
        super();

        // Erste Ansicht
        fortfahren = new Button("Drücke eine beliebige Taste um fortzufahren");
        fortfahrPane = new VBox();
        fortfahrPane.setAlignment(Pos.CENTER);
        fortfahrPane.getChildren().add(fortfahren);

        // Zweite Ansicht
        starten = new Button("Neues Spiel starten");
        laden = new Button("Spiel laden");
        startPane = new VBox();
        startPane.getChildren().addAll(starten, laden);
        startPane.setAlignment(Pos.CENTER);

        // Fläche für beide Ansichten (auswechselbar)
        kontrollPane = new StackPane();
        startPane.setOpacity(0);
        kontrollPane.getChildren().addAll(startPane, fortfahrPane);
        kontrollPane.setAlignment(Pos.TOP_CENTER);

        // GESAMT
        ResourceAssist resourceAssist = ResourceAssist.getInstance();
        System.out.println(resourceAssist.holeRessourceAusOrdnern("assets", "bilder", "bg.jpg"));
        Image hintergrund = new Image(resourceAssist.holeRessourceAusOrdnern("assets", "bilder", "bg.jpg"));
        BackgroundSize bSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false);
        setBackground(new Background(new BackgroundImage(hintergrund, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, bSize)));
        setCenter(kontrollPane);
    }


}
