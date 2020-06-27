package StreetSim.ui;

import StreetSim.business.Strassennetz;
import javafx.scene.layout.Pane;

/**
 *
 * @param <T> entsprechend des Namen der Applikationsklasse
 */
public abstract class AbstractController<T> {

    protected Strassennetz netz;
    protected Pane rootView;
    protected T app;

    public AbstractController() {

    }

    /**
     * entsprechende Methoden die durch GUI-Interaktion entstehen
     * stoßen über die Strassennetz-Instanz gleichnamige Methoden im Model an
     */
    public void handlerAnmelden() {

    }

}
