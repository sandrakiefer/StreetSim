package streetsim.business;

/**
 * Abbildung einer einzelnen Ampel mit den passenden Zuständen
 * (rot, gelb, grün, an/aus)
 */
public class Ampel {

    private boolean rot;
    private boolean gelb;
    private boolean gruen;
    private final Himmelsrichtung richtung;

    public Ampel(Himmelsrichtung richtung) {
        this.richtung = richtung;
    }

    /**
     * Schaltung zwischen den verschiedenen Ampelphasen
     */
    public void schalte() {

    }

    public Himmelsrichtung getRichtung() {
        return richtung;
    }

    public boolean isRot() {
        return rot;
    }

    public boolean isGelb() {
        return gelb;
    }


    public boolean isGruen() {
        return gruen;
    }

}
