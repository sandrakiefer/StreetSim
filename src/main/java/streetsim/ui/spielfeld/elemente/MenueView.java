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

        HBox auswahlAbschnitte = new HBox();
        Label abschnitte = new Label("Straßenabschnitte");
        GeradeView gerade = new GeradeView();
        KreuzungView kreuzung = new KreuzungView();
        KurveView kurve = new KurveView();
        TStueckView tstueck = new TStueckView();
        auswahlAbschnitte.getChildren().addAll(abschnitte, gerade, kreuzung, kurve, tstueck);
        auswahlAbschnitte.setAlignment(Pos.TOP_LEFT);

        VBox autoBox = new VBox();
        Label autos = new Label("Autos");
        autoBox.getChildren().add(autos);
        autoBox.getChildren().addAll(autoViews);
        autoBox.setAlignment(Pos.TOP_LEFT);
        ScrollPane auswahlAutos = new ScrollPane();
        auswahlAutos.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        auswahlAutos.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        auswahlAutos.setContent(autoBox);

        HBox auswahlAmpeln = new HBox();
        Label ampeln = new Label("Ampeln");
        AmpelView ampelView = new AmpelView();
        auswahlAmpeln.getChildren().addAll(ampeln, ampelView);
        auswahlAmpeln.setAlignment(Pos.TOP_LEFT);
        getChildren().addAll(auswahlAbschnitte, auswahlAutos, auswahlAmpeln);
        setAlignment(Pos.CENTER);

    }
}
