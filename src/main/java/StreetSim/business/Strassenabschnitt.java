package StreetSim.business;

import javafx.beans.property.BooleanProperty;

import java.util.ArrayList;

/**
 * Verwaltung der Richtungen in welche ein Strassenabschnitt führt,
 * verwendet Ampelschaltung für optionale Ampeln
 */
public class Strassenabschnitt implements Ampelschaltung {

    private int positionX;
    private int positionY;
    private ArrayList<Himmelsrichtung> richtungen;
    private int groesse;
    private ArrayList<Ampel> ampeln;
    private BooleanProperty ampelAktiv;

    public Strassenabschnitt() {
        richtungen = new ArrayList();
        ampeln = new ArrayList();
    }

    /**
     * aktiviert alle Ampeln der Liste und startet die "zeitSchalt"-Methode
     */
    public void ampelnAktivieren() {

    }

    @Override
    public void schalte() {

    }

    @Override
    public void zeitSchalte() {

    }

}
