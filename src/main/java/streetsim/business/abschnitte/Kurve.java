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
public class Kurve extends Strassenabschnitt {

    public Kurve(int positionX, int positionY, ArrayList<Himmelsrichtung> richtungen, int groesse, ArrayList<Ampel> ampeln) {
        super(positionX, positionY, richtungen, groesse, ampeln);
    }

}
