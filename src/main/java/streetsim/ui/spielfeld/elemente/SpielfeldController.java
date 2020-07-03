package streetsim.ui.spielfeld.elemente;

import javafx.collections.MapChangeListener;
import javafx.collections.ObservableMap;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.input.DataFormat;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.StackPane;
import streetsim.business.Auto;
import streetsim.business.Position;
import streetsim.business.Strassenabschnitt;
import streetsim.business.Strassennetz;
import streetsim.business.abschnitte.Gerade;
import streetsim.business.abschnitte.Kreuzung;
import streetsim.business.abschnitte.Kurve;
import streetsim.business.abschnitte.TStueck;
import streetsim.ui.AbstractController;
import streetsim.ui.StreetSimApp;

import javax.imageio.ImageIO;
import javax.swing.text.html.ImageView;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Verwaltung von Aktionen auf dem Spielfeld
 */
public class SpielfeldController extends AbstractController<StreetSimApp> {

    ObservableMap<Position, Strassenabschnitt> abschnitte;
    ObservableMap<Position, ArrayList<Auto>> autos;
    List<ImageView> alleAbschnitte;

    public SpielfeldController(Strassennetz netz, StreetSimApp app) {
        super(netz, app);

        rootView = new SpielfeldView();

        abschnitte = netz.getAbschnitte();
        autos = netz.getAutos();

        alleAbschnitte = new LinkedList<>();
    }

    @Override
    public void handlerAnmelden() {
        abschnitte.addListener((MapChangeListener<Position, Strassenabschnitt>) change -> {
            if (change.wasAdded()) {

            } else if (change.wasRemoved()) {
                //TODO: do stuff
            }
        });

        rootView.setOnDragOver(event -> {
            boolean dropSupported = true;
            Dragboard dragboard = event.getDragboard();

            if (event.getTransferMode() != TransferMode.COPY) dropSupported = false;

            if (!dragboard.hasString()) dropSupported = false;
            else {
                String dataString = dragboard.getString();
                if (!ViewDataFormats.DATA_FORMATS.contains(dataString)) dropSupported = false;

                Strassenabschnitt s = netz.strasseAnPos((int) Math.round(event.getX()), (int) Math.round(event.getY()));
                switch (dataString) {
                    case ViewDataFormats.AMPEL_FORMAT:
                        if (s == null || s.isAmpelAktiv()) dropSupported = false;
                        break;
                    case ViewDataFormats.AUTO_FORMAT:

                        if (s == null) dropSupported = false;
                        break;
                    case ViewDataFormats.GERADE_FORMAT:
                    case ViewDataFormats.KREUZUNG_FORMAT:
                    case ViewDataFormats.KURVE_FORMAT:
                    case ViewDataFormats.TSTUECK_FORMAT:
                        if (s != null) dropSupported = false;
                        break;
                    default:
                        dropSupported = Arrays.stream(Auto.AutoModell.values()).map(Enum::name).collect(Collectors.toList()).contains(dataString);
                        break;
                }
            }

            if (dropSupported) event.acceptTransferModes(TransferMode.COPY);
            event.consume();
        });

        rootView.setOnDragDropped(event -> {
            Dragboard dragboard = event.getDragboard();

            String dataString = dragboard.getString();
            Strassenabschnitt s = netz.strasseAnPos((int) Math.round(event.getX()), (int) Math.round(event.getY()));
            if (dataString.equals(ViewDataFormats.AMPEL_FORMAT)) {
                s.ampelnAktivieren();
            } else if (Arrays.stream(Auto.AutoModell.values()).map(Enum::name).collect(Collectors.toList()).contains(dataString)) {
                Auto.AutoModell am = Auto.AutoModell.valueOf(dataString);

            } else if (dataString.equals(ViewDataFormats.GERADE_FORMAT)) {
                Gerade g = new Gerade((int) Math.round(event.getX()), (int) Math.round(event.getY()));
                netz.strasseAdden(g);
            } else if (dataString.equals(ViewDataFormats.KREUZUNG_FORMAT)) {
                Kreuzung k = new Kreuzung((int) Math.round(event.getX()), (int) Math.round(event.getY()));
                netz.strasseAdden(k);
            } else if (dataString.equals(ViewDataFormats.KURVE_FORMAT)) {
                Kurve k = new Kurve((int) Math.round(event.getX()), (int) Math.round(event.getY()));
                netz.strasseAdden(k);
            } else if (dataString.equals(ViewDataFormats.TSTUECK_FORMAT)) {
                TStueck t = new TStueck((int) Math.round(event.getX()), (int) Math.round(event.getY()));
                netz.strasseAdden(t);
            }

            event.setDropCompleted(true);
            event.consume();


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
