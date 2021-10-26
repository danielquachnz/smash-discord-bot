package au.com.ausmash.rest.exception;

public class ResourceNotFoundException extends HttpException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}