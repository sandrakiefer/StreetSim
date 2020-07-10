package streetsim.ui.spielfeld.elemente.strassenabschnitte;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import streetsim.ui.utils.ResourceAssist;

/**
 * Ein View Container für Kreuzungen
 */
public class KreuzungView extends ImageView {

    /**
     * Konstruktor, welcher ein vordefiniertes Bild lädt.
     */
    public KreuzungView() {
        super();

        ResourceAssist assist = ResourceAssist.getInstance();
        Image image = new Image(assist.holeRessourceAusOrdnern("assets", "strassenabschnitte", "kreuzung.png"));
        this.setImage(image);
    }
}
