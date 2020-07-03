package streetsim.ui.spielfeld.elemente;

import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import streetsim.business.Strassennetz;
import streetsim.ui.AbstractController;
import streetsim.ui.StreetSimApp;

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
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON", "*.json"));
        fileChooser.setInitialFileName(netz.getName());
        File file = fileChooser.showSaveDialog(app.getHauptStage().getOwner());
        netz.speicherNetz(file);
    }

}
