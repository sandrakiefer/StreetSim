package streetsim.ui.spielfeld.elemente;

import com.sun.javafx.binding.StringFormatter;
import javafx.application.Platform;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.scene.image.ImageView;
import streetsim.business.*;
import streetsim.business.abschnitte.Gerade;
import streetsim.business.abschnitte.Kreuzung;
import streetsim.business.abschnitte.Kurve;
import streetsim.ui.AbstractController;
import streetsim.ui.StreetSimApp;
import streetsim.ui.spielfeld.elemente.strassenabschnitte.*;

import java.util.*;

/**
 * Verwaltung von Aktionen auf dem Spielfeld
 */
public class SpielfeldController extends AbstractController<StreetSimApp> {

    Map<Strassenabschnitt, StrassenController> strassenController;
    Map<Auto, AutoController> autoController;

    ObservableMap<Position, Strassenabschnitt> abschnitte;
    ObservableList<Auto> autos;
    List<ImageView> alleAbschnitte;

    public SpielfeldController(Strassennetz netz, StreetSimApp app) {
        super(netz, app);

        rootView = new SpielfeldView();

        strassenController = new HashMap<>();
        autoController = new HashMap<>();
        abschnitte = netz.getAbschnitte();
        autos = netz.getAutoList();


        alleAbschnitte = new LinkedList<>();
        handlerAnmelden();
        init();
    }

    @Override
    public void handlerAnmelden() {
        abschnitte.addListener((MapChangeListener<Position, Strassenabschnitt>) change -> {
            if (change.wasAdded()) {
                Strassenabschnitt s = change.getValueAdded();
                strasseAdden(s);
            } else if (change.wasRemoved()) {
                Strassenabschnitt s = change.getValueRemoved();
                entfStrasse(s);
            }
        });

        app.getHauptStage().widthProperty().addListener(c -> ((SpielfeldView) rootView).setBreite(app.getHauptStage().getWidth()));

        app.getHauptStage().heightProperty().addListener(c -> ((SpielfeldView) rootView).setHoehe(app.getHauptStage().getHeight()));

        autos.addListener((ListChangeListener<Auto>) change -> {
            if(change.wasAdded()) {
                autoAdden(autos.get(change.getFrom()));
            }
        });


    }

    /**
     * Auto einem Strassenabschnitt hinzufügen
     *
     * @param a Auto
     */
    public void autoAdden(Auto a) {

        AutoModelle.valueOf(a.getAutoModell().toString()).getView();
        AutoView av = new AutoView(AutoModelle.valueOf(a.getAutoModell().toString()).getView());
        AutoController ac = new AutoController(a, av);
        autoController.put(a, ac);

        ac.getRootView().setLayoutX(a.getPositionX()-a.getBreite()/2);
        ac.getRootView().setLayoutY(a.getPositionY()-a.getLaenge()/2);

        ((SpielfeldView) rootView).addAmpelOderAuto(ac.getRootView());
    }

    /**
     * Ampeln auf gegebenen Strassenabschnitt aktivieren
     *
     * @param s Strassenabschnitt
     */
    public void ampelnAktivieren(Strassenabschnitt s) {
        strassenController.get(s).getAlleAmpeln().forEach(a -> ((SpielfeldView) rootView).addAmpelOderAuto(a.getRootView()));
    }

    /**
     * Strass dem Strassennetz hinzufügen
     *
     * @param s Strassenabschnitt
     */
    public void strasseAdden(Strassenabschnitt s) {
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
        sc.getAlleAmpeln().forEach(a -> ((SpielfeldView) rootView).addAmpelOderAuto(a.getRootView()));
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
        strassenController.get(s).getAlleAmpeln().forEach(a -> ((SpielfeldView) rootView).entferneAmpelOderAuto(a.getRootView()));
    }

    /**
     * beliebig viele Strassenabschnitte vom Strassennetz entfernen
     *
     * @param s Strassenabschnitte
     */
    public void entfStrasse(Strassenabschnitt... s) {
        for (Strassenabschnitt st : s) {
            StrassenController sc = strassenController.remove(st);
            sc.getAlleAmpeln().forEach(a -> ((SpielfeldView) rootView).entferneAmpelOderAuto(a.getRootView()));
            netz.entfStrasse(st);
            ((SpielfeldView) rootView).entferneAbschnitt(sc.getRootView());
        }
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

    private void init() {
        for (Strassenabschnitt s : abschnitte.values()) {
            strasseAdden(s);
            //TODO: Autos adden
        }
    }

}
