package taskmanagement.repository;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import taskmanagement.entity.Account;
import taskmanagement.entity.Task;

import java.util.List;

@Repository
public interface TasksRepository extends JpaRepository<Task, Long>, JpaSpecificationExecutor<Task> {
    List<Task> findByOrderByIdDesc();
    List<Task> findByAuthorOrderByIdDesc(Account account);

//    @Query("""
//        SELECT t FROM Task t
//        JOIN FETCH t.author r
//        LEFT JOIN FETCH t.assignee a
//        LEFT JOIN FETCH t.comments c
//        WHERE (:author IS NULL OR t.author.username = :author)
//          AND (:assignee IS NULL OR t.assignee.username = :assignee)
//        ORDER BY t.id DESC
//    """)
//    List<Task> findAllByAuthorAndAssignee(
//            @Param("author") String author,
//            @Param("assignee") String assignee
//    );

}
