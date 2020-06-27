package StreetSim.business;

import javafx.beans.property.SimpleIntegerProperty;

/**
 * In eine Himmelsrichtung sich fortbewegendes Objekt
 * welches auf das Strassennetz, inbesondere Ampeln, reagiert
 */
public class Auto {

    private SimpleIntegerProperty geschwindigkeit;
    private Himmelsrichtung richtung;
    private SimpleIntegerProperty positionX;
    private SimpleIntegerProperty positionY;
    private int breite;
    private int laenge;
    private String farbe;
    private Strassennetz strassennetz;

    public Auto() {

    }

    /**
     * eigenständiges Fahren der Autos (durch Thread)
     */
    public void fahre() {

    }

}
