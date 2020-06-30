package streetsim.ui;

import streetsim.business.Strassennetz;
import javafx.application.Application;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.Map;

/**
 * Verwaltung der gesamten Applikation
 */
public class StreetSimApp extends Application {

    private Strassennetz netz;
    private Pane rootView;
    private Map<streetsim.ui.Szenen, Pane> szenen;

    @Override
    public void init() throws Exception {
        super.init();
        // TODO: init HashMap szenen
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

    }

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Wechseln der Szene
     *
     * @param s Szene
     */
    public void wechsleSzene(streetsim.ui.Szenen s) {

    }

}
