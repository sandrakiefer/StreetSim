package streetsim.business.exceptions;

/**
 * wird beim Laden eines Strassennetzes geworfen,
 * falls eine korrupte/nicht parsebare Datei ausgewählt wurde
 */
public class DateiParseException extends RuntimeException {
    public DateiParseException() {

    }

    public DateiParseException(String message) {
        super(message);
    }

    public DateiParseException(String message, Throwable cause) {
        super(message, cause);
    }

}
