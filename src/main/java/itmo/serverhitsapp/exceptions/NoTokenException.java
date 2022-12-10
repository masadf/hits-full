package itmo.serverhitsapp.exceptions;

public class NoTokenException extends AuthException {
    public NoTokenException(String message) {
        super(message);
    }
}
