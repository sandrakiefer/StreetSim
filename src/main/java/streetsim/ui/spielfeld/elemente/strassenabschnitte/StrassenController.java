package streetsim.ui.spielfeld.elemente.strassenabschnitte;

import javafx.scene.image.ImageView;
import streetsim.business.Ampel;
import streetsim.business.Strassenabschnitt;
import streetsim.ui.spielfeld.AbstractModelController;
import streetsim.ui.spielfeld.elemente.AmpelController;
import streetsim.ui.spielfeld.elemente.AmpelView;

import java.util.Collection;
import java.util.HashMap;

/**
 * Controller für Straßenabschnitte.
 *
 * {@inheritDoc}
 */
public class StrassenController extends AbstractModelController<Strassenabschnitt> {

    private final HashMap<Ampel, AmpelController> alleAmpeln;

    /**
     * {@inheritDoc}
     */
    public StrassenController(Strassenabschnitt model, ImageView rootView) {
        super(model, rootView);
        alleAmpeln = new HashMap<>();
        initAmpelPos();
        handlerAnmelden();
        init();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void handlerAnmelden() {

        model.positionXProperty().addListener(c -> rootView.setLayoutX(model.getPositionX()));
        model.positionYProperty().addListener(c -> rootView.setLayoutY(model.getPositionY()));

        /*
         * Meldet für alle Ampeln des Straßenabschnitts einen Listener an, der die endgültige Position der Ampel setzt,
         * wenn sich die relative Position verändert.
         * z.B. beim Rotieren oder Verschieben.
         */
        model.getAmpeln().forEach(f -> {
            f.getRelPosX().addListener(change -> alleAmpeln.get(f).setAbsolutePosX(model.getPositionX() + f.getRelPosX().intValue()));

            f.getRelPosY().addListener(change -> alleAmpeln.get(f).setAbsolutePosY(model.getPositionY() + f.getRelPosY().intValue()));
        });

        /*
         * Wird angestoßen, wenn der Straßenabschnitt rotiert wird und rotiert dementsprechend die dazugehörigen Ampeln.
         */
        model.rotiertCounterProperty().addListener((observable, oldValue, newValue) -> rootView.setRotate(rootView.getRotate() + 90));

        /*
         * Wird angestoßen, sobald die Ampeln für den Straßenabschnitt eingeschaltet wurden.
         */
        model.ampelAktivProperty().addListener(c -> {
            if (!model.ampelAktivProperty().getValue()) {
                getAlleAmpeln().forEach(a -> a.getRootView().setVisible(false));
            } else {
                getAlleAmpeln().forEach(a -> a.getRootView().setVisible(true));
            }
        });
    }

    /**
     * Beim Initialisieren des Straßenabschnitts werden ebenfalls die dazugehörigen Ampeln initialisiert.
     */
    public void initAmpelPos() {
        model.getAmpeln().forEach(f -> {
            AmpelView ampelView = new AmpelView();
            ampelView.setVisible(false);
            AmpelController ampelCon = new AmpelController(f, ampelView);
            ampelCon.setAbsolutePosX(model.getPositionX() + f.getRelPosX().intValue());
            ampelCon.setAbsolutePosY(model.getPositionY() + f.getRelPosY().intValue());
            alleAmpeln.put(f, ampelCon);
            if (model.isAmpelAktiv()) {
                ampelCon.getRootView().setVisible(true);
            }
        });
    }

    public Collection<AmpelController> getAlleAmpeln() {
        return alleAmpeln.values();
    }

    private void init() {
        for (int i = 0; i < model.getRotiertCounter(); i++) {
            rootView.setRotate(rootView.getRotate() + 90);
        }
    }
}
