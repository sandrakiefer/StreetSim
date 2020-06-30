package streetsim.business;

import streetsim.business.exceptions.DateiParseException;
import streetsim.business.exceptions.FalschRotiertException;
import streetsim.business.exceptions.SchonBelegtException;
import streetsim.business.exceptions.WeltLeerException;
import streetsim.data.DatenService;
import javafx.beans.property.BooleanProperty;
import javafx.collections.ObservableMap;

import java.util.*;

/**
 * Verwaltung aller Strassenabschnitte und Autos sowie Großteil
 * der Anwendungslogik und Schnittstelle für obere Schicht (UI)
 */
public class Strassennetz {

    private ObservableMap<Position, Strassenabschnitt> abschnitte;
    private ObservableMap<Position, ArrayList<Auto>> autos;
    private BooleanProperty simuliert;
    private String name;
    private DatenService datenService;

    public Strassennetz() {
        // TODO: init ObservableMap abschnitte
        // TODO: init ObservableMap autos (und ArrayList autos)
    }

    /**
     * steht das Auto an einem Strassenabschnitt, an dem es in eine andere Richtung abbiegen kann
     * zufällige Bestimmung der möglichen Abbiegerichtung
     *
     * @param a Auto
     * @return Strassenabschnitt
     */
    public Optional<Strassenabschnitt> stehtAnKreuzung(Auto a) {
        return null;
    }

    /**
     *
     * @param a Auto
     * @param s Strassenabschnitt
     * @return steht das Ampel an einer Ampel oder nicht
     */
    public boolean stehtAnAmpel(Auto a, Strassenabschnitt s) {
        return false;
    }

    /**
     * fügt ein Auto zum Strassennetz hinzu (Autos-Map)
     *
     * @param a Auto
     * @throws SchonBelegtException wenn ein Auto auf dem Strassennetz mit selber Position und Richtung existiert
     */
    public void autoAdden(Auto a) throws SchonBelegtException {

    }

    /**
     * fügt ein Strassenabschnitt zum Strassennetz hinzu (Abschnitte-Map)
     *
     * @param s Strassenabschnitt
     * @throws SchonBelegtException an der Position ist bereits ein anderer Strassenabschnitt platziert
     * @throws FalschRotiertException kein Strassenfluss möglich
     */
    public void strasseAdden(Strassenabschnitt s) throws SchonBelegtException, FalschRotiertException {

    }

    /**
     * Überprüfung der Position des Autos
     *
     * @param a Auto
     * @return schon belegt oder nicht
     */
    public boolean posBelegt(Auto a) {
        return false;
    }

    /**
     * Überprüfung der Position des Strassenabschnitts
     *
     * @param s Strassenabschnitt
     * @return schon belegt oder nicht
     */
    public boolean posBelegt(Strassenabschnitt s) {
        return false;
    }

    /**
     * Überprüfung einer freien Position
     *
     * @param x X-Koordinate
     * @param y y-Koordinate
     * @return schon belegt oder nicht
     */
    public boolean posBelegt(int x, int y) {
        return false;
    }

    /**
     * entfernt beliebig viele Autos vom Strassennetz
     *
     * @param a Autos
     */
    public void entfAuto(Auto[] a) {

    }

    /**
     * aktiviert Ampeln an gegebenen Strassenabschnitt
     * automatisches Schalten von Ampeln wird aktiviert
     *
     * @param s Strassenabschnitt
     */
    public void ampelnAktivieren(Strassenabschnitt s) {

    }

    /**
     * deaktiviert Ampeln an gegeben Strassenabschnitt
     *
     * @param s Strassenabschnitt
     */
    public void ampelnDeaktivieren(Strassenabschnitt s) {

    }

    /**
     * speichert aktuelles Strassennetz im Dateisystem
     *
     * @throws WeltLeerException keine Attribute auf Strassennetz gesetzt
     */
    public void speicherNetz() throws WeltLeerException {

    }

    /**
     * versucht ein Strassennetz aus einer Datei zu laden
     *
     * @throws DateiParseException Datei konnte nicht gelesen werden
     */
    public void ladeNetz() throws DateiParseException {

    }

    /**
     * versucht den Strassenabschnitt um 90 Grad im Uhrzeigersinn zu rotieren
     *
     * @param s Strassenabschnitt
     * @throws FalschRotiertException keine Verknüpfung zu einem anderen Strassenabschnitt
     */
    public void rotiereStrasse(Strassenabschnitt s) throws FalschRotiertException {

    }

    /**
     * entfernt beliebig viele Strassenabschnitte
     * entfernt ebenfalls sich darauf befindlichen Autos
     *
     * @param s Strassenabschnitte
     */
    public void entfStrasse(Strassenabschnitt[] s) {

    }

    /**
     * versucht beliebig viele Strassenabschnitt zu verschieben
     * eventuell darauf befindliche Autos werden mit verschoben
     *
     * @param s Strassenabschnitte
     * @param xOff zu verschiebener Wert (Offset) der X-Koordinate
     * @param yOff zu verschiebener Wert (Offset) der Y-Koordinate
     */
    public void bewegeStrasse(Strassenabschnitt[] s, int xOff, int yOff) {

    }

    /**
     * passt die Geschwindigkeit eines Autos an
     *
     * @param a Auto
     * @param geschwindigkeit Geschwindigkeit (Intervall zwischen 0 und 1)
     */
    public void geschwindigkeitAnpassen(Auto a, float geschwindigkeit) {

    }

    /**
     * entfernt alle Autos vom Strassennetz
     */
    public void entfAlleAutos() {

    }

    /**
     * entfernt alle Strassen vom Strassennetz
     */
    public void entfAlleStrassen() {

    }

    /**
     * entfernt alle Ampeln vom Strassennetz
     */
    public void entfAlleAmpeln() {

    }

    /**
     * setzt die geladene Welt in den Ausgangszustand
     */
    public void reset() {

    }

    /**
     * Starten der Simulation
     *
     * @throws WeltLeerException keine Attribute auf Strassennetz gesetzt
     */
    public void starteSimulation() throws WeltLeerException {

    }

    /**
     * Pausieren der Simulation
     */
    public void pausiereSimulation() {

    }

    public boolean isSimuliert() {
        return simuliert.get();
    }

    public BooleanProperty simuliertProperty() {
        return simuliert;
    }

    public void setSimuliert(boolean simuliert) {
        this.simuliert.set(simuliert);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
