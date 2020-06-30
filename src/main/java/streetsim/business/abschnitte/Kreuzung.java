package streetsim.business.abschnitte;

import streetsim.business.Ampel;
import streetsim.business.Himmelsrichtung;
import streetsim.business.Strassenabschnitt;

import java.util.ArrayList;

/**
 * Realisierung eines Strassenabschnitts
 * maximal vie Ampel-Instanzen
 * vier mögliche Strassenrichtungen
 */
public class Kreuzung extends Strassenabschnitt {

    public Kreuzung(int positionX, int positionY, ArrayList<Himmelsrichtung> richtungen, int groesse, ArrayList<Ampel> ampeln) {
        super(positionX, positionY, richtungen, groesse, ampeln);
    }

    @Override
    public void ampelnAktivieren() {

    }

    @Override
    public void schalte() {

    }

    @Override
    public void zeitSchalte() {

    }
}
