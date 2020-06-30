package streetsim.ui.utils;

import java.io.File;
import java.io.InputStream;

/**
 * Eine Utility-Klasse, die einen beim Dateizugriff
 * auf den Ressourcen-Ordner unterstützt.
 */
public class ResourceAssist {

    public static ResourceAssist instance;

    private ResourceAssist() {
    }

    public static ResourceAssist getInstance() {
        if (instance == null) instance = new ResourceAssist();
        return instance;
    }

    /**
     * Eine Methode, die aus einzelnen Subfoldern einen Betriebssystemabhängigen Pfad
     * korrekt zusammenbaut und die gewünschte Ressource als InputStream zurückgibt.
     *
     * Beispiel:
     * gewünschte Ressource liegt im Pfad unter Unix = "assets/bilder/foto.png"
     * wird erzielt durch:
     * <code>
     *     resourceAssistInstance.holeRessourceAusOrdnern("assets", "bilder", "foto.png");
     * </code>
     * (erzeugt unter Windows entsprechend intern "assets\bilder\foto.png")
     *
     * Wenn ein führendes "/" oder "\" erwünscht ist, muss dieses an erster Stelle
     * der pfadteile durch <code>File.separator</code> angegeben werden.
     *
     * @param pfadteile einzelne Ordner des Pfades
     * @return InputStream
     */
    public InputStream holeRessourceAusOrdnern(String... pfadteile) {
        StringBuilder sb = new StringBuilder();
        for (String pfad : pfadteile) {
            sb.append(pfad).append(File.separator);
        }
        String path = sb.substring(0, sb.length() - 1);
        return ResourceAssist.class.getResourceAsStream(path);
    }
}
