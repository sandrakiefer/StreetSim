package streetsim.ui.startseite;

import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import streetsim.business.Strassennetz;
import streetsim.ui.AbstractController;
import streetsim.ui.StreetSimApp;
import streetsim.ui.Szenen;
import streetsim.ui.utils.FadeAssist;

import java.io.File;

/**
 * Verwaltung von Aktionen auf der Startseite
 */
public class StartseiteController extends AbstractController<StreetSimApp> {

    private final Button fortfahren, starten, laden;
    private final VBox fortfahrPane, startPane;
    private final StackPane kontrollPane;
    private final FadeAssist fadeAssist;

    public StartseiteController(Strassennetz netz, StreetSimApp app) {
        super(netz, app);
        rootView = new StartseiteView();
        fortfahren = ((StartseiteView) rootView).fortfahren;
        starten = ((StartseiteView) rootView).starten;
        laden = ((StartseiteView) rootView).laden;
        fortfahrPane = ((StartseiteView) rootView).fortfahrPane;
        startPane = ((StartseiteView) rootView).startPane;
        kontrollPane = ((StartseiteView) rootView).kontrollPane;
        fadeAssist = FadeAssist.getInstance();
        handlerAnmelden();
    }

    /**
     * stößt gleichnamige Methode in der Business-Schicht an
     * lädt ein Strassennetz
     */
    private void ladeNetz() {
        //TODO: init Spielstand an netz-Instanz
        FileChooser fileChooser = new FileChooser();
//        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON", "*.json"));
        File file = fileChooser.showOpenDialog(app.getHauptStage().getOwner());
        netz.setName("Rudolf");
        System.out.println(netz.getName());
        if (file != null) netz.ladeNetz(file);
        System.out.println(netz.getName());
        app.wechsleSzene(Szenen.SPIEL_VIEW);
    }

    /**
     * erzeugt in der Startseite eine neue Strassennetz-Instanz
     * wechselt die View zu "SPIEL_VIEW"
     */
    private void erstelle() {
        app.wechsleSzene(Szenen.SPIEL_VIEW);
    }

    @Override
    public void handlerAnmelden() {
        rootView.setOnKeyPressed(e -> fortfahren.fire());
        fortfahren.setOnAction(e -> {
            fadeAssist.crossFade(fortfahrPane, startPane);
            kontrollPane.getChildren().remove(fortfahrPane);
        });
        starten.setOnAction(e -> erstelle());
        laden.setOnAction(e -> ladeNetz());
    }
}
