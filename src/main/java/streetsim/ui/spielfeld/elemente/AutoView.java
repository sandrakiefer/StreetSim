package streetsim.ui.spielfeld.elemente;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import streetsim.business.Auto;
import streetsim.ui.utils.ResourceAssist;

public class AutoView extends ImageView {

    private final Auto.AutoModell autoModell;

    Button b;

    public AutoView(Image image, Auto.AutoModell autoModell){
        super(image);
        this.autoModell = autoModell;
        b = new Button("auto");
        b.setPrefSize(32, 32);

    }

    public AutoView(AutoView autoView){
        this(autoView.getImage(), autoView.autoModell);
    }


    public Auto.AutoModell getAutoModell() {
        return autoModell;
    }
}