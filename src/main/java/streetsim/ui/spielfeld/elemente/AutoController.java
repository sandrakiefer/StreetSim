package streetsim.ui.spielfeld.elemente;

import javafx.scene.image.ImageView;
import streetsim.business.Auto;
import streetsim.ui.spielfeld.AbstractModelController;

public class AutoController extends AbstractModelController<Auto> {


    public AutoController(Auto model, ImageView rootView) {
        super(model, rootView);
        handlerAnmelden();
    }

    @Override
    public void handlerAnmelden() {

    }
}
