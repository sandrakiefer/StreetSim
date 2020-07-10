package streetsim.ui.spielfeld.elemente;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

class SpielfeldView extends StackPane {

    final Pane abschnitte, autosUndAmpeln;
    final Label name;

    SpielfeldView() {
        super();
        abschnitte = new Pane();
        autosUndAmpeln = new Pane();
        name = new Label();
        name.setStyle("-fx-background-color: #c6ccc7; -fx-background-radius: 5; -fx-padding: 5; -fx-font-weight: bold; -fx-font-size: 14pt;");
        SpielfeldView.setMargin(name, new Insets(10, 0, 0, 10));
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
