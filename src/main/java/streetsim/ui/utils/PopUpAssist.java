package streetsim.ui.utils;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Utility-Klasse um PopUp Fenster zu erstellen.
 */
public class PopUpAssist {

    public static PopUpAssist instance;

    private PopUpAssist() {
    }

    public static PopUpAssist getInstance() {
        if (instance == null) instance = new PopUpAssist();
        return instance;
    }

    /**
     * PopUp wird als Modal-Window erzeugt.
     *
     * @param pane  View, die in dem PopUp angezeigt werden soll.
     * @param owner Stage, die unter dem PopUp liegt.
     * @return das fertige PopUp.
     */
    public Stage createPopUp(Pane pane, Stage owner) {
        Stage newWindow = new Stage();

        Scene scene = new Scene(pane);
        newWindow.setScene(scene);
        newWindow.initModality(Modality.WINDOW_MODAL);
        newWindow.initOwner(owner);
        return newWindow;
    }

    /**
     * Zentriert ein (am besten mit {@link #createPopUp(Pane, Stage)} erzeugtes PopUp.
     *
     * @param newWindow PopUp Fenster, das zentriert werden soll.
     * @param owner     Darunterliegende Stage.
     */
    public void center(Stage newWindow, Stage owner) {
        Platform.runLater(() -> {
            double x = owner.getX() + (owner.getWidth() / 2);
            double y = owner.getY() + (owner.getHeight() / 2);
            newWindow.setX(x - (newWindow.getWidth() / 2));
            newWindow.setY(y - (newWindow.getHeight() / 2));
        });
    }

}
