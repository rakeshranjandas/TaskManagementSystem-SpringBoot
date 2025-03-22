package taskmanagement.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import taskmanagement.entity.Account;

import java.util.Optional;

@Repository
public interface AccountsRepository extends CrudRepository<Account, Long> {
    Optional<Account> findByUsernameIgnoreCase(String username);
}
