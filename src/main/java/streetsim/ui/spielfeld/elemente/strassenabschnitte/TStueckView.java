package streetsim.ui.spielfeld.elemente.strassenabschnitte;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import streetsim.ui.utils.ResourceAssist;

/**
 * Ein View Container für T-Stücke
 */
public class TStueckView extends ImageView {

    /**
     * Konstruktor, welcher ein vordefiniertes Bild lädt.
     */
    public TStueckView(){
        super();

        ResourceAssist assist = ResourceAssist.getInstance();
        Image image = new Image(assist.holeRessourceAusOrdnern("assets", "strassenabschnitte", "tstueck.png"));
        this.setImage(image);
    }
}
