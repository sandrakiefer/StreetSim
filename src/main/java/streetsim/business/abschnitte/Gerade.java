package streetsim.business.abschnitte;

import streetsim.business.Ampel;
import streetsim.business.Himmelsrichtung;
import streetsim.business.Strassenabschnitt;

import java.util.ArrayList;
import java.util.List;

/**
 * Realisierung eines Strassenabschnitts
 * maximal zwei Ampel-Instanzen
 * zwei mögliche Strassenrichtungen
 */
public class Gerade extends Strassenabschnitt {

    public Gerade(int positionX, int positionY, int groesse) {
        // TODO groesse bleibt doch immer gleich?
        super(positionX, positionY, definiereRichtungen(), groesse);
    }

    public static List<Himmelsrichtung> definiereRichtungen() {
        List<Himmelsrichtung> richtungen = new ArrayList();
        richtungen.add(Himmelsrichtung.NORDEN);
        richtungen.add(Himmelsrichtung.SUEDEN);
        return richtungen;
    }

}
