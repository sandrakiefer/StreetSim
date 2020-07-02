package streetsim.ui.spielfeld.elemente;

import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import streetsim.business.Strassennetz;
import streetsim.ui.AbstractController;
import streetsim.ui.StreetSimApp;

/**
 * Verwaltung von Aktionen in der Navigationsleiste
 */
public class NavigationController extends AbstractController<StreetSimApp> {

    private final Button startPause, entferne, beende;

    public NavigationController(Strassennetz netz, StreetSimApp app) {
        super(netz, app);
        rootView = new NavigationView();
        startPause = ((NavigationView) rootView).startPause;
        entferne = ((NavigationView) rootView).entferne;
        beende = ((NavigationView) rootView).beende;
    }

    @Override
    public void handlerAnmelden() {

    }

    /**
     * Straten der Simulatiom
     */
    public void start() {

    }

    /**
     * Pausieren der Simulation
     */
    public void pause() {

    }

    /**
     * Entfernen aller Autos
     */
    public void entfAlleAutos() {

    }

    /**
     * Entfernen aller Strassen
     */
    public void entfAlleStrassen() {

    }

    /**
     * Entfernen aller Ampeln
     */
    public void entfAlleAmplen() {

    }

    /**
     * Felder leeren
     */
    public void feldLeeren() {

    }

    /**
     * Platzierung rückgängig machen
     */
    public void zurueck() {

    }

    /**
     * Strassennetz abspeichern
     */
    public void speicherNetz() {

    }

}
