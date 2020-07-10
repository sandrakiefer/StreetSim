package streetsim.ui;

import javafx.scene.layout.Pane;
import streetsim.business.Strassennetz;

/**
 * Klasse, die gröbere Komponenten als View/Controller-Container zusammenfasst.
 *
 * @param <T> Haupt-Anwendungsklasse
 */
public abstract class AbstractController<T> {

    protected Strassennetz netz;
    protected Pane rootView;
    protected T app;

    /**
     * Konstruktor, der eine Straßennetz-Instanz und eine Referenz zur Anwendungsklasse bekommt.
     *
     * @param netz Zentrales Model-Objekt
     * @param app  Anwendungsklasse
     */
    public AbstractController(Strassennetz netz, T app) {
        this.netz = netz;
        this.app = app;
    }

    /**
     * Konstruktor, der eine Referenz zur Anwendungsklasse bekommt.
     *
     * @param app Anwendungsklasse
     */
    public AbstractController(T app) {
        this.app = app;
    }

    /**
     * Konstruktor, der eine Straßennetz-Instanz bekommt.
     *
     * @param netz Zentrales Model-Objekt
     */
    public AbstractController(Strassennetz netz) {
        this.netz = netz;
    }

    /**
     * In dieser Methode sollen an observable Attribute entsprechende
     * Handler angemeldet werden und gleichnamige Methoden in der Straßennetz-Klasse
     * anstoßen bzw. auf Model-Änderung reagieren.
     */
    public abstract void handlerAnmelden();

    public Pane getRootView() {
        return rootView;
    }
}
