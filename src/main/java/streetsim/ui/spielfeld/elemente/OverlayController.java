package streetsim.ui.spielfeld.elemente;

import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import streetsim.business.Auto;
import streetsim.business.Strassenabschnitt;
import streetsim.business.Strassennetz;
import streetsim.ui.AbstractController;
import streetsim.ui.StreetSimApp;

/**
 * Klasse für Aktionen um Straßen/Autos zu editieren.
 *
 * {@inheritDoc}
 */
public class OverlayController extends AbstractController<StreetSimApp> {

    private final Button loescheStrasse, rotiereStrasse, deaktiviereAmpeln, loescheAuto;
    private final MenuButton geschwindigkeit;
    private final Slider geschwSlider;
    private final Image aktImage, deaktImage;
    private final ImageView deaktView;
    private Auto aktuellesAuto;

    /**
     * {@inheritDoc}
     */
    public OverlayController(Strassennetz netz) {
        super(netz);
        rootView = new OverlayView();
        loescheStrasse = ((OverlayView) rootView).loescheStrasse;
        rotiereStrasse = ((OverlayView) rootView).rotiereStrasse;
        deaktiviereAmpeln = ((OverlayView) rootView).deaktiviereAmpeln;
        loescheAuto = ((OverlayView) rootView).loescheAuto;
        geschwindigkeit = ((OverlayView) rootView).geschwindigkeit;
        deaktView = ((OverlayView) rootView).deaktView;
        aktImage = ((OverlayView) rootView).aktImage;
        deaktImage = ((OverlayView) rootView).deaktImage;
        geschwSlider = ((OverlayView) rootView).speed;
        disable();
        handlerAnmelden();
    }

    /**
     * Setzt die Position für die Buttons des ausgewählten Straßenabschnitts.
     * @param x Koordinate des Straßenabschnitts
     * @param y Koordinate des Straßenabschnitts
     */
    public void setPosition(double x, double y) {
        ((OverlayView) rootView).setPosition(x, y);
    }

    /**
     * Setzt die position für die Buttons des ausgewählten Autos.
     * @param x Koordinate des Autos
     * @param y Koordinate des Autos
     */
    public void setAutoPosition(double x, double y) {
        ((OverlayView) rootView).setAutoPos(x, y);
    }

    /**
     * Macht die Buttons für das Löschen, Rotieren des Straßenabschnitts und das (De-)Aktivieren der Ampeln
     * und die RootView sichtbar.
     * Zusätzlich werden die Buttons für das Löschen und das Anpassen der Geschwindigkeit der Autos unsichtbar.
     * @param s Straßenabschnitt, welches editiert werden soll.
     */
    public void enableStrasse(Strassenabschnitt s) {
        deaktView.setImage(s.isAmpelAktiv() ? deaktImage : aktImage);
        s.ampelAktivProperty().addListener((observable, oldValue, newValue) -> deaktView.setImage(s.isAmpelAktiv() ? deaktImage : aktImage));
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

    /**
     * Macht die Buttons für das Löschen und das Anpassen der Geschwindigkeit der Autos und die RootView sichtbar.
     * Zusätzlich werden die Buttons für das Löschen und das Rotieren des Straßenabschnitts und das (De-)Aktivieren der Ampeln unsichtbar.
     */
    public void enableAuto() {
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

    /**
     * Deaktiviert alle Buttons und macht die Overlay-View unsichtbar.
     */
    public void disable() {
        loescheStrasse.setDisable(true);
        rotiereStrasse.setDisable(true);
        deaktiviereAmpeln.setDisable(true);
        loescheAuto.setDisable(true);
        geschwindigkeit.setDisable(true);
        rootView.setVisible(false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void handlerAnmelden() {
        loescheStrasse.setOnAction(e -> loescheStrasse(loescheStrasse.getLayoutX(), loescheStrasse.getLayoutY()));
        loescheAuto.setOnAction(e -> loescheAuto());
        rotiereStrasse.setOnAction(e -> rotiereStrasse(rotiereStrasse.getLayoutX(), rotiereStrasse.getLayoutY()));
        deaktiviereAmpeln.setOnAction(e -> deaktiviereAmpeln(deaktiviereAmpeln.getLayoutX(), deaktiviereAmpeln.getLayoutY()));
        geschwSlider.valueProperty().addListener((observable, oldValue, newValue) -> geschwindigkeitAuto(newValue));
    }

    /**
     * Setzt das aktuell ausgewählte Auto und passt den Geschwindigkeits-Slider an.
     * @param a angeklicktes Auto
     */
    public void aktAuto(Auto a) {
        aktuellesAuto = a;
        geschwSlider.setValue(a.getGeschwindigkeitsfaktor());
    }

    /**
     * Passt die Geschwindigkeit des Autos an
     * @param val Geschwindigkeitswert
     */
    public void geschwindigkeitAuto(Number val){ netz.geschwindigkeitAnpassen(aktuellesAuto, val.floatValue()); }

    private void loescheStrasse(double x, double y) {
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

    private void loescheAuto() {
        netz.entfAuto(aktuellesAuto);
        disable();
    }


}
