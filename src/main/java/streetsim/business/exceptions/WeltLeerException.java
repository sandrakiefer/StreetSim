package streetsim.business.exceptions;

/**
 * wird beim Speichern eines Entwurfs oder beim Straten einer Simulation geworfen,
 * wenn noch keine Elemente ins Strassennetz hinzugefügt worden sind (Strassennetz leer)
 */
public class WeltLeerException extends RuntimeException {

    public WeltLeerException(String message) {
        super(message);
    }
}
