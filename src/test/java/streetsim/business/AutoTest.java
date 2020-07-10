package streetsim.business;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import streetsim.business.abschnitte.Gerade;
import streetsim.business.abschnitte.Kreuzung;
import streetsim.business.abschnitte.Kurve;
import streetsim.business.abschnitte.TStueck;

import static org.junit.jupiter.api.Assertions.*;

public class AutoTest {

    private Auto instance;
    private Strassennetz netz;

    @BeforeEach
    public void init() {
        netz = Strassennetz.getInstance();

        Kurve ku = new Kurve(50, 50);
        Gerade g = new Gerade(50 + Strassenabschnitt.GROESSE, 50);
        Kreuzung kr = new Kreuzung(50 + (2 * Strassenabschnitt.GROESSE), 50);
        TStueck t = new TStueck(50 + (3 * Strassenabschnitt.GROESSE), 50);

        netz.strasseAdden(ku);
        netz.strasseAdden(g);
        netz.strasseAdden(kr);
        netz.strasseAdden(t);

        int positionX = 110;
        int positionY = 120;
        Auto.AutoModell autoModell = Auto.AutoModell.BLAU;
        instance = new Auto(positionX, positionY, autoModell);

        netz.autoAdden(instance);
    }

    @AfterEach
    public void cleanUp() {
        netz.reset();
    }

    @Test
    public void testRotiere_Richtung() {
        assertEquals(Himmelsrichtung.NORDEN, instance.getRichtung());
        instance.rotiere();
        assertEquals(Himmelsrichtung.OSTEN, instance.getRichtung());
        instance.rotiere();
        assertEquals(Himmelsrichtung.SUEDEN, instance.getRichtung());
        instance.rotiere();
        assertEquals(Himmelsrichtung.WESTEN, instance.getRichtung());
        instance.rotiere();
        assertEquals(Himmelsrichtung.NORDEN, instance.getRichtung());
    }

    @Test
    public void testRotiere_Position() {
        int ausgangsX = instance.getPositionX();
        int ausgangsY = instance.getPositionY();

        instance.rotiere();
        assertNotEquals(ausgangsX, instance.getPositionX());
        assertNotEquals(ausgangsY, instance.getPositionY());

        instance.rotiere();
        instance.rotiere();
        instance.rotiere();
        assertEquals(ausgangsX, instance.getPositionX());
        assertEquals(ausgangsY, instance.getPositionY());
    }

    @Test
    public void testDistanzBisMitte() {
        int dist = 56;
        int mX = 64;
        int mY = 64;
        assertEquals(dist, instance.distanzBisMitte(mX, mY));
    }

    @Test
    public void testDistanzBisMitte_NachRotationGleich() {
        int expDist = 56;
        int mX = 64;
        int mY = 64;

        for (int i = 0; i < 4; i++) {
            int dist = instance.distanzBisMitte(mX, mY);
            assertEquals(expDist, dist);
            instance.rotiere();
        }

    }

    @Test
    public void testFahre_EinmalNachNorden() {
        int expX = instance.getPositionX();
        int expY = instance.getPositionY() - instance.getGeschwindigkeit();
        instance.fahre();
        assertEquals(expX, instance.getPositionX());
        assertEquals(expY, instance.getPositionY());
    }

    @Test
    public void testFahre_AbbiegenNachOsten() {
        int startX = instance.getPositionX();

        /*
         so lange einen Schritt fahren, bis sich X Position ändert.
         sprich, bis Auto abbiegt
         */
        while (startX == instance.getPositionX()) {
            instance.fahre();
        }
        assertEquals(Himmelsrichtung.OSTEN, instance.getRichtung());
    }

    @Test
    public void testUTurn() {
        netz.reset();
        Gerade g = new Gerade(50, 50);
        netz.strasseAdden(g);
        netz.autoAdden(instance);

        Himmelsrichtung prevDir = instance.getRichtung();
        Position startP = new Position(instance.getPositionX(), instance.getPositionY());

        /*
        Annahme: Auto auf einer Geraden fährt ständige U-Turns gegen den Uhrzeigersinn
        -> Auto sollte als nächste Richtung dauernd in die "vorherige" Richtung (also gegen den Uhrzeigersinn)
         */

        Himmelsrichtung curDir;

        int wendeZaehler = 0;
        while (wendeZaehler < 17) {
            curDir = instance.getRichtung();
            instance.fahre();

            /*
            Zusatzbedingung: Position Objekt immer gleich -> Auto bleibt immer auf gleicher Straße
             */
            Position curP = new Position(instance.getPositionX(), instance.getPositionY());
            assertEquals(startP, curP);

            if (!curDir.equals(prevDir)) {
                assertEquals(prevDir.vorheriges(), curDir);
                prevDir = curDir;
                wendeZaehler++;
            }
        }

    }


}
