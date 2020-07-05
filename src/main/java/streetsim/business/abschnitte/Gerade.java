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

    public Gerade(int positionX, int positionY) {
        super(positionX, positionY, definiereRichtungen());
    }

    public static List<Himmelsrichtung> definiereRichtungen() {
        List<Himmelsrichtung> richtungen = new ArrayList<>();
        richtungen.add(Himmelsrichtung.WESTEN);
        richtungen.add(Himmelsrichtung.OSTEN);
        return richtungen;
    }

}
