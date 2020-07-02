package streetsim.ui.spielfeld;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import streetsim.business.Strassennetz;
import streetsim.ui.AbstractController;
import streetsim.ui.StreetSimApp;
import streetsim.ui.spielfeld.elemente.MenueController;
import streetsim.ui.spielfeld.elemente.NavigationController;
import streetsim.ui.spielfeld.elemente.SpielfeldController;

/**
 * Verwaltung der folgenden drei Controller:
 * SpielviewController, Spielfeldcontroller, MenueController
 */
public class SpielViewController extends AbstractController<StreetSimApp> {
    private BorderPane spielView;

    public SpielViewController(Strassennetz netz, StreetSimApp app) {
        super(netz, app);

        rootView = new StackPane();
        spielView = new BorderPane();

        NavigationController navCon = new NavigationController(netz, app);
        MenueController menCon = new MenueController(netz, app);
        SpielfeldController spielfeldCon = new SpielfeldController(netz, app);

        Pane menView = menCon.getRootView();
        Pane navView = navCon.getRootView();
        Pane spielfeldView = spielfeldCon.getRootView();

        spielView.setRight(menView);
        spielView.setLeft(navView);

        rootView.getChildren().addAll(spielfeldView, spielView);
    }

    @Override
    public void handlerAnmelden() {

    }

    /*
    //TODO:
    Im Pausemenü sobald gedragged wird soll das Seitenmenü eingeklappt werden und erst nach bearbeiten wieder eingeblendet
     */
}
