package streetsim.business;

import javafx.geometry.Pos;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import streetsim.business.abschnitte.Kreuzung;
import streetsim.business.abschnitte.Kurve;

import static org.junit.jupiter.api.Assertions.*;

public class StrassennetzTest {

    private Strassennetz instance;
    private Kreuzung kreuzung;
    private Position kreuzungP;
    private Kurve kurve;
    private Position kurveP;
    private Auto polizei;
    private Position polizeiPos;
    private Auto rot;
    private Position rotPos;

    @BeforeEach
    public void setUp(){
        instance = Strassennetz.getInstance();

        kreuzung = new Kreuzung(50, 50);
        kreuzungP = new Position(50,50);
        instance.strasseAdden(kreuzung);

        kurve = new Kurve(50, 50);
        kurveP = new Position(50,50);

        int positionX = 110;
        int positionY = 120;

        Auto.AutoModell autoModell = Auto.AutoModell.POLIZEI;
        polizei = new Auto(positionX, positionY, autoModell);
        polizeiPos = new Position(positionX, positionY);
        rot = new Auto(positionX, positionY, autoModell);
        rotPos = new Position(positionX, positionY);
        instance.autoAdden(polizei);
    }
    @AfterEach
    public void cleanUo() {instance.reset();}

    @Test
    @DisplayName("Netz gefüllt")
    public void listenTest(){
        assertTrue(instance.getAbschnitte().containsKey(kreuzungP), "Position des Abschnitts muss in der Map als Key vorhanden sein");
        assertTrue(instance.getAbschnitte().containsValue(kreuzung), "Abschnitt muss in der Map als Value vorhanden sein");
        assertTrue(instance.getAutoList().contains(polizei), "Auto muss in autoList");
        assertTrue(instance.getAutos().containsKey(polizeiPos), "Position des Autos muss in der Map als Key vorhanden sein");
        assertTrue(instance.getAutos().get(polizeiPos).contains(polizei), "Auto muss in der Liste der zugehörigen Position vorhanden sein");
    }

    @Test
    @DisplayName("Auto an der Kreuzung")
    public void anKreuzungTest(){
        assertTrue(instance.stehtAnKreuzung(polizei), "Auto muss an der Krezung stehen");
    }

    @Test
    @DisplayName("Auto steht an der Ampel")
    public void anAmpelTest(){
        assertTrue(instance.stehtAnAmpel(polizei, kreuzung), "Auto muss an der Ampel stehen");
    }

    @Test
    @DisplayName("Positionen für Autos/Strassen belegt")
    public void posBelegtTest(){
        assertTrue(instance.posBelegt(rot), "Position sollte belegt sein");
        assertTrue(instance.posBelegt(kurve), "Position sollte belegt sein");
    }

    @Test
    @DisplayName("An Position sollte Strassenabschnitt vorhanden sein")
    public void strasseAnPosTest(){
        assertEquals(kreuzung, instance.strasseAnPos(50,50), "Position muss Kurve beinhalten");
    }

    @Test
    @DisplayName("Auto(s) aus dem Netz entfernen")
    public void entfAutosTest(){
        instance.entfAuto(polizei);
        assertFalse(instance.getAutos().get(polizeiPos).contains(polizei), "Auto sollte nicht mehr in der Map sein");
        assertFalse(instance.getAutoList().contains(polizei), "Auto sollte nicht mehr in der Liste sein");
    }

    @Test
    @DisplayName("")
    public void entfAbschnittTest(){
        instance.entfStrasse(kreuzung);
        assertFalse(instance.getAbschnitte().containsKey(kreuzungP));
        assertFalse(instance.getAbschnitte().containsValue(kreuzung));
    }

    @Test
    @DisplayName("Ampeln aktivieren/deaktivieren")
    public void ampelnAktDeaktTest(){
        instance.ampelnAktivieren(kreuzung);
        assertTrue(kreuzung.isAmpelAktiv(), "Ampeln sollten aktiviert sein");
        instance.ampelnDeaktivieren(kreuzung);
        assertFalse(kreuzung.isAmpelAktiv(), "Ampeln sollten deaktiviert sein");
    }
}
