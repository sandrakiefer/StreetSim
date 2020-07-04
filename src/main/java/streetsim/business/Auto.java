package streetsim.business;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.shape.Rectangle;

import java.util.*;

/**
 * In eine Himmelsrichtung sich fortbewegendes Objekt
 * welches auf das Strassennetz, inbesondere Ampeln, reagiert
 */
public class Auto {

    public enum AutoModell {ROT, POLIZEI, BLAU}

    private int geschwindigkeit;
    private static final int MAXGESCHWINDIGKEIT = 8;
    private SimpleObjectProperty<Himmelsrichtung> richtung = new SimpleObjectProperty<>(this, "richtung");

    private Stack<Wendepunkt> wendepunkte;

    private SimpleIntegerProperty positionX;
    private SimpleIntegerProperty positionY;
    private int breite;
    private int laenge;
    private AutoModell autoModell;
    @JsonIgnore
    private Strassennetz strassennetz;
    @JsonIgnore
    private Rectangle rectangle;

    public Auto(int positionX, int positionY, int breite, int laenge, AutoModell autoModell) {
        setGeschwindigkeit(0.5f);
        this.positionX = new SimpleIntegerProperty(positionX);
        this.positionY = new SimpleIntegerProperty(positionY);
        this.breite = breite;
        this.laenge = laenge;
        this.strassennetz = Strassennetz.getInstance();
        this.autoModell = autoModell;
        this.wendepunkte = new Stack();
        initRectangle();
        positionierung();
    }

    public void positionierung() {
        int offX = (this.positionX.get() % Strassenabschnitt.GROESSE) - Strassenabschnitt.GROESSE / 2;
        int offY = (this.positionY.get() % Strassenabschnitt.GROESSE) - Strassenabschnitt.GROESSE / 2;
        if (Math.abs(offX) < Math.abs(offY)) {
            if (offX < 0) {
                this.richtung.set(Himmelsrichtung.SUEDEN);
                this.positionX.set((Strassenabschnitt.GROESSE - this.breite) / 2);
            } else {
                this.richtung.set(Himmelsrichtung.NORDEN);
                this.positionX.set((Strassenabschnitt.GROESSE + this.breite) / 2);
            }
        } else {
            if (offY < 0) {
                this.richtung.set(Himmelsrichtung.WESTEN);
                this.positionY.set((Strassenabschnitt.GROESSE - this.breite) / 2);
            } else {
                this.richtung.set(Himmelsrichtung.OSTEN);
                this.positionY.set((Strassenabschnitt.GROESSE + this.breite) / 2);
            }
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
        Strassenabschnitt aktuellerAbschnitt = strassennetz.getAbschnitte().get(p);
        int mittelpunktX = aktuellerAbschnitt.getPositionX() + aktuellerAbschnitt.getGroesse() / 2;
        int mittelpunktY = aktuellerAbschnitt.getPositionY() + aktuellerAbschnitt.getGroesse() / 2;
        if (strassennetz.stehtAnKreuzung(this)) {
            // Neue Richtung bestimmen
            List<Himmelsrichtung> r = aktuellerAbschnitt.getRichtungen();
            r.remove(this.getRichtung().gegenueber());
            Himmelsrichtung abbiegerichtung = r.get(new Random().nextInt(r.size()));
            if(abbiegerichtung.equals(this.richtung.get().naechstes())){
                // Rechtsabbieger
                int x = mittelpunktX + ((this.richtung.get().gegenueber().getX() + this.richtung.get().naechstes().getX()) * this.breite / 2);
                int y = mittelpunktY + ((this.richtung.get().gegenueber().getY() + this.richtung.get().naechstes().getY()) * this.laenge / 2);
                wendepunkte.push(new Wendepunkt(x,y,abbiegerichtung));
            } else if(abbiegerichtung.naechstes().equals(this.richtung.get())){
                // Linksabbieger
                int x = mittelpunktX + ((this.richtung.get().getX() + this.richtung.get().naechstes().getX()) * this.breite / 2);
                int y = mittelpunktY + ((this.richtung.get().getY() + this.richtung.get().naechstes().getY()) * this.laenge / 2);
                wendepunkte.push(new Wendepunkt(x,y,abbiegerichtung));
            }
            if (aktuellerAbschnitt.isAmpelAktiv()) {
                if (strassennetz.stehtAnAmpel(this, aktuellerAbschnitt)) {
                    return;
                } else if (abbiegerichtung.equals(richtung.get().naechstes())) {
                    // links abbiegen
                    if (kreuzungBlockiert(p,mittelpunktX,mittelpunktY,List.of(richtung.get().gegenueber()))) {
                        return;
                    }
                }
            } else {
                // Abbiegerichtung beachten
                // Rechtsabbieger dürfen immer fahren
                if (!abbiegerichtung.equals(richtung.get().naechstes())) {
                    if (abbiegerichtung.equals(richtung.get())) {
                        // gerade fahren
                        Himmelsrichtung rechtsVonUns = richtung.get().vorheriges();
                        if (kreuzungBlockiert(p, mittelpunktX, mittelpunktY, List.of(rechtsVonUns))) {
                            return;
                        }
                    } else {
                        // links abbiegen
                        Himmelsrichtung rechtsVonUns = richtung.get().vorheriges();
                        Himmelsrichtung gegenueberVonuns = richtung.get().gegenueber();
                        if (kreuzungBlockiert(p, mittelpunktX, mittelpunktY, List.of(rechtsVonUns, gegenueberVonuns))) {
                            return;
                        }
                    }
                }
            }
        }
        // U-Turn
        if (distanzBisMitteKleiner(-50, mittelpunktX, mittelpunktY)) {
            Position naechsterAbschnitt = new Position(p.getPositionX() + this.richtung.get().getX(), p.getPositionY() + this.richtung.get().getY());
            if (!(strassennetz.getAbschnitte().containsKey(naechsterAbschnitt) && strassennetz.getAbschnitte().get(naechsterAbschnitt).getRichtungen().contains(this.getRichtung().gegenueber()))) {
                // Distanz des Wendepunkts vom Mittelpunkt
                int wendepunktDistanz = 60;
                int basisX = mittelpunktX + richtung.get().getX() * wendepunktDistanz;
                int basisY = mittelpunktY + richtung.get().getY() * wendepunktDistanz;
                int w1x = basisX + (richtung.get().naechstes().getX() * breite / 2);
                int w1y = basisY + (richtung.get().naechstes().getY() * breite / 2);
                int w2x = basisX + (richtung.get().vorheriges().getX() * breite / 2);
                int w2y = basisY + (richtung.get().vorheriges().getY() * breite / 2);
                wendepunkte.push(new Wendepunkt(w1x, w1y, richtung.get().vorheriges()));
                wendepunkte.push(new Wendepunkt(w2x, w2y, richtung.get().gegenueber()));
            }
        }
        // fahren und Wendepunkte dabei beachten
        int distanz = (wendepunkte.size() > 0) ? wendepunkte.peek().distanzBisWendepunkt(this.positionX.get(), this.positionY.get()) : 0;
        if (wendepunkte.size() > 0 &&  distanz < geschwindigkeit) {
            Wendepunkt w = wendepunkte.pop();
            this.richtung.set(w.getRichtung());
            this.positionX.set(w.getX() + w.getRichtung().getX() * (geschwindigkeit - distanz));
            this.positionY.set(w.getY() + w.getRichtung().getY() * (geschwindigkeit - distanz));
        } else {
            this.positionX.add(this.richtung.get().getX() * geschwindigkeit);
            this.positionY.add(this.richtung.get().getY() * geschwindigkeit);
        }
        // Auto in neuen Strassenabschnitt verlegen
        Position neu = new Position(positionX.get(), positionY.get());
        if (!neu.equals(p)) {
            strassennetz.getAutos().get(p).remove(this);
            strassennetz.getAutos().get(neu).add(this);
        }
    }

    public int distanzBisMitte(int mittelpunktX, int mittelpunktY) {
        return this.getRichtung().getX() * (mittelpunktX - this.getPositionX()) + this.getRichtung().getY() * (mittelpunktY - this.getPositionY());
    }

    public boolean distanzBisMitteKleiner(int schranke, int mittelpunktX, int mittelpunktY) {
        int distanzBisMitte = this.distanzBisMitte(mittelpunktX, mittelpunktY);
        if (distanzBisMitte > 0 && distanzBisMitte < schranke) {
            return true;
        }
        return false;
    }

    public boolean kreuzungBlockiert(Position p, int mittelpunktX, int mittelpunktY, List<Himmelsrichtung> h) {
        for (Auto a: strassennetz.getAutos().get(p)) {
            if (h.contains(a.getRichtung())) {
                // Bereichsprüfung
                if (distanzBisMitteKleiner(40, mittelpunktX,mittelpunktY)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean autoKollision(Position p, Rectangle newR) {
        for (Auto a : strassennetz.getAutos().get(p)) {
            if (newR.intersects(a.getRectangle().getLayoutBounds()) && !this.equals(a)) {
                return true;
            }
        }
        return false;
    }

    class Wendepunkt {

        private int x;
        private int y;
        private Himmelsrichtung richtung;

        public Wendepunkt(int x, int y, Himmelsrichtung richtung) {
            this.x = x;
            this.y = y;
            this.richtung = richtung;
        }

        public int distanzBisWendepunkt(int x, int y) {
           return Math.abs(x - this.x) + Math.abs(y - this.y);
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public Himmelsrichtung getRichtung() {
            return richtung;
        }

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
