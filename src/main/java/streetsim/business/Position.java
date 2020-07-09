package streetsim.business;

import java.util.Objects;

/**
 * Abbildung der Position von den X und Y-Koordinaten
 * auf die Kachelkoordinaten
 */
public class Position {

    private final int positionX;
    private final int positionY;

    public Position(int positionX, int positionY) {
        this.positionX = positionX - ((positionX + Strassenabschnitt.GROESSE) % Strassenabschnitt.GROESSE);
        this.positionY = positionY - ((positionY + Strassenabschnitt.GROESSE) % Strassenabschnitt.GROESSE);
    }

    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
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

    @Override
    public String toString() {
        return String.format("Position(%d,%d)", positionX, positionY);
    }
}
