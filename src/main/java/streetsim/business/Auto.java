package streetsim.business;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.shape.Rectangle;

import java.util.List;
import java.util.Optional;
import java.util.Random;

/**
 * In eine Himmelsrichtung sich fortbewegendes Objekt
 * welches auf das Strassennetz, inbesondere Ampeln, reagiert
 */
public class Auto {

    public enum AutoModell {ROT, POLIZEI, BLAU}

    private int geschwindigkeit;
    private static final int MAXGESCHWINDIGKEIT = 8;
    private SimpleObjectProperty<Himmelsrichtung> richtung = new SimpleObjectProperty<>(this, "richtung");

    private Himmelsrichtung abbiegerichtung;
    private int abbiegePunktX;
    private int abbiegePunktY;

    private SimpleIntegerProperty positionX;
    private SimpleIntegerProperty positionY;
    private int breite;
    private int laenge;
    private AutoModell autoModell;
    @JsonIgnore
    private Strassennetz strassennetz;
    @JsonIgnore
    private Rectangle rectangle;

    public Auto(float geschwindigkeitsfaktor, Himmelsrichtung richtung, int positionX, int positionY, int breite, int laenge, AutoModell autoModell) {
        //TODO: geschwindigkeit default am anfang?
        //TODO: Himmelsrichtung lieber berechnen?
        setGeschwindigkeit(geschwindigkeitsfaktor);
        this.richtung.set(richtung);
        this.positionX = new SimpleIntegerProperty(positionX);
        this.positionY = new SimpleIntegerProperty(positionY);
        this.breite = breite;
        this.laenge = laenge;
        this.strassennetz = Strassennetz.getInstance();
        this.autoModell = autoModell;
        initRectangle();
        // vertikal Auto richtig positionieren
        if (richtung.getX() == 0) {
            positionX = (int) (positionX - (positionX % Strassenabschnitt.GROESSE) + (positionX / 2) + (richtung.naechstes().getX() * breite * 0.5));
        }
        // horizontal Auto richtig positionieren
        else {
            positionY = (int) (positionY - (positionY % Strassenabschnitt.GROESSE) + (positionY / 2) + (richtung.naechstes().getY() * breite * 0.5));
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
        // TODO: Linksabbieger muss andere beachten

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
            // Neue Richtung bestimmen
            List<Himmelsrichtung> r = kreuzung.get().getRichtungen();
            if(r.contains(this.getRichtung().gegenueber())){
                r.remove(this.getRichtung().gegenueber());
            }
            abbiegerichtung = r.get(new Random().nextInt(r.size()));
            int mittelpunktX = kreuzung.get().getPositionX() + kreuzung.get().getGroesse() / 2;
            int mittelpunktY = kreuzung.get().getPositionY() + kreuzung.get().getGroesse() / 2;

            if(abbiegerichtung.equals(this.richtung.get().naechstes())){
                // Rechtsabbieger
                this.abbiegePunktX = mittelpunktX + ((this.richtung.get().gegenueber().getX() + this.richtung.get().naechstes().getX()) * this.breite / 2);
                this.abbiegePunktY = mittelpunktY + ((this.richtung.get().gegenueber().getY() + this.richtung.get().naechstes().getY()) * this.laenge / 2);
            } else if(abbiegerichtung.naechstes().equals(this.richtung.get())){
                // Linksabbieger
                this.abbiegePunktX = mittelpunktX + ((this.richtung.get().getX() + this.richtung.get().naechstes().getX()) * this.breite / 2);
                this.abbiegePunktY = mittelpunktY + ((this.richtung.get().getY() + this.richtung.get().naechstes().getY()) * this.laenge / 2);
               }

            if (kreuzung.get().isAmpelAktiv()) {
                if (strassennetz.stehtAnAmpel(this, kreuzung.get())) {
                    return;
                }
            } else {
                // Abbiegerichtung beachten
                // TODO: STVO
            }
        }

        if (abbiegerichtung.equals(richtung.get())) {
            this.positionX.add(this.richtung.get().getX() * geschwindigkeit);
            this.positionY.add(this.richtung.get().getY() * geschwindigkeit);
        } else {
            int distanz = Math.abs(this.positionX.get() - abbiegePunktX) + Math.abs(this.positionY.get() - abbiegePunktY);
            if (geschwindigkeit > distanz) {
                this.richtung.set(abbiegerichtung);
                this.positionX.set(abbiegePunktX + abbiegerichtung.getX() * (geschwindigkeit - distanz));

            }
        }
        // Fahren!
            //if weg > position -> wendepunkt



        //if (strassennetz == null) strassennetz = Strassennetz.getInstance();

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

    public void setGeschwindigkeit(float geschwindigkeitsfaktor) {
        this.geschwindigkeit = Math.min(Math.round(geschwindigkeitsfaktor * MAXGESCHWINDIGKEIT), MAXGESCHWINDIGKEIT);
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
