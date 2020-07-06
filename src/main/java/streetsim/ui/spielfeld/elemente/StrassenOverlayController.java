package streetsim.ui.spielfeld.elemente;

import javafx.scene.control.Button;
import streetsim.business.Strassenabschnitt;
import streetsim.business.Strassennetz;
import streetsim.ui.AbstractController;
import streetsim.ui.StreetSimApp;

public class StrassenOverlayController extends AbstractController<StreetSimApp> {

    private Button loesche, rotiere, deaktiviereAmpeln;

    public StrassenOverlayController(Strassennetz netz) {
        super(netz);
        rootView = new StrassenOverlay();
        loesche = ((StrassenOverlay) rootView).loesche;
        rotiere = ((StrassenOverlay) rootView).rotiere;
        deaktiviereAmpeln = ((StrassenOverlay) rootView).deaktiviereAmpeln;
        disable();
        handlerAnmelden();
    }

    public void setPosition(double x, double y) {
        ((StrassenOverlay) rootView).setPosition(x, y);
    }

    public void enable(){
        loesche.setDisable(false);
        rotiere.setDisable(false);
        deaktiviereAmpeln.setDisable(false);
        rootView.setVisible(true);
    }

    public void disable(){
        loesche.setDisable(true);
        rotiere.setDisable(true);
        deaktiviereAmpeln.setDisable(true);
        rootView.setVisible(false);
    }

    @Override
    public void handlerAnmelden() {
        loesche.setOnAction(e -> loescheStrasse(loesche.getLayoutX(), loesche.getLayoutY()));
        rotiere.setOnAction(e -> rotiereStrasse(rotiere.getLayoutX(), rotiere.getLayoutY()));
        deaktiviereAmpeln.setOnAction(e -> deaktiviereAmpeln(deaktiviereAmpeln.getLayoutX(), deaktiviereAmpeln.getLayoutY()));
    }

    private void loescheStrasse(double x, double y){
        Strassenabschnitt s = netz.strasseAnPos((int) Math.round(x), (int) Math.round(y));
        netz.entfStrasse(s);
        disable();
    }

    private void rotiereStrasse(double x, double y) {
        Strassenabschnitt s = netz.strasseAnPos((int) Math.round(x), (int) Math.round(y));
        netz.rotiereStrasse(s);
    }

    private void deaktiviereAmpeln(double x, double y) {
        Strassenabschnitt s = netz.strasseAnPos((int) Math.round(x), (int) Math.round(y));
        if (s.ampelAktivProperty().getValue()) netz.ampelnDeaktivieren(s);
        else netz.ampelnAktivieren(s);
    }

}
