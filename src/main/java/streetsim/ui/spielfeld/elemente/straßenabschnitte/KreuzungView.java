package streetsim.ui.spielfeld.elemente.straßenabschnitte;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import streetsim.ui.utils.ResourceAssist;

public class KreuzungView extends ImageView {

    public KreuzungView(){
        super();

        ResourceAssist assist = ResourceAssist.getInstance();
        Image image = new Image(assist.holeRessourceAusOrdnern("assets", "strassenabschnitte", "kreuzung.png"));
        this.setImage(image);
    }
}
