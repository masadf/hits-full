package itmo.serverhitsapp.exceptions;

public class UsernameExistException extends AuthException {
    public UsernameExistException(String message) {
        super(message);
    }
}
