package streetsim.ui.spielfeld.elemente;

import com.sun.javafx.binding.StringFormatter;
import javafx.application.Platform;
import javafx.collections.MapChangeListener;
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
    ObservableMap<Position, ArrayList<Auto>> autos;
    List<ImageView> alleAbschnitte;

    public SpielfeldController(Strassennetz netz, StreetSimApp app) {
        super(netz, app);

        rootView = new SpielfeldView();

        strassenController = new HashMap<>();
        autoController = new HashMap<>();

        //TODO: auto map notwendig? an Position können mehrere autos sein ist doch egal
        abschnitte = netz.getAbschnitte();
        autos = netz.getAutos();
        System.out.println(autos.size());

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
                sc.getAlleAmpeln().forEach(a -> ((SpielfeldView) rootView).addAmpelOderAuto(a.getRootView()));

            } else if (change.wasRemoved()) {
                Strassenabschnitt s = change.getValueRemoved();
                StrassenController sc = strassenController.remove(s);
                sc.getAlleAmpeln().forEach(a -> ((SpielfeldView) rootView).entferneAmpelOderAuto(a.getRootView()));
                netz.entfStrasse(s);
                ((SpielfeldView) rootView).entferneAbschnitt(sc.getRootView());

            }
        });

        autos.addListener((MapChangeListener<Position, ArrayList<Auto>>) change -> {
            if(change.wasAdded()) {
                change.getValueAdded().forEach(a -> {
                    ((SpielfeldView) rootView).addAmpelOderAuto(autoController.get(a).getRootView());
                });
            }

            if(change.wasRemoved()) {
                change.getValueRemoved().forEach(a -> {
                    ((SpielfeldView) rootView).entferneAmpelOderAuto(autoController.get(a).getRootView());
                });
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
//        System.out.println("wir kommen der Sache näher");
        AutoModelle.valueOf(a.getAutoModell().toString()).getView();

        AutoView av = new AutoView(AutoModelle.valueOf(a.getAutoModell().toString()).getView());

        AutoController ac = new AutoController(a, av);

        autoController.put(a, ac);
        ac.getRootView().setLayoutX(a.getPositionX());
        ac.getRootView().setLayoutY(a.getPositionY());
//        System.out.println(String.format("Auto \n PosX: %d \n PosY: %d \n", a.getPositionX(), a.getPositionY()));

        ((SpielfeldView) rootView).addAmpelOderAuto(ac.getRootView());
//
//        System.out.println("wir kommen der Sache viiiiiiiiiiiiel näher");
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
