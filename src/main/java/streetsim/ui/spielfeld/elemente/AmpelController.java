package streetsim.ui.spielfeld.elemente;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import streetsim.business.Ampel;
import streetsim.ui.spielfeld.AbstractModelController;
import streetsim.ui.utils.ResourceAssist;

/**
 * Controller für Ampeln.
 *
 * {@inheritDoc}
 */
public class AmpelController extends AbstractModelController<Ampel> {

    private final ResourceAssist assist = ResourceAssist.getInstance();

    private final Image ampelRot = new Image(assist.holeRessourceAusOrdnern("assets", "ampeln", "ampelRot.png"));
    private final Image ampelGelb = new Image(assist.holeRessourceAusOrdnern("assets", "ampeln", "ampelGelb.png"));
    private final Image ampelRotGelb = new Image(assist.holeRessourceAusOrdnern("assets", "ampeln", "ampelRotGelb.png"));
    private final Image ampelGruen = new Image(assist.holeRessourceAusOrdnern("assets", "ampeln", "ampelGruen.png"));


    /**
     * {@inheritDoc}
     */
    public AmpelController(Ampel model, ImageView rootView) {
        super(model, rootView);
        ausrichtung();
        init();
        handlerAnmelden();
    }

    /**
     * setzt die endgültige X Koordinate für Ampel fest.
     * @param absolutePosX X-Koordinate
     */
    public void setAbsolutePosX(double absolutePosX) {
        rootView.setLayoutX(absolutePosX);
    }

    /**
     * Setzt die endgültige Y Koordinate für Ampel fest.
     * @param absolutePosY Y-Koordinate
     */
    public void setAbsolutePosY(double absolutePosY) {
        rootView.setLayoutY(absolutePosY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void handlerAnmelden() {

        model.isRotProperty().addListener(change ->{
            if (model.isRot() && !model.isGelb() && !model.isGruen()) Platform.runLater(() -> rootView.setImage(ampelRot));
        });

        model.isGelbProperty().addListener(change ->{
            if (model.isRot() && model.isGelb() && !model.isGruen()) Platform.runLater(()-> rootView.setImage(ampelRotGelb));
            else if (!model.isRot() && model.isGelb() && !model.isGruen()) Platform.runLater(() -> rootView.setImage(ampelGelb));
        });

        model.isGruenProperty().addListener(change -> {
            if (!model.isRot() && !model.isGelb() && model.isGruen()) Platform.runLater(() -> rootView.setImage(ampelGruen));
        });

        model.richtungProperty().addListener(c -> ausrichtung());
    }

    private void ausrichtung(){
        switch (model.getRichtung()) {
            case NORDEN:
                rootView.setRotate(180);
                break;
            case OSTEN:
                rootView.setRotate(270);
                break;
            case SUEDEN:
                rootView.setRotate(0);
                break;
            case WESTEN:
                rootView.setRotate(90);
                break;
        }
    }

    private void init(){
        if (model.isRot() && !model.isGelb() && !model.isGruen()) rootView.setImage(ampelRot);
        else if (model.isRot() && model.isGelb() && !model.isGruen()) rootView.setImage(ampelRotGelb);
        else if (!model.isRot() && model.isGelb() && !model.isGruen()) rootView.setImage(ampelGelb);
        else if (!model.isRot() && !model.isGelb() && model.isGruen()) rootView.setImage(ampelGruen);
    }
}
