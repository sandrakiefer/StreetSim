package streetsim.business.exceptions;

/**
 * wird beim Hinzufügen eines Strassenabschnitts oder Autos
 * auf belegter Postion geworfen
 */
public class SchonBelegtException extends RuntimeException {

    public SchonBelegtException(String message) {
        super(message);
    }
}
