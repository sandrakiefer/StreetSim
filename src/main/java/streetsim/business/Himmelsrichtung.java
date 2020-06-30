package streetsim.business;

/**
 * Abbildung der Himmelsrichtungen
 */
public enum Himmelsrichtung {

    NORDEN(0,-1), OSTEN(1,0), SUEDEN(0,1), WESTEN(-1,0);

    private int x;
    private int y;

    Himmelsrichtung(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

}
