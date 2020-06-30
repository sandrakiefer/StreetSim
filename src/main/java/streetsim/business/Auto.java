package streetsim.business;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.shape.Rectangle;

/**
 * In eine Himmelsrichtung sich fortbewegendes Objekt
 * welches auf das Strassennetz, inbesondere Ampeln, reagiert
 */
public class Auto {

    private SimpleIntegerProperty geschwindigkeit;
    private Himmelsrichtung richtung;
    private SimpleIntegerProperty positionX;
    private SimpleIntegerProperty positionY;
    private int breite;
    private int laenge;
    private String farbe;
    private final Strassennetz strassennetz;
    private Rectangle rectangle;

    public Auto(SimpleIntegerProperty geschwindigkeit, Himmelsrichtung richtung, SimpleIntegerProperty positionX,
                SimpleIntegerProperty positionY, int breite, int laenge, String farbe, Strassennetz strassennetz) {
        this.geschwindigkeit = geschwindigkeit;
        this.richtung = richtung;
        this.positionX = positionX;
        this.positionY = positionY;
        this.breite = breite;
        this.laenge = laenge;
        this.farbe = farbe;
        this.strassennetz = strassennetz;
        rectangle = new Rectangle(positionX.get(),positionY.get(),breite,laenge);
        rectangle.xProperty().bind(positionX);
        rectangle.yProperty().bind(positionY);
    }

    /**
     * eigenständiges Fahren der Autos (durch Thread)
     */
    public void fahre() {

    }

    public int getGeschwindigkeit() {
        return geschwindigkeit.get();
    }

    public SimpleIntegerProperty geschwindigkeitProperty() {
        return geschwindigkeit;
    }

    public void setGeschwindigkeit(int geschwindigkeit) {
        this.geschwindigkeit.set(geschwindigkeit);
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
