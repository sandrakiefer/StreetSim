package streetsim.business.exceptions;

/**
 * Wird beim Hinzufügen eines Straßenabschnitts oder Autos
 * auf belegter Postion geworfen
 */
public class SchonBelegtException extends RuntimeException {

    public SchonBelegtException(String message) {
        super(message);
    }
}
