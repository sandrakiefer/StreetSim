package streetsim.ui;

import javafx.scene.Scene;
import streetsim.business.Strassennetz;
import javafx.application.Application;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import streetsim.ui.spielfeld.SpielViewController;
import streetsim.ui.startseite.StartseiteController;
import streetsim.ui.utils.ResourceAssist;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Verwaltung der gesamten Applikation
 */
public class StreetSimApp extends Application {

    //TODO: before release: change back to private
    protected Strassennetz netz;
    protected Map<Szenen, Pane> szenen;
    protected Scene aktuelleSzene;
    private Stage hauptStage;

    @Override
    public void init() throws Exception {
        super.init();

        szenen = new HashMap<>();
        netz = Strassennetz.getInstance();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        this.hauptStage = primaryStage;

        szenen.put(Szenen.STARTSEITE, new StartseiteController(netz, this).getRootView());
        szenen.put(Szenen.SPIEL_VIEW, new SpielViewController(netz, this).getRootView());

        aktuelleSzene = new Scene(szenen.get(Szenen.STARTSEITE));
        aktuelleSzene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());

        this.hauptStage.setScene(aktuelleSzene);
        this.hauptStage.setTitle("StreetSim");
        this.hauptStage.setHeight(1080);
        this.hauptStage.setWidth(1920);
        this.hauptStage.show();
        this.hauptStage.setOnHidden(e -> {
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

    public Stage getHauptStage() {
        return hauptStage;
    }

}
