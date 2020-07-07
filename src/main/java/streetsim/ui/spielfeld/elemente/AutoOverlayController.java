package streetsim.ui.spielfeld.elemente;

import streetsim.business.Strassennetz;
import streetsim.ui.AbstractController;
import streetsim.ui.StreetSimApp;

public class AutoOverlayController extends AbstractController<StreetSimApp> {

    public AutoOverlayController(Strassennetz netz){
        super(netz);
        rootView = new AutoOverlay();

    }


    @Override
    public void handlerAnmelden() {

    }
}
