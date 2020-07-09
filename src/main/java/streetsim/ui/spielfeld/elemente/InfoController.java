package streetsim.ui.spielfeld.elemente;

import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import streetsim.business.Strassennetz;
import streetsim.ui.AbstractController;
import streetsim.ui.StreetSimApp;
import streetsim.ui.utils.PopUpAssist;

public class InfoController extends AbstractController<StreetSimApp> {

    private Label nachricht;
    private Stage popup;
    private PopUpAssist popAssist;

    public InfoController(StreetSimApp app) {
        super(app);
        rootView = new InfoView();
        nachricht = ((InfoView) rootView).nachricht;
        popAssist = PopUpAssist.getInstance();

        handlerAnmelden();
    }

    @Override
    public void handlerAnmelden() {

    }

    public void zeige(String info) {
        nachricht.setText(info);
        popup = popAssist.createPopUp(rootView, app.getHauptStage());
        // TODO: pos
        popup.initModality(Modality.NONE);
        popup.setAlwaysOnTop(true);
        //TODO: showen, und nach timeout schliessen
        //TODO: integrieren und aufrufen
    }
}
