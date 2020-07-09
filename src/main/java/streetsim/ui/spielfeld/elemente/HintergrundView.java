package streetsim.ui.spielfeld.elemente;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import streetsim.ui.utils.ResourceAssist;

/**
 * Klasse, die mögliche Hintergründe für die {@link SpielfeldView} definiert.
 */
public class HintergrundView extends ImageView {

    /**
     * Konstruktor, der Hintergrundbild festlegt.
     */
    public HintergrundView(){
        super();
        ResourceAssist assist = ResourceAssist.getInstance();
        this.setImage(new Image(assist.holeRessourceAusOrdnern("assets", "hintergruende", "HintergrundGruen.png")));
    }
}
