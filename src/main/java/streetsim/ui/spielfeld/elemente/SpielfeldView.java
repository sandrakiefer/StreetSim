package streetsim.ui.spielfeld.elemente;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

class SpielfeldView extends StackPane {

    Pane abschnitte;
    Pane autosUndAmpeln;
    Label name;

    SpielfeldView() {
        super();
        abschnitte = new Pane();
        autosUndAmpeln = new Pane();
        name = new Label();
        name.setAlignment(Pos.TOP_LEFT);
        setAlignment(Pos.TOP_LEFT);
        this.getChildren().addAll(abschnitte, autosUndAmpeln, name);

    }

    void addAbschnitt(ImageView imageView) {
        abschnitte.getChildren().addAll(imageView);
    }

    void entferneAbschnitt(ImageView imageView) {
        abschnitte.getChildren().remove(imageView);
    }

    void addAmpelOderAuto(ImageView imageView) {
        autosUndAmpeln.getChildren().addAll(imageView);
    }

    void entferneAmpelOderAuto(ImageView imageView) {
        autosUndAmpeln.getChildren().remove(imageView);
    }

    void setBreite(double breite) {
        Platform.runLater(() -> {
            abschnitte.setPrefWidth(breite);
            autosUndAmpeln.setPrefWidth(breite);
        });
    }

    void setHoehe(double hoehe) {
        Platform.runLater(() -> {
            abschnitte.setPrefHeight(hoehe);
            autosUndAmpeln.setPrefHeight(hoehe);
        });
    }

}
