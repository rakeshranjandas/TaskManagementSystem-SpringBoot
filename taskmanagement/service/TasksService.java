package taskmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import taskmanagement.dto.TaskDTO;
import taskmanagement.entity.Account;
import taskmanagement.entity.Task;
import taskmanagement.mapper.TaskMapper;
import taskmanagement.repository.AccountsRepository;
import taskmanagement.repository.TasksRepository;
import taskmanagement.request.CreateTaskRequest;
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
}
