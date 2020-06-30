package streetsim.business.abschnitte;

import streetsim.business.Ampel;
import streetsim.business.Himmelsrichtung;
import streetsim.business.Strassenabschnitt;

import java.util.ArrayList;

/**
 * Realisierung eines Strassenabschnitts
 * maximal zwei Ampel-Instanzen
 * zwei mögliche Strassenrichtungen
 */
public class Gerade extends Strassenabschnitt {

    public Gerade(int positionX, int positionY, ArrayList<Himmelsrichtung> richtungen, int groesse, ArrayList<Ampel> ampeln) {
        super(positionX, positionY, richtungen, groesse, ampeln);
    }

}
