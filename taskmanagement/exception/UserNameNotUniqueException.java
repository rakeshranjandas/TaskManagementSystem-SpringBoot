package taskmanagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class UserNameNotUniqueException extends RuntimeException {
    public UserNameNotUniqueException() {
        super("User name is already exists!");
    }
}

