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
 * Verwaltung von Aktionen im Menü
 */
public class MenueController extends AbstractController<StreetSimApp> {

//    enum AutoModelle {
//
//        BLAU(new Image(ResourceAssist.getInstance().holeRessourceAusOrdnern("assets", "autos", "blauesAuto.png")), Auto.AutoModell.BLAU),
//        POLIZEI(new Image(ResourceAssist.getInstance().holeRessourceAusOrdnern("assets", "autos", "polizeiAuto.png")), Auto.AutoModell.POLIZEI),
//        ROT(new Image(ResourceAssist.getInstance().holeRessourceAusOrdnern("assets", "autos", "rotesAuto.png")), Auto.AutoModell.ROT);
//
//        private final AutoView view;
//
//        AutoModelle(Image img, Auto.AutoModell autoModell) {
//            view = new AutoView(img, autoModell);
//        }
//
//        public AutoView getView() {
//            return view;
//        }
//
//        public static List<AutoView> getAllViews() {
//            return Arrays.stream(AutoModelle.values()).map(AutoModelle::getView).collect(Collectors.toList());
//        }
//    }


    GeradeView gerade;
    KreuzungView kreuzung;
    KurveView kurve;
    TStueckView tstueck;
    List<AutoView> autoViews;
    List<ImageView> alleViews;
    AmpelView ampelView;

    public MenueController(Strassennetz netz, StreetSimApp app) {
        super(netz, app);

        rootView = new MenueView(AutoModelle.getAllViews());

        gerade = ((MenueView) rootView).gerade;
        kreuzung = ((MenueView) rootView).kreuzung;
        kurve = ((MenueView) rootView).kurve;
        tstueck = ((MenueView) rootView).tstueck;
        autoViews = ((MenueView) rootView).autoViews;
        ampelView = ((MenueView) rootView).ampelView;
        ampelView.setImage(new Image(ResourceAssist.getInstance().holeRessourceAusOrdnern("assets", "ampeln", "ampelRotGelb.png")));

        List<ImageView> viewList = List.of(gerade, kreuzung, kurve, tstueck, ampelView);
        alleViews = new ArrayList<>();
        alleViews.addAll(viewList);
        alleViews.addAll(autoViews);

        handlerAnmelden();
    }

    @Override
    public void handlerAnmelden() {
        alleViews.forEach(e -> {
            e.setOnDragDetected(event -> {

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

            });
        });
    }
}
