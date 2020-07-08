package streetsim.ui.spielfeld.elemente;

import javafx.scene.control.Button;
import streetsim.business.Auto;
import streetsim.business.Strassennetz;
import streetsim.ui.AbstractController;
import streetsim.ui.StreetSimApp;

public class AutoOverlayController extends AbstractController<StreetSimApp> {

    private Button loesche;
    private Auto aktuellesAuto;

    public AutoOverlayController(Strassennetz netz){
        super(netz);
        rootView = new AutoOverlay();
        loesche = ((AutoOverlay) rootView).loesche;
        disable();
        handlerAnmelden();

    }

    public void enable(){
        loesche.setDisable(false);
        rootView.setVisible(true);
    }

    public void disable(){
        loesche.setDisable(true);
        rootView.setVisible(false);
    }

    @Override
    public void handlerAnmelden() {
        loesche.setOnAction(e -> loescheAuto(aktuellesAuto));
    }

    public void setPosition(int x, int y){((AutoOverlay) rootView).setPosition(x,y);}

    public void setAuto(Auto a){
        aktuellesAuto = a;
    }

    private void loescheAuto(Auto auto){
        netz.entfAuto(auto);
        disable();
    }
}
