package streetsim.business;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Verwaltung der Richtungen in welche ein Strassenabschnitt führt,
 * verwendet Ampelschaltung für optionale Ampeln
 */
public abstract class Strassenabschnitt implements Ampelschaltung {

    private SimpleIntegerProperty positionX = new SimpleIntegerProperty(this, "positionX");
    private SimpleIntegerProperty positionY = new SimpleIntegerProperty(this, "positionY");
    private List<Himmelsrichtung> richtungen;
    public static final int GROESSE = 128;
    private List<Ampel> ampeln;
    private BooleanProperty ampelAktiv;

    public Strassenabschnitt(int positionX, int positionY, List<Himmelsrichtung> richtungen) {
        Position p = new Position(positionX, positionY);
        this.positionX.set(p.getPositionX());
        this.positionY.set(p.getPositionY());
        this.richtungen = richtungen;
        this.ampeln = baueAmpeln(richtungen);
        ampelAktiv = new SimpleBooleanProperty();
    }

    /**
     * aktiviert alle Ampeln der Liste und startet die "zeitSchalt"-Methode
     */
    public void ampelnAktivieren() {
        for (Ampel a : ampeln) {
            if (a.getRichtung().getX() == 0) {
                a.setGruenPhase();
            } else {
                a.setRotPhase();
            }
        }
        ampelAktiv.setValue(true);
    }

    /**
     * rotiert den Strassenabschnitt um 90° im Uhrzeigersinn
     */
    public void rotiere() {
        for (Himmelsrichtung r: richtungen) {
            r = r.naechstes();
        }
        for (Ampel a: ampeln) {
            a.rotiere();
        }
    }

    public static List<Ampel> baueAmpeln(List<Himmelsrichtung> richtungen) {
        List<Ampel> ampeln = new ArrayList();
        for (Himmelsrichtung r: richtungen) {
            ampeln.add(new Ampel(r));
        }
        return ampeln;
    }

    @Override
    public void schalte() {
        for (Ampel a: ampeln) {
            a.schalte();
        }
    }

    public List<Himmelsrichtung> getRichtungen() {
        return richtungen;
    }

    public int getGroesse() {
        return GROESSE;
    }

    public List<Ampel> getAmpeln() {
        return ampeln;
    }

    public boolean isAmpelAktiv() {
        return ampelAktiv.get();
    }

    public void setAmpelAktiv(boolean b) {
        ampelAktiv.setValue(b);
    }

    public int getPositionX() {
        return positionX.get();
    }

    public void setPositionX(int positionX) {
        this.positionX.set(positionX);
    }

    public int getPositionY() {
        return positionY.get();
    }

    public void setPositionY(int positionY) {
        this.positionY.set(positionY);
    }

    public SimpleIntegerProperty positionXProperty() {
        return positionX;
    }

    public SimpleIntegerProperty positionYProperty() {
        return positionY;
    }
}
