package taskmanagement.service;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import taskmanagement.dto.TaskDTO;
import taskmanagement.entity.Account;
import taskmanagement.entity.Comment;
import taskmanagement.entity.Task;
import taskmanagement.enums.TaskStatus;
import taskmanagement.exception.ForbiddenAssignException;
import taskmanagement.exception.InvalidAssigneeException;
import taskmanagement.exception.InvalidTaskStatusException;
import taskmanagement.exception.TaskNotFoundException;
import taskmanagement.mapper.TaskMapper;
import taskmanagement.repository.AccountsRepository;
import taskmanagement.repository.TasksRepository;
import taskmanagement.request.AddCommentRequest;
import taskmanagement.request.AssignTaskRequest;
import taskmanagement.request.CreateTaskRequest;
import taskmanagement.request.UpdateStatusRequest;
import taskmanagement.security.AccountAdapter;

import java.util.List;
import java.util.Optional;

@Service
public class TasksService {

    @Autowired
    private TasksRepository tasksRepository;

    @Autowired
    private TaskMapper taskMapper;

    @Autowired
    private AccountsRepository accountsRepository;

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

    public List<TaskDTO> getAll(String author, String assignee) {

        List<Task> tasks = tasksRepository.findAllByAuthorAndAssignee(
                author == null ? null: author.toLowerCase(),
                assignee == null ? null: assignee.toLowerCase()
        );

        return taskMapper.toDTO(tasks);
    }

    public List<TaskDTO> getAllQBE(String author, String assignee) {
        Task probe = createProbeTask(author, assignee);
        System.err.println(probe);

        ExampleMatcher matcher = ExampleMatcher
                .matchingAll()
                .withIgnoreNullValues();

        Example<Task> example = Example.of(probe, matcher);
        List<Task> tasks = tasksRepository.findAll(example, Sort.by(Sort.Direction.DESC, "id"));

        return taskMapper.toDTO(tasks);
    }

    private Task createProbeTask(String author, String assignee) {
        Task probe = new Task();

        if (author != null) {
            Optional<Account> authorAccount = accountsRepository.findByUsernameIgnoreCase(author);
            if (authorAccount.isPresent()) {
                probe.setAuthor(authorAccount.get());
            }
        }

        if (assignee != null) {
            Optional<Account> assigneeAccount = accountsRepository.findByUsernameIgnoreCase(assignee);
            if (assigneeAccount.isPresent()) {
                probe.setAssignee(assigneeAccount.get());
            }
        }

        return probe;
    }

    public List<TaskDTO> getAll() {
        Iterable<Task> tasks = tasksRepository.findByOrderByIdDesc();

        return taskMapper.toDTO(tasks);
    }

    public List<TaskDTO> getAllByAuthor(String author) {
        Optional<Account> authorAccount = accountsRepository.findByUsernameIgnoreCase(author);

        if (authorAccount.isEmpty()) {
            return List.of();
        }

        List<Task> tasksByAuthor = tasksRepository.findByAuthorOrderByIdDesc(authorAccount.get());

        return taskMapper.toDTO(tasksByAuthor);
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

        Task task = taskOptional.get();
        task.addComment(comment);

        System.err.println(task);

        tasksRepository.save(task);
    }
}
