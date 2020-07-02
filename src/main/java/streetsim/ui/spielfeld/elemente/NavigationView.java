package streetsim.ui.spielfeld.elemente;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class NavigationView extends HBox {

    Button startPause, entferne, beende;

    public NavigationView() {
        super();

        startPause = new Button();
        entferne = new Button();
        beende = new Button();

        //styling über css
        this.getChildren().addAll(startPause, entferne, beende);
        this.setAlignment(Pos.CENTER);
    }
}
