package streetsim.ui.spielfeld.elemente;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import streetsim.business.Ampel;
import streetsim.ui.utils.ResourceAssist;

import java.awt.*;

public class AutoOverlay extends Pane {

    private static final double BUTTON_SIZE = Ampel.HOEHE;

    Button loesche;
    MenuButton geschwindigkeit;
    MenuItem slider;

    AutoOverlay(){
        super();

        loesche = new Button();
        ImageView loescheView = new ImageView(new Image(ResourceAssist.getInstance().holeRessourceAusOrdnern("assets", "icons", "loeschen.png")));
        loescheView.setFitWidth(BUTTON_SIZE);
        loescheView.setFitHeight(BUTTON_SIZE);
        loesche.setGraphic(loescheView);
        loesche.setPadding(Insets.EMPTY);
        loesche.setPrefSize(BUTTON_SIZE, BUTTON_SIZE);

//        geschwindigkeit = new MenuButton();
//        ImageView geschwindigkeitView = new ImageView(new Image(ResourceAssist.getInstance().holeRessourceAusOrdnern("assets", "icons", "loeschen.png")));

        getChildren().addAll(loesche);

    }

    void setPosition(double posX, double posY) {
        setLayoutX(posX);
        setLayoutY(posY);
        loesche.setLayoutX(posX);
        loesche.setLayoutY(posY);
    }
}
