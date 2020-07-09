package streetsim.ui.spielfeld;

import javafx.scene.image.ImageView;

/**
 * Eine Klasse, die für "kleinteilige" Business-Objekte als Container dient.
 * In ihr soll das Model-Objekt und die zugehörige View verwaltet werden.
 * @param <T> Haupt-Anwendungsklasse
 */
public abstract class AbstractModelController<T> {

    protected T model;
    protected ImageView rootView;

    /**
     * Konstruktor des Model/View-Containers.
     * @param model Model-Objekt
     * @param rootView View-Repräsentation
     */
    public AbstractModelController(T model, ImageView rootView) {
        this.model = model;
        this.rootView = rootView;
    }

    /**
     * In dieser Methode sollen an observable Attribute entsprechende
     * Handler angemeldet werden und auf Model-Änderungen reagiert werden.
     */
    public abstract void handlerAnmelden();

    public ImageView getRootView() {
        return rootView;
    }

}
