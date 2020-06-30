package streetsim.ui;

import streetsim.business.Strassennetz;
import javafx.scene.layout.Pane;

/**
 *
 * @param <T> entsprechend des Namen der Applikationsklasse
 */
public abstract class AbstractController<T> {

    protected Strassennetz netz;
    protected Pane rootView;
    protected T app;

    public AbstractController(Strassennetz netz, Pane rootView, T app) {
        this.netz = netz;
        this.rootView = rootView;
        this.app = app;
    }

    public AbstractController(Pane rootView, T app) {
        this.rootView = rootView;
        this.app = app;
    }

    /**
     * entsprechende Methoden die durch GUI-Interaktion entstehen
     * stoßen über die Strassennetz-Instanz gleichnamige Methoden im Model an
     */
    public abstract void handlerAnmelden();



}
