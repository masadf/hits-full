package itmo.serverhitsapp.exceptions;

public class SessionCountException extends AuthException {
    public SessionCountException(String message) {
        super(message);
    }
}
