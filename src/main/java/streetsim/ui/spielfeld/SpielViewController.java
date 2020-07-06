package streetsim.ui.spielfeld;

import javafx.scene.input.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import streetsim.business.Auto;
import streetsim.business.Position;
import streetsim.business.Strassenabschnitt;
import streetsim.business.Strassennetz;
import streetsim.business.abschnitte.Gerade;
import streetsim.business.abschnitte.Kreuzung;
import streetsim.business.abschnitte.Kurve;
import streetsim.business.abschnitte.TStueck;
import streetsim.ui.AbstractController;
import streetsim.ui.StreetSimApp;
import streetsim.ui.spielfeld.elemente.*;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Verwaltung der folgenden drei Controller:
 * Spielfeldcontroller, NavigationsController, MenueController
 */
public class SpielViewController extends AbstractController<StreetSimApp> {
    private BorderPane spielView;
    private Pane menView, navView, spielfeldView, overlayView;
    private NavigationController navCon;
    private MenueController menCon;
    private SpielfeldController spielfeldCon;
    private StrassenOverlayController overlayController;

    public SpielViewController(Strassennetz netz, StreetSimApp app) {
        super(netz, app);

        rootView = new StackPane();
        spielView = new BorderPane();

        navCon = new NavigationController(netz, app);
        menCon = new MenueController(netz, app);
        spielfeldCon = new SpielfeldController(netz, app);
        overlayController = new StrassenOverlayController(netz);

        menView = menCon.getRootView();
        navView = navCon.getRootView();
        spielfeldView = spielfeldCon.getRootView();
        overlayView = overlayController.getRootView();

        spielView.setRight(menView);
        spielView.setLeft(navView);

        rootView.getChildren().addAll(spielfeldView, spielView, overlayView);
        handlerAnmelden();
    }

    @Override
    public void handlerAnmelden() {

        rootView.setOnDragDetected(e -> {
            System.out.println("DRAG DETECTED");
            Dragboard dragboard = rootView.startDragAndDrop(TransferMode.COPY);

            ClipboardContent content = new ClipboardContent();

            Strassenabschnitt s = netz.strasseAnPos((int) Math.round(e.getX()), (int) Math.round(e.getY()));

            if (s != null) {
                content.put(DragDataFormats.ABSCHNITTFORMAT, s);
                dragboard.setContent(content);
            }
        });

        rootView.setOnDragOver(event -> {
            //TODO: MenüView ausblenden

            boolean dropSupported = true;
            Dragboard dragboard = event.getDragboard();

            if (event.getTransferMode() != TransferMode.COPY) dropSupported = false;

            Strassenabschnitt s = netz.strasseAnPos((int) Math.round(event.getX()), (int) Math.round(event.getY()));
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
                        dropSupported = Arrays.stream(Auto.AutoModell.values()).map(Enum::name).collect(Collectors.toList()).contains(dataString);
                        break;
                }
            } else if (dragboard.hasContent(DragDataFormats.ABSCHNITTFORMAT)) {
                if (s != null) dropSupported = false;
            }

            if (dropSupported) event.acceptTransferModes(TransferMode.COPY);
            event.consume();
        });

        rootView.setOnDragDropped(event -> {
            Dragboard dragboard = event.getDragboard();
            if (dragboard.hasString()) {
                String dataString = dragboard.getString();
                Strassenabschnitt s = netz.strasseAnPos((int) Math.round(event.getX()), (int) Math.round(event.getY()));

                switch (dataString) {
                    case DragDataFormats.AMPEL_FORMAT:
                        spielfeldCon.ampelnAktivieren(s);
                        s.ampelnAktivieren();
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
                            Auto.AutoModell am = Auto.AutoModell.valueOf(dataString);
                        }
                        break;
                }
            } else if (dragboard.hasContent(DragDataFormats.ABSCHNITTFORMAT)) {
                Strassenabschnitt s = (Strassenabschnitt) dragboard.getContent(DragDataFormats.ABSCHNITTFORMAT);
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
                    overlayController.setPosition(p.getPositionX(), p.getPositionY());
                    overlayController.enable();
                    return;
                }
            }
            overlayController.disable();
        });

    }

    /*
    //TODO:
    Im Pausemenü sobald gedragged wird soll das Seitenmenü eingeklappt werden und erst nach bearbeiten wieder eingeblendet
     */
}
