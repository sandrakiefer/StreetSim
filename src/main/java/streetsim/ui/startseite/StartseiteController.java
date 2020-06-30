package streetsim.ui.startseite;

import javafx.scene.layout.Pane;
import streetsim.ui.AbstractController;
import streetsim.ui.StreetSimApp;

/**
 * Verwaltung von Aktionen auf der Startseite
 */
public class StartseiteController extends AbstractController<StreetSimApp> {

    public StartseiteController(Pane rootView, StreetSimApp app) {
        super(rootView, app);
    }

    /**
     * stößt gleichnamige Methode in der Business-Schicht an
     * lädt ein Strassennetz
     */
    public void ladeNetz() {

    }

    /**
     * erzeugt in der Startseite eine neue Strassennetz-Instanz
     * wechselt die View zu "SPIEL_VIEW"
     */
    public void erstelle() {

    }

    @Override
    public void handlerAnmelden() {

    }
}
