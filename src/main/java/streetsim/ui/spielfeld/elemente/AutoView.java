package streetsim.ui.spielfeld.elemente;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import streetsim.business.Auto;

class AutoView extends ImageView {

    private final Auto.AutoModell autoModell;

    AutoView(Image image, Auto.AutoModell autoModell){
        super(image);
        this.autoModell = autoModell;
    }

    public AutoView(AutoView autoView){
        this(autoView.getImage(), autoView.autoModell);
    }

    public Auto.AutoModell getAutoModell() {
        return autoModell;
    }
}