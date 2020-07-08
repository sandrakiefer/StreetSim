package streetsim.ui.spielfeld;

import com.google.gson.Gson;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import org.hildan.fxgson.FxGson;
import streetsim.business.*;
import streetsim.business.abschnitte.Gerade;
import streetsim.business.abschnitte.Kreuzung;
import streetsim.business.abschnitte.Kurve;
import streetsim.business.abschnitte.TStueck;
import streetsim.business.exceptions.KeinAbschnittException;
import streetsim.business.exceptions.SchonBelegtException;
import streetsim.ui.AbstractController;
import streetsim.ui.StreetSimApp;
import streetsim.ui.spielfeld.elemente.*;
import streetsim.ui.spielfeld.elemente.strassenabschnitte.GeradeView;
import streetsim.ui.spielfeld.elemente.strassenabschnitte.KreuzungView;
import streetsim.ui.spielfeld.elemente.strassenabschnitte.KurveView;
import streetsim.ui.spielfeld.elemente.strassenabschnitte.TStueckView;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Verwaltung der folgenden drei Controller:
 * Spielfeldcontroller, NavigationsController, MenueController
 */
public class SpielViewController extends AbstractController<StreetSimApp> {
    private BorderPane spielView;
    private Pane menView, navView, spielfeldView, overlayView, autoOverlayView;
    private HintergrundView hv;
    private NavigationController navCon;
    private MenueController menCon;
    private SpielfeldController spielfeldCon;
    private StrassenOverlayController overlayController;
    private AutoOverlayController autoOverlayController;
    private boolean autoOverlay = false;

    public SpielViewController(Strassennetz netz, StreetSimApp app) {
        super(netz, app);

        rootView = new StackPane();
        spielView = new BorderPane();

        hv = new HintergrundView();

        navCon = new NavigationController(netz, app);
        menCon = new MenueController(netz, app);
        spielfeldCon = new SpielfeldController(netz, app);
        overlayController = new StrassenOverlayController(netz);
        autoOverlayController = new AutoOverlayController(netz);

        menView = menCon.getRootView();
        navView = navCon.getRootView();
        spielfeldView = spielfeldCon.getRootView();
        overlayView = overlayController.getRootView();
        autoOverlayView = autoOverlayController.getRootView();

        spielView.setRight(menView);
        spielView.setLeft(navView);

        rootView.getChildren().addAll(hv, spielfeldView, spielView, overlayView);
        handlerAnmelden();
    }

    @Override
    public void handlerAnmelden() {
//TODO: menue view ausklappbar per button sowie einklappen bei dragdetect
        rootView.setOnDragDetected(e -> {
            Strassenabschnitt s = netz.strasseAnPos((int) Math.round(e.getX()), (int) Math.round(e.getY()));


            if (s != null) {

                Dragboard dragboard = rootView.startDragAndDrop(TransferMode.MOVE);
                ClipboardContent content = new ClipboardContent();
                Gson gson = FxGson.coreBuilder().registerTypeAdapter(Strassenabschnitt.class, StrassenAdapter.getInstance())
                        .create();
                String serializedStrasse = gson.toJson(s, Strassenabschnitt.class);

                content.put(DragDataFormats.ABSCHNITTFORMAT, serializedStrasse);

                ImageView imageView;

                if (s instanceof Gerade) imageView = new GeradeView();
                else if (s instanceof Kreuzung) imageView = new KreuzungView();
                else if (s instanceof Kurve) imageView = new KurveView();
                else imageView = new TStueckView();

                for (int i = 0; i < s.getRotiertCounter(); i++) {
                    imageView.setRotate(imageView.getRotate() + 90);
                }
                Image img = imageView.snapshot(null, null);

                dragboard.setDragView(img);
                dragboard.setContent(content);
            }
        });

        rootView.setOnDragOver(event -> {
            //TODO: MenüView ausblenden

            boolean dropSupported = true;
            Dragboard dragboard = event.getDragboard();

            Strassenabschnitt s = netz.strasseAnPos((int) Math.round(event.getX()), (int) Math.round(event.getY()));
            if (event.getTransferMode() == TransferMode.COPY) {
                if (dragboard.hasString()) {
                    String dataString = dragboard.getString();

                    switch (dataString) {
                        case DragDataFormats.AMPEL_FORMAT:
                            if (s == null || s.isAmpelAktiv()) dropSupported = false;
                            break;
                        case DragDataFormats.GERADE_FORMAT:
                        case DragDataFormats.KREUZUNG_FORMAT:
                        case DragDataFormats.KURVE_FORMAT:
                        case DragDataFormats.TSTUECK_FORMAT:
                            if (s != null) dropSupported = false;
                            break;
                        default:
                            dropSupported = Arrays.stream(Auto.AutoModell.values()).map(Enum::name).collect(Collectors.toList()).contains(dataString) && s != null;
                            break;
                    }
                    if (dropSupported) event.acceptTransferModes(TransferMode.COPY);
                }
            } else {
                dropSupported = event.getTransferMode() == TransferMode.MOVE && dragboard.hasContent(DragDataFormats.ABSCHNITTFORMAT) && s == null;
                if (dropSupported) event.acceptTransferModes(TransferMode.MOVE);

            }


            event.consume();
        });

        rootView.setOnDragDropped(event -> {
            Dragboard dragboard = event.getDragboard();
            if (dragboard.hasString()) {
                String dataString = dragboard.getString();
                Strassenabschnitt s = netz.strasseAnPos((int) Math.round(event.getX()), (int) Math.round(event.getY()));

                switch (dataString) {
                    case DragDataFormats.AMPEL_FORMAT:
                        netz.ampelnAktivieren(s);
                        break;
                    case DragDataFormats.GERADE_FORMAT:
                        Gerade g = new Gerade((int) Math.round(event.getX()), (int) Math.round(event.getY()));
                        netz.strasseAdden(g);
                        break;
                    case DragDataFormats.KREUZUNG_FORMAT:
                        Kreuzung kr = new Kreuzung((int) Math.round(event.getX()), (int) Math.round(event.getY()));
                        netz.strasseAdden(kr);
                        break;
                    case DragDataFormats.KURVE_FORMAT:
                        Kurve ku = new Kurve((int) Math.round(event.getX()), (int) Math.round(event.getY()));
                        netz.strasseAdden(ku);
                        break;
                    case DragDataFormats.TSTUECK_FORMAT:
                        TStueck t = new TStueck((int) Math.round(event.getX()), (int) Math.round(event.getY()));
                        netz.strasseAdden(t);
                        break;
                    default:
                        if (Arrays.stream(Auto.AutoModell.values()).map(Enum::name).collect(Collectors.toList()).contains(dataString)) {
                            if (netz.posBelegt(s)) {
                                Auto.AutoModell am = Auto.AutoModell.valueOf(dataString);
                                Auto a = new Auto((int) Math.round(event.getX()), (int) Math.round(event.getY()), am);
                                try {
                                    netz.autoAdden(a);
                                } catch (KeinAbschnittException | SchonBelegtException e) {
                                    // TODO infotext in ui?
                                    event.setDropCompleted(true);
                                    event.consume();
                                    return;
                                }
                            }
                        }
                        break;
                }
            } else if (dragboard.hasContent(DragDataFormats.ABSCHNITTFORMAT)) {
                String serializedStrasse = (String) dragboard.getContent(DragDataFormats.ABSCHNITTFORMAT);
                Gson gson = FxGson.coreBuilder().registerTypeAdapter(Strassenabschnitt.class, StrassenAdapter.getInstance())
                        .enableComplexMapKeySerialization()
                        .create();
                Strassenabschnitt s = gson.fromJson(serializedStrasse, Strassenabschnitt.class);
                netz.bewegeStrasse(s, (int) Math.round(event.getX()), (int) Math.round(event.getY()));
            }


            event.setDropCompleted(true);
            event.consume();


        });

        rootView.setOnMouseClicked(e -> {
            if (e.getButton().equals(MouseButton.SECONDARY)) {
                double x = e.getX();
                double y = e.getY();
                if (netz.strasseAnPos((int) Math.round(x), (int) Math.round(y)) != null) {
                    Position p = new Position((int) Math.round(x), (int) Math.round(y));
//                    spielfeldCon.getAutoMap().get(p).forEach(a -> {
                        //überprüfen ob rechtsklick innerhalb des Autos geschehen ist um Auto Overlay zu aktivieren
//                        if (x <= a.getPositionX()+ a.getBreite()/2 && x >= a.getPositionX()-a.getBreite()/2
//                                && y >= a.getPositionY()-a.getLaenge()/2 && y <= a.getPositionY()+a.getLaenge()/2){
//                            autoOverlayController.setPosition(a.getPositionX(), a.getPositionY());
//                            autoOverlayController.enable();
//                            autoOverlayController.setAuto(a);
//                            System.out.println("autoOverlay roll out!!!!");
//                            autoOverlay = true;
//                            return;
//                        }
//                    });
                    if(!autoOverlay) {
                        overlayController.setPosition(p.getPositionX(), p.getPositionY());
                        overlayController.enable();
                        return;
                    }
                }
            }
            autoOverlay = false;
            overlayController.disable();
            autoOverlayController.disable();
        });

    }

    /*
    //TODO:
    Im Pausemenü sobald gedragged wird soll das Seitenmenü eingeklappt werden und erst nach bearbeiten wieder eingeblendet
     */
}
