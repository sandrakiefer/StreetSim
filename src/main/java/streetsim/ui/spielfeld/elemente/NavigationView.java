package streetsim.ui.spielfeld.elemente;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class NavigationView extends Pane {

    Button startPause, entferne, beende;

    public NavigationView() {
        super();
        VBox navBox = new VBox();

        HBox box = new HBox();
        startPause = new Button();
        entferne = new Button();
        beende = new Button();

        //styling über css
        box.getChildren().addAll(startPause, entferne, beende);
        box.setAlignment(Pos.CENTER);

        navBox.getChildren().addAll(box);
        navBox.setAlignment(Pos.BOTTOM_CENTER);

        getChildren().addAll(navBox);
    }
}
