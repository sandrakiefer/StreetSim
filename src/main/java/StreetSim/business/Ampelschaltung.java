package StreetSim.business;

/**
 * Korrekte Schlatung mehrerer Ampeln
 */
public interface Ampelschaltung {

    /**
     * schaltet alle Ampeln auf dem aktuellen Strassenabschnitt um
     */
    public void schalte();

    /**
     * schaltet alle Ampeln auf dem aktuellen Strassenabschnitt
     * nach einem festgelegtem Intervall um
     */
    public void zeitSchalte();

}
