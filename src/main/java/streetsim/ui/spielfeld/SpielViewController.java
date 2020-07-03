package streetsim.ui.spielfeld;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.*;
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
 * Spielfeldcontroller, NavigationsController, MenueController
 */
public class SpielViewController extends AbstractController<StreetSimApp> {
    private BorderPane spielView;
    private Pane menView, navView, spielfeldView;
    private NavigationController navCon;
    private MenueController menCon;
    private SpielfeldController spielfeldCon;

    public SpielViewController(Strassennetz netz, StreetSimApp app) {
        super(netz, app);

        rootView = new StackPane();
        spielView = new BorderPane();

        navCon = new NavigationController(netz, app);
        menCon = new MenueController(netz, app);
        spielfeldCon = new SpielfeldController(netz, app);

        menView = menCon.getRootView();
        navView = navCon.getRootView();
        spielfeldView = spielfeldCon.getRootView();

        spielView.setRight(menView);
        spielView.setLeft(navView);

        rootView.getChildren().addAll(spielfeldView, spielView);
        handlerAnmelden();
    }

    @Override
    public void handlerAnmelden() {

    }

    /*
    //TODO:
    Im Pausemenü sobald gedragged wird soll das Seitenmenü eingeklappt werden und erst nach bearbeiten wieder eingeblendet
     */
}
