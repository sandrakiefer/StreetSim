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
    private Position autoPos;
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
        autoPos = new Position(positionX, positionY);
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
        assertTrue(instance.getAutos().containsKey(autoPos), "Position des Autos muss in der Map als Key vorhanden sein");
        assertTrue(instance.getAutos().get(autoPos).contains(polizei), "Auto muss in der Liste der zugehörigen Position vorhanden sein");
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
        assertTrue(instance.posBelegt(rot));
    }
}
