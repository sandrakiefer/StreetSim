package streetsim.business.exceptions;

/**
 * Wird beim Laden eines Straßennetzes geworfen,
 * falls eine korrupte/nicht parsebare Datei ausgewählt wurde
 */
public class DateiParseException extends RuntimeException {

    public DateiParseException(String message, Throwable cause) {
        super(message, cause);
    }

}
