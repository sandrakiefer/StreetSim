package streetsim.ui.spielfeld;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public abstract class AbstractModelController<T> {

    protected T model;
    protected ImageView rootView;

    public AbstractModelController(T model, ImageView rootView) {
        this.model = model;
        this.rootView = rootView;
    }

    public abstract void handlerAnmelden();

    public ImageView getRootView() {
        return rootView;
    }

}
