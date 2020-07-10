package streetsim.business.exceptions;

/**
 * Wird geworfen, wenn ein Auto platziert werden möchte
 * wo noch gar kein Straßenabschnitt platziert worden ist
 */
public class KeinAbschnittException extends RuntimeException {

    public KeinAbschnittException(String message) {
        super(message);
    }
}
