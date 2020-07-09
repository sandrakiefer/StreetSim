package streetsim.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import streetsim.business.Strassennetz;
import streetsim.ui.spielfeld.SpielViewController;
import streetsim.ui.startseite.StartseiteController;

import java.util.HashMap;
import java.util.Map;

/**
 * Haupt-JavaFX-Anwendung
 */
public class StreetSimApp extends Application {

    public static final int BREITE = 1920;
    public static final int HOEHE = 1080;

    private Strassennetz netz;
    private Map<Szenen, Pane> szenen;
    private Scene aktuelleSzene;
    private Stage hauptStage;

    /**
     * {@inheritDoc}
     */
    @Override
    public void init() throws Exception {
        super.init();

        szenen = new HashMap<>();
        netz = Strassennetz.getInstance();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void start(Stage primaryStage) {

        //TODO: fixe groesse
        this.hauptStage = primaryStage;
        this.hauptStage.setTitle("StreetSim");
        this.hauptStage.setFullScreen(true);
        this.hauptStage.setResizable(false);
        this.hauptStage.setHeight(HOEHE);
        this.hauptStage.setWidth(BREITE);

        szenen.put(Szenen.STARTSEITE, new StartseiteController(netz, this).getRootView());

        aktuelleSzene = new Scene(szenen.get(Szenen.STARTSEITE));
        aktuelleSzene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());

        this.hauptStage.setScene(aktuelleSzene);
        this.hauptStage.show();
        this.hauptStage.setOnHidden(e -> System.exit(0));

    }

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Wechseln der Szene
     *
     * @param s Szene
     */
    public void wechsleSzene(Szenen s) {
        szenen.put(Szenen.SPIEL_VIEW, new SpielViewController(netz, this).getRootView());
        if (szenen.containsKey(s)) {
            aktuelleSzene.setRoot(szenen.get(s));
        }
    }

    public Stage getHauptStage() {
        return hauptStage;
    }

}
