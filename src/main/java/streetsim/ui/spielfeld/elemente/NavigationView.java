package streetsim.ui.spielfeld.elemente;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.HBox;
import streetsim.ui.utils.StyleAssist;

class NavigationView extends HBox {

    Button startPause, beende;
    MenuItem ampeln, autos, strassen, alles, speichern, speichernUnter;
    MenuButton entferne;

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

        MenuButton speichernButton = new MenuButton();
        speichernButton.setId("save");
        speichernButton.setPickOnBounds(true);

        speichern = new MenuItem("Speichern");
        speichernUnter = new MenuItem("Speichern unter");
        speichernButton.getItems().addAll(speichern, speichernUnter);

        beende = new Button();
        beende.setId("exit");
        beende.setPickOnBounds(true);

        StyleAssist.getInstance().wendeCSSKlassenAn("navbtn", startPause, speichernButton, beende, entferne);

        this.setId("abstand");
        HBox hintergrund = new HBox();
        hintergrund.getChildren().addAll(startPause, entferne, speichernButton, beende);
        hintergrund.setMaxSize(this.getWidth(), startPause.getHeight());
        hintergrund.setId("navbtn-hintergrund");
        this.getChildren().addAll(hintergrund);
        this.setAlignment(Pos.BOTTOM_RIGHT);
    }
}
