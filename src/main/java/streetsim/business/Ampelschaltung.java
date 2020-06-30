package streetsim.business;

/**
 * Korrekte Schlatung mehrerer Ampeln
 */
public interface Ampelschaltung {

    /**
     * schaltet alle Ampeln auf dem aktuellen Strassenabschnitt um
     */
    void schalte();

    /**
     * schaltet alle Ampeln auf dem aktuellen Strassenabschnitt
     * nach einem festgelegtem Intervall um
     */
    void zeitSchalte();

}
