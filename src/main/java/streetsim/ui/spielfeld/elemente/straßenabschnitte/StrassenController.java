package streetsim.ui.spielfeld.elemente.straßenabschnitte;

import javafx.application.Platform;
import javafx.scene.image.ImageView;
import streetsim.business.Strassenabschnitt;
import streetsim.business.abschnitte.Gerade;

import streetsim.ui.spielfeld.AbstractModelController;

public class StrassenController extends AbstractModelController<Strassenabschnitt> {


    public StrassenController(Strassenabschnitt model, ImageView rootView) {
        super(model, rootView);
        handlerAnmelden();
    }

    @Override
    public void handlerAnmelden() {

        model.positionXProperty().addListener(c -> {
            Platform.runLater(() -> rootView.setLayoutX(model.getPositionX()));
        });

        model.positionYProperty().addListener(c -> {
            Platform.runLater(() -> rootView.setLayoutY(model.getPositionY()));
        });

    }
}
