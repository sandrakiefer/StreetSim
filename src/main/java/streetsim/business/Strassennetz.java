package streetsim.business;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import streetsim.business.abschnitte.TStueck;
import streetsim.business.exceptions.*;
import javafx.beans.property.BooleanProperty;
import javafx.collections.ObservableMap;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

/**
 * Verwaltung aller Strassenabschnitte und Autos (über die Position)
 * sowie den Großteil der Anwendungslogik und Schnittstelle für obere Schicht (UI)
 */
public class Strassennetz {

    private ObservableMap<Position, Strassenabschnitt> abschnitte;
    private ObservableMap<Position, ArrayList<Auto>> autos;
    private BooleanProperty simuliert;
    private String name;
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
     * Bestimmung ob gegebenes Auto an einer Kreuzung steht
     * (vorgegebener Wertebereich [20,30])
     *
     * @param a Auto
     * @return steht an Kreuzung
     */
    public boolean stehtAnKreuzung(Auto a) {
        Position p = new Position(a.getPositionX(), a.getPositionY());
        Strassenabschnitt s = abschnitte.get(p);
        int mittelpunktX = s.getPositionX() + s.getGroesse() / 2;
        int mittelpunktY = s.getPositionY() + s.getGroesse() / 2;
        int distanz = a.distanzBisMitte(mittelpunktX, mittelpunktY);
        if (distanz > 20 && distanz < 30) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Überprüfung ob die Ampel rot ist
     *
     * @param a Auto
     * @param s Strassenabschnitt
     * @return steht das Auto an einer Ampel oder nicht
     */
    public boolean stehtAnAmpel(Auto a, Strassenabschnitt s) {
        // Beispiel Auto fährt nach Norden aber steht an Ampel Süden
        Himmelsrichtung h = a.getRichtung().gegenueber();
        for (Ampel ampel : s.getAmpeln()) {
            if (ampel.getRichtung().equals(h)) {
                return ampel.isRot();
            }
        }
        return false;
    }

    /**
     * fügt ein Auto zum Strassennetz hinzu (Autos-Map)
     *
     * @param a Auto
     * @throws SchonBelegtException   wenn ein Auto auf dem Strassennetz mit selber Position und Richtung existiert
     * @throws KeinAbschnittException wenn Auto auf stelle platziert werden soll, wo noch kein Strassenabschnitt platziert worden ist
     */
    public void autoAdden(Auto a) throws SchonBelegtException, KeinAbschnittException {
        Position p = new Position(a.getPositionX(), a.getPositionY());
        if (instance.abschnitte.containsKey(p)) {
            if (!instance.autos.containsKey(p)) {
                instance.autos.put(p, new ArrayList());
            }
            if (instance.posBelegt(a)) {
                //TODO: Auto an nächstmöglicher Position ablegen @UI @Logik? (optional)
                throw new SchonBelegtException();
            } else {
                instance.autos.get(p).add(a);
            }
        } else {
            throw new KeinAbschnittException();
        }
    }

    /**
     * fügt ein Strassenabschnitt zum Strassennetz hinzu (Abschnitte-Map)
     *
     * @param s Strassenabschnitt
     * @throws SchonBelegtException an der Position ist bereits ein anderer Strassenabschnitt platziert
     */
    public void strasseAdden(Strassenabschnitt s) throws SchonBelegtException {
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
     * Bestimmung der Position mit den Koordinaten
     *
     * @param x X-Koordinate
     * @param y Y-Koordinate
     * @return Strassenabschnitt an der gegeben Position
     */
    public Strassenabschnitt strasseAnPos(int x, int y) {
        Position p = new Position(x, y);
        return instance.abschnitte.get(p);
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
    public void speicherNetz(File file) {
        String name = file.getName();
        instance.setName(name);
        Gson gsonBuilder = FxGson.coreBuilder()
                .registerTypeAdapter(Strassenabschnitt.class, new StrassenAdapter())
                .enableComplexMapKeySerialization()
                .setPrettyPrinting()
                .create();
        String jsonResult = gsonBuilder.toJson(instance);
        String path = file.getPath();
        try {
            Files.writeString(Paths.get(path), jsonResult, StandardCharsets.UTF_8);
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
        try {
            String json = Files.readString(Paths.get(file.getPath()), StandardCharsets.UTF_8);
            Gson gson = FxGson.coreBuilder()
                    .registerTypeAdapter(Strassenabschnitt.class, new StrassenAdapter())
                    .enableComplexMapKeySerialization()
                    .setPrettyPrinting()
                    .create();
            instance = gson.fromJson(json, Strassennetz.class);
        } catch (IOException e) {
            throw new DateiParseException("Datei konnte nicht gelesen werden.", e);
        }
    }

    /**
     * rotiert Strassenabschnitt um 90 Grad im Uhrzeigersinn
     *
     * @param s Strassenabschnitt
     */
    public void rotiereStrasse(Strassenabschnitt s) {
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
     * verschiebt Strassenabschnitt,
     * eventuell darauf befindliche Autos werden mit verschoben
     *
     * @param s Strassenabschnitt
     * @param x X-Koordinate
     * @param y Y-Koordinate
     */
    public void bewegeStrasse(Strassenabschnitt s, int x, int y) {
        // TODO: SchonBelegtException hier auch möglich?
        Position oldP = new Position(s.getPositionX(), s.getPositionY());
        Position newP = new Position(x, y);
        int xOff = newP.getPositionX() - oldP.getPositionX();
        int yOff = newP.getPositionY() - oldP.getPositionY();
        for (Auto a : instance.autos.get(oldP)) {
            a.setPositionX(a.getPositionX() + xOff);
            a.setPositionY(a.getPositionY() + yOff);
        }
        instance.autos.put(newP, instance.autos.remove(oldP));
        instance.entfStrasse(s);
        instance.abschnitte.put(newP, s);
    }

    /**
     * passt die Geschwindigkeit des Autos an
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
        instance.entfAlleAutos();
        instance.abschnitte.clear();
    }

    /**
     * deaktiviert alle Ampeln vom Strassennetz
     */
    public void alleAmpelnDeaktivieren() {
        for (Map.Entry<Position, Strassenabschnitt> entry : instance.abschnitte.entrySet()) {
            instance.ampelnDeaktivieren(entry.getValue());
        }
    }

    /**
     * setzt die geladene Welt in den Ausgangszustand
     */
    public void reset() {
        entfAlleAutos();
        alleAmpelnDeaktivieren();
        entfAlleStrassen();
        name = null;
        instance.simuliert.setValue(false);
    }

    /**
     * Starten der Simulation
     * startet Thread für automatisierte Schaltung aller Ampeln (nach bestimmten Zeitintervall)
     * startet Thread für automatisiertes Fahren aller Autos
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
                    instance.alleAmpelnDeaktivieren();
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

    public void setAbschnitte(ObservableMap<Position, Strassenabschnitt> abschnitte) {
        this.abschnitte = abschnitte;
    }

    public void setAutos(ObservableMap<Position, ArrayList<Auto>> autos) {
        this.autos = autos;
    }

    public static void main(String[] args) {
        // Testen der Klassen
        /*Strassennetz s = getInstance();
        Strassenabschnitt str = new TStueck(128, 128);
        System.out.println(str.getPositionX() + " " + str.getPositionY());
        s.strasseAdden(str);
        Auto brum = new Auto(80+128,80+128, Auto.AutoModell.ROT);
        System.out.println(brum.getPositionX() + " " + brum.getPositionY() + " " + brum.getRichtung().name());
        brum = new Auto(80,10, Auto.AutoModell.ROT);
        System.out.println(brum.getPositionX() + " " + brum.getPositionY() + " " + brum.getRichtung().name());
        brum = new Auto(54,100, Auto.AutoModell.ROT);
        System.out.println(brum.getPositionX() + " " + brum.getPositionY() + " " + brum.getRichtung().name());
        //Auto brumbrum = new Auto(0.7f, Himmelsrichtung.NORDEN,100,100,20,30,"blau",s);
//        Auto brum = new Auto(0.9f, Himmelsrichtung.WESTEN, 100, 100, 10, 20);
        //s.autoAdden(brumbrum);
        //s.autoAdden(brum);*/
    }

}


