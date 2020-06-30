package streetsim.ui.spielfeld.elemente;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import streetsim.ui.utils.ResourceAssist;

public class AutoView extends ImageView {

    public AutoView(){
        super();

        ResourceAssist assist = ResourceAssist.getInstance();
        Image image = new Image(assist.holeRessourceAusOrdnern("assets", "straßenabschnitte", "polizeiauto.png"));
    }
}