package streetsim.business;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PositionTest {

    Position p1;
    Position p2;
    Position p3;

    @Test
    @DisplayName("Test ob abrunden für Kachel-Verhalten funktioniert")
    public void positionTest() {
        p1 = new Position(127, 60);
        p2 = new Position(129, 70);
        p3 = new Position(257, 130);

        assertEquals(p1.getPositionX(), 0, "X sollte an 0ter Stelle sein");
        assertEquals(p1.getPositionY(), 0, "Y sollte an 0ter Stelle sein");
        assertEquals(p2.getPositionX(), 128, "X sollte an 1ter Stelle sein");
        assertEquals(p2.getPositionY(), 0, "Y sollte an 0ter Stelle sein");
        assertEquals(p3.getPositionX(), 256, "X sollte an 2ter Stelle sein");
        assertEquals(p3.getPositionY(), 128, "Y sollte an 1ter Stelle sein");
    }
}
