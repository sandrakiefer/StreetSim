package streetsim.ui.spielfeld.elemente;

import javafx.scene.image.ImageView;
import streetsim.business.Auto;
import streetsim.ui.spielfeld.AbstractModelController;

/**
 * Controller für Autos.
 * <p>
 * {@inheritDoc}
 */
public class AutoController extends AbstractModelController<Auto> {

    /**
     * {@inheritDoc}
     */
    public AutoController(Auto model, ImageView rootView) {
        super(model, rootView);

        ausrichtung();
        handlerAnmelden();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void handlerAnmelden() {
        model.positionXProperty().addListener(c -> rootView.setLayoutX(model.getPositionX() - (double) model.getBreite() / 2));

        model.positionYProperty().addListener(c -> rootView.setLayoutY(model.getPositionY() - (double) model.getLaenge() / 2));

        model.richtungProperty().addListener((observable, oldValue, newValue) -> ausrichtung());
    }

    private void ausrichtung() {
        switch (model.getRichtung()) {
            case OSTEN:
                rootView.setRotate(90);
                break;
            case SUEDEN:
                rootView.setRotate(180);
                break;
            case WESTEN:
                rootView.setRotate(270);
                break;
            case NORDEN:
                rootView.setRotate(0);
                break;
        }
    }
}
