package streetsim.ui.spielfeld.elemente;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import streetsim.ui.utils.ResourceAssist;

public class HintergrundView extends ImageView {

    public HintergrundView(){
        super();
        ResourceAssist assist = ResourceAssist.getInstance();
        this.setImage(new Image(assist.holeRessourceAusOrdnern("assets", "hintergruende", "hintergrundGruen.png")));
    }
}
