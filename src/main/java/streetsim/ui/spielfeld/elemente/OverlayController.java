package streetsim.ui.spielfeld.elemente;

import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import streetsim.business.Auto;
import streetsim.business.Strassenabschnitt;
import streetsim.business.Strassennetz;
import streetsim.ui.AbstractController;
import streetsim.ui.StreetSimApp;

public class OverlayController extends AbstractController<StreetSimApp> {

    private Button loescheStrasse, rotiereStrasse, deaktiviereAmpeln, loescheAuto;
    private MenuButton geschwindigkeit;
    private MenuItem geschwSlider;
    private Auto aktuellesAuto;

    public OverlayController(Strassennetz netz) {
        super(netz);
        rootView = new OverlayView();
        loescheStrasse = ((OverlayView) rootView).loescheStrasse;
        rotiereStrasse = ((OverlayView) rootView).rotiereStrasse;
        deaktiviereAmpeln = ((OverlayView) rootView).deaktiviereAmpeln;
        loescheAuto = ((OverlayView) rootView).loescheAuto;
        geschwindigkeit = ((OverlayView) rootView).geschwindigkeit;
        geschwSlider = ((OverlayView) rootView).geschwindigkeit.getItems().get(0);
        disable();
        handlerAnmelden();
    }

    public void setPosition(double x, double y) {
        ((OverlayView) rootView).setPosition(x, y);
    }

    public void setAutoPosition(double x, double y){ ((OverlayView) rootView).setAutoPos(x , y); }

    public void enableStrasse(){
        loescheStrasse.setDisable(false);
        loescheStrasse.setVisible(true);
        rotiereStrasse.setDisable(false);
        rotiereStrasse.setVisible(true);
        deaktiviereAmpeln.setDisable(false);
        deaktiviereAmpeln.setVisible(true);
        loescheAuto.setDisable(true);
        loescheAuto.setVisible(false);
        geschwindigkeit.setDisable(true);
        geschwindigkeit.setVisible(false);
        rootView.setVisible(true);
    }

    public void enableAuto(){
        loescheStrasse.setDisable(true);
        loescheStrasse.setVisible(false);
        rotiereStrasse.setDisable(true);
        rotiereStrasse.setVisible(false);
        deaktiviereAmpeln.setDisable(true);
        deaktiviereAmpeln.setVisible(false);
        loescheAuto.setDisable(false);
        loescheAuto.setVisible(true);
        geschwindigkeit.setDisable(false);
        geschwindigkeit.setVisible(true);
        rootView.setVisible(true);
    }

    public void disable(){
        loescheStrasse.setDisable(true);
        rotiereStrasse.setDisable(true);
        deaktiviereAmpeln.setDisable(true);
        loescheAuto.setDisable(true);
        geschwindigkeit.setDisable(true);
        rootView.setVisible(false);
    }

    @Override
    public void handlerAnmelden() {
        loescheStrasse.setOnAction(e -> loescheStrasse(loescheStrasse.getLayoutX(), loescheStrasse.getLayoutY()));
        loescheAuto.setOnAction(e -> loescheAuto(aktuellesAuto));
        rotiereStrasse.setOnAction(e -> rotiereStrasse(rotiereStrasse.getLayoutX(), rotiereStrasse.getLayoutY()));
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

    private void loescheAuto(Auto ... a){
        netz.entfAuto(aktuellesAuto);
        disable();
    }

    public void aktAuto(Auto a){
        aktuellesAuto = a;
    }

}
