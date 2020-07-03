package streetsim.ui.utils;

import javafx.scene.layout.Region;

public class StyleAssist {

    private static StyleAssist instance;

    private StyleAssist (){}

    public static StyleAssist getInstance() {
        if (instance == null) instance = new StyleAssist();
        return instance;
    }

    public void wendeCSSKlassenAn(String cssKlasse, Region... regions) {
        for (Region r : regions) r.getStyleClass().add(cssKlasse);
    }
}
