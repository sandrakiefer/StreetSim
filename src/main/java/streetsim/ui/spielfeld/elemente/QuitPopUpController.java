package streetsim.ui.spielfeld.elemente;

import javafx.scene.control.Button;
import streetsim.business.Strassennetz;
import streetsim.ui.AbstractController;
import streetsim.ui.StreetSimApp;

/**
 * Klasse, die beim Verlassen vor ungespeicherten Änderungen warnt.
 * Der Controller und dessen View wird lediglich in einem PopUp verwendet.
 */
public class QuitPopUpController extends AbstractController<StreetSimApp> {

    private final Button speichern, verwerfen, abbrechen;
    private final NavigationController aufrufer;

    /**
     * Konstruktor, der die View initialisiert und zusätzlich den Aufrufer festlegt, um
     * mit seinen Methoden Aktionen durchzuführen.
     *
     * @param netz     Straßennetz Model Objekt
     * @param app      Hauptanwendung
     * @param aufrufer Aufrufer des Controllers
     */
    public QuitPopUpController(Strassennetz netz, StreetSimApp app, NavigationController aufrufer) {
        super(netz, app);

        rootView = new QuitPopUpView();

        this.aufrufer = aufrufer;

        speichern = ((QuitPopUpView) rootView).speichern;
        verwerfen = ((QuitPopUpView) rootView).verwerfen;
        abbrechen = ((QuitPopUpView) rootView).abbrechen;

        handlerAnmelden();
    }

    @Override
    public void handlerAnmelden() {

        speichern.setOnAction(e -> {
            aufrufer.speicherNetz();
            aufrufer.beenden();
        });
        verwerfen.setOnAction(e -> aufrufer.beenden());
        abbrechen.setOnAction(e -> aufrufer.popUpSchliessen());

    }
}
