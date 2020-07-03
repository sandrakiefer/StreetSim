package streetsim.business.abschnitte;

import streetsim.business.Ampel;
import streetsim.business.Himmelsrichtung;
import streetsim.business.Strassenabschnitt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Realisierung eines Strassenabschnitts
 * maximal vie Ampel-Instanzen
 * vier mögliche Strassenrichtungen
 */
public class Kreuzung extends Strassenabschnitt {

    public Kreuzung(int positionX, int positionY) {
        super(positionX, positionY, definiereRichtungen());
    }

    public static List<Himmelsrichtung> definiereRichtungen() {
        List<Himmelsrichtung> richtungen = Arrays.asList(Himmelsrichtung.values());
        return richtungen;
    }

}
