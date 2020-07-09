package streetsim.ui.startseite;

import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import streetsim.business.Strassennetz;
import streetsim.business.exceptions.DateiParseException;
import streetsim.ui.AbstractController;
import streetsim.ui.StreetSimApp;
import streetsim.ui.Szenen;
import streetsim.ui.spielfeld.elemente.InfoController;
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
    private InfoController infoController;

    public StartseiteController(Strassennetz netz, StreetSimApp app) {
        super(netz, app);
        rootView = new StartseiteView();
        fortfahren = ((StartseiteView) rootView).fortfahren;
        starten = ((StartseiteView) rootView).starten;
        laden = ((StartseiteView) rootView).laden;
        fortfahrPane = ((StartseiteView) rootView).fortfahrPane;
        startPane = ((StartseiteView) rootView).startPane;
        kontrollPane = ((StartseiteView) rootView).kontrollPane;
        infoController = new InfoController(app);

        starten.setDisable(true);
        laden.setDisable(true);
        fadeAssist = FadeAssist.getInstance();
        handlerAnmelden();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void handlerAnmelden() {
        rootView.setOnKeyPressed(e -> fortfahren.fire());
        fortfahren.setOnAction(e -> {
            fadeAssist.crossFade(fortfahrPane, startPane);
            kontrollPane.getChildren().remove(fortfahrPane);
            starten.setDisable(false);
            laden.setDisable(false);
        });
        starten.setOnAction(e -> erstelle());
        laden.setOnAction(e -> ladeNetz());
    }

    private void ladeNetz() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON", "*.json"));
        File file = fileChooser.showOpenDialog(app.getHauptStage().getOwner());
        if (file != null) {
            try {
                netz.ladeNetz(file);
            } catch(DateiParseException dpe) {
                infoController.zeige(dpe.getMessage());
            }
            app.wechsleSzene(Szenen.SPIEL_VIEW);
        }
    }

    private void erstelle() {
        app.wechsleSzene(Szenen.SPIEL_VIEW);
    }

}
