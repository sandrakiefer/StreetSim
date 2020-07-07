package streetsim.ui.spielfeld.elemente;

import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import streetsim.business.Auto;
import streetsim.business.Himmelsrichtung;
import streetsim.business.Strassenabschnitt;
import streetsim.business.Strassennetz;
import streetsim.business.abschnitte.TStueck;
import streetsim.business.exceptions.WeltLeerException;
import streetsim.ui.AbstractController;
import streetsim.ui.StreetSimApp;
import streetsim.ui.Szenen;

import java.io.File;

/**
 * Verwaltung von Aktionen in der Navigationsleiste
 */
public class NavigationController extends AbstractController<StreetSimApp> {

    private final Button startPause, speichern, beende;
    private final MenuItem ampeln, autos, strassen, alles;

    public NavigationController(Strassennetz netz, StreetSimApp app) {
        super(netz, app);
        rootView = new NavigationView();
        startPause = ((NavigationView) rootView).startPause;
        speichern = ((NavigationView) rootView).speichern;
        beende = ((NavigationView) rootView).beende;

        ampeln = ((NavigationView) rootView).ampeln;
        autos = ((NavigationView) rootView).autos;
        strassen = ((NavigationView) rootView).strassen;
        alles = ((NavigationView) rootView).alles;

        handlerAnmelden();
    }

    @Override
    public void handlerAnmelden() {
        speichern.setOnAction(e -> speicherNetz());
        beende.setOnAction(e -> zurueck());

        ampeln.setOnAction(e -> entfAlleAmplen());
        autos.setOnAction(e -> entfAlleAutos());
        strassen.setOnAction(e -> entfAlleStrassen());
        alles.setOnAction(e -> feldLeeren());

        startPause.setOnAction(e -> {
            try{
                if(!netz.isSimuliert()) start();
                else pause();
            } catch(WeltLeerException ex){
                //TODO: user informieren dass Welt leer ist
            }
        });
    }

    /**
     * Straten der Simulatiom
     */
    public void start() {
        netz.starteSimulation();
        startPause.setId("pause");
    }

    /**
     * Pausieren der Simulation
     */
    public void pause() {
        netz.pausiereSimulation();
        startPause.setId("play");
    }

    /**
     * Entfernen aller Autos
     */
    public void entfAlleAutos() {
        netz.entfAlleAutos();
    }

    /**
     * Entfernen aller Strassen
     */
    public void entfAlleStrassen() {
        netz.entfAlleStrassen();
    }

    /**
     * Entfernen aller Ampeln
     */
    public void entfAlleAmplen() {
        netz.alleAmpelnDeaktivieren();
    }

    /**
     * Felder leeren
     */
    public void feldLeeren() {
        netz.entfAlleAutos();
        netz.alleAmpelnDeaktivieren();
        netz.entfAlleStrassen();
    }

    /**
     * Platzierung rückgängig machen
     */
    public void zurueck() {
        //TODO: Frage ob wirklich beendet werden soll / speicherhinweis
        netz.reset();
        app.wechsleSzene(Szenen.STARTSEITE);
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
