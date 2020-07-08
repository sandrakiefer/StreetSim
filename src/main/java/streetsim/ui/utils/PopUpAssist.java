package streetsim.ui.utils;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class PopUpAssist {

    public static PopUpAssist instance;

    private PopUpAssist(){}

    public static PopUpAssist getInstance() {
        if (instance == null) instance = new PopUpAssist();
        return instance;
    }

    public Stage createPopUp(Pane pane, Stage owner){
        Stage newWindow = new Stage();

        Scene scene = new Scene(pane);
        newWindow.setScene(scene);
        newWindow.initModality(Modality.WINDOW_MODAL);
        newWindow.initOwner(owner);
        return newWindow;
    }

    public void center(Stage newWindow, Stage owner) {
        Platform.runLater(() -> {
            double x = owner.getX() + (owner.getWidth() / 2);
            double y = owner.getY() + (owner.getHeight() / 2);
            newWindow.setX(x - (newWindow.getWidth() / 2));
            newWindow.setY(y - (newWindow.getHeight() / 2));
        });
    }

    public void fullSize(Stage newWindow, Stage owner) {
        Platform.runLater(() -> {
            newWindow.setWidth(owner.getWidth());
            newWindow.setHeight(owner.getHeight());
        });
    }

    public void transparentize(Stage newWindow) {
        newWindow.initStyle(StageStyle.TRANSPARENT);
        newWindow.getScene().setFill(Color.TRANSPARENT);
    }
}
