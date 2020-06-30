package streetsim.ui;

import javafx.scene.layout.Pane;
import streetsim.business.Strassennetz;

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
