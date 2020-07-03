package streetsim.ui.spielfeld.elemente;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.util.Stack;

public class SpielfeldView extends StackPane {

    Pane abschnitte;
    Pane autosUndAmpeln;

    public SpielfeldView(){
        super();
        abschnitte = new Pane();
        autosUndAmpeln = new Pane();

        this.getChildren().addAll(abschnitte, autosUndAmpeln);

    }

    public void addAbschnitt(ImageView imageView){
        abschnitte.getChildren().addAll(imageView);

    }
    public void addAmpelOderAuto(ImageView imageView){
        autosUndAmpeln.getChildren().addAll(imageView);
    }
}
