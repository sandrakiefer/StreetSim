package streetsim.ui.spielfeld.elemente;

import javafx.scene.control.Button;
import streetsim.business.Strassennetz;
import streetsim.ui.AbstractController;
import streetsim.ui.StreetSimApp;
import streetsim.ui.spielfeld.elemente.QuitPopUpView;

public class QuitPopUpController extends AbstractController<StreetSimApp> {

    private Button speichern, verwerfen, abbrechen;
    private NavigationController aufrufer;
    
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
