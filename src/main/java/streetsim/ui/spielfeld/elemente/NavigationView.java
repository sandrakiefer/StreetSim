package streetsim.ui.spielfeld.elemente;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import streetsim.ui.utils.StyleAssist;

class NavigationView extends HBox {

    Button startPause, speichern, beende;
    MenuButton entferne;
    MenuItem ampeln, autos, strassen, alles;

    NavigationView() {
        super();

        startPause = new Button();
        startPause.setId("play");
        startPause.setPickOnBounds(true);

        ampeln = new MenuItem("Ampeln zurücksetzen");
        autos = new MenuItem("Autos zurücksetzen");
        strassen = new MenuItem("Straßen zurücksetzen");
        alles = new MenuItem("Alles zurücksetzen");
        entferne = new MenuButton();
        entferne.getItems().addAll(ampeln, autos, strassen, alles);
        entferne.setId("delete");
        entferne.setPickOnBounds(true);

        speichern = new Button();
        speichern.setId("save");
        speichern.setPickOnBounds(true);

        beende = new Button();
        beende.setId("exit");
        beende.setPickOnBounds(true);

        StyleAssist.getInstance().wendeCSSKlassenAn("navbtn", startPause, speichern, beende, entferne);

        this.getChildren().addAll(startPause, entferne, speichern, beende);
        this.setAlignment(Pos.BOTTOM_RIGHT);
    }
}
