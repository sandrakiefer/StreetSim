package streetsim.business;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.shape.Rectangle;

/**
 * In eine Himmelsrichtung sich fortbewegendes Objekt
 * welches auf das Strassennetz, inbesondere Ampeln, reagiert
 */
public class Auto {

    private float geschwindigkeit;
    private Himmelsrichtung richtung;
    private SimpleIntegerProperty positionX;
    private SimpleIntegerProperty positionY;
    private int breite;
    private int laenge;
    private String farbe;
    private final Strassennetz strassennetz;
    private Rectangle rectangle;

    public Auto(float geschwindigkeit, Himmelsrichtung richtung, int positionX, int positionY, int breite, int laenge, String farbe, Strassennetz strassennetz) {
        this.geschwindigkeit = geschwindigkeit;
        this.richtung = richtung;
        this.positionX = new SimpleIntegerProperty(positionX);
        this.positionY = new SimpleIntegerProperty(positionY);
        this.breite = breite;
        this.laenge = laenge;
        this.farbe = farbe;
        this.strassennetz = strassennetz;
        rectangle = new Rectangle(positionX,positionY,breite,laenge);
        rectangle.xProperty().bind(this.positionX);
        rectangle.yProperty().bind(this.positionY);
    }

    /**
     * eigenständiges Fahren der Autos
     */
    public void fahre() {
        // TODO: Darstellung (linke oder rechte Seite)
        // TODO: um Geschwindigkeit in aktueller Richtung fahren
        // TODO: Kreuzung und Ampeln checken
        // TODO: kollision
        Position p = new Position(positionX.get(), positionY.get());
        Strassenabschnitt s = strassennetz.getAbschnitte().get(p);
        int mittelpunktX = s.getPositionX() + s.getGroesse() / 2;
        int mittelpunktY = s.getPositionY() + s.getGroesse() / 2;
    }

    public float getGeschwindigkeit() {
        return geschwindigkeit;
    }

    public void setGeschwindigkeit(float geschwindigkeit) {
        this.geschwindigkeit = geschwindigkeit;
    }

    public Himmelsrichtung getRichtung() {
        return richtung;
    }

    public void setRichtung(Himmelsrichtung richtung) {
        this.richtung = richtung;
    }

    public int getPositionX() {
        return positionX.get();
    }

    public SimpleIntegerProperty positionXProperty() {
        return positionX;
    }

    public void setPositionX(int positionX) {
        this.positionX.set(positionX);
    }

    public int getPositionY() {
        return positionY.get();
    }

    public SimpleIntegerProperty positionYProperty() {
        return positionY;
    }

    public void setPositionY(int positionY) {
        this.positionY.set(positionY);
    }

    public int getBreite() {
        return breite;
    }

    public void setBreite(int breite) {
        this.breite = breite;
    }

    public int getLaenge() {
        return laenge;
    }

    public void setLaenge(int laenge) {
        this.laenge = laenge;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

}
