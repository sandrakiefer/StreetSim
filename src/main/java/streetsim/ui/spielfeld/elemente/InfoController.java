package streetsim.ui.spielfeld.elemente;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import streetsim.business.Strassennetz;
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

    private Label nachricht;
    private Stage popup;
    private PopUpAssist popAssist;

    /**
     * Konstruktor des Controllers
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
     * @param info Warnungstext, der angezeigt wird
     */
    public void zeige(String info) {
        System.out.println(info);
        nachricht.setText(info);

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
                    rootView = new InfoView();
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

    }
}
