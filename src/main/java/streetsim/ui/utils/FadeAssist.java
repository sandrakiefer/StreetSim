package streetsim.ui.utils;

import javafx.animation.FadeTransition;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class FadeAssist {

    private static FadeAssist instance;

    private FadeAssist() {}

    public static FadeAssist getInstance(){
        if (instance == null) instance = new FadeAssist();
        return instance;
    }

    public void fadeInPane(Pane pane) {
        FadeTransition fadeIn = new FadeTransition(Duration.millis(300), pane);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.play();
    }

    public void fadeOutPane(Pane pane) {
        FadeTransition fadeOut = new FadeTransition(Duration.millis(300), pane);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);
        fadeOut.play();
    }

    public void crossFade(Pane paneToHide, Pane paneToShow) {
        FadeTransition fadeOut = new FadeTransition(Duration.millis(300), paneToHide);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);
        fadeOut.setOnFinished(e -> fadeInPane(paneToShow));
        fadeOut.play();
    }
}
