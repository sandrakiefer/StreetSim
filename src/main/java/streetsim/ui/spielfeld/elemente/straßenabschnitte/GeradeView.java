package streetsim.ui.spielfeld.elemente.straßenabschnitte;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import streetsim.ui.utils.ResourceAssist;

public class GeradeView extends ImageView {

    public GeradeView(){
        super();

        ResourceAssist assist = ResourceAssist.getInstance();
        Image image = new Image(assist.holeRessourceAusOrdnern("assets", "straßenabschnitte", "gerade.png"));

    }
}
