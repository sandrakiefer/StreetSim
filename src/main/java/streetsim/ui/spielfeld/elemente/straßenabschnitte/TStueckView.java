package streetsim.ui.spielfeld.elemente.straßenabschnitte;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import streetsim.ui.utils.ResourceAssist;

public class TStueckView extends ImageView {

    public TStueckView(){
        super();

        ResourceAssist assist = ResourceAssist.getInstance();
        Image image = new Image(assist.holeRessourceAusOrdnern("assets", "strassenabschnitte", "tstueck.png"));
        this.setImage(image);
    }
}
