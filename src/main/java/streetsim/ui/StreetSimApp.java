package streetsim.ui;

import javafx.scene.Scene;
import streetsim.business.Strassennetz;
import javafx.application.Application;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import streetsim.ui.spielfeld.SpielViewController;
import streetsim.ui.startseite.StartseiteController;
import streetsim.ui.startseite.StartseiteView;

import java.util.HashMap;
import java.util.Map;

/**
 * Verwaltung der gesamten Applikation
 */
public class StreetSimApp extends Application {

    private Strassennetz netz;
    private Map<Szenen, Pane> szenen;
    private Scene aktuelleSzene;

    @Override
    public void init() throws Exception {
        super.init();

        szenen = new HashMap<>();
        netz = new Strassennetz();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        szenen.put(Szenen.STARTSEITE, new StartseiteController(netz, this).getRootView());
        szenen.put(Szenen.SPIEL_VIEW, new SpielViewController(netz, this).getRootView());

        aktuelleSzene = new Scene(szenen.get(Szenen.STARTSEITE));

        primaryStage.setScene(aktuelleSzene);
        primaryStage.setTitle("StreetSim");
        primaryStage.setHeight(1080);
        primaryStage.setWidth(1920);
        primaryStage.show();
        primaryStage.setOnHidden(e -> {
            System.exit(0);
        });

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
        if (szenen.containsKey(s)) {
            aktuelleSzene.setRoot(szenen.get(s));
        }
    }

}
