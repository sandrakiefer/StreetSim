package streetsim.ui.spielfeld.elemente;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import streetsim.business.Auto;
import streetsim.business.Strassennetz;
import streetsim.ui.AbstractController;
import streetsim.ui.StreetSimApp;
import streetsim.ui.spielfeld.elemente.straßenabschnitte.GeradeView;
import streetsim.ui.spielfeld.elemente.straßenabschnitte.KreuzungView;
import streetsim.ui.spielfeld.elemente.straßenabschnitte.KurveView;
import streetsim.ui.spielfeld.elemente.straßenabschnitte.TStueckView;
import streetsim.ui.utils.ResourceAssist;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Verwaltung von Aktionen im Menü
 */
public class MenueController extends AbstractController<StreetSimApp> {

    enum AutoModelle{

        BLAU(new Image(ResourceAssist.getInstance().holeRessourceAusOrdnern("assets", "autos", "blauesAuto.png"))),
        POLIZEI(new Image(ResourceAssist.getInstance().holeRessourceAusOrdnern("assets", "autos", "polizeiAuto.png"))),
        ROT(new Image(ResourceAssist.getInstance().holeRessourceAusOrdnern("assets", "autos", "rotesAuto.png")));

        private final AutoView view;

        AutoModelle(Image img) {
            view = new AutoView(img);
        }

        public AutoView getView() {
            return view;
        }

        public static List<AutoView> getAllViews(){
            return Arrays.stream(AutoModelle.values()).map(AutoModelle::getView).collect(Collectors.toList());
        }
    }

    GeradeView gerade;
    KreuzungView kreuzung;
    KurveView kurve;
    TStueckView tstueck;
    List<AutoView> autoViews;
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

        handlerAnmelden();
    }

    @Override
    public void handlerAnmelden() {

    }
}
