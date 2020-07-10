package streetsim.business;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class AmpelTest {

    private Ampel norden;
    private Ampel sueden;
    private Ampel westen;
    private Ampel osten;

    @Test
    @BeforeEach
    public void ampelInit() {
        norden = new Ampel(Himmelsrichtung.NORDEN);
        sueden = new Ampel(Himmelsrichtung.SUEDEN);
        westen = new Ampel(Himmelsrichtung.WESTEN);
        osten = new Ampel(Himmelsrichtung.OSTEN);
    }

    @Test
    @DisplayName("Ampel sollten nach 2 Sekunden geschaltet werden")
    public void testSchaltung() {

        assertTrue(norden.isRot() && !norden.isGelb() && !norden.isGruen());

        assertTimeoutPreemptively(Duration.ofSeconds(4), () -> {
            norden.schalte();
        }, "Sollte fertig geschaltet sein");

        norden.setGruenPhase();

        assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
            norden.schalte();
        }, "Sollte fertig geschaltet sein");

//        assertEquals(true, !norden.isRot() && !norden.isGelb() && norden.isGruen(), "Zustand Gruen erwartet");
//        assertEquals(false, norden.isRot() && !norden.isGelb() && !norden.isGruen(), "Zustand sollte nicht Rot sein");
//        assertEquals(true, norden.isRot() && norden.isGelb() && !norden.isGruen(), "Zustand Rot Gelb erwartet");
//        assertEquals(true, norden.isRot() && !norden.isGelb() && norden.isGruen(), "Zustand Rot erwartet");
    }

    @Test
    @DisplayName("Phasen werden manuell gesetzt")
    public void testPhasen() {

        norden.setGruenPhase();
        assertTrue(!norden.isRot() && !norden.isGelb() && norden.isGruen(), "Zustand Gruen erwartet");

        norden.setRotPhase();
        assertTrue(norden.isRot() && !norden.isGelb() && !norden.isGruen(), "Zustand Rot erwartet");

        norden.ausschalten();
        assertTrue(!norden.isRot() && !norden.isGelb() && !norden.isGruen(), "Ampel sollte ausgeschaltet sein");
    }

    @Test
    @DisplayName("Rotation um 90°")
    public void testRotation() {
        assertEquals(Himmelsrichtung.NORDEN, norden.getRichtung(), "Ampel Norden startet im Norden");
        assertEquals(Himmelsrichtung.OSTEN, osten.getRichtung(), "Ampel Osten startet im Osten");
        assertEquals(Himmelsrichtung.SUEDEN, sueden.getRichtung(), "Ampel Sueden startet im Sueden");
        assertEquals(Himmelsrichtung.WESTEN, westen.getRichtung(), "Ampel Westen startet im Westen");

        norden.rotiere();
        osten.rotiere();
        sueden.rotiere();
        westen.rotiere();

        assertEquals(Himmelsrichtung.OSTEN, norden.getRichtung(), "Ampel Norden im Osten");
        assertEquals(Himmelsrichtung.SUEDEN, osten.getRichtung(), "Ampel Osten im Sueden");
        assertEquals(Himmelsrichtung.WESTEN, sueden.getRichtung(), "Ampel Sueden im Westen");
        assertEquals(Himmelsrichtung.NORDEN, westen.getRichtung(), "Ampel Westen im Norden");

        norden.rotiere();
        osten.rotiere();
        sueden.rotiere();
        westen.rotiere();

        assertEquals(Himmelsrichtung.SUEDEN, norden.getRichtung(), "Ampel Norden im Sueden");
        assertEquals(Himmelsrichtung.WESTEN, osten.getRichtung(), "Ampel Osten im Westen");
        assertEquals(Himmelsrichtung.NORDEN, sueden.getRichtung(), "Ampel Sueden im Norden");
        assertEquals(Himmelsrichtung.OSTEN, westen.getRichtung(), "Ampel Westen im Osten");

        norden.rotiere();
        osten.rotiere();
        sueden.rotiere();
        westen.rotiere();

        assertEquals(Himmelsrichtung.WESTEN, norden.getRichtung(), "Ampel Norden im Sueden");
        assertEquals(Himmelsrichtung.NORDEN, osten.getRichtung(), "Ampel Osten im Westen");
        assertEquals(Himmelsrichtung.OSTEN, sueden.getRichtung(), "Ampel Sueden im Norden");
        assertEquals(Himmelsrichtung.SUEDEN, westen.getRichtung(), "Ampel Westen im Osten");

        norden.rotiere();
        osten.rotiere();
        sueden.rotiere();
        westen.rotiere();

        assertEquals(Himmelsrichtung.NORDEN, norden.getRichtung(), "Ampel Norden wieder in Ausgangsposition");
        assertEquals(Himmelsrichtung.OSTEN, osten.getRichtung(), "Ampel Osten wieder in Ausgangsposition");
        assertEquals(Himmelsrichtung.SUEDEN, sueden.getRichtung(), "Ampel Sueden wieder in Ausgangsposition");
        assertEquals(Himmelsrichtung.WESTEN, westen.getRichtung(), "Ampel Westen wieder in Ausgangsposition");
    }

}
