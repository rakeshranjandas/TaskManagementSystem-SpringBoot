package taskmanagement.service;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import taskmanagement.dto.CommentDTO;
import taskmanagement.dto.TaskDTO;
import taskmanagement.dto.TaskWithTotalCommentsDTO;
import taskmanagement.entity.Account;
import taskmanagement.entity.Comment;
import taskmanagement.entity.Task;
import taskmanagement.enums.TaskStatus;
import taskmanagement.exception.ForbiddenAssignException;
import taskmanagement.exception.InvalidAssigneeException;
import taskmanagement.exception.InvalidTaskStatusException;
import taskmanagement.exception.TaskNotFoundException;
import taskmanagement.mapper.CommentMapper;
import taskmanagement.mapper.TaskMapper;
import taskmanagement.repository.AccountsRepository;
import taskmanagement.repository.CommentsRepository;
import taskmanagement.repository.TasksRepository;
import taskmanagement.repository.specifications.TaskSpecifications;
import taskmanagement.request.AddCommentRequest;
import taskmanagement.request.AssignTaskRequest;
import taskmanagement.request.CreateTaskRequest;
import taskmanagement.request.UpdateStatusRequest;
import taskmanagement.security.AccountAdapter;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class TasksService {

    @Autowired
    private TasksRepository tasksRepository;

    @Autowired
    private TaskMapper taskMapper;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private AccountsRepository accountsRepository;

    @Autowired
    private CommentsRepository commentsRepository;

    private Account getAccount() {
        AccountAdapter accountAdapter = (AccountAdapter) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        return accountAdapter.getAccount();
    }

    public TaskDTO create(CreateTaskRequest createTaskRequest) {
        Task task = new Task();
        task.setTitle(createTaskRequest.getTitle());
        task.setDescription(createTaskRequest.getDescription());
        task.setAuthor(getAccount());

        Task newTask = tasksRepository.save(task);

        return taskMapper.toDTO(newTask);
    }

    public List<TaskWithTotalCommentsDTO> getAll(String author, String assignee) {

        Specification<Task> spec = Specification.where(null); // Start a specification

        if (author != null) {
            Optional<Account> authorAccount = accountsRepository.findByUsernameIgnoreCase(author);
            spec = spec.and(TaskSpecifications.hasAuthor(authorAccount));
        }

        if (assignee != null) {
            Optional<Account> authorAccount = accountsRepository.findByUsernameIgnoreCase(assignee);
            spec = spec.and(TaskSpecifications.hasAssignee(authorAccount));
        }

        List<Task> tasks = tasksRepository.findAll(spec, Sort.by(Sort.Direction.DESC, "id"));

        return taskMapper.toDTOWithTotalComments(tasks);
    }

    public TaskDTO assign(Long taskId, AssignTaskRequest assignTaskRequest) {

        Optional<Task> taskOptional = tasksRepository.findById(taskId);
        if (taskOptional.isEmpty()) {
            throw new TaskNotFoundException();
        }

        Task task = taskOptional.get();
        Account loggedAccount = getAccount();

        if (!loggedAccount.equals(task.getAuthor())) {
            throw new ForbiddenAssignException();
        }

        String assignee = assignTaskRequest.getAssignee();
        if (assignee.equals(Task.NO_ASSIGNEE)) {
            task.removeAssignee();

        } else {
            Optional<Account> accountOptional = accountsRepository.findByUsernameIgnoreCase(assignee);
            if (accountOptional.isEmpty()) {
                throw new InvalidAssigneeException();
            }

            Account account = accountOptional.get();
            task.setAssignee(account);
        }

        Task savedTask = tasksRepository.save(task);

        return taskMapper.toDTO(savedTask);
    }

    public TaskDTO updateStatus(Long taskId, UpdateStatusRequest updateStatusRequest) {
        Optional<Task> taskOptional = tasksRepository.findById(taskId);
        if (taskOptional.isEmpty()) {
            throw new TaskNotFoundException();
        }

        Task task = taskOptional.get();
        Account loggedAccount = getAccount();

        if (!loggedAccount.equals(task.getAuthor()) && !loggedAccount.equals(task.getAssignee())) {
            throw new ForbiddenAssignException();
        }

        try {
            TaskStatus taskStatus = TaskStatus.fromValue(updateStatusRequest.getStatus());
            task.setStatus(taskStatus);

            Task savedTask = tasksRepository.save(task);
            return taskMapper.toDTO(savedTask);

        } catch (IllegalArgumentException e) {
            throw new InvalidTaskStatusException();
        }

    }

    public void addComment(Long taskId, @Valid AddCommentRequest addCommentRequest) {
        Optional<Task> taskOptional = tasksRepository.findById(taskId);
        if (taskOptional.isEmpty()) {
            throw new TaskNotFoundException();
        }

        Comment comment = new Comment();
        comment.setText(addCommentRequest.getText());
        comment.setAuthor(getAccount());

        Task task = taskOptional.get();
        task.addComment(comment);

        tasksRepository.save(task);
    }

    public List<CommentDTO> getComments(Long taskId) {
        Optional<Task> taskOptional = tasksRepository.findById(taskId);
        if (taskOptional.isEmpty()) {
            throw new TaskNotFoundException();
        }

        Task task = taskOptional.get();
        List<Comment> comments = commentsRepository.findByTaskOrderByIdDesc(task);

        return commentMapper.toDTO(comments);
    }
}
