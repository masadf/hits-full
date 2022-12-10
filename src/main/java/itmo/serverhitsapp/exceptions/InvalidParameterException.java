package itmo.serverhitsapp.exceptions;

public class InvalidParameterException extends ServiceException {
    public InvalidParameterException(String message) {
        super(message);
    }
}
