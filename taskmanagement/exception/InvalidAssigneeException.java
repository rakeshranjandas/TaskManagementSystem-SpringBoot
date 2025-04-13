package taskmanagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class InvalidAssigneeException extends RuntimeException {
    public InvalidAssigneeException() {
        super("Assignee is invalid.");
    }
}
