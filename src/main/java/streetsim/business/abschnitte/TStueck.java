package streetsim.business.abschnitte;

import streetsim.business.Ampel;
import streetsim.business.Himmelsrichtung;
import streetsim.business.Strassenabschnitt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Realisierung eines Strassenabschnitts
 * maximal drei Ampel-Instanzen
 * drei mögliche Strassenrichtungen
 */
public class TStueck extends Strassenabschnitt {

    public TStueck(int positionX, int positionY) {
        super(positionX, positionY, definiereRichtungen());
    }

    public static List<Himmelsrichtung> definiereRichtungen() {
        List<Himmelsrichtung> richtungen = new ArrayList<>();
        richtungen.add(Himmelsrichtung.OSTEN);
        richtungen.add(Himmelsrichtung.SUEDEN);
        richtungen.add(Himmelsrichtung.WESTEN);
        return richtungen;
    }

}
