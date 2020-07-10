package streetsim.business;

/**
 * Abbildung der Himmelsrichtungen
 * (Norden, Osten, Süden, Westen)
 */
public enum Himmelsrichtung {

    NORDEN(0, -1), OSTEN(1, 0), SUEDEN(0, 1), WESTEN(-1, 0);

    private final int x;
    private final int y;

    Himmelsrichtung(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Gibt nächste Himmelsrichtung an
     * (90° im Uhrzeigersinn)
     *
     * @return Himmelsrichtung
     */
    public Himmelsrichtung naechstes() {
        return Himmelsrichtung.values()[(this.ordinal() + 1) % 4];
    }

    /**
     * Gibt gegenüberliegende Himmelsrichtung an
     * (180°)
     *
     * @return Himmelsrichtung
     */
    public Himmelsrichtung gegenueber() {
        return Himmelsrichtung.values()[(this.ordinal() + 2) % 4];
    }

    /**
     * Gibt vorherige Himmelsrichtung an
     * (270° im Uhrzeigersinn / 90° gegen den Uhrzeigersinn)
     *
     * @return Himmelsrichtung
     */
    public Himmelsrichtung vorheriges() {
        return Himmelsrichtung.values()[(this.ordinal() + 3) % 4];
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

}
