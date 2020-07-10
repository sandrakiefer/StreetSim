package streetsim.business;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import streetsim.business.abschnitte.Gerade;
import streetsim.business.abschnitte.Kreuzung;
import streetsim.business.abschnitte.Kurve;
import streetsim.business.abschnitte.TStueck;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

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
    @DisplayName("Prüfen der Ausrichtung nach Rotation")
    public void testRotiere_Richtung() {
        assertEquals(Himmelsrichtung.NORDEN, instance.getRichtung(), "Auto Richtung Norden");
        instance.rotiere();
        assertEquals(Himmelsrichtung.OSTEN, instance.getRichtung(), "Auto Richtung Osten");
        instance.rotiere();
        assertEquals(Himmelsrichtung.SUEDEN, instance.getRichtung(), "Auto Richtung Süden");
        instance.rotiere();
        assertEquals(Himmelsrichtung.WESTEN, instance.getRichtung(), "Auto Richtung Westen");
        instance.rotiere();
        assertEquals(Himmelsrichtung.NORDEN, instance.getRichtung(), "Auto Richtung Norden");
    }

    @Test
    @DisplayName("Prüfen der X/Y Koordinaten nach 4 Rotationen")
    public void testRotiere_Position() {
        int ausgangsX = instance.getPositionX();
        int ausgangsY = instance.getPositionY();

        instance.rotiere();
        assertNotEquals(ausgangsX, instance.getPositionX(), "Nach einer Rotation ungleiche Koordinaten");
        assertNotEquals(ausgangsY, instance.getPositionY(), "Nach einer Rotation ungleiche Koordinaten");

        instance.rotiere();
        instance.rotiere();
        instance.rotiere();
        assertEquals(ausgangsX, instance.getPositionX(), "Nach 4 Rotationen wieder gleiche Koordinaten");
        assertEquals(ausgangsY, instance.getPositionY(), "Nach 4 Rotationen wieder gleiche Koordinaten");
    }

    @Test
    @DisplayName("Test der Distanz")
    public void testDistanzBisMitte() {
        int dist = 56;
        int mX = 64;
        int mY = 64;
        assertEquals(dist, instance.distanzBisMitte(mX, mY));
    }

    @Test
    @DisplayName("Nach jeder Rotation soll die Distanz zum Mittelpunkt gleich sein")
    public void testDistanzBisMitte_NachRotationGleich() {
        int expDist = 56;
        int mX = 64;
        int mY = 64;

        for (int i = 0; i < 4; i++) {
            int dist = instance.distanzBisMitte(mX, mY);
            assertEquals(expDist, dist, "Gleiche Distanz wie zu Beginn");
            instance.rotiere();
        }

    }

    @Test
    @DisplayName("Auto soll 4 Pixel nach Norden")
    public void testFahre_EinmalNachNorden() {
        int expX = instance.getPositionX();
        int expY = instance.getPositionY() - instance.getGeschwindigkeit();
        instance.fahre();
        assertEquals(expX, instance.getPositionX(), "X unverändert");
        assertEquals(expY, instance.getPositionY(), "Y 4 kleiner");
    }

    @Test
    @DisplayName("Auto soll auf der Kurve nach Abbiegen Richtung Osten fahren")
    public void testFahre_AbbiegenNachOsten() {
        int startX = instance.getPositionX();

        /*
         so lange einen Schritt fahren, bis sich X Position ändert.
         sprich, bis Auto abbiegt
         */
        while (startX == instance.getPositionX()) {
            instance.fahre();
        }
        assertEquals(Himmelsrichtung.OSTEN, instance.getRichtung(), "Auto fährt nach Osten");
    }

    @Test
    @DisplayName("Test auf einer Geraden, ob Auto wenden kann")
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
            assertEquals(startP, curP, "Auto soll Abschnitt nicht verlassen");

            if (!curDir.equals(prevDir)) {
                assertEquals(prevDir.vorheriges(), curDir, "Richtung sollte gegen den Uhrzeigersinn gedreht sein");
                prevDir = curDir;
                wendeZaehler++;
            }
        }

    }


}
