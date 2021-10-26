package au.com.ausmash.rest.exception;

public abstract class HttpException extends RuntimeException {
    public HttpException(String message) {
        super(message);
    }
}