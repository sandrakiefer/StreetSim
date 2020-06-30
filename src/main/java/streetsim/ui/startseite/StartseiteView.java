package streetsim.ui.startseite;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

public class StartseiteView extends BorderPane {

    Button fortfahren, starten, laden;
    StackPane kontrollPane;
    HBox fortfahrPane, startPane;

    StartseiteView(){
        super();

        // OBERER TEIL
        Image logo = new Image(getClass().getResourceAsStream("/assets/logo.png"));
        ImageView logoAnsicht = new ImageView(logo);
        VBox logoPane = new VBox();
        logoPane.getChildren().add(logoAnsicht);
        logoPane.setAlignment(Pos.CENTER);

        // UNTERER TEIL
        fortfahren = new Button("Drücke eine beliebige Taste um fortzufahren");
        fortfahrPane = new HBox();
        fortfahrPane.setAlignment(Pos.CENTER);
        fortfahrPane.getChildren().add(fortfahren);

        starten = new Button("Neues Spiel starten");
        laden = new Button("Spiel laden");
        startPane = new HBox();
        startPane.getChildren().addAll(starten, laden);
        startPane.setAlignment(Pos.CENTER);

        kontrollPane = new StackPane();
        startPane.setOpacity(0);
        kontrollPane.getChildren().addAll(startPane, kontrollPane);


        // GESAMT
        Image hintegrund = new Image(getClass().getResourceAsStream("/assets/bg.jpg"));
        BackgroundSize bSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false);
        setBackground(new Background(new BackgroundImage(hintegrund, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, bSize)));
        setCenter(logoAnsicht);
        setBottom(kontrollPane);
    }


}
