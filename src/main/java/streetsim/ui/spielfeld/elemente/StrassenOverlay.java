package streetsim.ui.spielfeld.elemente;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import streetsim.business.Ampel;
import streetsim.business.Strassenabschnitt;
import streetsim.ui.utils.ResourceAssist;

class StrassenOverlay extends Pane {

    private static final double BUTTON_SIZE = Ampel.HOEHE;
    private static final double OVERLAY_GROESSE = Strassenabschnitt.GROESSE;

    Button loesche, rotiere, deaktiviereAmpeln;

    StrassenOverlay(){
        super();
        setPrefSize(OVERLAY_GROESSE, OVERLAY_GROESSE);

        loesche = new Button();
        loesche.setGraphic(new ImageView(new Image(ResourceAssist.getInstance().holeRessourceAusOrdnern("assets", "icons", "loeschen.png"))));
        loesche.setPrefSize(BUTTON_SIZE, BUTTON_SIZE);

        rotiere = new Button();
        rotiere.setGraphic(new ImageView(new Image(ResourceAssist.getInstance().holeRessourceAusOrdnern("assets", "icons", "rotieren.png"))));
        rotiere.setPrefSize(BUTTON_SIZE, BUTTON_SIZE);

        deaktiviereAmpeln = new Button();
        deaktiviereAmpeln.setGraphic(new ImageView(new Image(ResourceAssist.getInstance().holeRessourceAusOrdnern("assets", "icons", "ampelweg.png"))));
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
