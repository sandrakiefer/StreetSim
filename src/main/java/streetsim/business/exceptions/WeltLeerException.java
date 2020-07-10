package streetsim.business.exceptions;

/**
 * wird beim Starten einer Simulation geworfen,
 * wenn noch keine Elemente ins Straßennetz hinzugefügt worden sind (Straßennetz leer)
 */
public class WeltLeerException extends RuntimeException {

    public WeltLeerException(String message) {
        super(message);
    }
}
