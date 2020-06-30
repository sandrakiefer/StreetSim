package streetsim.ui.spielfeld;

import javafx.scene.layout.Pane;
import streetsim.business.Strassennetz;
import streetsim.ui.AbstractController;
import streetsim.ui.StreetSimApp;

/**
 * Verwaltung von Aktionen in der Navigationsleiste
 */
public class NavigationController extends AbstractController<StreetSimApp> {


    public NavigationController(Strassennetz netz, Pane rootView, StreetSimApp app) {
        super(netz, rootView, app);
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
