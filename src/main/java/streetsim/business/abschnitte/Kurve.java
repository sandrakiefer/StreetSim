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
public class Kurve extends Strassenabschnitt {

    public Kurve(int positionX, int positionY) {
        super(positionX, positionY, definiereRichtungen());
    }

    public static List<Himmelsrichtung> definiereRichtungen() {
        return Arrays.asList(Himmelsrichtung.OSTEN, Himmelsrichtung.SUEDEN);
    }

    @Override
    public String toString() {
        return "Kurve" + super.toString();
    }

}
