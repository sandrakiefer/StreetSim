package streetsim.ui.spielfeld.elemente;

import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import streetsim.business.*;
import streetsim.business.abschnitte.TStueck;
import streetsim.business.exceptions.WeltLeerException;
import streetsim.ui.AbstractController;
import streetsim.ui.StreetSimApp;
import streetsim.ui.Szenen;
import streetsim.ui.utils.PopUpAssist;

import java.io.File;
import java.util.*;

/**
 * Verwaltung von Aktionen in der Navigationsleiste
 */
public class NavigationController extends AbstractController<StreetSimApp> {

    private final Button startPause, speichern, beende;
    private final MenuItem ampeln, autos, strassen, alles;
    private Stage popup;

    private Map<Position, Strassenabschnitt> abschnitte;
    private Map<Position, List<Auto>> autoMap;
    private List<Auto> autoList;

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

        speicherStand();
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
        if (!standGespeichert()) {
            QuitPopUpController quit = new QuitPopUpController(netz, app, this);
            PopUpAssist popAssist = PopUpAssist.getInstance();
            popup = popAssist.createPopUp(quit.getRootView(), app.getHauptStage());
            popAssist.center(popup, app.getHauptStage());
            popup.show();
        } else {
            beenden();
        }
    }

    public void beenden(){
        popUpSchliessen();
        netz.reset();
        app.wechsleSzene(Szenen.STARTSEITE);
    }

    /**
     * Strassennetz abspeichern
     */
    public void speicherNetz() {
        popUpSchliessen();
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON", "*.json"));
        fileChooser.setInitialFileName(netz.getName());
        File file = fileChooser.showSaveDialog(app.getHauptStage().getOwner());
        if (file != null) {
            netz.speicherNetz(file);
            speicherStand();
        }
    }

    public void popUpSchliessen(){
        if (popup != null) popup.close();
    }

    private boolean standGespeichert(){
        return netz.getAbschnitte().equals(abschnitte) &&
               netz.getAutoList().equals(autoList) &&
               netz.getAutos().equals(autoMap);
    }

    private void speicherStand(){
        abschnitte = new HashMap<>(netz.getAbschnitte());
        autoMap = new HashMap<>(netz.getAutos());
        autoList = new ArrayList<>(netz.getAutoList());
    }
}
