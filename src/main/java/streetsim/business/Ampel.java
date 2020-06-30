package streetsim.business;

import javafx.beans.property.SimpleBooleanProperty;

/**
 * Abbildung einer einzelnen Ampel mit den passenden Zuständen
 * (rot, gelb, grün, an/aus)
 */
public class Ampel {

    private SimpleBooleanProperty rot;
    private SimpleBooleanProperty gelb;
    private SimpleBooleanProperty gruen;
    private final Himmelsrichtung richtung;
    private boolean schaltet = false;

    public Ampel(Himmelsrichtung richtung) {
        this.richtung = richtung;
        rot = new SimpleBooleanProperty(true);
        gelb = new SimpleBooleanProperty(false);
        gruen = new SimpleBooleanProperty(false);
    }

    /**
     * Schaltung zwischen den verschiedenen Ampelphasen
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

    public void setGruenTrue() {
        rot.set(false);
        gelb.set(false);
        gruen.set(true);
    }

}
