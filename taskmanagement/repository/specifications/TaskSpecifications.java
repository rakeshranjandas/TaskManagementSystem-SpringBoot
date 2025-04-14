package taskmanagement.repository.specifications;

import org.springframework.data.jpa.domain.Specification;
import taskmanagement.entity.Account;
import taskmanagement.entity.Task;

import java.util.Optional;

public class TaskSpecifications {
    public static Specification<Task> hasAuthor(Optional<Account> author) {
        if (author.isEmpty()) {
            return returnNothing();
        }

        return (root, query, cb) ->
                author == null ? null : cb.equal(root.get("author"), author.get());
    }

    public static Specification<Task> hasAssignee(Optional<Account> assignee) {
        if (assignee.isEmpty()) {
            return returnNothing();
        }

        return (root, query, cb) ->
                assignee == null ? null : cb.equal(root.get("assignee"), assignee.get());
    }

    public static Specification<Task> returnNothing() {
        return (root, query, cb) -> cb.disjunction();
    }

}