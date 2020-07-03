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
public class Kurve extends Strassenabschnitt {

    public Kurve(int positionX, int positionY) {
        super(positionX, positionY, definiereRichtungen());
    }

    public static List<Himmelsrichtung> definiereRichtungen() {
        List<Himmelsrichtung> richtungen = new ArrayList();
        richtungen.add(Himmelsrichtung.OSTEN);
        richtungen.add(Himmelsrichtung.SUEDEN);
        return richtungen;
    }

}
