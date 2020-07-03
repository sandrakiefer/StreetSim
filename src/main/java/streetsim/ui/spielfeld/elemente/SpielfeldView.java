package streetsim.ui.spielfeld.elemente;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.util.Stack;

public class SpielfeldView extends StackPane {

    Pane abschnitte;
    Pane autosUndAmpeln;

    public SpielfeldView() {
        super();
        abschnitte = new Pane();
        autosUndAmpeln = new Pane();
        this.getChildren().addAll(abschnitte, autosUndAmpeln);

    }

    public void addAbschnitt(ImageView imageView) {
        Platform.runLater(() -> abschnitte.getChildren().addAll(imageView));
    }

    public void entferneAbschnitt(ImageView imageView) {
        Platform.runLater(() -> abschnitte.getChildren().remove(imageView));
    }

    public void addAmpelOderAuto(ImageView imageView) {
        Platform.runLater(() -> autosUndAmpeln.getChildren().addAll(imageView));
    }

    public void entferneAmpelOderAuto(ImageView imageView) {
        Platform.runLater(() -> autosUndAmpeln.getChildren().remove(imageView));
    }

    public void setBreite(double breite) {
        Platform.runLater(() -> {
            abschnitte.setPrefWidth(breite);
            autosUndAmpeln.setPrefWidth(breite);
        });
    }

    public void setHoehe(double hoehe) {
        Platform.runLater(() -> {
            abschnitte.setPrefHeight(hoehe);
            autosUndAmpeln.setPrefHeight(hoehe);
        });
    }

}
