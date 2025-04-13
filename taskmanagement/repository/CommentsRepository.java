package taskmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import taskmanagement.entity.Comment;
import taskmanagement.entity.Task;

import java.util.List;

@Repository
public interface CommentsRepository extends JpaRepository<Comment, Long> {
    @Query("""
        SELECT c FROM Comment c
        JOIN FETCH c.author r
        WHERE c.task = :task
        ORDER BY c.id DESC
    """)
    List<Comment> findByTaskOrderByIdDesc(@Param("task") Task task);
}
