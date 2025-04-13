package taskmanagement.mapper;

import org.springframework.stereotype.Component;
import taskmanagement.dto.CommentDTO;
import taskmanagement.entity.Comment;

import java.util.ArrayList;
import java.util.List;

@Component
public class CommentMapper {

    public CommentDTO toDTO(Comment comment) {
        return new CommentDTO(
            Long.toString(comment.getId()),
            Long.toString(comment.getTask().getId()),
            comment.getText(),
            comment.getAuthor().getUsername()
        );
    }

    public List<CommentDTO> toDTO(List<Comment> comments) {
        List<CommentDTO> commentsDTO = new ArrayList<>();

        for (Comment comment: comments) {
            commentsDTO.add(toDTO(comment));
        }

        return commentsDTO;
    }

}
