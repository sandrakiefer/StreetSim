package streetsim.business;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import streetsim.business.abschnitte.TStueck;
import streetsim.business.exceptions.DateiParseException;
import streetsim.business.exceptions.FalschRotiertException;
import streetsim.business.exceptions.SchonBelegtException;
import streetsim.business.exceptions.WeltLeerException;
import streetsim.data.DatenService;
import javafx.beans.property.BooleanProperty;
import javafx.collections.ObservableMap;

import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
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
    private transient String test;

    public static Strassennetz instance;

    private Strassennetz() {
        abschnitte = FXCollections.observableHashMap();
        autos = FXCollections.observableHashMap();
        simuliert = new SimpleBooleanProperty();
    }

    public static Strassennetz getInstance() {
        if (instance == null) instance = new Strassennetz();
        return instance;
    }

    /**
     * steht das Auto an einem Strassenabschnitt, an dem es in eine andere Richtung abbiegen kann
     * zufällige Bestimmung der möglichen Abbiegerichtung
     *
     * @param a Auto
     * @return Strassenabschnitt
     */
    public Optional<Strassenabschnitt> stehtAnKreuzung(Auto a) {
        // TODO: Lösungsansatz Distanz vom Auto zum Mittelpunkt (ACHTUNG: Referenzpunkt Auto konsistent)
        return null;
    }

    /**
     * @param a Auto
     * @param s Strassenabschnitt
     * @return steht das Ampel an einer Ampel oder nicht
     */
    public boolean stehtAnAmpel(Auto a, Strassenabschnitt s) {
        // TODO: Lösungsansatz stehtAnKruezug() aufrufen und wenn ja Ampel überprüfen (ACHTUNG: Referenzpunkt Auto konsistent)
        return false;
    }

    /**
     * fügt ein Auto zum Strassennetz hinzu (Autos-Map)
     *
     * @param a Auto
     * @throws SchonBelegtException wenn ein Auto auf dem Strassennetz mit selber Position und Richtung existiert
     */
    public void autoAdden(Auto a) throws SchonBelegtException {
        Position p = new Position(a.getPositionX(), a.getPositionY());
        // TODO: kein Strassenabschnitt an der Stelle (? Exception)
        if (instance.abschnitte.containsKey(p)) {
            if (!instance.autos.containsKey(p)) {
                instance.autos.put(p, new ArrayList());
            }
            if (posBelegt(a)) {
                throw new SchonBelegtException();
            } else {
                instance.autos.get(p).add(a);
            }
        }
    }

    /**
     * fügt ein Strassenabschnitt zum Strassennetz hinzu (Abschnitte-Map)
     *
     * @param s Strassenabschnitt
     * @throws SchonBelegtException   an der Position ist bereits ein anderer Strassenabschnitt platziert
     * @throws FalschRotiertException kein Strassenfluss möglich
     */
    public void strasseAdden(Strassenabschnitt s) throws SchonBelegtException, FalschRotiertException {
        // TODO: ? FalschRotiertException unnötig
        // auch willkürlich möglich
        Position p = new Position(s.getPositionX(), s.getPositionY());
        if (instance.abschnitte.containsKey(p)) {
            throw new SchonBelegtException();
        } else {
            instance.abschnitte.put(p, s);
        }
    }

    /**
     * Überprüfung der Position des Autos
     *
     * @param a Auto
     * @return schon belegt oder nicht
     */
    public boolean posBelegt(Auto a) {
        Position p = new Position(a.getPositionX(), a.getPositionY());
        for (Auto brum : instance.autos.get(p)) {
            if (a.getRectangle().intersects(brum.getRectangle().getLayoutBounds())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Überprüfung der Position des Strassenabschnitts
     *
     * @param s Strassenabschnitt
     * @return schon belegt oder nicht
     */
    public boolean posBelegt(Strassenabschnitt s) {
        Position p = new Position(s.getPositionX(), s.getPositionY());
        return instance.abschnitte.containsKey(p);
    }

    /**
     * entfernt beliebig viele Autos vom Strassennetz
     *
     * @param a Autos
     */
    public void entfAuto(Auto... a) {
        for (Auto brum : a) {
            Position p = new Position(brum.getPositionX(), brum.getPositionY());
            instance.autos.get(p).remove(brum);
        }
    }

    /**
     * aktiviert Ampeln an gegebenen Strassenabschnitt
     * automatisches Schalten von Ampeln wird aktiviert
     *
     * @param s Strassenabschnitt
     */
    public void ampelnAktivieren(Strassenabschnitt s) {
        s.ampelnAktivieren();
    }

    /**
     * deaktiviert Ampeln an gegeben Strassenabschnitt
     *
     * @param s Strassenabschnitt
     */
    public void ampelnDeaktivieren(Strassenabschnitt s) {
        s.setAmpelAktiv(false);
    }

    /**
     * speichert aktuelles Strassennetz im Dateisystem
     *
     * @throws WeltLeerException keine Attribute auf Strassennetz gesetzt
     */
    public void speicherNetz(File file) throws WeltLeerException {
        // TODO: speichern
        // TODO: data rausschmeißen

        ObjectMapper mapper = new ObjectMapper();
        try {
            // TODO: Rekursion unterbinden
            String jsonResult = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(this);
            System.out.println(jsonResult);
            String path = file.getPath();
            String name = path.substring(path.lastIndexOf("/") + 1, path.lastIndexOf(".json"));
            instance.setName(name);
            FileWriter f = new FileWriter(path);

            f.write(jsonResult);
            f.flush();
            f.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * versucht ein Strassennetz aus einer Datei zu laden
     *
     * @throws DateiParseException Datei konnte nicht gelesen werden
     */
    public void ladeNetz(File file) throws DateiParseException {
        //instance.setName("Björn");
        ObjectMapper mapper = new ObjectMapper();
        try {
            // TODO: dezerialise Position
            String path = file.getPath();
            instance = mapper.readValue(Files.readString(Path.of(path)), Strassennetz.class);
            String name = path.substring(path.lastIndexOf("/") + 1, path.lastIndexOf(".json"));
            instance.setName(name);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * versucht den Strassenabschnitt um 90 Grad im Uhrzeigersinn zu rotieren
     *
     * @param s Strassenabschnitt
     * @throws FalschRotiertException keine Verknüpfung zu einem anderen Strassenabschnitt
     */
    public void rotiereStrasse(Strassenabschnitt s) throws FalschRotiertException {
        // TODO: FlaschRotiertException sinvoll? (wie überprüfen, beschränkt Benutzer, lose Enden = Sackgasse)
        s.rotiere();
    }

    /**
     * entfernt beliebig viele Strassenabschnitte
     * entfernt ebenfalls sich darauf befindlichen Autos
     *
     * @param s Strassenabschnitte
     */
    public void entfStrasse(Strassenabschnitt... s) {
        for (Strassenabschnitt stra : s) {
            Position p = new Position(stra.getPositionX(), stra.getPositionY());
            instance.abschnitte.remove(p);
        }
    }

    /**
     * versucht Strassenabschnitt zu verschieben
     * eventuell darauf befindliche Autos werden mit verschoben
     *
     * @param s Strassenabschnitt
     * @param x X-Koordinate
     * @param y Y-Koordinate
     */
    public void bewegeStrasse(Strassenabschnitt s, int x, int y) {
        Position oldP = new Position(s.getPositionX(), s.getPositionY());
        Position newP = new Position(x, y);
        int xOff = newP.getPositionX() - oldP.getPositionX();
        int yOff = newP.getPositionY() - oldP.getPositionY();
        for (Auto a : instance.autos.get(oldP)) {
            a.setPositionX(a.getPositionX() + xOff);
            a.setPositionY(a.getPositionY() + yOff);
        }
        instance.autos.put(newP, instance.autos.remove(oldP));
        entfStrasse(s);
        instance.abschnitte.put(newP, s);
    }

    /**
     * passt die Geschwindigkeit eines Autos an
     *
     * @param a               Auto
     * @param geschwindigkeit Geschwindigkeit (Intervall zwischen 0 und 1)
     */
    public void geschwindigkeitAnpassen(Auto a, float geschwindigkeit) {
        a.setGeschwindigkeit(geschwindigkeit);
    }

    /**
     * entfernt alle Autos vom Strassennetz
     */
    public void entfAlleAutos() {
        instance.autos.clear();
    }

    /**
     * entfernt alle Strassen vom Strassennetz
     */
    public void entfAlleStrassen() {
        entfAlleAutos();
        instance.abschnitte.clear();
    }

    // TODO Ändernung von entfAlleAmpeln zu alleAmpelnDeaktivieren

    /**
     * deaktiviert alle Ampeln vom Strassennetz
     */
    public void alleAmpelnDeaktivieren() {
        for (Map.Entry<Position, Strassenabschnitt> entry : instance.abschnitte.entrySet()) {
            ampelnDeaktivieren(entry.getValue());
        }
    }

    /**
     * setzt die geladene Welt in den Ausgangszustand
     */
    public void reset() {
        // TODO unnötig? (gleich mit entfAlleStrassen)
        // namen entfernen (Singelton - null?)
        // simuliert = false
    }

    /**
     * Starten der Simulation
     *
     * @throws WeltLeerException keine Attribute auf Strassennetz gesetzt
     */
    public void starteSimulation() throws WeltLeerException {
        if (instance.abschnitte.isEmpty()) {
            throw new WeltLeerException();
        }
        instance.simuliert.setValue(true);
        // Ampelschaltung
        int millisek = 10000;
        new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                if (instance.simuliert.get()) {
                    for (Strassenabschnitt s : instance.abschnitte.values()) {
                        if (s.isAmpelAktiv()) {
                            s.schalte();
                        }
                    }
                    try {
                        Thread.sleep(millisek);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    Thread.currentThread().interrupt();
                    alleAmpelnDeaktivieren();
                }

            }
        }).start();
        // Autos fahren
        new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                if (instance.simuliert.get()) {
                    for (Map.Entry<Position, ArrayList<Auto>> entry : instance.autos.entrySet()) {
                        for (Auto a : entry.getValue()) {
                            a.fahre();
                            // TODO: wann wird auto in andere Liste verschoben?
                        }
                    }
                } else {
                    Thread.currentThread().interrupt();
                }
            }
        }).start();
    }

    /**
     * Pausieren der Simulation
     */
    public void pausiereSimulation() {
        instance.simuliert.setValue(false);
    }

    public boolean isSimuliert() {
        return instance.simuliert.get();
    }

    public BooleanProperty simuliertProperty() {
        return instance.simuliert;
    }

    public void setSimuliert(boolean simuliert) {
        instance.simuliert.set(simuliert);
    }

    public String getName() {
        return instance.name;
    }

    public void setName(String name) {
        instance.name = name;
    }

    public ObservableMap<Position, Strassenabschnitt> getAbschnitte() {
        return instance.abschnitte;
    }

    public ObservableMap<Position, ArrayList<Auto>> getAutos() {
        return instance.autos;
    }

    public static void main(String[] args) {
        Strassennetz s = new Strassennetz();
        Strassenabschnitt str = new TStueck(100, 100);
        s.strasseAdden(str);
        //Auto brumbrum = new Auto(0.7f, Himmelsrichtung.NORDEN,100,100,20,30,"blau",s);
        Auto brum = new Auto(0.9f, Himmelsrichtung.WESTEN, 100, 100, 10, 20, "geln", s);
        //s.autoAdden(brumbrum);
        //s.autoAdden(brum);
    }

}


