package itmo.serverhitsapp.exceptions;

public class IncorrectUserCredentialsException extends AuthException {
    public IncorrectUserCredentialsException(String message) {
        super(message);
    }
}
