package streetsim.business.abschnitte;

import streetsim.business.Himmelsrichtung;
import streetsim.business.Strassenabschnitt;

import java.util.Arrays;
import java.util.List;

/**
 * Realisierung eines Straßenabschnitts
 * maximal drei Ampel-Instanzen und
 * drei mögliche Straßenrichtungen
 */
public class TStueck extends Strassenabschnitt {

    public TStueck(int positionX, int positionY) {
        super(positionX, positionY, definiereRichtungen());
    }

    public static List<Himmelsrichtung> definiereRichtungen() {
        return Arrays.asList(Himmelsrichtung.OSTEN, Himmelsrichtung.SUEDEN, Himmelsrichtung.WESTEN);
    }

    @Override
    public String toString() {
        return "TStueck" + super.toString();
    }

}
