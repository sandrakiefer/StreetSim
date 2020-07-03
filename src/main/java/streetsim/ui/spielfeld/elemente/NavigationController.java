package streetsim.ui.spielfeld.elemente;

import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import streetsim.business.Auto;
import streetsim.business.Himmelsrichtung;
import streetsim.business.Strassenabschnitt;
import streetsim.business.Strassennetz;
import streetsim.business.abschnitte.TStueck;
import streetsim.ui.AbstractController;
import streetsim.ui.StreetSimApp;
import streetsim.ui.Szenen;

import java.io.File;

/**
 * Verwaltung von Aktionen in der Navigationsleiste
 */
public class NavigationController extends AbstractController<StreetSimApp> {

    private final Button startPause, speichern, beende;
    private final MenuButton entferne;

    public NavigationController(Strassennetz netz, StreetSimApp app) {
        super(netz, app);
        rootView = new NavigationView();
        startPause = ((NavigationView) rootView).startPause;
        entferne = ((NavigationView) rootView).entferne;
        speichern = ((NavigationView) rootView).speichern;
        beende = ((NavigationView) rootView).beende;

        handlerAnmelden();
    }

    @Override
    public void handlerAnmelden() {
        speichern.setOnAction(e -> speicherNetz());
        beende.setOnAction(e -> zurueck());
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
        //TODO: Frage ob wirklich beendet werden soll / speicherhinweis
        //TODO: resettem des Netzes
        app.wechsleSzene(Szenen.STARTSEITE);
    }

    /**
     * Strassennetz abspeichern
     */
    public void speicherNetz() {
        //tmp init of netz for save test

        Strassenabschnitt str = new TStueck(100, 100);
        netz.strasseAdden(str);
        Auto brum = new Auto(0.9f, Himmelsrichtung.WESTEN, 100, 100, 10, 20);
        netz.autoAdden(brum);

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON", "*.json"));
        fileChooser.setInitialFileName(netz.getName());
        File file = fileChooser.showSaveDialog(app.getHauptStage().getOwner());
        netz.speicherNetz(file);
    }

}
