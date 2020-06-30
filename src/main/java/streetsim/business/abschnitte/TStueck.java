package streetsim.business.abschnitte;

import streetsim.business.Ampel;
import streetsim.business.Himmelsrichtung;
import streetsim.business.Strassenabschnitt;

import java.util.ArrayList;

/**
 * Realisierung eines Strassenabschnitts
 * maximal drei Ampel-Instanzen
 * drei mögliche Strassenrichtungen
 */
public class TStueck extends Strassenabschnitt {
    
    public TStueck(int positionX, int positionY, ArrayList<Himmelsrichtung> richtungen, int groesse, ArrayList<Ampel> ampeln) {
        super(positionX, positionY, richtungen, groesse, ampeln);
    }

}
