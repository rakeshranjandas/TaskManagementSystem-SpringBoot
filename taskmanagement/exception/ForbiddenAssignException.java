package taskmanagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class ForbiddenAssignException extends RuntimeException {
    public ForbiddenAssignException() {
        super("Only authors are allowed to assign.");
    }
}
