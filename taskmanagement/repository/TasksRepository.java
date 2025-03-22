package taskmanagement.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import taskmanagement.entity.Account;
import taskmanagement.entity.Task;

import java.util.List;

@Repository
public interface TasksRepository extends CrudRepository<Task, Long>, PagingAndSortingRepository<Task, Long> {
    List<Task> findByOrderByIdDesc();
    List<Task> findByAuthorOrderByIdDesc(Account account);
}
