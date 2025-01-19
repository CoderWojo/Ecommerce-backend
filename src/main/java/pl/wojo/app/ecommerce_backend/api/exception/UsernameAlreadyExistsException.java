package pl.wojo.app.ecommerce_backend.api.exception;

public class UsernameAlreadyExistsException extends Exception{
    public UsernameAlreadyExistsException(String message) {
        super(message);
    }
}
