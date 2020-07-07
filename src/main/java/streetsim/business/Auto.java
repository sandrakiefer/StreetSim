package streetsim.business;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Pos;
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

    private final Queue<Wendepunkt> wendepunkte;

    private final SimpleIntegerProperty positionX;
    private final SimpleIntegerProperty positionY;
    private final int breite;
    private final int laenge;
    private AutoModell autoModell;
    private final transient Strassennetz strassennetz;
    private Rectangle rectangle;

    public Auto(int positionX, int positionY, AutoModell autoModell) {
        setGeschwindigkeit(0.5f);
        this.positionX = new SimpleIntegerProperty(positionX);
        this.positionY = new SimpleIntegerProperty(positionY);
        this.breite = 32;
        this.laenge = 32;
        this.strassennetz = Strassennetz.getInstance();
        this.autoModell = autoModell;
        this.wendepunkte = new LinkedList();
        initRectangle();
        positionierung();
    }

    /**
     * Positionierung und Ausrichtung des Autos
     * an nächstgelegener Stelle des Strassenabschnittes
     */
    public void positionierung() {
        // Distanz der oberen linken Ecke zum Mittelpunkt
        int offX = (this.positionX.get() % Strassenabschnitt.GROESSE) - Strassenabschnitt.GROESSE / 2;
        int offY = (this.positionY.get() % Strassenabschnitt.GROESSE) - Strassenabschnitt.GROESSE / 2;
        // Wert welcher näher zur Mitte liegt wird angepasst
        if (Math.abs(offX) < Math.abs(offY)) {
            // Trennung nach Richtung aus welcher der Punkt angepasst wird (rechts/links)
            if (offX < 0) {
                this.richtung.set(Himmelsrichtung.SUEDEN);
                this.positionX.set(this.positionX.get() - (this.positionX.get() % Strassenabschnitt.GROESSE) + ((Strassenabschnitt.GROESSE - this.breite) / 2));

            } else {
                this.richtung.set(Himmelsrichtung.NORDEN);
                this.positionX.set(this.positionX.get() - (this.positionX.get() % Strassenabschnitt.GROESSE) + ((Strassenabschnitt.GROESSE + this.breite) / 2));

            }
        } else {
            // Trennung nach Richtung aus welcher der Punkt angepasst wird (oben/unten)
            if (offY < 0) {
                this.richtung.set(Himmelsrichtung.WESTEN);
                this.positionY.set(this.positionY.get() - (this.positionY.get() % Strassenabschnitt.GROESSE) + ((Strassenabschnitt.GROESSE - this.breite) / 2));
            } else {
                this.richtung.set(Himmelsrichtung.OSTEN);
                this.positionY.set(this.positionY.get() - (this.positionY.get() % Strassenabschnitt.GROESSE) + ((Strassenabschnitt.GROESSE + this.breite) / 2));
            }
        }
    }

    /**
     * Abbilden der Koordinaten des Autos auf ein Rechteck
     * (für die Kollisionserkennung)
     */
    private void initRectangle(){
        rectangle = new Rectangle(positionX.doubleValue() - (breite / 2),positionY.doubleValue() - (laenge / 2), breite, laenge);
        // automatische Anpassung an die Position
        rectangle.xProperty().bind(Bindings.subtract(this.positionX, breite / 2));
        rectangle.yProperty().bind(Bindings.subtract(this.positionY, laenge / 2));
    }

    /**
     * eigenständiges Fahren des Autos
     *
     * Überprüfung auf eventuell eintretende Kollision
     * Überprüfung und Berücksichtigung der aktuellen Ampelphase
     * Berücksichtigung der Verkehrsregeln (rechts vor links)
     * Überprüfung auf Fortführung des Strassenabschnittes (Einleitung eines U-Turns)
     * Fahren unter Berücksichtigung möglicher zuvor ermittelter Wendepuntke (Abbiegen, U-Turn)
     * mögliches Versetzen des Autos in neuen Strassenabschnitt (Handover)
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
        // Kreuzungs- und Ampel-Überprüfung
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
                wendepunkte.add(new Wendepunkt(x,y,abbiegerichtung));
            } else if(abbiegerichtung.naechstes().equals(this.richtung.get())){
                // Linksabbieger
                int x = mittelpunktX + ((this.richtung.get().getX() + this.richtung.get().naechstes().getX()) * this.breite / 2);
                int y = mittelpunktY + ((this.richtung.get().getY() + this.richtung.get().naechstes().getY()) * this.laenge / 2);
                wendepunkte.add(new Wendepunkt(x,y,abbiegerichtung));
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
                wendepunkte.add(new Wendepunkt(w1x, w1y, richtung.get().vorheriges()));
                wendepunkte.add(new Wendepunkt(w2x, w2y, richtung.get().gegenueber()));
            }
        }
        // fahren und Wendepunkte dabei beachten
        int distanz = (wendepunkte.size() > 0) ? wendepunkte.peek().distanzBisWendepunkt(this.positionX.get(), this.positionY.get()) : 0;
        if (wendepunkte.size() > 0 &&  distanz < geschwindigkeit) {
            Wendepunkt w = wendepunkte.remove();
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

    /**
     * Berechnung der Distanz bis zum Mittelpunkt
     * unter Berücksichtigung der Bewegungsrichtung
     * (Rechtsverkehr Offset wird vernachlässigt)
     *
     * @param mittelpunktX Mittelpunkt-Koordinate-X des aktuellen Abschnitts
     * @param mittelpunktY Mittelpunkt-Koordinate-Y des aktuellen Abschnitts
     * @return Distanz
     */
    public int distanzBisMitte(int mittelpunktX, int mittelpunktY) {
        return this.getRichtung().getX() * (mittelpunktX - this.getPositionX()) + this.getRichtung().getY() * (mittelpunktY - this.getPositionY());
    }

    /**
     * Überprüfung der Distanz nach gegebener Schranke
     *
     * @param schranke Grenze ab welcher true zurückgegeben wird
     * @param mittelpunktX Mittelpunkt-Koordinate-X des aktuellen Abschnitts
     * @param mittelpunktY Mittelpunkt-Koordinate-Y des aktuellen Abschnitts
     * @return Distanz bis Mitte ist kleiner als Schranke (Vorzeichen definiert Richtung)
     */
    public boolean distanzBisMitteKleiner(int schranke, int mittelpunktX, int mittelpunktY) {
        int distanzBisMitte = this.distanzBisMitte(mittelpunktX, mittelpunktY);
        if (distanzBisMitte > 0 && distanzBisMitte < schranke) {
            return true;
        }
        return false;
    }

    /**
     * Überprüfung ob Autos eines Abschnittes
     * (fahren in angegebener Himmelsrichtung)
     * sich im relevanten Bereich zum Abbiegen
     * des Abschnittes befinden
     *
     * @param p Position des Strassenabschnitts
     * @param mittelpunktX Mittelpunkt-Koordinate-X des aktuellen Abschnitts
     * @param mittelpunktY Mittelpunkt-Koordinate-Y des aktuellen Abschnitts
     * @param h Liste von zu beachtenden Himmelsrichtungen
     * @return Kreuzung (Bereich zum Abbiegen) ist blockiert
     */
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

    /**
     * Überprüfung der zu fahrenden Strecke
     * auf Kollision mit anderem Auto
     *
     * @param p Position des Strassenabschnitts
     * @param newR Rechteck des Autos auf künftiger Position
     * @return ob nächste Position des Autos verfügbar ist (ohne Kollision mit anderem Auto)
     */
    public boolean autoKollision(Position p, Rectangle newR) {
        for (Auto a : strassennetz.getAutos().get(p)) {
            if (newR.intersects(a.getRectangle().getLayoutBounds()) && !this.equals(a)) {
                return true;
            }
        }
        return false;
    }

    /**
     * rotiert ein Auto um 90° im Uhrzeigersinn
     */
    public void rotiere() {
        Position p = new Position(this.getPositionX(), this.getPositionY());
        Strassenabschnitt aktuellerAbschnitt = strassennetz.getAbschnitte().get(p);
        int mittelpunkt[] = {aktuellerAbschnitt.getPositionX() + aktuellerAbschnitt.getGroesse() / 2, aktuellerAbschnitt.getPositionY() + aktuellerAbschnitt.getGroesse() / 2};
        int alterPunkt[] = {this.getPositionX(), this.getPositionY()};
        int zwPunkt[] = {(int)(Math.cos(270.0) * (alterPunkt[0] - mittelpunkt[0])) + (int)(-Math.sin(270.0) * (alterPunkt[1] - mittelpunkt[1])),
                         (int)(Math.sin(270.0) * (alterPunkt[0] - mittelpunkt[0])) + (int)(Math.cos(270.0) * (alterPunkt[1] - mittelpunkt[1]))};
        int neuerPunkt[] = {zwPunkt[0] + mittelpunkt[0], zwPunkt[1] + mittelpunkt[1]};
        this.setPositionX(neuerPunkt[0]);
        this.setPositionY(neuerPunkt[1]);
        this.setRichtung(this.getRichtung().naechstes());
    }

    /**
     * innere Klasse zur Definition eines Wendepunktes
     * bestehend aus den Koordinaten und der Richtung nach Wendung
     */
    class Wendepunkt {

        private int x;
        private int y;
        private Himmelsrichtung richtung;

        public Wendepunkt(int x, int y, Himmelsrichtung richtung) {
            this.x = x;
            this.y = y;
            this.richtung = richtung;
        }

        /**
         * Berechnet Manhatten-Distanz bis zum übergebenen Koordinaten
         *
         * @param x X-Koordinate
         * @param y Y-Koordinate
         * @return Distanz
         */
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

    public int getLaenge() {
        return laenge;
    }

    public Rectangle getRectangle() {
        if (rectangle == null) initRectangle();
        return rectangle;
    }

    public SimpleObjectProperty<Himmelsrichtung> richtungProperty() {
        return richtung;
    }

    public Auto.AutoModell getAutoModell(){
        return this.autoModell;
    }
}
