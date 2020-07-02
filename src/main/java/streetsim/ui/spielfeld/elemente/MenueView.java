package streetsim.ui.spielfeld.elemente;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import streetsim.ui.spielfeld.elemente.straßenabschnitte.GeradeView;
import streetsim.ui.spielfeld.elemente.straßenabschnitte.KreuzungView;
import streetsim.ui.spielfeld.elemente.straßenabschnitte.KurveView;
import streetsim.ui.spielfeld.elemente.straßenabschnitte.TStueckView;

import java.util.List;

class MenueView extends VBox {

    MenueView(List<AutoView> autoViews){
        super();

        VBox auswahlAbschnitte = new VBox();
        Label abschnitte = new Label("Straßenabschnitte");
        VBox abschnittBox = new VBox();
        GeradeView gerade = new GeradeView();
        KreuzungView kreuzung = new KreuzungView();
        KurveView kurve = new KurveView();
        TStueckView tstueck = new TStueckView();
        abschnittBox.getChildren().addAll(gerade, kreuzung, kurve, tstueck);
        auswahlAbschnitte.getChildren().addAll(abschnitte, abschnittBox);
        auswahlAbschnitte.setAlignment(Pos.TOP_LEFT);

        HBox autoAuswahl = new HBox();
        Label autos = new Label("Autos");
        HBox autoBox = new HBox();
        autoBox.getChildren().add(autos);
        autoBox.getChildren().addAll(autoViews);
        autoBox.setAlignment(Pos.TOP_LEFT);
        ScrollPane auswahlAutos = new ScrollPane();
        auswahlAutos.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        auswahlAutos.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        auswahlAutos.setContent(autoBox);
        autoAuswahl.getChildren().addAll(autos, auswahlAutos);

        VBox auswahlAmpeln = new VBox();
        Label ampeln = new Label("Ampeln");
        AmpelView ampelView = new AmpelView();
        auswahlAmpeln.getChildren().addAll(ampeln, ampelView);
        auswahlAmpeln.setAlignment(Pos.TOP_LEFT);
        getChildren().addAll(auswahlAbschnitte, autoAuswahl, auswahlAmpeln);
        setAlignment(Pos.TOP_CENTER);

    }
}
