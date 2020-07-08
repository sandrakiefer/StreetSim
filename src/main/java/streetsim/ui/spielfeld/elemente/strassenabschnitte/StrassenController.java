package streetsim.ui.spielfeld.elemente.strassenabschnitte;

import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import streetsim.business.Ampel;
import streetsim.business.Strassenabschnitt;

import streetsim.ui.spielfeld.AbstractModelController;
import streetsim.ui.spielfeld.elemente.AmpelController;
import streetsim.ui.spielfeld.elemente.AmpelView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class StrassenController extends AbstractModelController<Strassenabschnitt> {

    HashMap<Ampel, AmpelController> alleAmpeln;

    public StrassenController(Strassenabschnitt model, ImageView rootView) {
        super(model, rootView);
        alleAmpeln = new HashMap<>();
        initAmpelPos();
        handlerAnmelden();
        init();
    }

    @Override
    public void handlerAnmelden() {
        //TODO: platform run later entfernt muss vielleicht wieder rein
        model.positionXProperty().addListener(c -> rootView.setLayoutX(model.getPositionX()));
        model.positionYProperty().addListener(c -> rootView.setLayoutY(model.getPositionY()));

        //TODO: platform run later entfernt muss vielleicht wieder rein
        model.getAmpeln().forEach(f -> {
            f.getRelPosX().addListener(change -> {
                alleAmpeln.get(f).setAbsolutePosX(model.getPositionX() + f.getRelPosX().intValue());
            });

            f.getRelPosY().addListener(change -> {
                alleAmpeln.get(f).setAbsolutePosY(model.getPositionY() + f.getRelPosY().intValue());
            });
        });

        model.rotiertCounterProperty().addListener((observable, oldValue, newValue) -> {
            rootView.setRotate(rootView.getRotate() + 90);
        });

        model.ampelAktivProperty().addListener(c -> {
            if (!model.ampelAktivProperty().getValue()) {
                getAlleAmpeln().forEach(a -> a.getRootView().setVisible(false));
            } else {
                getAlleAmpeln().forEach(a -> a.getRootView().setVisible(true));
            }
        });
    }

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
