package streetsim.business;

import java.util.Objects;

/**
 * Abbildung der Position von den X und Y-Koordinaten
 */
public class Position {

    private final int positionX;
    private final int positionY;
    private static final int KACHELGROESSE = 100;

    public Position(int positionX, int positionY) {
        this.positionX = positionX - (positionX % KACHELGROESSE);
        this.positionY = positionY - (positionY % KACHELGROESSE);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return positionX == position.positionX &&
                positionY == position.positionY;
    }

    @Override
    public int hashCode() {
        return Objects.hash(positionX, positionY);
    }

    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }

}