package streetsim.ui.spielfeld.elemente;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.HBox;

class NavigationView extends HBox {

    Button startPause, beende;
    MenuButton entferne;
    MenuItem ampeln, autos, straßen, alles;

    NavigationView() {
        super();

        startPause = new Button();
        ampeln = new MenuItem("Ampeln zurücksetzen");
        autos = new MenuItem("Autos zurücksetzen");
        straßen = new MenuItem("Straßen zurücksetzen");
        alles = new MenuItem("Alles zurücksetzen");
        entferne = new MenuButton();
        entferne.getItems().addAll(ampeln, autos, straßen, alles);
        beende = new Button();

        //styling über css
        this.getChildren().addAll(startPause, entferne, beende);
        this.setAlignment(Pos.CENTER);
    }
}
