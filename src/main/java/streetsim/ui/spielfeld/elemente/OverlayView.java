package streetsim.ui.spielfeld.elemente;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import streetsim.business.Ampel;
import streetsim.business.Strassenabschnitt;
import streetsim.ui.utils.ResourceAssist;
import streetsim.ui.utils.StyleAssist;

class OverlayView extends Pane {

    private static final double BUTTON_SIZE = Ampel.HOEHE;
    private static final double OVERLAY_GROESSE = Strassenabschnitt.GROESSE;

    Button loescheStrasse, rotiereStrasse, deaktiviereAmpeln, loescheAuto;
    MenuButton geschwindigkeit;
    Slider speed;
    CustomMenuItem menuItem;

    OverlayView(){
        super();
        setPrefSize(OVERLAY_GROESSE, OVERLAY_GROESSE);

        loescheStrasse = new Button();
        ImageView loescheView = new ImageView(new Image(ResourceAssist.getInstance().holeRessourceAusOrdnern("assets", "icons", "loeschen.png")));
        loescheView.setFitWidth(BUTTON_SIZE);
        loescheView.setFitHeight(BUTTON_SIZE);
        loescheStrasse.setGraphic(loescheView);
        loescheStrasse.setPadding(Insets.EMPTY);
        loescheStrasse.setPrefSize(BUTTON_SIZE, BUTTON_SIZE);

        rotiereStrasse = new Button();
        ImageView rotiereView = new ImageView(new Image(ResourceAssist.getInstance().holeRessourceAusOrdnern("assets", "icons", "rotieren.png")));
        rotiereView.setFitWidth(BUTTON_SIZE);
        rotiereView.setFitHeight(BUTTON_SIZE);
        rotiereStrasse.setGraphic(rotiereView);
        rotiereStrasse.setPadding(Insets.EMPTY);
        rotiereStrasse.setPrefSize(BUTTON_SIZE, BUTTON_SIZE);

        deaktiviereAmpeln = new Button();
        ImageView deaktView = new ImageView(new Image(ResourceAssist.getInstance().holeRessourceAusOrdnern("assets", "icons", "ampelweg.png")));
        deaktView.setFitWidth(BUTTON_SIZE);
        deaktView.setFitHeight(BUTTON_SIZE);
        deaktiviereAmpeln.setGraphic(deaktView);
        deaktiviereAmpeln.setPadding(Insets.EMPTY);
        deaktiviereAmpeln.setPrefSize(BUTTON_SIZE, BUTTON_SIZE);

        loescheAuto = new Button();
        ImageView loescheView2 = new ImageView(new Image(ResourceAssist.getInstance().holeRessourceAusOrdnern("assets", "icons", "loeschen.png")));
        loescheView2.setFitWidth(BUTTON_SIZE);
        loescheView2.setFitHeight(BUTTON_SIZE);
        loescheAuto.setGraphic(loescheView2);
        loescheAuto.setPadding(Insets.EMPTY);
        loescheAuto.setPrefSize(BUTTON_SIZE, BUTTON_SIZE);

        geschwindigkeit = new MenuButton();
        ImageView geschwView = new ImageView(new Image(ResourceAssist.getInstance().holeRessourceAusOrdnern("assets", "icons", "geschwindigkeit.png")));
        geschwView.setFitWidth(BUTTON_SIZE);
        geschwView.setFitHeight(BUTTON_SIZE);
        geschwindigkeit.setGraphic(geschwView);
        geschwindigkeit.setPadding(Insets.EMPTY);
        geschwindigkeit.setPickOnBounds(true);
        geschwindigkeit.setPrefSize(BUTTON_SIZE, BUTTON_SIZE);

        menuItem = new CustomMenuItem(speed);
        menuItem.setHideOnClick(false);
        geschwindigkeit.getItems().addAll(menuItem);


        getChildren().addAll(loescheStrasse, rotiereStrasse, deaktiviereAmpeln, loescheAuto, geschwindigkeit);

    }

    void setPosition(double posX, double posY) {
        setLayoutX(posX);
        setLayoutY(posY);
        loescheStrasse.setLayoutX(posX);
        loescheStrasse.setLayoutY(posY);
        rotiereStrasse.setLayoutX(posX + OVERLAY_GROESSE - BUTTON_SIZE);
        rotiereStrasse.setLayoutY(posY);
        deaktiviereAmpeln.setLayoutX(posX + OVERLAY_GROESSE - BUTTON_SIZE);
        deaktiviereAmpeln.setLayoutY(posY + OVERLAY_GROESSE - BUTTON_SIZE);
    }

    void setAutoPos(double x, double y){
        setLayoutX(x);
        setLayoutY(y);
        loescheAuto.setLayoutX(x);
        loescheAuto.setLayoutY(y);
        geschwindigkeit.setLayoutX(x + BUTTON_SIZE);
        geschwindigkeit.setLayoutY(y);

    }

}
