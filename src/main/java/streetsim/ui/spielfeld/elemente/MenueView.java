package streetsim.ui.spielfeld.elemente;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import streetsim.ui.spielfeld.elemente.strassenabschnitte.GeradeView;
import streetsim.ui.spielfeld.elemente.strassenabschnitte.KreuzungView;
import streetsim.ui.spielfeld.elemente.strassenabschnitte.KurveView;
import streetsim.ui.spielfeld.elemente.strassenabschnitte.TStueckView;

import java.util.List;

class MenueView extends VBox {

    GeradeView gerade;
    KreuzungView kreuzung;
    KurveView kurve;
    TStueckView tstueck;
    List<AutoView> autoViews;
    AmpelView ampelView;

    MenueView(List<AutoView> autoViews) {
        super();

        this.autoViews = autoViews;

        VBox auswahlAbschnitte = new VBox();
        Label abschnitte = new Label("Straßenabschnitte");
        VBox abschnittBox = new VBox();
        gerade = new GeradeView();
        kreuzung = new KreuzungView();
        kurve = new KurveView();
        tstueck = new TStueckView();
        abschnittBox.getChildren().addAll(gerade, kreuzung, kurve, tstueck);
        auswahlAbschnitte.getChildren().addAll(abschnitte, abschnittBox);
        auswahlAbschnitte.setAlignment(Pos.TOP_LEFT);
        auswahlAbschnitte.setSpacing(10);


        VBox autoAuswahl = new VBox();
        Label autos = new Label("Autos");
        HBox autoBox = new HBox();
        autoBox.getChildren().add(autos);
        autoBox.getChildren().addAll(autoViews);
        autoBox.setAlignment(Pos.TOP_LEFT);
        autoBox.getStyleClass().add("menu");
        ScrollPane auswahlAutos = new ScrollPane();
        auswahlAutos.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        auswahlAutos.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        auswahlAutos.setContent(autoBox);
        autoAuswahl.getStyleClass().add("menu");
        autoAuswahl.getChildren().addAll(autos, auswahlAutos);

        VBox auswahlAmpeln = new VBox();
        Label ampeln = new Label("Ampeln");
        ampelView = new AmpelView();
        auswahlAmpeln.getChildren().addAll(ampeln, ampelView);
        auswahlAmpeln.setAlignment(Pos.TOP_LEFT);
        getChildren().addAll(auswahlAbschnitte, autoAuswahl, auswahlAmpeln);
        setAlignment(Pos.TOP_RIGHT);
        getStyleClass().add("menu");
        setSpacing(10);

    }
}
