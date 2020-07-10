package streetsim.ui.spielfeld.elemente;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import streetsim.ui.AbstractController;
import streetsim.ui.StreetSimApp;
import streetsim.ui.utils.PopUpAssist;

/**
 * Eine Controller-Klasse, die benutzt werden soll, wenn der Benutzer
 * Fehleingaben während der Laufzeit macht.
 * Diese Klasse zeigt dem Benutzer ein kleines PopUp-Fenster, dass ihn über
 * seine Fehleingabe informiert
 */
public class InfoController extends AbstractController<StreetSimApp> {

    private final Label nachricht;
    private final PopUpAssist popAssist;
    private Stage popup;

    /**
     * Konstruktor des Controllers
     *
     * @param app Hauptanwendung
     */
    public InfoController(StreetSimApp app) {
        super(app);
        rootView = new InfoView();
        nachricht = ((InfoView) rootView).nachricht;
        popAssist = PopUpAssist.getInstance();

        handlerAnmelden();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void handlerAnmelden() {

    }

    /**
     * Diese Methode erstellt und zeigt ein PopUp Fenster.
     *
     * @param info Warntext, der angezeigt wird
     */
    public void zeige(String info) {
        ((InfoView)rootView).nachricht.setText(info);

        popup = popAssist.createPopUp(rootView, app.getHauptStage());
        popAssist.center(popup, app.getHauptStage());
        popup.initStyle(StageStyle.TRANSPARENT);
        popup.setAlwaysOnTop(true);

        popup.show();
        new Thread(() -> {
            try {
                Thread.sleep(4000);
                Platform.runLater(() -> {
                    popup.close();
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        rootView = new InfoView();
    }
}
