package streetsim.ui.spielfeld.elemente.strassenabschnitte;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import streetsim.ui.utils.ResourceAssist;

/**
 * Ein View Container für Kurven
 */
public class KurveView extends ImageView {

    /**
     * Konstruktor, welcher ein vordefiniertes Bild lädt.
     */
    public KurveView() {
        super();

        ResourceAssist assist = ResourceAssist.getInstance();
        Image image = new Image(assist.holeRessourceAusOrdnern("assets", "strassenabschnitte", "kurve.png"));
        this.setImage(image);
    }
}
