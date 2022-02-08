package ExceptionsHandler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class PriceAPIException extends RuntimeException {
    private HttpStatus status;
    private String message;

    public PriceAPIException(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public PriceAPIException(String message, HttpStatus status, String message1) {
        super(message);
        this.status = status;
        this.message = message1;
    }

    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
