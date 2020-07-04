package streetsim.ui.spielfeld.elemente;

import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableMap;
import javafx.scene.image.ImageView;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import streetsim.business.*;
import streetsim.business.abschnitte.Gerade;
import streetsim.business.abschnitte.Kreuzung;
import streetsim.business.abschnitte.Kurve;
import streetsim.business.abschnitte.TStueck;
import streetsim.ui.AbstractController;
import streetsim.ui.StreetSimApp;
import streetsim.ui.spielfeld.elemente.straßenabschnitte.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Verwaltung von Aktionen auf dem Spielfeld
 */
public class SpielfeldController extends AbstractController<StreetSimApp> {

    Map<Strassenabschnitt, StrassenController> strassenController;
    Map<Auto, AutoController> autoController;

    ObservableMap<Position, Strassenabschnitt> abschnitte;
    ObservableMap<Position, ArrayList<Auto>> autos;
    List<ImageView> alleAbschnitte;

    public SpielfeldController(Strassennetz netz, StreetSimApp app) {
        super(netz, app);

        rootView = new SpielfeldView();

        strassenController = new HashMap<>();
        autoController = new HashMap<>();

        abschnitte = netz.getAbschnitte();
        autos = netz.getAutos();

        alleAbschnitte = new LinkedList<>();
        handlerAnmelden();
    }

    @Override
    public void handlerAnmelden() {
        abschnitte.addListener((MapChangeListener<Position, Strassenabschnitt>) change -> {
            if (change.wasAdded()) {
                Strassenabschnitt s = change.getValueAdded();
                ImageView strassenView;

                if (s instanceof Gerade) strassenView = new GeradeView();
                else if (s instanceof Kreuzung) strassenView = new KreuzungView();
                else if (s instanceof Kurve) strassenView = new KurveView();
                else strassenView = new TStueckView();

                Platform.runLater(() -> {
                    strassenView.setLayoutX(s.getPositionX());
                    strassenView.setLayoutY(s.getPositionY());
                });

                StrassenController sc = new StrassenController(s, strassenView);
                strassenController.put(s, sc);
                ((SpielfeldView) rootView).addAbschnitt(strassenView);
            } else if (change.wasRemoved()) {
                //TODO: do stuff
            }
        });

        app.getHauptStage().widthProperty().addListener(c -> ((SpielfeldView)rootView).setBreite(app.getHauptStage().getWidth()));

        app.getHauptStage().heightProperty().addListener(c -> ((SpielfeldView)rootView).setHoehe(app.getHauptStage().getHeight()));

    }

    /**
     * Auto einem Strassenabschnitt hinzufügen
     *
     * @param a Auto
     */
    public void autoAdden(Auto a) {

    }

    /**
     * Ampeln auf gegebenen Strassenabschnitt aktivieren
     *
     * @param s Strassenabschnitt
     */
    public void ampelnAktivieren(Strassenabschnitt s) {

    }

    /**
     * Strass dem Strassennetz hinzufügen
     *
     * @param s Strassenabschnitt
     */
    public void strasseAdden(Strassenabschnitt s) {

    }

    /**
     * beliebig viele Autos vom Strassennetz entfernen
     *
     * @param a Autos
     */
    public void entfAuto(Auto[] a) {

    }

    /**
     * Ampeln auf Strassnabschnitt deaktivieren
     *
     * @param s Strassenabschnitt
     */
    public void ampelnDeaktivierenAn(Strassenabschnitt s) {

    }

    /**
     * beliebig viele Strassenabschnitte vom Strassennetz entfernen
     *
     * @param s Strassenabschnitte
     */
    public void entfStrasse(Strassenabschnitt[] s) {

    }

    /**
     * bewegt beliebig viele Strassenabschnitte in X und Y Richtung
     *
     * @param s    Strassenabschnitteå
     * @param offX X-Koordinate
     * @param offY Y-Koordinate
     */
    public void bewegeStrasse(Strassenabschnitt[] s, int offX, int offY) {

    }

    /**
     * rotiert Strassenabschnitt
     *
     * @param s Strassenabschnitt
     */
    public void rotiereStrasse(Strassenabschnitt s) {

    }

    /**
     * passt die Geschwindigkeit der Autos an
     *
     * @param geschwindigkeit Geschwindigkeit
     */
    public void geschwindigkeitAnpassen(int geschwindigkeit) {

    }

    /**
     * überprüft ob an der gegeben Position bereits etwas platziert ist
     *
     * @param x X-Koordinate
     * @param y Y-Koordinate
     */
    public void posBelegt(int x, int y) {

    }

}
