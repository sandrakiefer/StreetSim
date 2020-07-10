package streetsim.ui.spielfeld.elemente.strassenabschnitte;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import streetsim.ui.utils.ResourceAssist;

/**
 * Ein View Container für Geraden
 */
public class GeradeView extends ImageView {

    /**
     * Konstruktor, welcher ein vordefiniertes Bild lädt.
     */
    public GeradeView() {
        super();

        ResourceAssist assist = ResourceAssist.getInstance();
        Image image = new Image(assist.holeRessourceAusOrdnern("assets", "strassenabschnitte", "gerade.png"));
        this.setImage(image);
    }
}
