package streetsim.business.exceptions;

/**
 * wird geworfen, wenn ein Auto platziert werden möchte
 * wo noch gar kein Strassenabschnitt platziert worden ist
 */
public class KeinAbschnittException extends RuntimeException {

    public KeinAbschnittException(String message) {
        super(message);
    }
}
