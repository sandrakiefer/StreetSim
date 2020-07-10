package streetsim.business;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import streetsim.business.abschnitte.Kreuzung;
import streetsim.business.abschnitte.Kurve;
import streetsim.business.abschnitte.TStueck;
import streetsim.business.exceptions.SchonBelegtException;

import static org.junit.jupiter.api.Assertions.*;

public class StrassennetzTest {

    private Strassennetz instance;
    private Kreuzung kreuzung;
    private Position kreuzungP;
    private Kurve kurve;
    private Auto polizei;
    private Position polizeiPos;
    private Auto rot;

    @BeforeEach
    public void setUp() {
        instance = Strassennetz.getInstance();

        kreuzung = new Kreuzung(50, 50);
        kreuzungP = new Position(50, 50);
        instance.strasseAdden(kreuzung);

        kurve = new Kurve(50, 50);

        int positionX = 110;
        int positionY = 120;

        Auto.AutoModell autoModell = Auto.AutoModell.POLIZEI;
        polizei = new Auto(positionX, positionY, autoModell);
        polizeiPos = new Position(positionX, positionY);
        rot = new Auto(positionX, positionY, autoModell);
        instance.autoAdden(polizei);
    }

    @AfterEach
    public void cleanUp() {
        instance.reset();
    }

    @Test
    @DisplayName("Netz gefüllt")
    public void listenTest() {
        assertTrue(instance.getAbschnitte().containsKey(kreuzungP), "Position des Abschnitts muss in der Map als Key vorhanden sein");
        assertTrue(instance.getAbschnitte().containsValue(kreuzung), "Abschnitt muss in der Map als Value vorhanden sein");
        assertTrue(instance.getAutoList().contains(polizei), "Auto muss in autoList");
        assertTrue(instance.getAutos().containsKey(polizeiPos), "Position des Autos muss in der Map als Key vorhanden sein");
        assertTrue(instance.getAutos().get(polizeiPos).contains(polizei), "Auto muss in der Liste der zugehörigen Position vorhanden sein");
    }

    @Test
    @DisplayName("Auto an der Kreuzung")
    public void anKreuzungTest() {
        assertTrue(instance.stehtAnKreuzung(polizei), "Auto muss an der Krezung stehen");
    }

    @Test
    @DisplayName("Auto adden an einer Position, auf der schon ein Auto steht")
    public void autoAdden_SchonBelegtException() {
        assertThrows(SchonBelegtException.class, () -> instance.autoAdden(rot), "An dieser Position ist schon ein Auto.");
    }

    @Test
    @DisplayName("Straße hinzufügen, wo schon eine Straße platziert wurde")
    public void strasseAdden_SchonBelegtException() {
        assertThrows(SchonBelegtException.class, () -> instance.strasseAdden(kurve), "An dieser Position ist schon eine Straße");
    }

    @Test
    @DisplayName("Auto steht an der Ampel")
    public void anAmpelTest() {
        assertTrue(instance.stehtAnAmpel(polizei, kreuzung), "Auto muss an der Ampel stehen");
    }

    @Test
    @DisplayName("Positionen für Autos/Strassen belegt")
    public void posBelegtTest() {
        assertTrue(instance.posBelegt(rot), "Position sollte belegt sein");
        assertTrue(instance.posBelegt(kurve), "Position sollte belegt sein");
    }

    @Test
    @DisplayName("An Position sollte Strassenabschnitt vorhanden sein")
    public void strasseAnPosTest() {
        assertEquals(kreuzung, instance.strasseAnPos(50, 50), "Position muss Kurve beinhalten");
    }

    @Test
    @DisplayName("Auto(s) aus dem Netz entfernen")
    public void entfAutosTest() {
        instance.entfAuto(polizei);
        assertFalse(instance.getAutos().get(polizeiPos).contains(polizei), "Auto sollte nicht mehr in der Map sein");
        assertFalse(instance.getAutoList().contains(polizei), "Auto sollte nicht mehr in der Liste sein");
    }

    @Test
    @DisplayName("Strassenabschnitte aus dem Netz entfernen")
    public void entfAbschnittTest() {
        instance.entfStrasse(kreuzung);
        assertFalse(instance.getAbschnitte().containsKey(kreuzungP), "Position des Abschitts muss aus Map als Key entfernt worden sein");
        assertFalse(instance.getAbschnitte().containsValue(kreuzung), "Abschnitt muss aus der map als Value entfernt sein");
    }

    @Test
    @DisplayName("Ampeln aktivieren/deaktivieren")
    public void ampelnAktDeaktTest() {
        instance.ampelnAktivieren(kreuzung);
        assertTrue(kreuzung.isAmpelAktiv(), "Ampeln sollten aktiviert sein");
        instance.ampelnDeaktivieren(kreuzung);
        assertFalse(kreuzung.isAmpelAktiv(), "Ampeln sollten deaktiviert sein");
    }

    @Test
    @DisplayName("Strasse an neue Position bewegen")
    public void bewegeStrasseTest() {
        instance.bewegeStrasse(kreuzung, 129, 129);
        assertNull(instance.strasseAnPos(50, 50), "sollte keine Strasse an Position beinhalten");
        assertNotNull(instance.strasseAnPos(128, 128), "sollte ein Abschnitt an Postion beinhalten");
        assertEquals(kreuzung, instance.strasseAnPos(128, 128), "sollte der neuplatzierte Abschnitt sein");
    }

    @Test
    @DisplayName("Geschwindigkeit anpassen")
    public void geschwindigkeitAnpassenTest() {
        assertEquals(4, polizei.getGeschwindigkeit(), "Geschwindigkeit default auf 4");
        instance.geschwindigkeitAnpassen(polizei, 1.0f);
        assertEquals(8, polizei.getGeschwindigkeit(), "Geschwindigkeit nun auf 100% -> 8");
    }

    @Test
    @DisplayName("Entferne alle Autos und Strassen")
    public void entfAlleAutosAbschnitte() {
        instance.entfAlleAutos();
        instance.entfAlleStrassen();
        assertTrue(instance.getAutos().isEmpty(), "Alle Autos sollten aus der Map entfernt worden sein");
        assertTrue(instance.getAutoList().isEmpty(), "Alle Autos sollten aus der Liste entfernt worden sein");
        assertTrue(instance.getAbschnitte().isEmpty(), "Alle Abschnitte sollten aus der Map entfernt worden sein");
    }

    @Test
    @DisplayName("Alle Ampeln deaktiviern")
    public void alleAmpelnDeaktivierenTest() {
        TStueck t = new TStueck(150, 150);
        instance.strasseAdden(t);

        instance.ampelnAktivieren(t);
        instance.ampelnAktivieren(kreuzung);

        instance.alleAmpelnDeaktivieren();
        assertFalse(t.isAmpelAktiv(), "Ampel sollte deaktiviert sein");
        assertFalse(kreuzung.isAmpelAktiv(), "Ampel sollte deaktiviert sein");
    }

    @Test
    @DisplayName("Zurücksetzen des Netzes")
    public void resetTest() {
        instance.reset();
        assertTrue(instance.getAbschnitte().isEmpty(), "Abschnitte-Map leer");
        assertTrue(instance.getAutoList().isEmpty(), "Auto-Liste leer");
        assertTrue(instance.getAutos().isEmpty(), "Auto-Map leer");
        assertNull(instance.getName(), "Kein Name gesetzt");
    }
}
