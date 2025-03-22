package taskmanagement.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import taskmanagement.dto.TaskDTO;
import taskmanagement.entity.Task;
import taskmanagement.request.CreateTaskRequest;
import taskmanagement.service.TasksService;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TasksController {

    @Autowired
    private TasksService tasksService;

    @GetMapping
    public ResponseEntity<List<TaskDTO>> getTasks(@RequestParam(name = "author", required = false) String author) {
        List<TaskDTO> tasks = null;

        if (author == null) {
            tasks = tasksService.getAll();
        } else {
            tasks =  tasksService.getAllByAuthor(author);
        }

        return new ResponseEntity(tasks, HttpStatus.OK);
    }

    @PostMapping
    public TaskDTO createTask(@Valid @RequestBody CreateTaskRequest createTaskRequest) {
        return tasksService.create(createTaskRequest);
    }
}
