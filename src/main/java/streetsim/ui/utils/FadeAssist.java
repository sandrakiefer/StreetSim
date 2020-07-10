package streetsim.ui.utils;

import javafx.animation.FadeTransition;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

/**
 * Eine Utility-Klasse, die für smoothe Pane-Übergänge sorgt.
 */
public class FadeAssist {

    private static FadeAssist instance;

    private FadeAssist() {
    }

    public static FadeAssist getInstance() {
        if (instance == null) instance = new FadeAssist();
        return instance;
    }

    /**
     * Einfaden des Panes
     *
     * @param pane Pane, welches eingeblendet werden soll.
     */
    public void fadeInPane(Pane pane) {
        FadeTransition fadeIn = new FadeTransition(Duration.millis(300), pane);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.play();
    }

    /**
     * Crossfade zwischen zwei Panes.
     *
     * @param paneToHide Pane, welches aktuell angezeigt wird
     * @param paneToShow Pane, welches als Nachergebnis angezeigt werden soll.
     */
    public void crossFade(Pane paneToHide, Pane paneToShow) {
        FadeTransition fadeOut = new FadeTransition(Duration.millis(300), paneToHide);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);
        fadeOut.setOnFinished(e -> fadeInPane(paneToShow));
        fadeOut.play();
    }
}
