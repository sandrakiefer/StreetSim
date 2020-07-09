package streetsim.business.abschnitte;

import streetsim.business.Himmelsrichtung;
import streetsim.business.Strassenabschnitt;

import java.util.Arrays;
import java.util.List;

/**
 * Realisierung eines Strassenabschnitts
 * maximal zwei Ampel-Instanzen und
 * zwei mögliche Strassenrichtungen
 */
public class Gerade extends Strassenabschnitt {

    public Gerade(int positionX, int positionY) {
        super(positionX, positionY, definiereRichtungen());
    }

    public static List<Himmelsrichtung> definiereRichtungen() {
        return Arrays.asList(Himmelsrichtung.WESTEN, Himmelsrichtung.OSTEN);
    }

    @Override
    public String toString() {
        return "Gerade" + super.toString();
    }
}
