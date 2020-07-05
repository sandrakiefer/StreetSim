package streetsim.ui.spielfeld.elemente.strassenabschnitte;

import javafx.application.Platform;
import javafx.scene.image.ImageView;
import streetsim.business.Ampel;
import streetsim.business.Strassenabschnitt;

import streetsim.ui.spielfeld.AbstractModelController;
import streetsim.ui.spielfeld.elemente.AmpelController;
import streetsim.ui.spielfeld.elemente.AmpelView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StrassenController extends AbstractModelController<Strassenabschnitt> {

    HashMap<Ampel, AmpelController> alleAmpeln;
    List<AmpelController> alleAmpelCon;


    public StrassenController(Strassenabschnitt model, ImageView rootView) {
        super(model, rootView);
        alleAmpeln = new HashMap<>();
        alleAmpelCon = new ArrayList<>();
        initAmpelPos();
        handlerAnmelden();
    }

    @Override
    public void handlerAnmelden() {

        model.positionXProperty().addListener(c -> {
            Platform.runLater(() -> rootView.setLayoutX(model.getPositionX()));
        });

        model.positionYProperty().addListener(c -> {
            Platform.runLater(() -> rootView.setLayoutY(model.getPositionY()));
        });

        model.getAmpeln().forEach( f -> {
            f.getRelPosX().addListener( change ->{
                Platform.runLater(() -> alleAmpeln.get(f).setAbsolutePosX(model.getPositionX() + f.getRelPosX().intValue()));
            });

            f.getRelPosY().addListener( change ->{
                Platform.runLater(() -> alleAmpeln.get(f).setAbsolutePosY(model.getPositionY() + f.getRelPosY().intValue()));
            });
        });

        model.richtungenProperty().addListener((observable, oldValue, newValue) -> {
            if (model.getRichtungen().size() > 0) {
                rootView.setRotate(rootView.getRotate() + 90);
            }
        });
    }

    public void initAmpelPos(){
        model.getAmpeln().forEach(f -> {
            AmpelView ampelView = new AmpelView();
            AmpelController ampelCon = new AmpelController(f, ampelView);
            alleAmpelCon.add(ampelCon);
            ampelCon.setAbsolutePosX(model.getPositionX() + f.getRelPosX().intValue());
            ampelCon.setAbsolutePosY(model.getPositionY() + f.getRelPosY().intValue());
            alleAmpeln.put(f, ampelCon);
        });
    }

    public List<AmpelController> getAlleAmpelController(){
        return alleAmpelCon;
    }
}
