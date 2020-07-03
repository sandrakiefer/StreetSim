package streetsim.business;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.shape.Rectangle;

import java.util.Optional;

/**
 * In eine Himmelsrichtung sich fortbewegendes Objekt
 * welches auf das Strassennetz, inbesondere Ampeln, reagiert
 */
public class Auto {

    // TODO: Anpassung des Wertebereichs der Geschwindigkeit (so maximale Geschwindigkeit = 1 Pixel)
    private float geschwindigkeit;
    private SimpleObjectProperty<Himmelsrichtung> richtung = new SimpleObjectProperty<>(this, "richtung");
    private SimpleIntegerProperty positionX;
    private SimpleIntegerProperty positionY;
    private int breite;
    private int laenge;
    private String farbe;
    @JsonIgnore
    private Strassennetz strassennetz;
    @JsonIgnore
    private Rectangle rectangle;

    public Auto(float geschwindigkeit, Himmelsrichtung richtung, int positionX, int positionY, int breite, int laenge) {
        this.geschwindigkeit = geschwindigkeit;
        this.richtung.set(richtung);
        this.positionX = new SimpleIntegerProperty(positionX);
        this.positionY = new SimpleIntegerProperty(positionY);
        this.breite = breite;
        this.laenge = laenge;
        this.strassennetz = Strassennetz.getInstance();
        initRectangle();
        // vertikal Auto richtig positionieren
        if (richtung.getX() == 0) {
            positionX = (int) (positionX - (positionX % Strassenabschnitt.GROESSE) + (positionX / 2) + (richtung.next().getX() * breite * 0.5));
        }
        // horizontal Auto richtig positionieren
        else {
            positionY = (int) (positionY - (positionY % Strassenabschnitt.GROESSE) + (positionY / 2) + (richtung.next().getY() * breite * 0.5));
        }
    }

    /**
     * Abbilden der Koordinaten auf ein Rechteck
     * (für die Kollisionserkennung)
     */
    private void initRectangle(){
        rectangle = new Rectangle(positionX.doubleValue() - (breite / 2),positionY.doubleValue() - (laenge / 2), breite, laenge);
        rectangle.xProperty().bind(Bindings.subtract(this.positionX, breite / 2));
        rectangle.yProperty().bind(Bindings.subtract(this.positionY, laenge / 2));
    }

    /**
     * eigenständiges Fahren der Autos
     */
    public void fahre() {
        // TODO: Darstellung (linke oder rechte Seite)
        // TODO: um Geschwindigkeit in aktueller Richtung fahren
        // TODO: Kreuzung und Ampeln checken
        // TODO: Kollision

        // Kollisions-Überprüfung (wenn fahren Kollision hervorruft stoppt das Auto)
        Position p = new Position(positionX.get(), positionY.get());
        Rectangle newR = new Rectangle(this.rectangle.getX() + this.richtung.get().getX() * geschwindigkeit,this.rectangle.getY() + this.richtung.get().getY() * geschwindigkeit,breite,laenge);
        if (autoKollision(p, newR)) {
            return;
        }
        // falls Front des Autos in nächsten Abschnitt reinragt
        int vorneX = positionX.get() + richtung.get().getX() * (laenge / 2);
        int vorneY = positionY.get() + richtung.get().getY() * (laenge / 2);
        Position vorneP = new Position(vorneX, vorneY);
        if (!vorneP.equals(p)) {
            if (autoKollision(vorneP, newR)) {
                return;
            }
        }
        // falls Heck des Autos in nächsten Abschnitt reinragt
        int hintenX = positionX.get() - richtung.get().getX() * (laenge / 2);
        int hintenY = positionY.get() - richtung.get().getY() * (laenge / 2);
        Position hintenP = new Position(hintenX, hintenY);
        if (!hintenP.equals(p)) {
            if (autoKollision(hintenP, newR)) {
                return;
            }
        }

        // Kruezungs- und Ampel-Überprüfung
        Optional<Strassenabschnitt> kreuzung = strassennetz.stehtAnKreuzung(this);
        if (kreuzung.isPresent()) {
            if (kreuzung.get().isAmpelAktiv()) {
                if (strassennetz.stehtAnAmpel(this, kreuzung.get())) {
                    return;
                }
            } else {
                // TODO: STVO
            }
        }


        if (strassennetz == null) strassennetz = Strassennetz.getInstance();

    }

    public boolean autoKollision(Position p, Rectangle newR) {
        for (Auto a : strassennetz.getAutos().get(p)) {
            if (newR.intersects(a.getRectangle().getLayoutBounds()) && !this.equals(a)) {
                return true;
            }
        }
        return false;
    }

    public float getGeschwindigkeit() {
        return geschwindigkeit;
    }

    public void setGeschwindigkeit(float geschwindigkeit) {
        this.geschwindigkeit = geschwindigkeit;
    }

    public Himmelsrichtung getRichtung() {
        return richtung.get();
    }

    public void setRichtung(Himmelsrichtung richtung) {
        this.richtung.set(richtung);
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
        if (rectangle == null) initRectangle();
        return rectangle;
    }

    public SimpleObjectProperty<Himmelsrichtung> richtungProperty() {
        return richtung;
    }

}
