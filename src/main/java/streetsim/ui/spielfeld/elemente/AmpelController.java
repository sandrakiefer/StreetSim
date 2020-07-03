package streetsim.ui.spielfeld.elemente;

import javafx.scene.image.ImageView;
import streetsim.business.Ampel;
import streetsim.ui.spielfeld.AbstractModelController;

public class AmpelController extends AbstractModelController<Ampel> {

    public AmpelController(Ampel model, ImageView rootView) {
        super(model, rootView);
        handlerAnmelden();
    }

    @Override
    public void handlerAnmelden() {

    }
}
