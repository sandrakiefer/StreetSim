package streetsim.ui.spielfeld.elemente;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import streetsim.ui.utils.ResourceAssist;

/**
 * View-Container für Ampeln.
 * Standard-Bild wird bei der Initialisierung geladen.
 */
public class AmpelView extends ImageView {

    /**
     * Konstruktor, der in der View das entsprechende Bild setzt.
     */
    public AmpelView(){
        super();
        ResourceAssist assist = ResourceAssist.getInstance();
        this.setImage(new Image(assist.holeRessourceAusOrdnern("assets", "ampeln", "ampelRot.png")));
    }
}
