package streetsim.ui.spielfeld.elemente;

import javafx.collections.MapChangeListener;
import javafx.collections.ObservableMap;
import javafx.scene.input.Dragboard;
import javafx.scene.layout.StackPane;
import streetsim.business.Auto;
import streetsim.business.Position;
import streetsim.business.Strassenabschnitt;
import streetsim.business.Strassennetz;
import streetsim.ui.AbstractController;
import streetsim.ui.StreetSimApp;

import javax.swing.text.html.ImageView;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 * Verwaltung von Aktionen auf dem Spielfeld
 */
public class SpielfeldController extends AbstractController<StreetSimApp> {

    ObservableMap<Position, Strassenabschnitt> abschnitte;
    ObservableMap<Position, ArrayList<Auto>> autos;
    List<ImageView> alleAbschnitte;

    public SpielfeldController(Strassennetz netz, StreetSimApp app) {
        super(netz, app);

        rootView = new StackPane();

        abschnitte = netz.getAbschnitte();
        autos = netz.getAutos();

        alleAbschnitte = new LinkedList<>();
    }

    @Override
    public void handlerAnmelden() {
        abschnitte.addListener((MapChangeListener<Position, Strassenabschnitt>) change -> {
            if (change.wasAdded()) {
                //TODO: do stuff
            } else if (change.wasRemoved()) {
                //TODO: do stuff
            }
        });

        rootView.setOnDragOver(event -> {
            boolean dropSupported = true;
            Dragboard dragboard = event.getDragboard();

            if(!dragboard.hasImage()){
                dropSupported = false;
            } else {
                if(dragboard.getImage()){

                }
            }
        });

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
     * @param s Strassenabschnitteå
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
