package taskmanagement.mapper;

import org.springframework.stereotype.Component;
import taskmanagement.dto.TaskDTO;
import taskmanagement.dto.TaskWithTotalCommentsDTO;
import taskmanagement.entity.Task;

import java.util.ArrayList;
import java.util.List;

@Component
public class TaskMapper {

    public TaskDTO toDTO(Task task) {
        return new TaskDTO(
                Long.toString(task.getId()),
                task.getTitle(),
                task.getDescription(),
                task.getStatus().name(),
                task.getAuthor().getUsername(),
                task.getAssignee() == null ? Task.NO_ASSIGNEE: task.getAssignee().getUsername()
        );
    }

    public List<TaskDTO> toDTO(Iterable<Task> tasks) {
        List<TaskDTO> tasksDTO = new ArrayList<>();

        for (Task task: tasks) {
            tasksDTO.add(toDTO(task));
        }

        return tasksDTO;
    }

    public TaskWithTotalCommentsDTO toDTOWithTotalComments(Task task) {
        return new TaskWithTotalCommentsDTO(
                toDTO(task),
                task.getComments().size()
        );
    }

    public List<TaskWithTotalCommentsDTO> toDTOWithTotalComments(List<Task> tasks) {
        List<TaskWithTotalCommentsDTO> tasksDTO = new ArrayList<>();

        for (Task task: tasks) {
            tasksDTO.add(toDTOWithTotalComments(task));
        }

        return tasksDTO;
    }
}
