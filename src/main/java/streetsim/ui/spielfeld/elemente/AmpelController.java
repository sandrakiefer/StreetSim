package streetsim.ui.spielfeld.elemente;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import streetsim.business.Ampel;
import streetsim.ui.spielfeld.AbstractModelController;
import streetsim.ui.utils.ResourceAssist;

public class AmpelController extends AbstractModelController<Ampel> {
    ResourceAssist assist = ResourceAssist.getInstance();
    Image ampelRot = new Image(assist.holeRessourceAusOrdnern("assets", "ampeln", "ampelRot.png"));
    Image ampelGelb = new Image(assist.holeRessourceAusOrdnern("assets", "ampeln", "ampelGelb.png"));
    Image ampelRotGelb = new Image(assist.holeRessourceAusOrdnern("assets", "ampeln", "ampelRotGelb.png"));
    Image ampelGruen = new Image(assist.holeRessourceAusOrdnern("assets", "ampeln", "ampelGruen.png"));

    public AmpelController(Ampel model, ImageView rootView) {
        super(model, rootView);
        ausrichtung();
        handlerAnmelden();
        init();
    }

    public void setAbsolutePosX(double absolutePosX) {
        Platform.runLater(() -> rootView.setLayoutX(absolutePosX));
    }

    public void setAbsolutePosY(double absolutePosY) {
        Platform.runLater(() -> rootView.setLayoutY(absolutePosY));
    }

    public double getAbsolutePosX(){ return rootView.getLayoutX(); }
    public double getAbsolutePosY(){ return rootView.getLayoutY(); }

    @Override
    public void handlerAnmelden() {

        model.isRotProperty().addListener(change ->{
            if (model.isRot() && !model.isGelb() && !model.isGruen()) rootView.setImage(ampelRot);
        });

        model.isGelbProperty().addListener(change ->{
            if (model.isRot() && model.isGelb() && !model.isGruen()) rootView.setImage(ampelRotGelb);
            else if (!model.isRot() && model.isGelb() && !model.isGruen()) rootView.setImage(ampelGelb);
        });

        model.isGruenProperty().addListener(change ->{
            if (!model.isRot() && !model.isGelb() && model.isGruen()) rootView.setImage(ampelGruen);
        });

        model.richtungProperty().addListener(c -> {
            ausrichtung();
        });
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

    public Ampel getModel(){
        return model;
    }

    private void init(){
        if (model.isRot() && !model.isGelb() && !model.isGruen()) rootView.setImage(ampelRot);
        else if (model.isRot() && model.isGelb() && !model.isGruen()) rootView.setImage(ampelRotGelb);
        else if (!model.isRot() && model.isGelb() && !model.isGruen()) rootView.setImage(ampelGelb);
        else if (!model.isRot() && !model.isGelb() && model.isGruen()) rootView.setImage(ampelGruen);
    }

}
