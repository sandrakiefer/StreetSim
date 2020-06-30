package streetsim.ui.startseite;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import streetsim.ui.AbstractController;
import streetsim.ui.StreetSimApp;

/**
 * Verwaltung von Aktionen auf der Startseite
 */
public class StartseiteController extends AbstractController<StreetSimApp> {

    private Button fortfahren, starten, laden;
    private HBox fortfahrPane, startPane;

    public StartseiteController(StreetSimApp app) {
        super(app);
        rootView = new StartseiteView();
        fortfahren = ((StartseiteView) rootView).fortfahren;
        starten = ((StartseiteView) rootView).fortfahren;
        laden = ((StartseiteView) rootView).laden;
        fortfahrPane = ((StartseiteView) rootView).fortfahrPane;
        startPane = ((StartseiteView) rootView).startPane;

        handlerAnmelden();
    }

    /**
     * stößt gleichnamige Methode in der Business-Schicht an
     * lädt ein Strassennetz
     */
    private void ladeNetz() {

    }

    /**
     * erzeugt in der Startseite eine neue Strassennetz-Instanz
     * wechselt die View zu "SPIEL_VIEW"
     */
    private void erstelle() {

    }

    private void wechsleUntereAnsicht(){

    }

    @Override
    public void handlerAnmelden() {

    }


}
