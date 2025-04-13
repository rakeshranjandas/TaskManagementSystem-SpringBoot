package taskmanagement.dto;

import com.jetbrains.cef.JdkEx;

public class TaskWithTotalCommentsDTO extends TaskDTO {

    private int total_comments;

    public TaskWithTotalCommentsDTO(
            String id,
            String title,
            String description,
            String status,
            String author,
            String assignee,
            int totalComments
    ) {
        super(id, title, description, status, author, assignee);
        this.total_comments = totalComments;
    }

    public TaskWithTotalCommentsDTO(TaskDTO taskDTO, int totalComments) {
        this(
                taskDTO.getId(),
                taskDTO.getTitle(),
                taskDTO.getDescription(),
                taskDTO.getStatus(),
                taskDTO.getAuthor(),
                taskDTO.getAssignee(),
                totalComments
        );
    }

    public int getTotal_comments() {
        return total_comments;
    }

    public void setTotal_comments(int total_comments) {
        this.total_comments = total_comments;
    }
}
