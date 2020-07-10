package streetsim.business;

import com.google.gson.Gson;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import org.hildan.fxgson.FxGson;
import streetsim.business.exceptions.DateiParseException;
import streetsim.business.exceptions.SchonBelegtException;
import streetsim.business.exceptions.WeltLeerException;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Verwaltung aller Straßenabschnitte und Autos (über die Position)
 * sowie den Großteil der Anwendungslogik und Schnittstelle für obere Schicht (UI)
 */
public class Strassennetz {

    public static Strassennetz instance;
    private final transient SimpleListProperty<Auto> autoList;
    private final BooleanProperty simuliert;
    private final SimpleStringProperty name = new SimpleStringProperty();
    private final ObservableMap<Position, Strassenabschnitt> abschnitte;
    private final Map<Position, List<Auto>> autos;

    private Strassennetz() {
        abschnitte = FXCollections.observableHashMap();
        autos = new HashMap<>();
        autoList = new SimpleListProperty<>(FXCollections.observableArrayList());
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
        Strassenabschnitt s = instance.abschnitte.get(p);
        int mittelpunktX = s.getPositionX() + Strassenabschnitt.GROESSE / 2;
        int mittelpunktY = s.getPositionY() + Strassenabschnitt.GROESSE / 2;
        int distanz = a.distanzBisMitte(mittelpunktX, mittelpunktY);
        return distanz > Strassenabschnitt.HALTELINIENABSTAND && distanz < Strassenabschnitt.HALTELINIENABSTAND + 8;
    }

    /**
     * Überprüfung ob die Ampel rot ist
     *
     * @param a Auto
     * @param s Straßenabschnitt
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
     * fügt ein Auto zum Straßennetz hinzu (Autos-Map)
     *
     * @param a Auto
     * @throws SchonBelegtException wenn ein Auto auf dem Straßennetz mit selber Position und Richtung existiert
     */
    public void autoAdden(Auto a) throws SchonBelegtException {
        Position p = new Position(a.getPositionX(), a.getPositionY());
        if (!instance.autos.containsKey(p)) {
            instance.autos.put(p, new ArrayList<>());
        }
        if (instance.posBelegt(a)) {
            throw new SchonBelegtException("An dieser Position ist schon ein Auto.");
        } else {
            instance.autos.get(p).add(a);
            instance.autoList.add(a);
        }

    }

    /**
     * fügt ein Straßenabschnitt zum Straßennetz hinzu (Abschnitte-Map)
     *
     * @param s Straßenabschnitt
     * @throws SchonBelegtException an der Position ist bereits ein anderer Straßenabschnitt platziert
     */
    public void strasseAdden(Strassenabschnitt s) throws SchonBelegtException {
        Position p = new Position(s.getPositionX(), s.getPositionY());
        if (instance.abschnitte.containsKey(p)) {
            throw new SchonBelegtException("Hier ist bereits eine Straße");
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
     * Überprüfung der Position des Straßenabschnitts
     *
     * @param s Straßenabschnitt
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
     * @return Straßenabschnitt an der gegeben Position
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
            instance.autoList.remove(brum);
        }
    }

    /**
     * aktiviert Ampeln an gegebenen Straßenabschnitt
     * automatisches Schalten von Ampeln wird aktiviert
     *
     * @param s Straßenabschnitt
     */
    public void ampelnAktivieren(Strassenabschnitt s) {
        s.ampelnAktivieren();
    }

    /**
     * deaktiviert Ampeln an gegeben Straßenabschnitt
     *
     * @param s Straßenabschnitt
     */
    public void ampelnDeaktivieren(Strassenabschnitt s) {
        s.setAmpelAktiv(false);
    }

    /**
     * speichert aktuelles Straßennetz im Dateisystem
     *
     * @param file Datei, in welche das Netz abgespeichert werden soll.
     */
    public void speicherNetz(File file) {
        String name = file.getName();
        instance.setName(name);
        Gson gsonBuilder = FxGson.coreBuilder()
                .registerTypeAdapter(Strassenabschnitt.class, StrassenAdapter.getInstance())
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
     * Versucht ein Straßennetz aus einer Datei zu laden
     *
     * @param file Datei, aus der Straßennetz geladen werden soll.
     * @throws DateiParseException Datei konnte nicht gelesen werden
     */
    public void ladeNetz(File file) throws DateiParseException {
        try {
            String json = Files.readString(Paths.get(file.getPath()), StandardCharsets.UTF_8);
            Gson gson = FxGson.coreBuilder()
                    .registerTypeAdapter(Strassenabschnitt.class, StrassenAdapter.getInstance())
                    .enableComplexMapKeySerialization()
                    .setPrettyPrinting()
                    .create();
            instance = gson.fromJson(json, Strassennetz.class);
            instance.autos.forEach((key, value) -> instance.autoList.addAll(value));
        } catch (IOException e) {
            throw new DateiParseException("Datei konnte nicht gelesen werden.", e);
        }
    }

    /**
     * rotiert Straßenabschnitt um 90 Grad im Uhrzeigersinn
     * und die darauf befindlichen Autos
     *
     * @param s Straßenabschnitt
     */
    public void rotiereStrasse(Strassenabschnitt s) {
        Position p = new Position(s.getPositionX(), s.getPositionY());
        if (instance.autos.containsKey(p)) {
            instance.autos.get(p).forEach(Auto::rotiere);
        }
        s.rotiere();
    }

    /**
     * entfernt beliebig viele Straßenabschnitte
     * entfernt ebenfalls sich darauf befindlichen Autos
     *
     * @param s Straßenabschnitte
     */
    public void entfStrasse(Strassenabschnitt... s) {
        for (Strassenabschnitt stra : s) {
            Position p = new Position(stra.getPositionX(), stra.getPositionY());
            instance.abschnitte.remove(p);
            if (instance.autos.containsKey(p)) instance.autos.remove(p).forEach(a -> instance.autoList.remove(a));
        }
    }

    /**
     * verschiebt Straßenabschnitt,
     * eventuell darauf befindliche Autos werden mit verschoben
     *
     * @param s Straßenabschnitt
     * @param x X-Koordinate
     * @param y Y-Koordinate
     */
    public void bewegeStrasse(Strassenabschnitt s, int x, int y) {
        Position oldP = new Position(s.getPositionX(), s.getPositionY());
        Position newP = new Position(x, y);
        int xOff = newP.getPositionX() - oldP.getPositionX();
        int yOff = newP.getPositionY() - oldP.getPositionY();
        if (instance.autos.containsKey(oldP)) {
            for (Auto a : instance.autos.get(oldP)) {
                a.setPositionX(a.getPositionX() + xOff);
                a.setPositionY(a.getPositionY() + yOff);
            }
            instance.autos.put(newP, instance.autos.remove(oldP));
        }
        instance.entfStrasse(s);
        s.setPositionX(newP.getPositionX());
        s.setPositionY(newP.getPositionY());
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
     * entfernt alle Autos vom Straßennetz
     */
    public void entfAlleAutos() {
        instance.autos.clear();
        instance.autoList.clear();
    }

    /**
     * entfernt alle Straßen vom Straßennetz
     */
    public void entfAlleStrassen() {
        instance.entfAlleAutos();
        instance.abschnitte.clear();
    }

    /**
     * deaktiviert alle Ampeln vom Straßennetz
     */
    public void alleAmpelnDeaktivieren() {
        for (Map.Entry<Position, Strassenabschnitt> entry : instance.abschnitte.entrySet()) {
            instance.ampelnDeaktivieren(entry.getValue());
        }
    }

    /**
     * Setzt die geladene Welt in den Ausgangszustand
     */
    public void reset() {
        entfAlleAutos();
        alleAmpelnDeaktivieren();
        entfAlleStrassen();
        instance.name.set(null);
        instance.simuliert.setValue(false);
    }

    /**
     * Starten der Simulation
     * startet Thread für automatisierte Schaltung aller Ampeln (nach bestimmten Zeitintervall)
     * startet Thread für automatisiertes Fahren aller Autos
     *
     * @throws WeltLeerException keine Attribute auf Straßennetz gesetzt
     */
    public void starteSimulation() throws WeltLeerException {
        if (instance.abschnitte.isEmpty() || instance.autoList.isEmpty()) {
            throw new WeltLeerException("Das Spielfeld ist leer. Es gibt nichts zu simulieren.");
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
                }
            }
        }).start();
        // Autos fahren
        new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                if (instance.simuliert.get()) {
                    for (Auto a : instance.autoList) {
                        a.fahre();
                    }
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
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

    public void setSimuliert(boolean simuliert) {
        instance.simuliert.set(simuliert);
    }

    public BooleanProperty simuliertProperty() {
        return instance.simuliert;
    }

    public String getName() {
        return instance.name.get();
    }

    public void setName(String name) {
        instance.name.set(name);
    }

    public ObservableMap<Position, Strassenabschnitt> getAbschnitte() {
        return instance.abschnitte;
    }

    public ObservableList<Auto> getAutoList() {
        return instance.autoList.get();
    }

    public Map<Position, List<Auto>> getAutos() {
        return instance.autos;
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    @Override
    public String toString() {
        return String.format("Straßennetz(%s, %s, %s)", name.get() == null ? "Untitled" : name.get(), abschnitte.toString(), autoList.get().toString());
    }
}
