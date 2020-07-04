package streetsim.business.abschnitte;

import streetsim.business.Himmelsrichtung;
import streetsim.business.Strassenabschnitt;

import java.util.Arrays;
import java.util.List;

/**
 * Realisierung eines Strassenabschnitts
 * maximal vie Ampel-Instanzen und
 * vier mögliche Strassenrichtungen
 */
public class Kreuzung extends Strassenabschnitt {

    public Kreuzung(int positionX, int positionY) {
        super(positionX, positionY, definiereRichtungen());
    }

    public static List<Himmelsrichtung> definiereRichtungen() {
        return Arrays.asList(Himmelsrichtung.values());
    }

}
