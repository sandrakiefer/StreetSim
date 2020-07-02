package streetsim.ui.spielfeld;

import javafx.scene.layout.Pane;
import streetsim.business.Strassennetz;
import streetsim.ui.AbstractController;
import streetsim.ui.StreetSimApp;

/**
 * Verwaltung der folgenden drei Controller:
 * SpielviewController, Spielfeldcontroller, MenueController
 */
public class SpielViewController extends AbstractController<StreetSimApp> {

    public SpielViewController(Strassennetz netz, StreetSimApp app) {
        super(netz, app);
    }

    @Override
    public void handlerAnmelden() {

    }

    /*
    //TODO:
    Im Pausemenü sobald gedragged wird soll das Seitenmenü eingeklappt werden und erst nach bearbeiten wieder eingeblendet
     */
}
