package streetsim.ui.spielfeld.elemente;

import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.event.Event;
import javafx.event.EventHandler;
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

import javax.swing.event.HyperlinkEvent;
import java.io.File;
import java.util.*;

/**
 * Verwaltung von Aktionen in der Navigationsleiste
 */
public class NavigationController extends AbstractController<StreetSimApp> {

    private final Button startPause, beende;
    private final MenuItem ampeln, autos, strassen, alles, speichern, speichernUnter;
    private MenuButton entferne;
    private Stage popup;

    private Map<Position, Strassenabschnitt> abschnitte;
    private Map<Position, List<Auto>> autoMap;
    private List<Auto> autoList;
    private InfoController infoController;

    private File file;

    public NavigationController(Strassennetz netz, StreetSimApp app) {
        super(netz, app);
        rootView = new NavigationView();
        startPause = ((NavigationView) rootView).startPause;
        speichern = ((NavigationView) rootView).speichern;
        speichernUnter = ((NavigationView) rootView).speichernUnter;
        beende = ((NavigationView) rootView).beende;
        entferne = ((NavigationView) rootView).entferne;

        ampeln = ((NavigationView) rootView).ampeln;
        autos = ((NavigationView) rootView).autos;
        strassen = ((NavigationView) rootView).strassen;
        alles = ((NavigationView) rootView).alles;
        infoController = new InfoController(app);

        speicherStand();
        handlerAnmelden();
    }

    @Override
    public void handlerAnmelden() {
        speichern.setOnAction(e -> speicherNetz());
        speichernUnter.setOnAction(e -> speicherNetzUnter());
        beende.setOnAction(e -> zurueck());

        ampeln.setOnAction(e -> entfAlleAmplen());
        autos.setOnAction(e -> entfAlleAutos());
        strassen.setOnAction(e -> entfAlleStrassen());
        alles.setOnAction(e -> feldLeeren());

        netz.simuliertProperty().addListener((obs, oldV, newV) -> entferne.setDisable(newV));

        startPause.setOnAction(e -> {
            try {
                if (!netz.isSimuliert()) start();
                else pause();
            } catch (WeltLeerException ex) {
                infoController.zeige(ex.getMessage());
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

    public void beenden() {
        popUpSchliessen();
        netz.reset();
        app.wechsleSzene(Szenen.STARTSEITE);
    }

    public void speicherNetzUnter() {
        popUpSchliessen();
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON", "*.json"));
        fileChooser.setInitialFileName(netz.getName());
        file = fileChooser.showSaveDialog(app.getHauptStage().getOwner());
        abspeichern();
    }

    /**
     * Strassennetz abspeichern
     */
    public void speicherNetz() {
        if (netz.getName() == null) speicherNetzUnter();
        else abspeichern();
    }

    private void abspeichern(){
        if (file != null) {
            netz.speicherNetz(file);
            speicherStand();
        } else {
            speicherNetzUnter();
        }
    }

    public void popUpSchliessen() {
        if (popup != null) popup.close();
    }

    private boolean standGespeichert() {
        return netz.getAbschnitte().equals(abschnitte) &&
               netz.getAutoList().equals(autoList) &&
               netz.getAutos().equals(autoMap);
    }

    private void speicherStand() {
        abschnitte = new HashMap<>(netz.getAbschnitte());
        autoMap = new HashMap<>(netz.getAutos());
        autoList = new ArrayList<>(netz.getAutoList());
    }
}
