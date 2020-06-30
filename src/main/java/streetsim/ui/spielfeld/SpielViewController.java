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

    public SpielViewController(Strassennetz netz, Pane rootView, StreetSimApp app) {
        super(netz, rootView, app);
    }

    @Override
    public void handlerAnmelden() {

    }
}
