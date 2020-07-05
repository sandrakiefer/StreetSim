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

    public AbstractController(Strassennetz netz, T app) {
        this.netz = netz;
        this.app = app;
    }

    public AbstractController(T app) {
        this.app = app;
    }

    public AbstractController(Strassennetz netz) {
        this.netz = netz;
    }

    /**
     * entsprechende Methoden die durch GUI-Interaktion entstehen
     * stoßen über die Strassennetz-Instanz gleichnamige Methoden im Model an
     */
    public abstract void handlerAnmelden();

    public Pane getRootView() {
        return rootView;
    }
}
