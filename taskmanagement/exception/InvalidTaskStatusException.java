package taskmanagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidTaskStatusException extends RuntimeException {
    public InvalidTaskStatusException() {
        super("Invalid task status provided. Should be \"CREATED\", \"IN_PROGRESS\" or \"COMPLETED\"");
    }
}
