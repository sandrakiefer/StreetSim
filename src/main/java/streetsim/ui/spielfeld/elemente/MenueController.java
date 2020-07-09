package streetsim.ui.spielfeld.elemente;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import streetsim.business.Strassennetz;
import streetsim.ui.AbstractController;
import streetsim.ui.StreetSimApp;
import streetsim.ui.spielfeld.elemente.strassenabschnitte.GeradeView;
import streetsim.ui.spielfeld.elemente.strassenabschnitte.KreuzungView;
import streetsim.ui.spielfeld.elemente.strassenabschnitte.KurveView;
import streetsim.ui.spielfeld.elemente.strassenabschnitte.TStueckView;
import streetsim.ui.utils.ResourceAssist;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller für Aktionen im Seitenmenü.
 *
 * {@inheritDoc}
 */
public class MenueController extends AbstractController<StreetSimApp> {

    private final List<ImageView> alleViews;

    /**
     * {@inheritDoc}
     */
    public MenueController(Strassennetz netz, StreetSimApp app) {
        super(netz, app);

        rootView = new MenueView(AutoModelle.getAllViews());

        GeradeView gerade = ((MenueView) rootView).gerade;
        KreuzungView kreuzung = ((MenueView) rootView).kreuzung;
        KurveView kurve = ((MenueView) rootView).kurve;
        TStueckView tstueck = ((MenueView) rootView).tstueck;
        List<AutoView> autoViews = ((MenueView) rootView).autoViews;
        AmpelView ampelView = ((MenueView) rootView).ampelView;
        ampelView.setImage(new Image(ResourceAssist.getInstance().holeRessourceAusOrdnern("assets", "ampeln", "ampelRotGelb.png")));

        List<ImageView> viewList = List.of(gerade, kreuzung, kurve, tstueck, ampelView);
        alleViews = new ArrayList<>();
        alleViews.addAll(viewList);
        alleViews.addAll(autoViews);

        handlerAnmelden();
    }

    /**
     * Meldet für alle views ein DragDetected-Event an
     * und übergibt dem {@link Dragboard} den Namen der ausgewählten View als String mit
     * und setzt die DragView zu dem Image der View.
     */
    @Override
    public void handlerAnmelden() {
        alleViews.forEach(e -> e.setOnDragDetected(event -> {

            Dragboard dragboard = e.startDragAndDrop(TransferMode.COPY);

            ClipboardContent content = new ClipboardContent();

            String df;
            if (e instanceof GeradeView) df = DragDataFormats.GERADE_FORMAT;
            else if (e instanceof KreuzungView) df = DragDataFormats.KREUZUNG_FORMAT;
            else if (e instanceof KurveView) df = DragDataFormats.KURVE_FORMAT;
            else if (e instanceof TStueckView) df = DragDataFormats.TSTUECK_FORMAT;
            else if (e instanceof AmpelView) df = DragDataFormats.AMPEL_FORMAT;
            else df = ((AutoView) e).getAutoModell().name();

            dragboard.setDragView(e.getImage());
            content.putString(df);
            dragboard.setContent(content);

        }));
    }

}
