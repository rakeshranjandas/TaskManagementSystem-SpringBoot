package taskmanagement.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import taskmanagement.dto.TaskDTO;
import taskmanagement.entity.Task;
import taskmanagement.request.AssignTaskRequest;
import taskmanagement.request.CreateTaskRequest;
import taskmanagement.request.UpdateStatusRequest;
import taskmanagement.service.TasksService;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TasksController {

    @Autowired
    private TasksService tasksService;

    @GetMapping
    public ResponseEntity<List<TaskDTO>> getTasks(
            @RequestParam(name = "author", required = false) String author,
            @RequestParam(name = "assignee", required = false) String assignee
    ) {
        List<TaskDTO> tasks = tasksService.getAll(author, assignee);

        return new ResponseEntity(tasks, HttpStatus.OK);
    }

    @PostMapping
    public TaskDTO createTask(@Valid @RequestBody CreateTaskRequest createTaskRequest) {
        return tasksService.create(createTaskRequest);
    }

    @PutMapping("/{taskId}/assign")
    public TaskDTO assignTask(@PathVariable("taskId") Long taskId, @Valid @RequestBody AssignTaskRequest assignTaskRequest) {
        return tasksService.assign(taskId, assignTaskRequest);
    }

    @PutMapping("/{taskId}/status")
    public TaskDTO updateStatus(@PathVariable("taskId") Long taskId, @Valid @RequestBody UpdateStatusRequest updateStatusRequest) {
        return tasksService.updateStatus(taskId, updateStatusRequest);
    }
}
