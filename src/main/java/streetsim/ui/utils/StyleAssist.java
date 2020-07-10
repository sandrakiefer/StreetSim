package streetsim.ui.utils;

import javafx.scene.layout.Region;

/**
 * Utility-Klasse, die zum einfacheren setzen von Style-Attributen nützlich ist.
 */
public class StyleAssist {

    private static StyleAssist instance;

    private StyleAssist() {
    }

    public static StyleAssist getInstance() {
        if (instance == null) instance = new StyleAssist();
        return instance;
    }

    /**
     * Eine Methode, die auf mehrere JavaFX-{@link Region}s eine gleiche CSS-Klasse anwendet.
     *
     * @param cssKlasse CSS-Klasse, die gesetzt werden soll.
     * @param regions   Regions, auf die die CSS-Klasse angewandt werden soll.
     */
    public void wendeCSSKlassenAn(String cssKlasse, Region... regions) {
        for (Region r : regions) r.getStyleClass().add(cssKlasse);
    }
}
