package taskmanagement.entity;

import jakarta.persistence.*;
import taskmanagement.enums.TaskStatus;

@Entity
@Table(name = "task")
public class Task {

    public static final String NO_ASSIGNEE = "none";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;

    private String description;

    @Enumerated(EnumType.STRING)
    private TaskStatus status = TaskStatus.CREATED;

    @ManyToOne
    @JoinColumn(name = "assignee")
    private Account assignee;

    @ManyToOne
    @JoinColumn(name = "author")
    private Account author;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public Account getAuthor() {
        return author;
    }

    public void setAuthor(Account author) {
        this.author = author;
    }

    public Account getAssignee() {
        return assignee;
    }

    public void setAssignee(Account assignee) {
        this.assignee = assignee;
    }

    public void removeAssignee() {
        this.assignee = null;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", assignee=" + assignee +
                ", author=" + author +
                '}';
    }
}
