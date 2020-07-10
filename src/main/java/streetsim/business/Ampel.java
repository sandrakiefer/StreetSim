package streetsim.business;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * Abbildung einer einzelnen Ampel mit den passenden Zuständen
 * (rot, gelb, grün, an/aus)
 */
public class Ampel {

    public static final double BREITE = 11;
    public static final double HOEHE = 29;

    private final SimpleBooleanProperty rot;
    private final SimpleBooleanProperty gelb;
    private final SimpleBooleanProperty gruen;
    private final SimpleObjectProperty<Himmelsrichtung> richtung = new SimpleObjectProperty<>();
    private final SimpleDoubleProperty relPosX = new SimpleDoubleProperty();
    private final SimpleDoubleProperty relPosY = new SimpleDoubleProperty();
    private boolean schaltet = false;

    public Ampel(Himmelsrichtung richtung) {
        this.richtung.set(richtung);
        rotiereRelativePosition();
        rot = new SimpleBooleanProperty(true);
        gelb = new SimpleBooleanProperty(false);
        gruen = new SimpleBooleanProperty(false);
    }

    /**
     * Schaltung zwischen den verschiedenen Ampelphasen
     * rot zu rot und gelb zu grün zu gelb zu rot
     */
    public void schalte() {
        if (!schaltet) {
            if (rot.get() && !gelb.get() && !gruen.get()) {
                schaltet = true;
                new Thread(() -> {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    gelb.set(true);
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    rot.set(false);
                    gelb.set(false);
                    gruen.set(true);
                    schaltet = false;
                }).start();
            } else if (!rot.get() && !gelb.get() && gruen.get()) {
                schaltet = true;
                new Thread(() -> {
                    gruen.set(false);
                    gelb.set(true);
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    gelb.set(false);
                    gruen.set(false);
                    rot.set(true);
                    schaltet = false;
                }).start();
            }
        }
    }

    /**
     * setzt die Ampel auf grün
     */
    public void setGruenPhase() {
        rot.set(false);
        gelb.set(false);
        gruen.set(true);
    }

    /**
     * setzt die Ampel auf rot
     */
    public void setRotPhase() {
        gelb.set(false);
        gruen.set(false);
        rot.set(true);
    }

    /**
     * schaltet die Ampel aus
     * (nichts leuchtet)
     */
    public void ausschalten() {
        rot.set(false);
        gelb.set(false);
        gruen.set(false);
    }

    /**
     * rotiert die Ampel um 90° im Uhrzeigersinn
     * (Himmelsrichtung ändert sich)
     */
    public void rotiere() {
        this.richtung.set(richtung.get().naechstes());
        rotiereRelativePosition();
    }

    private void rotiereRelativePosition(){
        double g = Strassenabschnitt.GROESSE;
        double offsetX = BREITE / 2;
        double offsetY = HOEHE / 2;
        switch (richtung.get()) {
            case SUEDEN:
                relPosX.set(((7 * g) / 8.0) - offsetX);
                relPosY.set(((7 * g) / 8.0) - offsetY);
                break;
            case WESTEN:
                relPosX.set((g / 8.0) - offsetX);
                relPosY.set(((7 * g) / 8.0) - offsetY);
                break;
            case NORDEN:
                relPosX.set((g / 8.0) - offsetX);
                relPosY.set((g / 8.0) - offsetY);
                break;
            case OSTEN:
                relPosX.set(((7 * g) / 8.0) - offsetX);
                relPosY.set(( g / 8.0) - offsetY);
                break;
        }
    }

    public SimpleDoubleProperty getRelPosX() {
        return relPosX;
    }

    public SimpleDoubleProperty getRelPosY() {
        return relPosY;
    }

    public Himmelsrichtung getRichtung() {
        return richtung.get();
    }

    public boolean isRot() {
        return rot.get();
    }

    public boolean isGelb() {
        return gelb.get();
    }

    public boolean isGruen() {
        return gruen.get();
    }

    public BooleanProperty isRotProperty() {return  rot;}

    public BooleanProperty isGelbProperty() {return  gelb;}

    public BooleanProperty isGruenProperty() {return  gruen;}

    public SimpleObjectProperty<Himmelsrichtung> richtungProperty() {
        return richtung;
    }

    @Override
    public String toString() {
        String farbe;
        if (rot.get() && !gelb.get() && !gruen.get()) farbe = "rot";
        else if (rot.get() && gelb.get() && !gruen.get()) farbe = "gelb-rot";
        else if (!rot.get() && gelb.get() && !gruen.get()) farbe = "gelb";
        else farbe = "gruen";

        return String.format("Ampel(%s)", farbe);
    }
}
