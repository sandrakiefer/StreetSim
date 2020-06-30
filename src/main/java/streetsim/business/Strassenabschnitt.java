package streetsim.business;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import java.util.ArrayList;

/**
 * Verwaltung der Richtungen in welche ein Strassenabschnitt führt,
 * verwendet Ampelschaltung für optionale Ampeln
 */
public abstract class Strassenabschnitt implements Ampelschaltung {

    private int positionX;
    private int positionY;
    private ArrayList<Himmelsrichtung> richtungen;
    private int groesse;
    private ArrayList<Ampel> ampeln;
    private BooleanProperty ampelAktiv;

    public Strassenabschnitt(int positionX, int positionY, ArrayList<Himmelsrichtung> richtungen, int groesse, ArrayList<Ampel> ampeln) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.richtungen = richtungen;
        this.groesse = groesse;
        this.ampeln = ampeln;
        ampelAktiv = new SimpleBooleanProperty();
    }

    /**
     * aktiviert alle Ampeln der Liste und startet die "zeitSchalt"-Methode
     */
    public void ampelnAktivieren() {
        for (Ampel a : ampeln) {
            if (a.getRichtung().getX() == 0) {
                a.setGruenTrue();
            }
        }
        ampelAktiv.setValue(true);
        zeitSchalte();
    }

    @Override
    public void schalte() {
        for (Ampel a: ampeln) {
            a.schalte();
        }
    }

    @Override
    public void zeitSchalte() {
        // TODO: Zeit-Intervall festlegen
        int millisek = 10000;
        new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    if (ampelAktiv.get()) {
                        schalte();
                        Thread.sleep(millisek - 100);
                    } else {
                        Thread.currentThread().interrupt();
                    }
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public ArrayList<Himmelsrichtung> getRichtungen() {
        return richtungen;
    }

    public int getGroesse() {
        return groesse;
    }

    public ArrayList<Ampel> getAmpeln() {
        return ampeln;
    }

    public boolean isAmpelAktiv() {
        return ampelAktiv.get();
    }

    public BooleanProperty ampelAktivProperty() {
        return ampelAktiv;
    }

    public int getPositionX() {
        return positionX;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }
}
