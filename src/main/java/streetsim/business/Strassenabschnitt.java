package streetsim.business;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Verwaltung der Richtungen in welche ein Strassenabschnitt führt,
 * verwendet Ampelschaltung für optionale Ampeln
 */
public abstract class Strassenabschnitt implements Ampelschaltung, Serializable {

    private SimpleIntegerProperty positionX = new SimpleIntegerProperty(this, "positionX");
    private SimpleIntegerProperty positionY = new SimpleIntegerProperty(this, "positionY");
    private SimpleListProperty<Himmelsrichtung> richtungen;
    public static final int GROESSE = 128;
    public static final int HALTELINIENABSTAND = 48;
    private List<Ampel> ampeln;
    private BooleanProperty ampelAktiv;
    private SimpleIntegerProperty rotiertCounter = new SimpleIntegerProperty();

    public Strassenabschnitt(){}

    public Strassenabschnitt(int positionX, int positionY, List<Himmelsrichtung> richtungen) {
        Position p = new Position(positionX, positionY);
        this.positionX.set(p.getPositionX());
        this.positionY.set(p.getPositionY());
        this.richtungen = new SimpleListProperty<>(FXCollections.observableArrayList(richtungen));
        this.ampeln = baueAmpeln(richtungen);
        ampelAktiv = new SimpleBooleanProperty();
        rotiertCounter.set(0);
    }

    /**
     * aktiviert alle Ampeln der Liste und startet die "zeitSchalt"-Methode
     */
    public void ampelnAktivieren() {
        for (Ampel a : ampeln) {
            if (a.getRichtung().getX() == 0 || ampeln.size() == 2) {
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
        List<Himmelsrichtung> neueRichtungen = new ArrayList<>();
        richtungen.forEach(r -> neueRichtungen.add(r.naechstes()));
        richtungen.clear();
        richtungen.addAll(neueRichtungen);
        for (Ampel a: ampeln) {
            a.rotiere();
        }
        rotiertCounter.set((rotiertCounter.get() + 1) % 4);
    }
    /**
     * Initialisierung der Ampeln mit den passenden Himmelsrichtungen
     *
     * @param richtungen Himmelsrichtung in welche die Ampeln aufgestellt werden
     * @return
     */
    public static List<Ampel> baueAmpeln(List<Himmelsrichtung> richtungen) {
        List<Ampel> ampeln = new ArrayList<>();
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

    public SimpleListProperty<Himmelsrichtung> richtungenProperty() {
        return richtungen;
    }

    public void setRichtungen(ObservableList<Himmelsrichtung> richtungen) {
        this.richtungen.set(richtungen);
    }

    public void setAmpeln(List<Ampel> ampeln) {
        this.ampeln = ampeln;
    }

    public BooleanProperty ampelAktivProperty() {
        return ampelAktiv;
    }

    public int getRotiertCounter() {
        return rotiertCounter.get();
    }

    public SimpleIntegerProperty rotiertCounterProperty() {
        return rotiertCounter;
    }
}
