package streetsim.ui.spielfeld.elemente;

import javafx.collections.ListChangeListener;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import streetsim.business.Auto;
import streetsim.business.Position;
import streetsim.business.Strassenabschnitt;
import streetsim.business.Strassennetz;
import streetsim.business.abschnitte.Gerade;
import streetsim.business.abschnitte.Kreuzung;
import streetsim.business.abschnitte.Kurve;
import streetsim.ui.AbstractController;
import streetsim.ui.StreetSimApp;
import streetsim.ui.spielfeld.elemente.strassenabschnitte.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller für Aktionen im Spielfeld
 * <p>
 * {@inheritDoc}
 */
public class SpielfeldController extends AbstractController<StreetSimApp> {

    private final Map<Strassenabschnitt, StrassenController> strassenController;
    private final Map<Auto, AutoController> autoController;

    private final ObservableMap<Position, Strassenabschnitt> abschnitte;
    private final Map<Position, List<Auto>> autoMap;
    private final ObservableList<Auto> autos;
    private final Label name;

    /**
     * {@inheritDoc}
     */
    public SpielfeldController(Strassennetz netz, StreetSimApp app) {
        super(netz, app);

        rootView = new SpielfeldView();

        strassenController = new HashMap<>();
        autoController = new HashMap<>();
        abschnitte = netz.getAbschnitte();
        autos = netz.getAutoList();
        autoMap = netz.getAutos();
        name = ((SpielfeldView) rootView).name;

        handlerAnmelden();
        init();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void handlerAnmelden() {

        netz.nameProperty().addListener((observable, oldValue, newValue) -> nameAnpassen(newValue));

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
            if (change.next() && change.wasAdded()) {
                autoAdden(autos.get(change.getFrom()));
            } else if (change.wasRemoved()) {
                entfAuto(change.getRemoved().toArray(Auto[]::new));
            }
        });


    }

    /**
     * Methode, die eine AutoView erzeugt, sobald ein Auto im Model erstellt wurde.
     *
     * @param a Auto, welches neu hinzugefügt wurde.
     */
    public void autoAdden(Auto a) {

        AutoView av = new AutoView(AutoModelle.valueOf(a.getAutoModell().name()).getView());
        av.setLayoutX(a.getPositionX() - (double) a.getBreite() / 2);
        av.setLayoutY(a.getPositionY() - (double) a.getLaenge() / 2);
        AutoController ac = new AutoController(a, av);
        autoController.put(a, ac);

        ((SpielfeldView) rootView).addAmpelOderAuto(ac.getRootView());
    }

    /**
     * Methode, die eine Straßenabschnitts-View erzeugt, sobald eine Instanz im Model erstellt wurde.
     *
     * @param s Straßenabschnitt, welches neu hinzugefügt wurde.
     */
    public void strasseAdden(Strassenabschnitt s) {
        ImageView strassenView;

        if (s instanceof Gerade) strassenView = new GeradeView();
        else if (s instanceof Kreuzung) strassenView = new KreuzungView();
        else if (s instanceof Kurve) strassenView = new KurveView();
        else strassenView = new TStueckView();

        strassenView.setLayoutX(s.getPositionX());
        strassenView.setLayoutY(s.getPositionY());

        StrassenController sc = new StrassenController(s, strassenView);
        strassenController.put(s, sc);
        ((SpielfeldView) rootView).addAbschnitt(strassenView);
        sc.getAlleAmpeln().forEach(a -> ((SpielfeldView) rootView).addAmpelOderAuto(a.getRootView()));
    }

    /**
     * Wenn ein Auto (oder mehrere Autos) im Model entfernt werden, werden die zugehörigen Views entfernt.
     *
     * @param a Autos, die entfernt wurden
     */
    public void entfAuto(Auto... a) {
        for (Auto brum : a) {
            ((SpielfeldView) rootView).entferneAmpelOderAuto(autoController.get(brum).getRootView());
            autoController.remove(brum);
        }
    }

    /**
     * Wenn ein Straßenabschnitt (oder mehrere Straßenabschnitte) im Model entfernt werden, werden die zugehörigen Views entfernt.
     *
     * @param s Straßenabschnitte, die entfernt wurden
     */
    public void entfStrasse(Strassenabschnitt... s) {
        for (Strassenabschnitt st : s) {
            StrassenController sc = strassenController.remove(st);
            sc.getAlleAmpeln().forEach(a -> ((SpielfeldView) rootView).entferneAmpelOderAuto(a.getRootView()));
            ((SpielfeldView) rootView).entferneAbschnitt(sc.getRootView());
        }
    }

    public Map<Position, List<Auto>> getAutoMap() {
        return autoMap;
    }

    private void init() {
        for (Strassenabschnitt s : abschnitte.values()) {
            strasseAdden(s);
        }
        nameAnpassen(netz.getName());

        for (Auto a : netz.getAutoList()) {
            autoAdden(a);
        }
    }

    private void nameAnpassen(String newValue) {
        name.setText(newValue != null ? newValue : "Untitled");
    }

}
