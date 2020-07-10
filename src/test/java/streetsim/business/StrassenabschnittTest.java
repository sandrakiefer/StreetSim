package streetsim.business;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import streetsim.business.abschnitte.Gerade;
import streetsim.business.abschnitte.Kreuzung;
import streetsim.business.abschnitte.Kurve;
import streetsim.business.abschnitte.TStueck;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StrassenabschnittTest {
    List<Himmelsrichtung> k1List;
    List<Himmelsrichtung> k2List;
    List<Himmelsrichtung> k3List;
    List<Himmelsrichtung> k4List;
    List<Himmelsrichtung> g1List;
    List<Himmelsrichtung> g2List;
    List<Himmelsrichtung> u1List;
    List<Himmelsrichtung> u2List;
    List<Himmelsrichtung> u3List;
    List<Himmelsrichtung> u4List;
    List<Himmelsrichtung> t1List;
    List<Himmelsrichtung> t2List;
    List<Himmelsrichtung> t3List;
    List<Himmelsrichtung> t4List;

    Kreuzung k;
    Gerade g;
    Kurve u;
    TStueck t;

    @BeforeEach public void setUp(){
        k1List = List.of(Himmelsrichtung.NORDEN, Himmelsrichtung.OSTEN, Himmelsrichtung.SUEDEN, Himmelsrichtung.WESTEN);
        k2List = List.of(Himmelsrichtung.OSTEN, Himmelsrichtung.SUEDEN, Himmelsrichtung.WESTEN, Himmelsrichtung.NORDEN);
        k3List = List.of(Himmelsrichtung.SUEDEN, Himmelsrichtung.WESTEN, Himmelsrichtung.NORDEN, Himmelsrichtung.OSTEN);
        k4List = List.of(Himmelsrichtung.WESTEN, Himmelsrichtung.NORDEN, Himmelsrichtung.OSTEN, Himmelsrichtung.SUEDEN);

        u1List = List.of(Himmelsrichtung.OSTEN, Himmelsrichtung.SUEDEN);
        u2List = List.of(Himmelsrichtung.SUEDEN, Himmelsrichtung.WESTEN);
        u3List = List.of(Himmelsrichtung.WESTEN, Himmelsrichtung.NORDEN);
        u4List = List.of(Himmelsrichtung.NORDEN, Himmelsrichtung.OSTEN);

        t1List = List.of(Himmelsrichtung.OSTEN, Himmelsrichtung.SUEDEN, Himmelsrichtung.WESTEN);
        t2List = List.of(Himmelsrichtung.SUEDEN, Himmelsrichtung.WESTEN, Himmelsrichtung.NORDEN);
        t3List = List.of(Himmelsrichtung.WESTEN, Himmelsrichtung.NORDEN, Himmelsrichtung.OSTEN);
        t4List = List.of(Himmelsrichtung.NORDEN, Himmelsrichtung.OSTEN, Himmelsrichtung.SUEDEN);

        g1List = List.of(Himmelsrichtung.OSTEN, Himmelsrichtung.WESTEN);
        g2List = List.of(Himmelsrichtung.NORDEN, Himmelsrichtung.SUEDEN);

        k = new Kreuzung(128, 128);
        g = new Gerade(0,0);
        u = new Kurve(128, 0);
        t = new TStueck(0, 128);

    }

    @Test
    public void erzeugeAbschnittTest(){
        assertEquals(Kreuzung.definiereRichtungen(), k.getRichtungen(), "Himmelsrichtungen von Kreuzung muessen uebereinstimmen");
        assertEquals(Gerade.definiereRichtungen(), g.getRichtungen(), "Himmelsrichtungen von Gerade muessen uebereinstimmen");
        assertEquals(Kurve.definiereRichtungen(), u.getRichtungen(), "Himmelsrichtungen von Kurve muessen uebereinstimmen");
        assertEquals(TStueck.definiereRichtungen(), t.getRichtungen(), "Himmelsrichtungen von TStueck muessen uebereinstimmen");

        assertEquals(4, k.getAmpeln().size(), "muss 4 Ampeln beinhalten");
        assertEquals(2, g.getAmpeln().size(), "muss 2 Ampeln beinhalten");
        assertEquals(2, u.getAmpeln().size(), "muss 2 Ampeln beinhalten");
        assertEquals(3, t.getAmpeln().size(), "muss 3 Ampeln beinhalten");
    }

    @Test
    public void ampelnAktivTest(){
        k.ampelnAktivieren();
        g.ampelnAktivieren();
        u.ampelnAktivieren();
        t.ampelnAktivieren();

        assertTrue(k.isAmpelAktiv(), "Ampeln sollten aktiv sein");
        assertTrue(g.isAmpelAktiv(), "Ampeln sollten aktiv sein");
        assertTrue(u.isAmpelAktiv(), "Ampeln sollten aktiv sein");
        assertTrue(t.isAmpelAktiv(), "Ampeln sollten aktiv sein");
    }

    @Test
    public void rotiereTest(){
        k.rotiere();
        g.rotiere();
        u.rotiere();
        t.rotiere();
        assertEquals(k2List, k.getRichtungen(), "Nach Rotation neue Richtungen");
        assertEquals(g2List, g.getRichtungen(), "Nach Rotation neue Richtungen");
        assertEquals(u2List, u.getRichtungen(), "Nach Rotation neue Richtungen");
        assertEquals(t2List, t.getRichtungen(), "Nach Rotation neue Richtungen");

        k.rotiere();
        u.rotiere();
        t.rotiere();
        assertEquals(k3List, k.getRichtungen(), "Nach Rotation neue Richtungen");
        assertEquals(u3List, u.getRichtungen(), "Nach Rotation neue Richtungen");
        assertEquals(t3List, t.getRichtungen(), "Nach Rotation neue Richtungen");

        k.rotiere();
        u.rotiere();
        t.rotiere();
        assertEquals(k4List, k.getRichtungen(), "Nach Rotation neue Richtungen");
        assertEquals(u4List, u.getRichtungen(), "Nach Rotation neue Richtungen");
        assertEquals(t4List, t.getRichtungen(), "Nach Rotation neue Richtungen");


        k.rotiere();
        g.rotiere();
        u.rotiere();
        t.rotiere();
        assertEquals(k1List, k.getRichtungen(), "Ausgangsposition");
        assertEquals(g1List, g.getRichtungen(), "Ausgangsposition");
        assertEquals(u1List, u.getRichtungen(), "Ausgangsposition");
        assertEquals(t1List, t.getRichtungen(), "Ausgangsposition");




    }
}
