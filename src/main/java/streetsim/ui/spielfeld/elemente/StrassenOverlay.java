package streetsim.ui.spielfeld.elemente;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import streetsim.business.Ampel;
import streetsim.business.Strassenabschnitt;
import streetsim.ui.utils.ResourceAssist;
import streetsim.ui.utils.StyleAssist;

class StrassenOverlay extends Pane {

    private static final double BUTTON_SIZE = Ampel.HOEHE;
    private static final double OVERLAY_GROESSE = Strassenabschnitt.GROESSE;

    Button loesche, rotiere, deaktiviereAmpeln;

    StrassenOverlay(){
        super();
        setPrefSize(OVERLAY_GROESSE, OVERLAY_GROESSE);

        loesche = new Button();
        ImageView loescheView = new ImageView(new Image(ResourceAssist.getInstance().holeRessourceAusOrdnern("assets", "icons", "loeschen.png")));
        loescheView.setFitWidth(BUTTON_SIZE);
        loescheView.setFitHeight(BUTTON_SIZE);
        loesche.setGraphic(loescheView);
        loesche.setPadding(Insets.EMPTY);
        loesche.setPrefSize(BUTTON_SIZE, BUTTON_SIZE);

        rotiere = new Button();
        ImageView rotiereView = new ImageView(new Image(ResourceAssist.getInstance().holeRessourceAusOrdnern("assets", "icons", "rotieren.png")));
        rotiereView.setFitWidth(BUTTON_SIZE);
        rotiereView.setFitHeight(BUTTON_SIZE);
        rotiere.setGraphic(rotiereView);
        rotiere.setPadding(Insets.EMPTY);
        rotiere.setPrefSize(BUTTON_SIZE, BUTTON_SIZE);

        deaktiviereAmpeln = new Button();
        ImageView deaktView = new ImageView(new Image(ResourceAssist.getInstance().holeRessourceAusOrdnern("assets", "icons", "ampelweg.png")));
        deaktView.setFitWidth(BUTTON_SIZE);
        deaktView.setFitHeight(BUTTON_SIZE);
        deaktiviereAmpeln.setGraphic(deaktView);
        deaktiviereAmpeln.setPadding(Insets.EMPTY);
        deaktiviereAmpeln.setPrefSize(BUTTON_SIZE, BUTTON_SIZE);


        getChildren().addAll(loesche, rotiere, deaktiviereAmpeln);

    }

    void setPosition(double posX, double posY) {
        setLayoutX(posX);
        setLayoutY(posY);
        loesche.setLayoutX(posX);
        loesche.setLayoutY(posY);
        rotiere.setLayoutX(posX + OVERLAY_GROESSE - BUTTON_SIZE);
        rotiere.setLayoutY(posY);
        deaktiviereAmpeln.setLayoutX(posX + OVERLAY_GROESSE - BUTTON_SIZE);
        deaktiviereAmpeln.setLayoutY(posY + OVERLAY_GROESSE - BUTTON_SIZE);
    }

}
