package streetsim.business;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;

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
    private Himmelsrichtung richtung;
    private boolean schaltet = false;

    private SimpleDoubleProperty relPosX = new SimpleDoubleProperty(this, "relPosX");
    private SimpleDoubleProperty relPosY = new SimpleDoubleProperty(this, "relPosY");

    public Ampel(Himmelsrichtung richtung) {
        this.richtung = richtung;
        rotiereRelativePosition();
        rot = new SimpleBooleanProperty(true);
        gelb = new SimpleBooleanProperty(false);
        gruen = new SimpleBooleanProperty(false);
    }

    /**
     * Schaltung zwischen den verschiedenen Ampelphasen
     * rot -> rot und gelb -> gruen -> gelb -> rot
     */
    public void schalte() {
        if (!schaltet) {
            if (rot.get() && !gelb.get() && !gruen.get()) {
                schaltet = true;
                new Thread(() -> {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    gelb.set(true);
                    try {
                        Thread.sleep(1000);
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
                    gelb.set(true);
                    gruen.set(false);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    rot.set(true);
                    gelb.set(false);
                    gruen.set(false);
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
        rot.set(true);
        gelb.set(false);
        gruen.set(false);
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
        this.richtung = richtung.naechstes();
        rotiereRelativePosition();
    }

    private void rotiereRelativePosition(){
        double g = Strassenabschnitt.GROESSE;
        double offsetX = BREITE / 2;
        double offsetY = HOEHE / 2;
        switch (richtung) {
            case NORDEN:
                relPosX.set(((7 * g) / 8.0) - offsetX);
                relPosY.set(((7 * g) / 8.0) - offsetY);
                break;
            case OSTEN:
                relPosX.set(((7 * g) / 8.0) - offsetX);
                relPosY.set((g / 8.0) - offsetY);
                break;
            case SUEDEN:
                relPosX.set((g / 8.0) - offsetX);
                relPosY.set((g / 8.0) - offsetY);
                break;
            case WESTEN:
                relPosX.set((g / 8.0) - offsetX);
                relPosY.set(((7 * g) / 8.0) - offsetY);
                break;
        }
    }

    public Himmelsrichtung getRichtung() {
        return richtung;
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

}
