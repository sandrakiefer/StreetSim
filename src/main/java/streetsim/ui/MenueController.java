package streetsim.ui;

import javafx.scene.layout.Pane;
import streetsim.business.Strassennetz;

/**
 * Verwaltung von Aktionen im Menü
 */
public class MenueController extends AbstractController<StreetSimApp> {

    public MenueController(Strassennetz netz, Pane rootView, StreetSimApp app) {
        super(netz, rootView, app);
    }

    @Override
    public void handlerAnmelden() {

    }
}
